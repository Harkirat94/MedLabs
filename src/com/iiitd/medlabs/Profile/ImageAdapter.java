package com.iiitd.medlabs.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.iiitd.medlabs.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	public ArrayList<Bitmap> imageIds = new ArrayList<Bitmap>();
    
	private Context Context1;
    public ImageAdapter(Context c) {
	      Context1 = c;
    //////////////////////////////////////
	      SharedPreferences dirPrefs = Context1.getSharedPreferences("Directory", Context.MODE_PRIVATE);
	  	String dirPath = dirPrefs.getString("reportPath", null);
	  	if(dirPath!=null){
	  		Log.d("HK","Dir not null");
	  		File directory = new File(dirPath);
	  		try {
	  		
	  			File[] directoryListing = directory.listFiles();
	  			if (directoryListing != null) {
	  		    for (File f : directoryListing) {
	  		    	Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
	  		    	imageIds.add(b);
	  		    	
	  		    }
	  		  }

	  } 
	  catch (FileNotFoundException e) 
	  {
	  e.printStackTrace();
	  }
	  }
/////////////////////////////////////
    }
    
	public int getCount() {
	      return imageIds.size();
	   }

	public Object getItem(int position) {
	      return null;
	   }

	public long getItemId(int position_no) {
	      return 0;
	   }

	public View getView(int position_no, View arg0, ViewGroup arg) {
	      ImageView   imageView = new ImageView(Context1);
	     
	      imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
	      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	      
	      //imageView.setImageResource(imageId[position_no]);
	      imageView.setImageBitmap(imageIds.get(position_no));
	      return imageView;
	   }

	  }
