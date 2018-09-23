package com.iiitd.medlabs.backend;

import android.content.Context;

public class Reminder {
	
	int _id;
	String _title;
	String _emailId; 
	int _day;
	int _month;
	int _year;
	int _hour;
	int _minute;
	
	String _reminderInfo;
	
	public Reminder() {
		
	}
	public static final String[] MONTHS = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	public Reminder(String title, String emailId ,int day, int month, int year, int hour, int minute) {
		this._title = title;
		this._emailId = emailId;
		this._day = day;
		this._month = month;
		this._year = year;
		this._hour = hour;
		this._minute = minute;
	}
	
	public void setReminderInfo(int month, int year, int day, int hour, int min){
		//ReminderDatabaseHandler dbR = new ReminderDatabaseHandler(c);
		
		this._reminderInfo = Integer.toString(day) + " " + MONTHS[_month] + " " + Integer.toString(year) + " - " + Integer.toString(hour) + ":" +Integer.toString(min); 
	}
	
	public String getReminderInfo(){
		return this._reminderInfo;
	}
	
	public String setANDGETReminderInfo(int month, int year, int day, int hour, int min){
		//ReminderDatabaseHandler dbR = new ReminderDatabaseHandler(c);
		
		return Integer.toString(day) + " " + MONTHS[_month] + " " + Integer.toString(year) + " - " + Integer.toString(hour) + ":" +Integer.toString(min); 
	}
	
	public int getId() {
		return this._id;
	}
	 
	public void setId(int myid) {
		this._id = myid;
	}
	
	public String getTitle() {
		return this._title;
	}
	 
	public void setTitle(String title) {
		this._title = title;
	}
	
	public String getEmailId() {
		return this._emailId;
	}
	 
	public void setEmailId(String emailId) {
		this._emailId = emailId;
	}
	
	public int getDay() {
		return this._day;
	}
	
	public void setDay(int day) {
		this._day = day;
	}
	
	public int getMonth() {
		return this._month;
	}
	
	public void setMonth(int month) {
		this._month = month;
	}
	
	public int getYear() {
		return this._year;
	}
	
	public void setYear(int year) {
		this._year = year;
	}
	
	public int getHour() {
		return this._hour;
	}
	
	public void setHour(int hour) {
		this._hour = hour;
	}
	
	public int getMinute() {
		return this._minute;
	}
	
	public void setMinute(int minute) {
		this._minute = minute;
	}
}