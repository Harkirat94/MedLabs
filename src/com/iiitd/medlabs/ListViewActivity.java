package com.iiitd.medlabs;

import java.util.ArrayList;

import com.iiitd.medlabs.backend.Contact;
import com.iiitd.medlabs.backend.DatabaseHandler;

import android.util.Log;
import android.view.View;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewActivity extends ListActivity {

	Cursor cursor;
	String[] columns;
	
	DatabaseHandler db = new DatabaseHandler(this);
	//SQLiteDatabase datab = this.getReadableDatabase(this);
	
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
		
		ArrayList<String> lab_arr = bundle.getStringArrayList("lab_array");
		
		Log.d("Reached", "Reached");
		
		 if(lab_arr.isEmpty()) {
				//ListView listView = (ListView)findViewById(R.id.listview);
				String arr = "No search found";
				ArrayList<String> log = new ArrayList<String>() ;
				log.add(arr);
				
				this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView1, log));
				
				listView = getListView();
			} 
			
			else {
	
		
		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView1, lab_arr));
		
		listView = getListView(); }
		
		//View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        //listView.addHeaderView(header);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				TextView textView = (TextView) view.findViewById(R.id.textView1);
				
				String itemSelected = textView.getText().toString();
				
				Contact contact = new Contact();
				contact = db.getLabDetailsbyName(itemSelected);
				
				Intent intent = new Intent();
				
				Bundle bundle = new Bundle();
				
				bundle.putInt("labId", contact.getID());
				bundle.putString("Name", contact.getName());
				bundle.putString("Distance", contact.getDistance());
				bundle.putString("Address", contact.getAddress()+", "+contact.getLocation());
				bundle.putString("Test", contact.getTests());
				bundle.putString("Details", contact.getDetails());
				
				//bundle.putString("Location", contact.getLocation());
				//bundle.putString("Pincode", contact.getPincode());
				//bundle.putString("City", contact.getCity());
				
				intent.setAction(".LabDetails");
				intent.putExtra("bundle", bundle);
				startActivity(intent);
				
			}
			
		});  
		
	}

}
