package com.iiitd.medlabs;

import java.util.ArrayList;
import java.util.List;

import com.iiitd.medlabs.Loaction.MyLocationListener;
import com.iiitd.medlabs.backend.Contact;
import com.iiitd.medlabs.backend.DatabaseHandler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class NearbyFragment extends Fragment{
	
	LocationManager locManagerObj;
	MyLocationListener locListenerObj;
	SharedPreferences prefs;
	Button nearbyBut;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		 View V = inflater.inflate(R.layout.nearby_layout, null);
		 nearbyBut = (Button) V.findViewById(R.id.buttonNearby);
		 
		 final DatabaseHandler db = new DatabaseHandler(this.getActivity().getBaseContext());
			nearbyBut.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					ArrayList<String> lab_ids = new ArrayList<String>();
					List<Contact> near = db.getAllContacts();
					//List<Contact> limitedNear = db.getAllContacts();
					for (Contact cn : near){
						// try-check Class cast
						/*
						Log.d("HK",cn.getDistance());
						float mydis = Float.parseFloat( cn.getDistance());
						
						if(mydis < 15){
							limitedNear.add(cn);
						}*/
					}
					
					
					ArrayList<String> lab_array = new ArrayList<String>();
					for (Contact cn : near){
						lab_array.add(cn.getName());
					}
					
					//for(i=0; i<lab_ids.size(); i++) {
					//	contact = db.getLabDetails(lab_ids.get(i));
					
					
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					Log.d("HK", lab_array.toString());
					intent.setAction(".ListViewActivity");
					bundle.putStringArrayList("lab_array", lab_array);
					//bundle.putIntegerArrayList("lab_ids", lab_ids);
					
					Log.d("ListViewActivit", "ListViewActivity");
					intent.putExtra("bundle", bundle);
					
					startActivity(intent);  
					
				}
			});
		 
		 
		 return V;
		
		
		
	}
	
	@Override
	public void onResume() {
		locManagerObj = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
		locListenerObj = new MyLocationListener(this.getActivity().getBaseContext());
		
		boolean en = locManagerObj.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		
		if(en == true){
			Log.d("HK","Entering loop");
			//Toast.makeText(this.getActivity().getBaseContext(), "Network Enabled", Toast.LENGTH_SHORT).show();
			locManagerObj.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 *15, 10, locListenerObj);
			
		}
		if (en == false){
			//Toast.makeText(myAct.getBaseContext(), "Network Not Enabled", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
		super.onResume();
	}
	
	@Override
	public void onPause() {
		locManagerObj.removeUpdates(locListenerObj);
		super.onPause();
	}
}
