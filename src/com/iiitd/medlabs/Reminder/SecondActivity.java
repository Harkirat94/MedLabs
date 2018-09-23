package com.iiitd.medlabs.Reminder;

import com.iiitd.medlabs.R;
import com.iiitd.medlabs.R.id;
import com.iiitd.medlabs.R.layout;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SecondActivity extends ActionBarActivity {

	TextView tView1;
	TextView tView2;
	TextView tView3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
	
		
		
	Intent intent = getIntent();
	
	String myMonth = intent.getExtras().getString("passedMonth");
	int month_int = Integer.parseInt(myMonth);
	month_int = month_int+1;
	
	tView1 = (TextView)findViewById(R.id.textView2);
	tView2 = (TextView)findViewById(R.id.textView4);
	tView3 = (TextView)findViewById(R.id.textView6);
	
	String message = intent.getExtras().getString("passedMsg");
	String date = intent.getExtras().getString("passedDay") + "-" + Integer.toString(month_int) + "-" + intent.getExtras().getString("passedYear");
	String time = intent.getExtras().getString("passedHr") + ":"+ intent.getExtras().getString("passedMin");
	
	tView1.setText(message);
	tView2.setText(date);
	tView3.setText(time);
	}
}
