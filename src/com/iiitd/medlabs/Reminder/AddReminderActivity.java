package com.iiitd.medlabs.Reminder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.iiitd.medlabs.R;
import com.iiitd.medlabs.backend.Reminder;
import com.iiitd.medlabs.backend.ReminderDatabaseHandler;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddReminderActivity extends ActionBarActivity {

	/* Objects used by Reminder Functionality */
	private ScheduleClient scheduleClient;
    private DatePicker d_picker;
    private TimePicker t_picker;
    
    ReminderDatabaseHandler dbR = new ReminderDatabaseHandler(this);
    Reminder reminder;
    
    EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_reminder);
		
		// Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
 
        // Get a reference to our date picker
        d_picker = (DatePicker) findViewById(R.id.datePicker);
        t_picker = (TimePicker) findViewById(R.id.timePicker);
        t_picker.setIs24HourView(true);
	}
	
	public void onDateSelectedButtonClick(View v){
        // Get the date from our datepicker
        int day = d_picker.getDayOfMonth();
        int month = d_picker.getMonth();
        int year = d_picker.getYear();
        
        int hour = t_picker.getCurrentHour();
        int minute = t_picker.getCurrentMinute();
        
        editText = (EditText)findViewById(R.id.editText1);
        String title = editText.getText().toString();
        
        // Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
		String customerEmail = prefs.getString("email", null);
		
        Reminder rem = new Reminder(title, customerEmail, day, month, year, hour, minute);
        Reminder addedRem = dbR.addAndreturnReminder(rem);
        Log.d("HK-Add", Integer.toString(addedRem.getId()));
        Log.d("HK-Add", Integer.toString(addedRem.getHour()));
        scheduleClient.setAlarmForNotification(c,addedRem);
        
        Toast.makeText(this, "Notification set for: "+ day +"/"+ (month+1) +"/"+ year + " at " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
        
        
		
        /*ed2 = (EditText)findViewById(R.id.editText2);
        ArrayList<Reminder>  allReminders = dbR.getAllReminders();
        String con = new String();
        for(Reminder r : allReminders){
        	con += r.getId() + " "+ r.getEmailId() +" "+ r.getTitle()+" " + Integer.toString(r.getHour())+" " + Integer.toString(r.getMonth());
        	con += "\n";
        }
        ed2.setText(con);*/
    }
    
    @Override
    protected void onStop() {
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }
}
