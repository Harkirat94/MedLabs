package com.iiitd.medlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private final String[] countries;

	public MyAdapter(Context context, String[] countries) {
		this.context = context;
		this.countries = countries;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			gridView = inflater.inflate(R.layout.profile_icons, null);

			//TextView textView = (TextView) gridView.findViewById(R.id.label);
			//textView.setText(countries[position]);

			ImageView flag = (ImageView) gridView .findViewById(R.id.flag);

			String mobile = countries[position];

			if (mobile.equals("Gallery")) {
				flag.setImageResource(R.drawable.gallery);
			} else if (mobile.equals("New Report")) {
				flag.setImageResource(R.drawable.new_report);
			} else if (mobile.equals("New Reminder")) {
				flag.setImageResource(R.drawable.new_reminder);
			} else if (mobile.equals("Existing Reminder")) {
				flag.setImageResource(R.drawable.existing_reminder);
			} else if (mobile.equals("Logout")) {
				flag.setImageResource(R.drawable.logout);
			} 

		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}

	@Override
	public int getCount() {
		return countries.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}

