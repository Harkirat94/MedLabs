package com.iiitd.medlabs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iiitd.medlabs.backend.Contact;
import com.iiitd.medlabs.backend.DatabaseHandler;
import com.iiitd.medlabs.backend.ProfileDatabaseHandler;
import com.iiitd.medlabs.backend.Test;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends Activity implements MyEventListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_splash_screen);
	  //new PrefetchData().execute(isRegistered);
	    new HttpVersion(this).execute(this);
	     
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	
	private class HttpGetLab extends AsyncTask<Context, Void, List<String>> {

		private Context c;
		String url = "http://medlabs.tk/getLabDetails";
		AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<String> doInBackground(Context... params) {
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
			return null;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			if (null != httpClient)
				httpClient.close();
			
			String as = (String) result.toString().subSequence(1,result.toString().length()-1 );
			DatabaseHandler db = new DatabaseHandler(c);
			
					try {
						JSONArray responseObject =  new JSONArray(as.toString());
						for (int i = 0; i < responseObject.length(); i++) {
						    JSONObject row = responseObject.getJSONObject(i);
						    int id =  Integer.parseInt(row.get("labId").toString());
						    db.addContact(new Contact(id,row.getString("name"),row.getString("address"),row.getString("location"),row.getString("city"),row.getString("pinCode"),row.getString("details"),null,row.getString("tests")));
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			        /**
			         * CRUD Operations
			         * */
			        // Reading all contacts        
			       /* Log.d("Reading: ", "Reading all contacts.."); 
			        List<Contact> contacts = db.getAllContacts();       
			        String log = "";
			        String aa = null ; 
			        for (Contact cn : contacts) {
			             aa = cn.getLocation();
			        	log += "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Location: " + cn.getLocation()
			            		+ " ,Tests: " + cn.getTests() + " ,Address: "  + cn.getAddress() + " ,Details: " + cn.getDetails() + " ,Distance: " + cn.getDistance() + " ,Pincode: " + cn.getPincode() + "\n";
			       
			        }
			        */
					 // Reading all contacts from location search       
				     /*   Log.d("Reading: ", "Reading all contacts.."); 
				        ArrayList<String> contacts = db.search("paschim vihar"); 
				        Log.d("hibol",contacts.get(0));
				        String log = "";
				        if (contacts.isEmpty())
				        {
				        	log = "NO MATCH FOUND";
				        }
				        else{
				        
				        	for (String cn : contacts) {
				             
				        		log += cn;
				        	}
				        }
			        Log.d("LOCATION",log);
	        Name.setText(log.toString());
	       
			*/
			
		}
	}
	
	


private class HttpGetTest extends AsyncTask<Context, Void, List<String>> {
	    private MyEventListener callback;
	    
	    HttpGetTest(MyEventListener cb) {
	        callback = cb;
	    }
		
	
		private Context c;
		String url = "http://medlabs.tk/getTests";
		AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<String> doInBackground(Context... params) {
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
			return null;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			if (null != httpClient)
				httpClient.close();
			
			String as = (String) result.toString().subSequence(1,result.toString().length()-1 );
			DatabaseHandler db = new DatabaseHandler(c);
			
			
					try {
						JSONArray responseObject =  new JSONArray(as.toString());
						for (int i = 0; i < responseObject.length(); i++) {
						    JSONObject row = responseObject.getJSONObject(i);
						    int id =  Integer.parseInt(row.get("testId").toString());
						    db.addTest(new Test(id,row.getString("name"),row.getString("description"),row.getString("requirements"),row.getString("labIds")));
						  
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					
					Log.d("Reading: ", "Reading all contacts.."); 
			        ArrayList<String> contacts = db.search("MRI"); 
			        String log = "";
			        if (contacts.isEmpty())
			        {
			        	log = "NO MATCH FOUND";
			        }
			        else{
			        
			        	for (String cn : contacts) {
			             
			        		log += cn;
			        	}
			        }
		        
					
					
					 // Reading all contacts from location search       
				        Log.d("Reading: ", "Reading all contacts.."); 
				        List<Test> tests = db.getAllTests();
				        String log1 = "";
				        if (tests.isEmpty())
				        {
				        	log1 = "NO TEST FOUND";
				        }
				        else{
				        
				        	for (Test tn : tests) {
				             
				        		log1 += "Id: "+tn.getID()+" ,Name: " + tn.getName() + "\n";
				        	
				        	}
				        }
				        if(callback != null) {
				            callback.onEventCompleted();
				        }
		}
	}
	
	private class JSONResponseHandler implements ResponseHandler<List<String>> {

		
		@Override
		public List<String> handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			
			
			List<String> result = new ArrayList<String>();
			
			String JSONResponse = new BasicResponseHandler()
					.handleResponse(response);
			result.add(JSONResponse.toString());
			
			return result;
		}
	}
////////////////////////////////////Getversion///////////////////////////////////////////////////////
	private class HttpVersion extends AsyncTask<Context, Void, List<String>> {
	    private MyEventListener callback;
	    
	    HttpVersion(MyEventListener cb) {
	        callback = cb;
	    }
		
	
		private Context c;
		String url = "http://medlabs.tk/getVersion";
		AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<String> doInBackground(Context... params) {
			c= params[0];
			
			HttpGet request = new HttpGet(url);
			JSONResponseHandlerVersion responseHandler = new JSONResponseHandlerVersion();
			try {
				return httpClient.execute(request, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			if (null != httpClient)
				httpClient.close();
			int myAppVersion=0;
			int id=0;
					try {
						JSONArray responseObject =  new JSONArray(result.toString());
						for (int i = 0; i < responseObject.length(); i++) {
						    JSONObject row = responseObject.getJSONObject(i);
						    id =  Integer.parseInt(row.get("version").toString());
						    
						    SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
						    myAppVersion = prefs.getInt("version", 0);
						   
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					 
				        if(callback != null) {
				            callback.onEvent3Completed(myAppVersion,id);
				        }
		}
	}
	
	private class JSONResponseHandlerVersion implements ResponseHandler<List<String>> {

		
		@Override
		public List<String> handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			
			
			List<String> result = new ArrayList<String>();
			
			String JSONResponse = new BasicResponseHandler()
					.handleResponse(response);
			result.add(JSONResponse.toString());
			
			return result;
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void onEventCompleted() {
		SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
	    boolean isRegistered = prefs.getBoolean("is_logged_in", false);
		Intent i = new Intent();
        if(isRegistered){
        	i.setAction(".MainActivity");
        	startActivity(i);
        }
        else{
        	i.setAction(".LoginActivity");
        	startActivity(i);
        }
        finish();
	}

	@Override
	public void onEvent2Completed() {
		
	}

	@Override
	public void onEventFailed() {
		
	}

	@Override
	public void onEvent3Completed(int myVersion, int dbVersion) {
		Log.d("HKHK", Integer.toString(myVersion));
		Log.d("HKHK", Integer.toString(dbVersion));
		
		
		 final SharedPreferences prefsV = getSharedPreferences("Login", Context.MODE_PRIVATE);
		 SharedPreferences.Editor editorV = prefsV.edit();
		 editorV.putInt("version", dbVersion);
		 editorV.commit();
		
		 if(myVersion==dbVersion){
			 /*DatabaseHandler db = new DatabaseHandler(this);
			    if( db.isEmptyLabs()){
			    	new HttpGetLab().execute(this);
			    }
			    if(db.isEmptyTests()){
			    	new HttpGetTest(this).execute(this);
			    }
			    else{
			    	onEventCompleted();
			    }*/
			 onEventCompleted();
		 }
		 else{
			 DatabaseHandler db = new DatabaseHandler(this);
			 db.clearDb();
			    new HttpGetLab().execute(this);
			 new HttpGetTest(this).execute(this);
			 onEventCompleted();
		 }
		
	}
    
}