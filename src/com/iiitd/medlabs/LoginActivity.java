package com.iiitd.medlabs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.iiitd.medlabs.Profile.ProfileDetails;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

public class LoginActivity extends ActionBarActivity implements MyEventListener {
	ProfileDetails userProfile = new ProfileDetails();
	protected static final int PICK_ACCOUNT_REQUEST = 10;
	static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;
	static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1002;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	String ACCESS_TOKEN = null;

	private static final String Profile_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
	private static final String email_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
	private static final String SCOPE = "oauth2:" + Profile_SCOPE + " " + email_SCOPE;
	
	String chosenAccount = null;
		
	Button signGoogleButton;
	TextView tv1;
	
	/* Variables for Push Notifications */
	Context context;
	GoogleCloudMessaging gcm;
	static final String TAG2 = "harryPush";
	public static final String PROPERTY_REG_ID = "registration_id";
    String regid;
    //String SENDER_ID = "331807951404";
    String SENDER_ID = "76480130894";
	/* Variables for Push Notifications ends*/
	
	static final String TAG = "harryOauth";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		signGoogleButton = (Button) findViewById(R.id.buttonSignGoogle);
		
		signGoogleButton.setOnClickListener(new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			Intent googlePicker =AccountPicker.newChooseAccountIntent(null,null,new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},false,null,null,null,null) ;
			startActivityForResult(googlePicker,PICK_ACCOUNT_REQUEST);
		}
	});
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_ACCOUNT_REQUEST && resultCode == RESULT_OK) {
				// For google accounts Account name is same as the username
			chosenAccount = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
	            signInGoogleOauth();
	        } else if (resultCode == RESULT_CANCELED) {
	            Toast.makeText(this, "Pick an Account to Sign-in", Toast.LENGTH_SHORT).show();
	        }
	  else if ((requestCode == REQUEST_CODE_RECOVER_FROM_AUTH_ERROR ||
	            requestCode == REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR)
	            && resultCode == RESULT_OK) {
	        // Receiving a result that follows a GoogleAuthException, try auth again
		  signInGoogleOauth();
	    }
	}

	private void signInGoogleOauth() {
	    if (chosenAccount == null) {
	    	Toast.makeText(this, "Username is null; No authentication will be performed", Toast.LENGTH_SHORT).show();
	    } else {
	        if (isDeviceOnline()) {
	        	//Toast.makeText(this,"network on", Toast.LENGTH_SHORT).show();
	            new backgroundGoogleLogin(LoginActivity.this, chosenAccount, SCOPE,this).execute();
	            
	        } else {
	            Toast.makeText(this, "network off" , Toast.LENGTH_SHORT).show();
	        }
	    }
	    
	}
	
	public boolean isDeviceOnline() {
	    ConnectivityManager connectMgrObj = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    //boolean ans = helpObj.isConnectedToNetwork(connectMgrObj);
	    NetworkInfo networkInfo = connectMgrObj.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    } else {
	        return false;
	    }
	    /*if (ans == true){
	    	Toast.makeText(this,"network on", Toast.LENGTH_LONG).show();
	    	return true;
	    }
	    else{
	    	Toast.makeText(this,"network off", Toast.LENGTH_LONG).show();
	    	return false;
	    }*/    
	}
	
	private class backgroundGoogleLogin extends AsyncTask<Void, Void, String>{
		
		private MyEventListener callback;
		Activity mActivity;
	    String mScope;
	    String mEmail;

	    backgroundGoogleLogin(Activity activity, String name, String scope, MyEventListener cb) {
	        this.mActivity = activity;
	        this.mScope = scope;
	        this.mEmail = name;
	        callback = cb;
	    }
		
		
		@Override
		protected String doInBackground(Void... params) {
			try {
	            String token = fetchToken();
	            if (token != null) {
	            	ACCESS_TOKEN = token;
	                return token;
	               
	            }
	        } catch (IOException e) {
	        }
	        return null;
		}
		
		 protected String fetchToken() throws IOException {
		        try {
		            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
		        } catch (UserRecoverableAuthException userRecoverableException) {
		        	Log.i(TAG, "No valid Google Play Services APK found.");
		            ((LoginActivity) mActivity).handleException(userRecoverableException);
		        } catch (GoogleAuthException fatalException) {
		        	Log.i(TAG, "Some other type of unrecoverable exception has occurred.");
		        }
		        return null;
		    }
		 @Override
		protected void onPostExecute(String result) {
			 Log.d("HK","flow1");
			 if(callback != null) {
		            callback.onEventCompleted();
		        }
			 //new AndroidHTTPGet().execute(result);
			//tv1.setText(result);
			
		}
	}
	
	
	/**
	 * This method is a hook for background threads and async tasks that need to
	 * provide the user a response UI when an exception occurs.
	 */
	public void handleException(final Exception e) {
	    runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	            if (e instanceof GooglePlayServicesAvailabilityException) {
	               int statusCode = ((GooglePlayServicesAvailabilityException)e)
	                        .getConnectionStatusCode();
	                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
	                        LoginActivity.this,
	                        REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
	                dialog.show();
	            } else if (e instanceof UserRecoverableAuthException) {
	                Intent intent = ((UserRecoverableAuthException)e).getIntent();
	                startActivityForResult(intent,
	                        REQUEST_CODE_RECOVER_FROM_AUTH_ERROR);
	            }
	        }
	    });
	}


	@Override
	public void onEventCompleted() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		if(ACCESS_TOKEN != null)
		{
			new AndroidHTTPGet(LoginActivity.this).execute(ACCESS_TOKEN);
		}
		else{
			//Toast.makeText(getApplicationContext(), "Problem in registering account", Toast.LENGTH_SHORT).show();
		}
	}
		
	private class AndroidHTTPGet extends AsyncTask<String, Void, ProfileDetails>{
		
		private MyEventListener callback;
		AndroidHTTPGet(MyEventListener cb) {
	        callback = cb;
	    }
			
			private static final String URL_address = "https://www.googleapis.com/oauth2/v2/userinfo";
			
			AndroidHttpClient httpClientObj = AndroidHttpClient.newInstance("");
			
			@Override
			protected ProfileDetails doInBackground(String... params) {
				String APIrequest = URL_address+ "?access_token=" + params[0];
				HttpGet request = new HttpGet(APIrequest);
				JSONParser responseHandler = new JSONParser();
				try {
					ProfileDetails response = httpClientObj.execute(request, responseHandler);
					return response;
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				return null;
			}
			 @Override
			protected void onPostExecute(ProfileDetails result) {
				 if(httpClientObj != null){
					userProfile.name = result.name;
					userProfile.picture = result.picture;
					userProfile.email = result.email;
			       /*save in internal here but don't display it here*/
				     	URL url;
				     	File directory = null;
						try {
							url = new URL (result.picture);
							Bitmap bitmap;
							bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
							ContextWrapper cw = new ContextWrapper(getApplicationContext());
							
					        // path to /data/data/yourapp/app_data/imageDir
							directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
					        // Create imageDir
					        File mypath=new File(directory,"profilePic.jpg");
					        
					        FileOutputStream fos = null;
					        try {           

					            fos = new FileOutputStream(mypath);
					            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					            fos.close();
					        } catch (Exception e) {
					            e.printStackTrace();
					        }
						
							
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						}
						 catch (IOException e) {
							e.printStackTrace();
						}
				    SharedPreferences dirPrefs = getSharedPreferences("Directory", Context.MODE_PRIVATE);
				    SharedPreferences.Editor dirEditor = dirPrefs.edit();
				    dirEditor.putString("picturePath",directory.getAbsolutePath());
				    dirEditor.commit();
					httpClientObj.close();
				}
				 if(callback != null) {
			            callback.onEvent2Completed();
				 }
			}
		
			 private class JSONParser implements ResponseHandler<ProfileDetails>{

				 @Override
				 public ProfileDetails handleResponse(HttpResponse response)
						 throws ClientProtocolException, IOException {
					 ProfileDetails receivedProfile = new ProfileDetails();
					 
					 String responseString = new BasicResponseHandler().handleResponse(response);
					 JSONObject receivedJson;
					 try {
						receivedJson = (JSONObject) new JSONTokener(responseString).nextValue();
						receivedProfile.picture = (String) receivedJson.get("picture");
						receivedProfile.name = (String) receivedJson.get("name");
						receivedProfile.email = (String) receivedJson.get("email");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					 return receivedProfile;
				 }
			 }
		}
	
	
	private class RegisterHTTPGet extends AsyncTask<String, Void, ProfileDetails>{
		
		private MyEventListener callback;
		RegisterHTTPGet(MyEventListener cb) {
	        callback = cb;
	    }
			
			private static final String URL_address = "http://medlabs.tk/registerCustomer";
			
			AndroidHttpClient httpClientObj = AndroidHttpClient.newInstance("");
			
			@Override
			protected ProfileDetails doInBackground(String... params) {
				// take values
				SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
			    String rName = prefs.getString("name", null);
			    String rEmail = prefs.getString("email", null);
			    String rname = rName.replaceAll(" ", "+");			    
			    
			    String APIrequest = URL_address+ "?emailId="+ rEmail + "&authCode=" + ACCESS_TOKEN + "&name=" +rname;
			    //String APIrequest = URL_address+ "?emailId=test&authcode=test&name=test";
				HttpGet request = new HttpGet(APIrequest);
				Log.d("HK",APIrequest);
				Log.d("HK","requ gone");
				JSONParser2 responseHandler = new JSONParser2();
				try {
					ProfileDetails response = httpClientObj.execute(request, responseHandler);
					return response;
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				return null;
			}
			
			 @Override
			protected void onPostExecute(ProfileDetails result) {
				 if(httpClientObj != null){
					httpClientObj.close();
				}
			}
		
			 private class JSONParser2 implements ResponseHandler<ProfileDetails>{

				 @Override
				 public ProfileDetails handleResponse(HttpResponse response)
						 throws ClientProtocolException, IOException {
					 String responseString = new BasicResponseHandler().handleResponse(response);
					 JSONObject responseObject;
					try {
						responseObject = new JSONObject(responseString.toString());
					    String reply;
						reply = (String) responseObject.get("Created");
						Log.d("asa",reply);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						  
						
					 return null;
				 }
			 }
		}
	
	
	
	
	@Override
	public void onEventFailed() {
	}


	@Override
	public void onEvent2Completed() {
		final SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
	    Log.i(TAG, "Saving shared preferes");
		SharedPreferences.Editor editor = prefs.edit();
	    editor.putBoolean("is_logged_in", true);
	    editor.putString("name",userProfile.name);
	    editor.putString("picture",userProfile.picture);
	    editor.putString("email",userProfile.email);
	    Log.d("HK","email is"+userProfile.email);
	    editor.commit();
	    new RegisterHTTPGet(LoginActivity.this).execute(ACCESS_TOKEN);
	    // Registration for Push notification
	    context = getApplicationContext();

		   if (checkPlayServices()) {
	            gcm = GoogleCloudMessaging.getInstance(this);
	            regid = getRegistrationId(context);

	            if (regid.isEmpty()) {
	                new registerInBackground().execute();
	            }
	        } else {
	            Log.i(TAG2, "No valid Google Play Services APK found.");
	        }
	    
		Intent i = new Intent();
		i.setAction(".MainActivity");
		startActivity(i);
		finish();
		
	}
	
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            //Log.i(TAG, "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
	    
	    String registrationId = prefs.getString("registration_id", "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    return registrationId;
	}
	
	private class registerInBackground extends AsyncTask<Void, Void, String> {
			@Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(context);
	                }
	                regid = gcm.register(SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                final SharedPreferences pr = getSharedPreferences("Login", Context.MODE_PRIVATE);
	                SharedPreferences.Editor myEditor = pr.edit();
	                myEditor.putString(PROPERTY_REG_ID, regid);
	        	    myEditor.commit();
	                
	        	    // Sending registration Id to Mittal's server.
	                sendRegistrationIdToBackend();
	            }
	            catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	            }
	            return msg;
	        }
	        @Override
	        protected void onPostExecute(String msg) {
	        }
	}
	
	private void sendRegistrationIdToBackend() {
		final SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		String emailId = prefs.getString("email", "");
	    //String url = "http://192.168.2.3:8080/register/registerApp?emailId="+ emailId +"&appId="+ registrationId;
		String url = "http://medlabs.tk/registerApp?emailId="+ emailId +"&appId="+ registrationId;
	    new SaveRegistarionMittalServer().execute(url);
	}
	
	private class SaveRegistarionMittalServer extends AsyncTask<String, Void, Void>{
		
			AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

			@Override
			protected Void doInBackground(String... params) {
				String url = params[0];
				
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
		protected void onPostExecute(Void result) {
			 if(httpClient != null){
				 httpClient.close();
				}
		}

	}
	
	private class JSONResponseHandler implements ResponseHandler<Void> {

		
		@Override
		public Void handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			return null;
			
		}
	}

	@Override
	public void onEvent3Completed(int myVersion, int dbVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
