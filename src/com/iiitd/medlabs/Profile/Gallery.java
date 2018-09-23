package com.iiitd.medlabs.Profile;

import com.iiitd.medlabs.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class Gallery extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
	
    GridView GV1 = (GridView) findViewById(R.id.gridview);
    GV1.setAdapter(new ImageAdapter(this));
    
    GV1.setOnItemClickListener(new OnItemClickListener() 
    {
        public void onItemClick(AdapterView<?> parent, View v, int position_no, long number)
        	{
      	  Intent intent =  new Intent(getApplicationContext(), PictureActivity.class);
            intent.putExtra("number", position_no);
            startActivity(intent);
        }
    });
	}

}
