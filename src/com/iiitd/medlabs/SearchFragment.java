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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment{
	
	LocationManager locManagerObj;
	MyLocationListener locListenerObj;
	SharedPreferences prefs;
	boolean flag = true;
	Button searchBut;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View V = inflater.inflate(R.layout.search_layout, null);
		searchBut = (Button) V.findViewById(R.id.button1);
		final DatabaseHandler db = new DatabaseHandler(this.getActivity().getBaseContext());
		
		ArrayList<String> ac2 = new ArrayList<String>();
		List<Contact> retContacts= db.getAllContacts();
		
		for (Contact cn : retContacts) {
			//aa = cn.getLocation();
			//autoComplete[i++] = cn.getName();
			ac2.add(cn.getName());
			//+= "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Location: " + cn.getLocation()
//				+ " ,Tests: " + cn.getTests() + " ,Address: " + cn.getAddress() + " ,Details: " + cn.getDetails() + " ,Distance: " + cn.getDistance() + " ,Pincode: " + cn.getPincode() + "\n";

			}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line,ac2);
		final AutoCompleteTextView seachBoxEditView = (AutoCompleteTextView) V.findViewById(R.id.autoCompleteSearchId);
		seachBoxEditView.setAdapter(adapter);
		
		searchBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String loc = seachBoxEditView.getText().toString().toLowerCase();
				ArrayList<String> lab_ids = new ArrayList<String>();
				
				//List<Contact> contacts = db.getAllLabDetails(loc);
				
				lab_ids = db.search(loc);
		  
				Contact contact = new Contact();
				int i;
				
				ArrayList<String> lab_array = new ArrayList<String>();
				
				
				//for(i=0; i<lab_ids.size(); i++) {
				//	contact = db.getLabDetails(lab_ids.get(i));
				
				for(i=0; i<lab_ids.size(); i++) {
					
					contact = db.getLabDetails(Integer.parseInt(lab_ids.get(i)));
					//String[] item = new String[] {String.valueOf(lab_ids.get(i)), contact.getName(), String.valueOf(contact.getDistance())};
					String item = contact.getName() ;
					
					lab_array.add(item);
					//lab_array.add(item[1]);
					//lab_array.add(item[2]);
				}  
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
		
		prefs = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
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
		//////////////////////////////////////////////////////////////////////////////////
		// if disabled Text view disable.
		
		/////////////////////////////////////////////////////////////////////////////////
		super.onResume();
		    
		    
		
	}
	
	@Override
	public void onPause() {
		locManagerObj.removeUpdates(locListenerObj);
		super.onPause();
	}
	
	
}