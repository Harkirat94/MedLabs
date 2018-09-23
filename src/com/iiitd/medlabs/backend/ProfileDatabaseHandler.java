package com.iiitd.medlabs.backend;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDatabaseHandler extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;           // Version No
	private static final String DATABASE_NAME = "MedLabsPersonal";   // Database Name
    private static final String TABLE_PERSONAL = "Personal";     // Labs table name
    
    // Personal Table Columns names
    private static final String KEY_REPID = "repId";
    private static final String KEY_LABID = "labId";
    private static final String KEY_EMAILID = "emailId";
    private static final String KEY_LABNAME = "labName";
    private static final String KEY_REPORT_NAME = "reportName";
    private static final String KEY_REPORT_LINK = "reportLink";
    private static final String KEY_TIME = "time";
    
    public ProfileDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
	      //Creating test table
	    	String CREATE_PERSONAl_TABLE = "CREATE TABLE " + TABLE_PERSONAL + "("
	    			+ KEY_REPID + " INTEGER,"
	                + KEY_EMAILID + " TEXT,"
	                + KEY_LABID + " TEXT,"
	                + KEY_LABNAME + " TEXT,"
	                + KEY_REPORT_NAME + " TEXT," 
	                + KEY_REPORT_LINK + " TEXT," 
	                + KEY_TIME + " TEXT" +")";
	        db.execSQL(CREATE_PERSONAl_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL);
        
        // Create tables again
        onCreate(db);		
	}
	
	public void insertEnrty(int repId, String emailId, String labId, String labName, String reportName, String reportLink, String time){
    	SQLiteDatabase db = this.getWritableDatabase();
    	
		ContentValues insertVal = new ContentValues();
		insertVal.put(KEY_REPID, repId);
		insertVal.put(KEY_EMAILID, emailId);
		insertVal.put(KEY_LABID, labId);
		insertVal.put(KEY_LABNAME, labName);
		insertVal.put(KEY_REPORT_NAME, reportName);
		insertVal.put(KEY_REPORT_LINK, reportLink);
		insertVal.put(KEY_TIME, time);
		db.insert(TABLE_PERSONAL, null, insertVal);
		db.close(); // Closing database connection
	}
    
    
    
	
    public boolean isEmptyPersonalTable(){
    	SQLiteDatabase db = this.getWritableDatabase();
    	String count = "SELECT count(*) FROM "+ TABLE_PERSONAL;
    	Cursor mcursor = db.rawQuery(count, null);
    	if (mcursor.moveToFirst()){
    		int icount = mcursor.getInt(0);
    		if(icount>0){
    			return false;
    		}
    		else{
    			return true;
    		}
    	}
    	return true;
    }
    
 // Getting All Contacts
    public List<Report> getAllReports() {
        List<Report> reportList = new ArrayList<Report>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_PERSONAL;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

        	do {
        		Report rep = new Report();
        		rep.setRepId(cursor.getInt(cursor.getColumnIndex(KEY_REPID)));
                rep.setEmailId(cursor.getString(cursor.getColumnIndex(KEY_EMAILID)));
                rep.setLabId(cursor.getString(cursor.getColumnIndex(KEY_LABID)));
                rep.setLabName(cursor.getString(cursor.getColumnIndex(KEY_LABNAME)));
                rep.setReportName(cursor.getString(cursor.getColumnIndex(KEY_REPORT_NAME)));
                rep.setReportLink(cursor.getString(cursor.getColumnIndex(KEY_REPORT_LINK)));
                rep.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                // Adding contact to list
                reportList.add(rep);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return reportList;
    }
    
    // Deleting single contact
    public void deleteReport(String reportName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSONAL, KEY_REPORT_NAME + " = ?",
                new String[] { reportName });
        db.close();
    }
    
    public Report getReportDetails(int argId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	
       Cursor cursor = db.query(TABLE_PERSONAL, null, KEY_REPID + "="  + Integer.toString(argId) , null, null, null, null);
       
       
       Report rep = new Report();
       cursor.moveToFirst();
       rep.setRepId(cursor.getInt(cursor.getColumnIndex(KEY_REPID)));
       rep.setEmailId(cursor.getString(cursor.getColumnIndex(KEY_EMAILID)));
       rep.setLabId(cursor.getString(cursor.getColumnIndex(KEY_LABID)));
       rep.setLabName(cursor.getString(cursor.getColumnIndex(KEY_LABNAME)));
       rep.setReportName(cursor.getString(cursor.getColumnIndex(KEY_REPORT_NAME)));
       rep.setReportLink(cursor.getString(cursor.getColumnIndex(KEY_REPORT_LINK)));
       rep.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
     	
        return rep;
    }
    
}
