package com.iiitd.medlabs.Reminder;

import java.util.ArrayList;
import com.iiitd.medlabs.R;
import com.iiitd.medlabs.backend.Reminder;
import com.iiitd.medlabs.backend.ReminderDatabaseHandler;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AllEvents extends ActionBarActivity {
    
    ListView list;
    ReminderListAdapter adapter;
    public  AllEvents CustomListView = null;
    public  ArrayList<Reminder> CustomListViewValuesArr = new ArrayList<Reminder>();
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
         
        CustomListView = this;
         
        setListData();
         
        Resources res =getResources();
        list= ( ListView )findViewById( R.id.list );  // List defined in XML ( See Below )
         
        adapter=new ReminderListAdapter( CustomListView, CustomListViewValuesArr,res );
        list.setAdapter( adapter );
         
    }
 
    public void setListData()
    {
         
        ReminderDatabaseHandler rDb = new ReminderDatabaseHandler(getBaseContext());
             ArrayList<Reminder> allRemArr = rDb.getAllReminders();
            final Reminder sched = new Reminder();
              for(Reminder r :allRemArr)
              {
               /*sched.setId(r.getId());
               sched.set
               sched.setImage("image"+i);
               sched.setUrl("http:\\www."+i+".com");*/
                
            CustomListViewValuesArr.add( r );
        }
         
    }
    
    public void onItemClick(int mPosition)
    {
        Reminder tempValues = ( Reminder ) CustomListViewValuesArr.get(mPosition);


       // SHOW ALERT                  

        Toast.makeText(getBaseContext(), Integer.toString(tempValues.getId()), Toast.LENGTH_LONG).show();
    }
}