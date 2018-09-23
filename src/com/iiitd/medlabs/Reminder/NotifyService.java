package com.iiitd.medlabs.Reminder;

import com.iiitd.medlabs.backend.ReminderDatabaseHandler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.iiitd.medlabs.R;

public class NotifyService extends Service {
	 
    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }
 
    private static final int NOTIFICATION = 123;
    public static final String INTENT_NOTIFY = "com.iiitd.medlabs.INTENT_NOTIFY";
    private NotificationManager mNM;
 
    @Override
    public void onCreate() {
        Log.d("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LocalService", "Received start id " + startId + ": " + intent);
         
        if(intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification(intent);
         
        return START_NOT_STICKY;
    }
 
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
 
    private final IBinder mBinder = new ServiceBinder();
 
    private void showNotification(Intent myIntent) {
    	Log.d("HK","inside notofication");
    	ReminderDatabaseHandler dbR = new ReminderDatabaseHandler(this);
    	CharSequence title = "Med Labs Reminder";
    	int icon = R.drawable.med_labs_icon;
    	CharSequence text = "Your appointment is today. Don't forget!.";
    	Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    	
    	NotificationCompat.Builder N_builder = new NotificationCompat.Builder(this)
    			.setContentTitle(title)
    		    .setContentText(text)
    		    .setSmallIcon(icon)
    			.setSound(soundUri, AudioManager.STREAM_NOTIFICATION);
    	
    	//Intent resultIntent = new Intent(this, SecondActivity.class);
    	Intent resultIntent = new Intent();
    	resultIntent.setAction(".SecondActivity");
    	resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	resultIntent.putExtra("passedMsg", myIntent.getExtras().getString("passedMsg"));
    	resultIntent.putExtra("passedHr", myIntent.getExtras().getString("passedHr"));
    	resultIntent.putExtra("passedMin", myIntent.getExtras().getString("passedMin"));
    	
    	resultIntent.putExtra("passedDay", myIntent.getExtras().getString("passedDay"));
    	resultIntent.putExtra("passedMonth", myIntent.getExtras().getString("passedMonth"));
    	resultIntent.putExtra("passedYear", myIntent.getExtras().getString("passedYear"));
    	
		//startActivity(intent);
    	
    	PendingIntent resultPendingIntent =
    		    PendingIntent.getActivity(
    		    this,
    		    0,
    		    resultIntent,
    		    PendingIntent.FLAG_UPDATE_CURRENT
    		);
    	
    	int requestCode = myIntent.getExtras().getInt("requestCode"); 
    	
    	Log.d("HK",Integer.toString(requestCode));
    	dbR.deleteReport(Integer.toString(requestCode));
    	
    	N_builder.setContentIntent(resultPendingIntent);
    	
    	mNM.notify(NOTIFICATION, N_builder.build());
    	
    }
}
