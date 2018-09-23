package com.iiitd.medlabs.backend;

public class Test  {

	 int _testID;
	 String _name;
	 String _description;
	 String _requirment;
	 String _labIds;
	    
	    
	    // constructor
	    public Test(int id, String name, String des, String req, String labIds){
	        this._testID = id;
	        this._name = name;
	        this._description = des;
	        this._requirment = req;
	        this._labIds = labIds;
	        
	    }
	   
	 // Empty constructor
	    public Test(){
	         
	    }
	    
	    // getting ID
	    public int getID(){
	        return this._testID;
	    }
	     
	    // setting ID
	    public void setID(int id){
	        this._testID = id;
	    }
	     
	  
	    
	    // getting Description
	    public String getDescription(){
	        return this._description;
	    }
	     
	    // setting Description
	    public void setDescription(String Details){
	        this._description = Details;
	    }
	    
	    
	    // getting Requirment
	    public String getRequirment(){
	        return this._requirment;
	    }
	     
	    // setting Requirment
	    public void setRequirment(String Requirment){
	        this._requirment = Requirment;
	    }
	    
	    // getting labID
	    public String getlabId(){
	        return this._labIds;
	    }
	     
	    // setting Requirment
	    public void setlabId(String labID){
	        this._labIds = labID;
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
