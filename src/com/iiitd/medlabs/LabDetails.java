package com.iiitd.medlabs;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.iiitd.medlabs.R;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;

public class LabDetails extends ActionBarActivity {

	Button couponButton;
	TextView tv;
	int labId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lab_details);
		couponButton = (Button) findViewById(R.id.buttonCoupon);
		tv = (TextView) findViewById(R.id.textViewCoupon);
		Intent intent = getIntent();
		Bundle newbundle = intent.getBundleExtra("bundle");
		
		TextView tv1 = (TextView)findViewById(R.id.textView1);
		TextView tv2 = (TextView)findViewById(R.id.textView2);
		TextView tv3 = (TextView)findViewById(R.id.textView3);
		TextView tv4 = (TextView)findViewById(R.id.textView4);
		TextView tv5 = (TextView)findViewById(R.id.textView5);
		
		labId = newbundle.getInt("labId"); 
		String name = newbundle.getString("Name");
		String distance = newbundle.getString("Distance");
		String address = newbundle.getString("Address");
		String test = newbundle.getString("Test");
		String details = newbundle.getString("Details");
		//String location = newbundle.getString("Location");
		//String pincode = newbundle.getString("Pincode");
		//String city = newbundle.getString("city");
	
		
		
		tv1.setText(name);
		tv2.setText(distance);
		tv3.setText(address);
		tv4.setText(test);
		tv5.setText(details);
		
		couponButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new CouponHTTPGet().execute();
			}
		});
		
	}
	
	private class CouponHTTPGet extends AsyncTask<Void, Void, String>{
		
			
			private static final String URL_address = "http://medlabs.tk/generateCoupon";
			
			AndroidHttpClient httpClientObj = AndroidHttpClient.newInstance("");
			
			@Override
			protected String doInBackground(Void... params) {
				
				SharedPreferences ePrefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
				String rEmail = ePrefs.getString("email", null);
				
				String APIrequest = URL_address+ "?emailId=" +rEmail +"&labId=" + labId;
				HttpGet request = new HttpGet(APIrequest);
				JSONParser responseHandler = new JSONParser();
				try {
					String response = httpClientObj.execute(request, responseHandler);
					return response;
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				return null;
			}
			 @Override
			protected void onPostExecute(String result) {
				 if(httpClientObj != null){
					httpClientObj.close();
				}
				tv.setText(result);
			}
		
			 private class JSONParser implements ResponseHandler<String>{

				 @Override
				 public String handleResponse(HttpResponse response)
						 throws ClientProtocolException, IOException {
					 String coupon = null;
					 
					 
					 String responseString = new BasicResponseHandler().handleResponse(response);
					 JSONObject receivedJson;
					 try {
						receivedJson = (JSONObject) new JSONTokener(responseString).nextValue();
						coupon = (String) receivedJson.get("Coupon");
						
						
						Log.d("HK",coupon);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					 return coupon;
				 }
			 }
		}
	
}
