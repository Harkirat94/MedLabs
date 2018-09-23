package com.iiitd.medlabs.Reminder;

import java.util.ArrayList;
import java.util.List;

import com.iiitd.medlabs.R;
import com.iiitd.medlabs.backend.Reminder;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReminderListAdapter extends BaseAdapter implements OnClickListener {
	private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    Reminder reminderValues=null;
    int i=0;
     
    public ReminderListAdapter(Activity a, ArrayList d,Resources resLocal) {
            activity = a;
            data=d;
            res = resLocal;
         
             inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         
    }
 
    public static class ViewHolder{
         
        public TextView titleAlarm;
        public TextView infoAlarm;
        public TextView idAlarm;
    }

	@Override
	public int getCount() {
		if(data.size()<=0)
            return 1;
        return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
        ViewHolder holder;
         
        if(convertView==null){
             
            vi = inflater.inflate(R.layout.reminder_list_layout, null);
             
            holder = new ViewHolder();
            holder.titleAlarm = (TextView) vi.findViewById(R.id.text1);
            holder.infoAlarm =(TextView)vi.findViewById(R.id.text2);
            holder.idAlarm =(TextView)vi.findViewById(R.id.text3);
             
            vi.setTag( holder );
        }
        else 
            holder=(ViewHolder)vi.getTag();
         
        if(data.size()<=0)
        {
            //holder.text.setText("No Data");
             
        }
        else
        {
            reminderValues=null;
        	reminderValues = ( Reminder ) data.get( position );
        	holder.titleAlarm.setText(reminderValues.getTitle());
            holder.infoAlarm.setText( reminderValues.setANDGETReminderInfo(reminderValues.getMonth(), reminderValues.getYear(), reminderValues.getDay(), reminderValues.getHour(), reminderValues.getMinute()) );
            holder.idAlarm.setText(Integer.toString(reminderValues.getId()));
            holder.idAlarm.setVisibility(View.INVISIBLE);
              
            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
	}

	@Override
	public void onClick(View v) {
		   Log.d("HK", "Row Tapped");
    }
     
    private class OnItemClickListener  implements OnClickListener{           
        private int mPosition;
         
        OnItemClickListener(int position){
             mPosition = position;
        }
         
        @Override
        public void onClick(View arg0) {

   
        	AllEvents sct = (AllEvents)activity;

            sct.onItemClick(mPosition);
        }               
	}
	
}