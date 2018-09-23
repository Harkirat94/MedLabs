package com.iiitd.medlabs.Reminder;

import java.util.Calendar;

import com.iiitd.medlabs.backend.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmTask implements Runnable{
    private final Calendar date;
    private final AlarmManager am;
    private final Context context;
    private final Reminder _rem;
 
    public AlarmTask(Context context, Calendar date, Reminder rem) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
        this._rem = rem;
    }
     
    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        Log.d("HK AlarmTask",Integer.toString(_rem.getId()));
        intent.putExtra("requestCode",  _rem.getId());
        intent.putExtra("passedMsg",  _rem.getTitle());
        intent.putExtra("passedHr",  Integer.toString(_rem.getHour()));
        intent.putExtra("passedMin",  Integer.toString(_rem.getMinute()));
        
        intent.putExtra("passedDay",  Integer.toString(_rem.getDay()));
        intent.putExtra("passedMonth",  Integer.toString(_rem.getMonth()));
        intent.putExtra("passedYear",  Integer.toString(_rem.getYear()));
                
        
        PendingIntent pendingIntent = PendingIntent.getService(context, _rem.getId(), intent, 0);
         
        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        am.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
    }
}