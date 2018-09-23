package com.iiitd.medlabs;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.iiitd.medlabs.backend.ProfileDatabaseHandler;

public class GcmIntentService extends IntentService {
	
	   ProfileDatabaseHandler profileDb;
	   static final String TAG = "HK-GCM";
	   public static final int NOTIFICATION_ID = 1;
	   private NotificationManager mNotificationManager;
	   NotificationCompat.Builder builder;
	   int myrepId;
	   String mylabId, myemailId, mylabName, myreportName, myreportLink, mytime;
	   
	   public GcmIntentService() {
	       super("GcmIntentService");
	   }

	   @Override
	   protected void onHandleIntent(Intent intent) {
	       Bundle extras = intent.getExtras();
	       GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
	       String messageType = gcm.getMessageType(intent);

	       if (!extras.isEmpty()) {
	           
	           if (GoogleCloudMessaging.
	                   MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
	           	sendNotification("Send : " + extras.toString());
	           } else if (GoogleCloudMessaging.
	                   MESSAGE_TYPE_DELETED.equals(messageType)) {
	           	sendNotification("Deleted messages on server: " + extras.toString());
	           } else if (GoogleCloudMessaging.
	                   MESSAGE_TYPE_MESSAGE.equals(messageType)) {
	        	   myrepId = Integer.parseInt((String) intent.getExtras().get("KEY_REPID"));
	        	   mylabId  = (String) intent.getExtras().get("KEY_LABID");
	        	   myemailId = (String) intent.getExtras().get("KEY_EMAILID");
	        	   mylabName = (String) intent.getExtras().get("KEY_LABNAME");
	        	   myreportName = (String) intent.getExtras().get("KEY_REPORT_NAME");
	        	   myreportLink = (String) intent.getExtras().get("KEY_REPORT_LINK");
	        	   mytime = (String) intent.getExtras().get("KEY_TIME");
	           }
	           Log.i(TAG, "Received: " + extras.toString());
	           //ShowToastInIntentService("Location Changed");
	           	sendNotification("New Report Arrived From: " +  intent.getExtras().getString("KEY_LABNAME"));
	           	
	           	profileDb = new ProfileDatabaseHandler(this);
	           	profileDb.insertEnrty( myrepId, myemailId, mylabId, mylabName, myreportName, myreportLink, mytime);
	           	           	
	       		
	            Log.i(TAG, "Received: " + extras.toString());
	       }
	       GcmBroadcastReceiver.completeWakefulIntent(intent);
	   }
	   public void ShowToastInIntentService(final String sText)
	   {  final Context MyContext = this;
	      new Handler(Looper.getMainLooper()).post(new Runnable()
	      {  @Override public void run()
	         {  Toast toast1 = Toast.makeText(MyContext, sText, Toast.LENGTH_LONG);
	            toast1.show(); 
	         }
	      });
	   };
	   
	   private void sendNotification(String msg) {
		   Log.i("HK", "fn1");
		   Log.i("HK", msg);
	   	long[] pat = { 0, 400, -1 };
		   //long[] pat = { 5000 };
	   	int icon = R.drawable.med_labs_icon;
	       mNotificationManager = (NotificationManager)
	               this.getSystemService(Context.NOTIFICATION_SERVICE);
	       Log.i("HK", "fn2");
	       
	       Intent intent = new Intent();
			intent.setAction(".DownloadReports");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
	       
	       
	       PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	               intent, 0);
	       Log.i("HK", "fn3");
	       NotificationCompat.Builder mBuilder =
	               new NotificationCompat.Builder(this)
	       .setSmallIcon(icon)
	       .setContentTitle("Med Labs")
	       .setStyle(new NotificationCompat.BigTextStyle()
	       .bigText(msg))
	       .setVibrate(pat)
	       .setContentText(msg);
	       mBuilder.setContentIntent(contentIntent);
	       mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	   }
	}
	
