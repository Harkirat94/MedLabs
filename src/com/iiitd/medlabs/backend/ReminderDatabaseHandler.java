package com.iiitd.medlabs.backend;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReminderDatabaseHandler extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "MedLabsReminder";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_REMINDER = "Reminder";
	
	public static final String KEY_ID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_EMAIL_ID = "emailId";
	public static final String KEY_DAY = "day";
	public static final String KEY_MONTH = "month";
	public static final String KEY_YEAR = "year";
	public static final String KEY_HOUR = "hour";
	public static final String KEY_MINUTE = "minute";
	
	//private SQLiteDatabase dbS;
    //private DatabaseReminder dbR;
		
	public ReminderDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String CREATE_REMINDER_TABLE = "CREATE TABLE " + TABLE_REMINDER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_TITLE + " TEXT,"
				+ KEY_EMAIL_ID + " TEXT,"
				+ KEY_DAY + " TEXT,"
				+ KEY_MONTH + " TEXT," 
				+ KEY_YEAR + " TEXT,"
				+ KEY_HOUR + " TEXT,"
				+ KEY_MINUTE + " TEXT"
				+ ")";
		
		db.execSQL(CREATE_REMINDER_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);

        // Create tables again
        onCreate(db);
		
	}
	
	public void addReminder(Reminder reminder) {
		SQLiteDatabase dbS = this.getWritableDatabase();
		ContentValues values = new ContentValues();
        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_EMAIL_ID, reminder.getEmailId());
        values.put(KEY_DAY, reminder.getDay());
        values.put(KEY_MONTH, reminder.getMonth());
        values.put(KEY_YEAR, reminder.getYear());
        values.put(KEY_HOUR, reminder.getHour());
        values.put(KEY_MINUTE, reminder.getMinute());
        
        dbS.insert(TABLE_REMINDER, null, values);
        dbS.close();
   }
	
	public Reminder addAndreturnReminder(Reminder reminder) {
		SQLiteDatabase dbS = this.getWritableDatabase();
		ContentValues values = new ContentValues();
        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_EMAIL_ID, reminder.getEmailId());
        values.put(KEY_DAY, reminder.getDay());
        values.put(KEY_MONTH, reminder.getMonth());
        values.put(KEY_YEAR, reminder.getYear());
        values.put(KEY_HOUR, reminder.getHour());
        values.put(KEY_MINUTE, reminder.getMinute());
        
        dbS.insert(TABLE_REMINDER, null, values);
        
        ArrayList<Reminder> ReminderList = new ArrayList<Reminder>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_REMINDER;
 
        Cursor cursor = dbS.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
        	
        	do {
        		Reminder reminder2 = new Reminder();
        		reminder2.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        		reminder2.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
        		reminder2.setEmailId(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ID)));
        		reminder2.setDay(cursor.getInt(cursor.getColumnIndex(KEY_DAY)));
        		reminder2.setMonth(cursor.getInt(cursor.getColumnIndex(KEY_MONTH)));
        		reminder2.setYear(cursor.getInt(cursor.getColumnIndex(KEY_YEAR)));
        		reminder2.setHour(cursor.getInt(cursor.getColumnIndex(KEY_HOUR)));
        		reminder2.setMinute(cursor.getInt(cursor.getColumnIndex(KEY_MINUTE)));
        		ReminderList.add(reminder2);
        	}while (cursor.moveToNext());
        }
        dbS.close();
        return ReminderList.get(ReminderList.size()-1);
        
        
        
   }
	
	public ArrayList<Reminder> getAllReminders() {
		
		ArrayList<Reminder> ReminderList = new ArrayList<Reminder>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_REMINDER;
 
        SQLiteDatabase dbS = this.getWritableDatabase();
        Cursor cursor = dbS.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
        	
        	do {
        		Reminder reminder = new Reminder();
        		reminder.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        		reminder.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
        		reminder.setEmailId(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ID)));
        		reminder.setDay(cursor.getInt(cursor.getColumnIndex(KEY_DAY)));
        		reminder.setMonth(cursor.getInt(cursor.getColumnIndex(KEY_MONTH)));
        		reminder.setYear(cursor.getInt(cursor.getColumnIndex(KEY_YEAR)));
        		reminder.setHour(cursor.getInt(cursor.getColumnIndex(KEY_HOUR)));
        		reminder.setMinute(cursor.getInt(cursor.getColumnIndex(KEY_MINUTE)));
        		ReminderList.add(reminder);
        	}while (cursor.moveToNext());
        }
        
        return ReminderList;
		
	}
	
	public Reminder getReminderDetailsByTitle(String title) {
		
	    SQLiteDatabase dbS = this.getReadableDatabase();
	       
	    Cursor c = dbS.query(TABLE_REMINDER, null, KEY_TITLE + "=?", new String[] {title} , null, null, null, null);
	       
	    Reminder reminder = new Reminder();
	       
	    c.moveToFirst();
	    
	    String remtitle = c.getString(c.getColumnIndex(KEY_TITLE));
     	reminder.setTitle(remtitle);
     	
     	////
     	String remEmailId = c.getString(c.getColumnIndex(KEY_EMAIL_ID));
     	reminder.setEmailId(remEmailId);
     	int myId = c.getInt(c.getColumnIndex(KEY_ID));
     	reminder.setId(myId);
     	/////
     	int day = c.getInt(c.getColumnIndex(KEY_DAY));
     	reminder.setDay(day);
     	int month = c.getInt(c.getColumnIndex(KEY_MONTH));
     	reminder.setDay(month);
     	int year = c.getInt(c.getColumnIndex(KEY_YEAR));
     	reminder.setDay(year);
     	int hour = c.getInt(c.getColumnIndex(KEY_HOUR));
     	reminder.setDay(hour);
     	int minute = c.getInt(c.getColumnIndex(KEY_MINUTE));
     	reminder.setDay(minute);
     	
     	return reminder;
		
	}
	
	// Deleting single contact
    public void deleteReport(String id) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDER, "CAST("+KEY_ID+" as TEXT)" + " = ?",
                new String[] { id });
        db.close();
    }
	
	
}
