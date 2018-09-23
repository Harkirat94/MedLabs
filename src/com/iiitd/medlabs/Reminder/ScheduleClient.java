package com.iiitd.medlabs.Reminder;

import java.util.Calendar;

import com.iiitd.medlabs.backend.Reminder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class ScheduleClient {
 
    private ScheduleService mBoundService;
    private Context mContext;
    private boolean mIsBound;
 
    public ScheduleClient(Context context) {
        mContext = context;
    }
     
    
    public void doBindService() {
        mContext.bindService(new Intent(mContext, ScheduleService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
     
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((ScheduleService.ServiceBinder) service).getService();
        }
 
        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };
 
    public void setAlarmForNotification(Calendar c, Reminder rem){
    	Log.d("Hk","alarm1");
    	Log.d("HK-client", Integer.toString(rem.getId()));
        mBoundService.setAlarm(c ,rem);
        Log.d("Hk","alarm2");
    }
     
    public void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            mContext.unbindService(mConnection);
            mIsBound = false;
        }
    }
}
