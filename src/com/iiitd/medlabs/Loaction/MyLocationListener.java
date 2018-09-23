package com.iiitd.medlabs.Loaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iiitd.medlabs.backend.DatabaseHandler;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyLocationListener implements LocationListener{

	final String TAG = "LocManager LocLis";
	Context ctx;
	public Double longitude, latitude;
	public Location obtainedLocation;
	
	public MyLocationListener(Context context) {
		this.ctx = context;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		if(location!= null){
			longitude = location.getLongitude();
			latitude = location.getLatitude();
			obtainedLocation = location;
			//Toast.makeText(ctx, "Location is "+location.getLatitude()+" , "+location.getLongitude()+" , "+location.getAltitude(), Toast.LENGTH_LONG).show();
			Log.d("HK","Async to get loc");
			new HttpGetDistance().execute(ctx);
		}
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.i(TAG, provider + "is enabled");
		//Toast.makeText(ctx, provider+"is enabled", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.i(TAG, provider + "is diabled");
		//Toast.makeText(ctx, provider+"is disabled", Toast.LENGTH_LONG).show();
	}
	
	
	
	
	
	
	private class HttpGetDistance extends AsyncTask<Context, Void, List<String>> {

		private Context c;
		//private Activity myAct;
		AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<String> doInBackground(Context... params) {
			//SharedPreferences prefs = params[0].getSharedPreferences("Login", Context.MODE_PRIVATE);
			//Float myLat = prefs.getFloat("latitude", (float) 0.0);
		    //Float myLong = prefs.getFloat("longitude", (float) 0.0);
		    if(latitude==0.0 || longitude==0.0){
		    	Log.d("HK","null null lat long");
		    	return null;
		    }
		    
		    String url = "http://medlabs.tk/nearby?lat="+latitude.toString()+"&long="+ longitude.toString();
			
		    
			c= params[0];
			HttpGet request = new HttpGet(url);
			JSONResponseHandler responseHandler = new JSONResponseHandler();
			try {
				return httpClient.execute(request, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.d("HK","output null");
			return null;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			Log.d("HK","starting to fill db");
			if (null != httpClient)
				httpClient.close();
			
			String as = (String) result.toString().subSequence(1,result.toString().length()-1 );
			DatabaseHandler db = new DatabaseHandler(c);
			
					try {
						JSONArray responseObject =  new JSONArray(as.toString());
						for (int i = 0; i < responseObject.length(); i++) {
						    JSONObject row = responseObject.getJSONObject(i);
						    int id =  Integer.parseInt(row.get("labId").toString());
						    String dis= row.getString("distance");
						    db.updateDistance(id, dis);
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
			
		}
	}
	private class JSONResponseHandler implements ResponseHandler<List<String>> {

		
		@Override
		public List<String> handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			
			
			List<String> result = new ArrayList<String>();
			
			String JSONResponse = new BasicResponseHandler().handleResponse(response);
			result.add(JSONResponse.toString());
			Log.d("HK",result.toString());
			return result;
		}
	}

}
