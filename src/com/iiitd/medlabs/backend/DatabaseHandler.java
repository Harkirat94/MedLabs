package com.iiitd.medlabs.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;           // Version No
	private static final String DATABASE_NAME = "MedLabs";   // Database Name
    private static final String TABLE_CONTACTS = "Labs";     // Labs table name
    private static final String TABLE_TESTS = "Tests";     // Tests table name
 
    // Labs Table Columns names
    private static final String KEY_LABID = "labId";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_CITY= "city";
    private static final String KEY_PINCODE = "pincode";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_TESTS = "tests";
    
    // Tests Table Columns names
    private static final String KEY_TESTID = "testId";
    private static final String KEY_TNAME = "name";
    private static final String KEY_DESCRIPTION= "description";
    private static final String KEY_REQUIREMENTS = "requirements";
    private static final String KEY_LABIDS = "labIds";
    
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating Labs table
    	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_LABID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_LOCATION + " TEXT," + KEY_CITY + " TEXT," 
                + KEY_PINCODE + " TEXT," + KEY_DETAILS + " TEXT," 
                + KEY_DISTANCE + " TEXT," 
                + KEY_TESTS + " TEXT" +")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        
        
      //Creating test table
    	String CREATE_TESTS_TABLE = "CREATE TABLE " + TABLE_TESTS + "("
                + KEY_TESTID + " INTEGER PRIMARY KEY," + KEY_TNAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_REQUIREMENTS + " TEXT," + KEY_LABIDS 
                + " TEXT" +")";
        db.execSQL(CREATE_TESTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTS);

        // Create tables again
        onCreate(db);
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact (Lab)
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_ADDRESS , contact.getAddress()); // Contact Phone
        values.put(KEY_LOCATION, contact.getLocation()); 
        values.put(KEY_CITY, contact.getCity()); 
        values.put(KEY_PINCODE, contact.getPincode()); 
        values.put(KEY_DETAILS, contact.getDetails()); 
        values.put(KEY_DISTANCE, contact.getDistance()); 
        values.put(KEY_TESTS, contact.getTests());               
     
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    
    
    // Adding new Test
    public void addTest(Test test) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TNAME, test.getName()); // 
        values.put(KEY_DESCRIPTION, test.getDescription()); // 
        values.put(KEY_REQUIREMENTS, test.getRequirment()); 
        values.put(KEY_LABIDS, test.getlabId()); 
        // Inserting Row
        db.insert(TABLE_TESTS, null, values);
        db.close(); // Closing database connection
    }
    
        public int updateDistance(int id, String dis) {
            SQLiteDatabase db = this.getWritableDatabase();
     
            ContentValues values = new ContentValues();
            values.put(KEY_DISTANCE, dis);
            
            // updating row
            return db.update(TABLE_CONTACTS, values, KEY_LABID + " = ?",
                    new String[] { String.valueOf(id) });
        }
    
    
    
    public ArrayList<String> search(String name) {
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor_loc = db.query(TABLE_CONTACTS, new String[] { KEY_LABID },
        		KEY_LOCATION + "=?", new String[] { name }, null, null, null, null);
        Cursor cursor_name = db.query(TABLE_CONTACTS, new String[] { KEY_LABID },
        		KEY_NAME + "=?", new String[] { name }, null, null, null, null);
        
        Cursor cursor_test = db.query(TABLE_TESTS, new String[] { KEY_LABIDS },
        		KEY_TNAME + "=?", new String[] { name }, null, null, null, null);
       
        ArrayList<String> name_ids = new ArrayList<String>();
        ArrayList<String> loc_ids = new ArrayList<String>();
        ArrayList<String> test_ids = new ArrayList<String>();
        
        // looping through all rows and adding to list
        if (cursor_loc.moveToFirst()) {
        	
        	do {
        		
        		loc_ids.add(cursor_loc.getString(cursor_loc.getColumnIndex(KEY_LABID)));
        		
            } while (cursor_loc.moveToNext());
        }
        
        if (cursor_test.moveToFirst()) {
        	do {
        		
        		String lab_ids = cursor_test.getString(cursor_test.getColumnIndex(KEY_LABIDS));
        		List<String> list_labIds = Arrays.asList(lab_ids.split(","));
        		test_ids.addAll(list_labIds);
        		
            } while (cursor_test.moveToNext());
        }
        
        if (cursor_name.moveToFirst()) {
        	do {
        		name_ids.add(cursor_name.getString(cursor_name.getColumnIndex(KEY_LABID)));
        		
            } while (cursor_name.moveToNext());
        }
        
        loc_ids.addAll(name_ids);
        loc_ids.addAll(test_ids);
        
        return loc_ids;
    }
    // Getting single contact
    /*
     * 
        		Contact contact = new Contact();
                
                contact.setID(Integer.parseInt(cursor_loc.getString(cursor_loc.getColumnIndex(KEY_LABID))));
                contact.setName(cursor_loc.getString(1));
                contact.setAddress(cursor_loc.getString(2));
                contact.setLocation(cursor_loc.getString(3));
                contact.setCity(cursor_loc.getString(4));
                contact.setPincode(cursor_loc.getString(5));
                contact.setDetails(cursor_loc.getString(6));
                contact.setDistance(cursor_loc.getString(7));
                contact.setTests(cursor_loc.getString(8));
                // Adding contact to list
                contactList.add(contact);
     
     * Contact getContact(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_LABID,
        		KEY_NAME, KEY_ADDRESS, KEY_LOCATION, KEY_CITY , KEY_PINCODE, KEY_DETAILS ,KEY_DISTANCE ,KEY_TESTS},
        		KEY_LOCATION + "=?", new String[] { name }, null, null, null, null);
        
        Contact contact = new Contact();
        if (cursor.getCount()>0){
            cursor.moveToFirst();
 
        //Contact contact = new Contact();
            contact.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_LABID))));
            contact.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            contact.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
            contact.setLocation(cursor.getString(cursor.getColumnIndex(KEY_LOCATION)));
            contact.setCity(cursor.getString(cursor.getColumnIndex(KEY_CITY)));
            contact.setPincode(cursor.getString(cursor.getColumnIndex(KEY_PINCODE)));
            contact.setDetails(cursor.getString(cursor.getColumnIndex(KEY_DETAILS)));
            contact.setDistance(cursor.getString(cursor.getColumnIndex(KEY_DISTANCE)));
        	contact.setTests(cursor.getString(cursor.getColumnIndex(KEY_TESTS)));
        }
        // return contact}
        return contact;
    }*/
     
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        	//Log.d(cursor.moveToFirst(),"sas");

        	do {
        		Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_LABID))));
                contact.setName(cursor.getString(1));
                contact.setAddress(cursor.getString(2));
                contact.setLocation(cursor.getString(3));
                contact.setCity(cursor.getString(4));
                contact.setPincode(cursor.getString(5));
                contact.setDetails(cursor.getString(6));
                contact.setDistance(cursor.getString(7));
                contact.setTests(cursor.getString(8));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
    
    
    
    
 // Getting All Tests
    public List<Test> getAllTests() {
        List<Test> testList = new ArrayList<Test>();
        String selectQuery = "SELECT  * FROM " + TABLE_TESTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        do {
        		Test test = new Test();
                
                test.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TESTID))));
                test.setName(cursor.getString(cursor.getColumnIndex(KEY_TNAME)));
                test.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                test.setRequirment(cursor.getString(cursor.getColumnIndex(KEY_REQUIREMENTS)));
                test.setlabId(cursor.getString(cursor.getColumnIndex(KEY_LABIDS)));
                // Adding contact to list
                testList.add(test);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return testList;
    }
    
    public Contact getLabDetails(int argId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	
       Cursor c = db.query(TABLE_CONTACTS, null, KEY_LABID + "="  + Integer.toString(argId) , null, null, null, null);
       
       
       Contact contact = new Contact();
       c.moveToFirst();
       int itemId = c.getInt( c.getColumnIndexOrThrow(KEY_LABID) );
     	contact.setID(itemId);
     	String name = c.getString(c.getColumnIndex(KEY_NAME));
     	contact.setName(name);
     	String location = c.getString(c.getColumnIndex(KEY_LOCATION));
     	contact.setLocation(location);
     	String tests = c.getString(c.getColumnIndex(KEY_TESTS));
     	contact.setTests(tests);
     	String add = c.getString(c.getColumnIndex(KEY_ADDRESS));
     	contact.setAddress(add);
     	String city = c.getString(c.getColumnIndex(KEY_CITY));
     	contact.setCity(city);
     	String pin = c.getString(c.getColumnIndex(KEY_PINCODE));
     	contact.setPincode(pin);
     	String det = c.getString(c.getColumnIndex(KEY_DETAILS));
     	contact.setDetails(det);
     	String dis = c.getString(c.getColumnIndex(KEY_DISTANCE));
     	contact.setDistance(dis);
     	
        return contact;
    }
    
    
    public Contact getLabDetailsbyName(String lab_name) {
    	SQLiteDatabase db = this.getReadableDatabase();
       
    	Cursor c = db.query(TABLE_CONTACTS, null, KEY_NAME + "=?", new String[] {lab_name} , null, null, null, null);
       
    	Contact contact = new Contact();
       
    	c.moveToFirst();
       
    	int itemId = c.getInt( c.getColumnIndexOrThrow(KEY_LABID) );
     	contact.setID(itemId);
     	String name = c.getString(c.getColumnIndex(KEY_NAME));
     	contact.setName(name);
     	String location = c.getString(c.getColumnIndex(KEY_LOCATION));
     	contact.setLocation(location);
     	String tests = c.getString(c.getColumnIndex(KEY_TESTS));
     	contact.setTests(tests);
     	String add = c.getString(c.getColumnIndex(KEY_ADDRESS));
     	contact.setAddress(add);
     	String city = c.getString(c.getColumnIndex(KEY_CITY));
     	contact.setCity(city);
     	String pin = c.getString(c.getColumnIndex(KEY_PINCODE));
     	contact.setPincode(pin);
     	String det = c.getString(c.getColumnIndex(KEY_DETAILS));
     	contact.setDetails(det);
     	String dis = c.getString(c.getColumnIndex(KEY_DISTANCE));
     	contact.setDistance(dis);
     	
        return contact;
    }
    
  
    public boolean isEmptyLabs(){
    	SQLiteDatabase db = this.getWritableDatabase();
    	String count = "SELECT count(*) FROM "+ TABLE_CONTACTS;
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
    
    public boolean isEmptyTests(){
    	SQLiteDatabase db = this.getWritableDatabase();
    	String count = "SELECT count(*) FROM "+ TABLE_TESTS;
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

    public void clearDb(){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTS);
        onCreate(db);
    }
    
    
    
    
  /*
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }
 
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }
 
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }*/
    
}
