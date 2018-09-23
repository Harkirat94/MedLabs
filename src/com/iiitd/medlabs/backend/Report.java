package com.iiitd.medlabs.backend;

public class Report  {

	 int _repId; 
	 String _emailId;
	 String _labId;
	 String _labName;
	 String _reportName;
	 String _reportLink;
	 String _time;
	 
        // constructor
	    public Report(int repId, String emailId, String labId, String labName, String reportName, String reportLink, String time){
	    	this._repId = repId;
	        this._emailId = emailId;
	        this._labId = labId;
	        this._labName = labName;
	        this._reportName = reportName;
	        this._reportLink = reportLink;
	        this._time = time;
	        
	    }
	   
	 // Empty constructor
	    public Report() {
		}
	    
	    // getting ID
	    public int getRepId(){
	        return this._repId;
	    }
	     
	    // setting ID
	    public void setRepId(int repId){
	        this._repId = repId;
	    }
	    
	    // getting ID
	    public String getEmailId(){
	        return this._emailId;
	    }
	     
	    // setting ID
	    public void setEmailId(String id){
	        this._emailId = id;
	    }
	    
	    public String getLabId(){
	        return this._labId;
	    }
	     
	    public void setLabId(String labId){
	        this._labId = labId;
	    }
	    
	    public String getLabName(){
	        return this._labName;
	    }

	    public void setLabName(String labName){
	        this._labName = labName;
	    }
	    
	    public String getReportName(){
	        return this._reportName;
	    }
	     
	    public void setReportName(String reportName){
	        this._reportName = reportName;
	    } 
	    
	    public String getReportLink(){
	        return this._reportLink;
	    }
	     
	    public void setReportLink(String reportLink){
	        this._reportLink = reportLink;
	    }
	    
	    public String getTime(){
	        return this._time;
	    }
	     
	    // setting name
	    public void setTime(String time){
	        this._time = time;
	    }
}
