package com.iiitd.medlabs.Profile;

import com.iiitd.medlabs.R;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class PictureActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		
		ImageView Image = (ImageView) findViewById(R.id.SingleView);
		Intent intent2 = getIntent();
		int position = intent2.getExtras().getInt("number");
        ImageAdapter imageAdapter = new ImageAdapter(this);
        //Bitmap bitmap=BitmapFactory;
        // imageview.setImageBitmap(bitmap);
        //Image.setImageResource(imageAdapter.imageId[position]);
        Image.setImageBitmap(imageAdapter.imageIds.get(position));
      //  imageView.setImageResource(mThumbIds[position]);
	}
}
