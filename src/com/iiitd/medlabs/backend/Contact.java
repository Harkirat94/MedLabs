package com.iiitd.medlabs.backend;

public class Contact {
	 //private variables
    int _labId;
    String _name;
    String _address;
    String _location;
    String _city;
    String _pinCode;
    String _details;
    String _distance;
    String _tests;
    
    //String _phone_number;
    
    
    // Empty constructor
    public Contact(){
         
    }
    // constructor
    public Contact(int id, String name, String add, String loc, String city, String pin, String det, String dis , String tests){
        this._labId = id;
        this._name = name;
        this._address = add;
        this._location = loc;
        this._city = city;
        this._pinCode = pin;
        this._details = det;
        this._distance = dis;
        this._tests = tests;
    }
     
    // constructor  ???? here ???
    //public Contact(String name, String _phone_number){
      //  this._name = name;
        //this._phone_number = _phone_number;
   // }
    
    
    
    // getting ID
    public int getID(){
        return this._labId;
    }
     
    // setting id
    public void setID(int id){
        this._labId = id;
    }
     
    // getting Address
    public String getAddress(){
        return this._address;
    }
     
    // setting Address
    public void setAddress(String address){
        this._address = address;
    }
    
    // getting Details
    public String getDetails(){
        return this._details;
    }
     
    // setting Details
    public void setDetails(String Details){
        this._details = Details;
    }
    
    // getting Distance
    public String getDistance(){
        return this._distance;
    }
     
    // setting Distance
    public void setDistance(String Distance){
        this._distance= Distance;
    }
    
    // getting City
    public String getCity(){
        return this._city;
    }
     
    // setting city
    public void setCity(String City){
        this._city = City;
    }
    
    // getting Pincode
    public String getPincode(){
        return this._pinCode;
    }
     
    // setting Pincode
    public void setPincode(String Pincode){
        this._pinCode = Pincode;
    }
    
    // getting Location
    public String getLocation(){
        return this._location;
    }
     
    // setting Location
    public void setLocation(String Location){
        this._location = Location;
    }
    
    // getting Tests
    public String getTests(){
        return this._tests;
    }
     
    // setting name
    public void setTests(String Tests){
        this._tests = Tests;
    }
    
    // getting name
    public String getName(){
        return this._name;
    }
     
    // setting name
    public void setName(String name){
        this._name = name;
    }
}
