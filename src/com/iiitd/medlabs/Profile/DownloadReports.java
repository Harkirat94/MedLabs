package com.iiitd.medlabs.Profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.iiitd.medlabs.R;
import com.iiitd.medlabs.backend.ProfileDatabaseHandler;
import com.iiitd.medlabs.backend.Report;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DownloadReports extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_reports);
	
		final ProfileDatabaseHandler db = new ProfileDatabaseHandler(this);
			
		
		if(db.isEmptyPersonalTable()){
			TextView myTextView = new TextView(this);
			myTextView.setText("No new reports");

			LinearLayout ll = (LinearLayout)findViewById(R.id.linear_download_buttons_id);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			ll.addView(myTextView, lp);
		}
		else
		{
			LinearLayout ll = (LinearLayout)findViewById(R.id.linear_download_buttons_id);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			
			List<Report> allReports = db.getAllReports();
			
			for(int i=0; i<allReports.size(); i++){
				final Report curReport = allReports.get(i);
				final Button myButton = new Button(this);
				TextView myTv = new TextView(this);
				myButton.setId(curReport.getRepId());
				myButton.setText(curReport.getLabName() + "-" + curReport.getTime() );
				myTv.setText(curReport.getTime());
				myButton.setLayoutParams(lp);
				ll.addView(myButton);
				ll.addView(myTv);
				
				myButton.setOnClickListener(new OnClickListener() {
			        public void onClick(View v) {
			            ////////////////////////////
			        	/* Save Image in Mobile's Private Memory*/
			        	new Thread(new Runnable() {
			                @Override
			                public void run() {
			                	URL url;
						     	File directory = null;
								try {
									Report repObj = new Report();
									Log.d("HK",Integer.toString(myButton.getId()));
									repObj = db.getReportDetails(myButton.getId());
									url = new URL (repObj.getReportLink());
									Bitmap bitmap;
									bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
									ContextWrapper cw = new ContextWrapper(getApplicationContext());
									
							        // path to /data/data/yourapp/app_data/imageDir
									directory = cw.getDir("reportImageDir", Context.MODE_PRIVATE);
							        // Create imageDir
							        File mypath=new File(directory,repObj.getReportName());
							        
							        FileOutputStream fos = null;
							        try {           

							            fos = new FileOutputStream(mypath);
							            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
							            fos.close();
							        } catch (Exception e) {
							            e.printStackTrace();
							        }
							    
							        db.deleteReport(repObj.getReportName());
									
								} catch (MalformedURLException e1) {
									e1.printStackTrace();
								}
								 catch (IOException e) {
									e.printStackTrace();
								}
						    SharedPreferences dirPrefs = getSharedPreferences("Directory", Context.MODE_PRIVATE);
						    SharedPreferences.Editor dirEditor = dirPrefs.edit();
						    dirEditor.putString("reportPath",directory.getAbsolutePath());
						    dirEditor.commit();
						    Intent i = new Intent();
							i.setAction(".DownloadReports");
							startActivity(i);
							finish();
			                }
			            }).start();
			        	
			        	
			        	/////////////////////////////
			        	
			        }
			    });
				 
				
			}
		}
	}
}