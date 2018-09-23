package com.iiitd.medlabs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.iiitd.medlabs.Profile.ProfileDetails;
import com.iiitd.medlabs.backend.Contact;
import com.iiitd.medlabs.backend.DatabaseHandler;
import com.iiitd.medlabs.backend.Reminder;
import com.iiitd.medlabs.backend.ReminderDatabaseHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProfileFragment extends Fragment {
	
	Button signIn;
	boolean isRegistered;
	TextView tv1;
	ProfileDetails userProfile;
	GridView gridView;
	Button logoutBut;
	Button downloadBut, galleryBut, reminderBut, allRemBut;
	static final String[] MOBILE_OS = new String[] { "Gallery", "New Report","New Reminder", "Existing Reminder", "Logout" };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
    @SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View V = inflater.inflate(R.layout.profile_layout, null);
		userProfile = new ProfileDetails();
		tv1 = (TextView) V.findViewById(R.id.textView2);
		/*logoutBut = (Button) V.findViewById(R.id.button1);
		downloadBut = (Button) V.findViewById(R.id.buttonDownload);
		galleryBut = (Button) V.findViewById(R.id.button3);
		reminderBut = (Button) V.findViewById(R.id.buttonRem);
		allRemBut = (Button) V.findViewById(R.id.buttonAllReminder);*/
		
		SharedPreferences prefs = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
		isRegistered = prefs.getBoolean("is_logged_in", false);
		
		if(isRegistered==true){
			userProfile.name =  prefs.getString("name",null);
			tv1.setText(userProfile.name);
			
		    SharedPreferences dirPrefs = this.getActivity().getSharedPreferences("Directory", Context.MODE_PRIVATE);
			String dirPath = dirPrefs.getString("picturePath", null);
			if(dirPath!=null){
				File directory = new File(dirPath);
				try {
					
					File f=new File(directory, "profilePic.jpg");
			        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
			        //Bitmap roundBItmap =  getRoundedShape(b);
			        ImageView img=(ImageView) V.findViewById(R.id.imageView1);
			        img.setImageBitmap(b);
			    } 
			    catch (FileNotFoundException e) 
			    {
			        e.printStackTrace();
			    }
			}
			final SharedPreferences vprefs = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
			final Context myctxCtx = this.getActivity().getApplicationContext();
		    
			gridView = (GridView) V.findViewById(R.id.gridView1);

			gridView.setAdapter(new MyAdapter(myctxCtx, MOBILE_OS));

			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					//Toast.makeText(myctxCtx,((TextView) v.findViewById(R.id.label)).getText(), Toast.LENGTH_SHORT).show();
					if(position==0){
						Intent intent = new Intent();
						intent.setAction(".Gallery");
						startActivity(intent);
					}
					if(position==1){
						Intent intent = new Intent();
						intent.setAction(".DownloadReports");
						startActivity(intent);
					}
					if(position==2){
						Intent intent = new Intent();
						intent.setAction(".AddReminderActivity");
						startActivity(intent);
					}
					if(position==3){
						Intent intent = new Intent();
						intent.setAction(".AllEvents");
						startActivity(intent);
					}
					if(position==4){
						SharedPreferences.Editor editor = vprefs.edit();
					    editor.putBoolean("is_logged_in", false);
					    editor.commit();
					    Intent intent = new Intent();
						intent.setAction(".LoginActivity");
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				}
			});
			
			////
			/*logoutBut.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					SharedPreferences.Editor editor = vprefs.edit();
				    editor.putBoolean("is_logged_in", false);
				    editor.commit();
				    Intent intent = new Intent();
					intent.setAction(".LoginActivity");
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);  
				}
			});
			/////
			
			downloadBut.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction(".DownloadReports");
					startActivity(intent);  
				}
			});
			galleryBut.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction(".ReportGallery");
					startActivity(intent);  
				}
			});
			
			reminderBut.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction(".AddReminderActivity");
					startActivity(intent);  
				}
			});
			
			final ReminderDatabaseHandler rDbHandler = new ReminderDatabaseHandler(this.getActivity().getBaseContext());
			allRemBut.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction(".AllEvents");
					startActivity(intent);  
				}
			});*/
		}
		return V;
		
	}
    
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
	    int targetWidth = 50;
	    int targetHeight = 50;
	    Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
	                        targetHeight,Bitmap.Config.ARGB_8888);

	    Canvas canvas = new Canvas(targetBitmap);
	    Path path = new Path();
	    path.addCircle(((float) targetWidth - 1) / 2,
	        ((float) targetHeight - 1) / 2,
	        (Math.min(((float) targetWidth), 
	        ((float) targetHeight)) / 2),
	        Path.Direction.CCW);

	    canvas.clipPath(path);
	    Bitmap sourceBitmap = scaleBitmapImage;
	    canvas.drawBitmap(sourceBitmap, 
	        new Rect(0, 0, sourceBitmap.getWidth(),
	        sourceBitmap.getHeight()), 
	        new Rect(0, 0, targetWidth, targetHeight), null);
	    return targetBitmap;
	}

}
