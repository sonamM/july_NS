package com.etouch.taf.core.config;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.TestSuiteManager;
import com.etouch.taf.core.driver.DriverBuilder;
import com.etouch.taf.core.driver.DriverManager;
import com.etouch.taf.core.exception.DriverException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ConfigUtil;
import com.etouch.taf.util.TestBedUtil;

import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlTest;

public  class CurrentTestBedOld extends Thread{
	
	public CurrentTestBedOld(){
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TestBed testBed=null;
	
	
	public String getTestBedName(){
		return this.getTestBed().getTestBedName();
	}


	private TestBed getTestBed() {
		return this.testBed;
	}


	public void setTestBed(TestBed testBed) {
		this.testBed = testBed;
	}
	
	public TestBed getCurrentTestBed(){
		return getTestBed();
	}
	
//	public  CurrentTestBed(String testBedName){
//		Thread t=new Thread(this,testBedName);
//		t.start();
//		t.setPriority(Thread.MAX_PRIORITY);
//		 this.testBed=TestBedUtil.loadTestBedDetails(testBedName);
//	}
//	
//	public  CurrentTestBed(TestBed testBed){
//		Thread t=new Thread(this,testBed.getTestBedName());
//		t.start();
//		t.setPriority(Thread.MAX_PRIORITY);
//		this.testBed=testBed;
//	}
	
	@Override
	public void run()
	   {
	      try
	      {	
	    	  	synchronized(this.testBed){
	    	  		CommonUtil.sop(" Current Test Bed Information now : "+ this.testBed.getTestBedName());
	    	  		DriverBuilder db=DriverManager.buildDriver(this.testBed.getTestBedName());
	    	  		Object driver=db.getDriver();
	    		  this.testBed.setDriver(driver);
	    		  
	    		  TestBedManager.INSTANCE.getCurrentTestBeds().put(this.testBed.getTestBedName(), this.testBed);
	    		  Thread.sleep(18000);
	    	  	
	    	  	}
	    	  //}
	         
	        
	     }
	      catch(DriverException e)
		     {
		        System.out.println("Driver is not created");
		     }
	     catch(InterruptedException e)
	     {
	        System.out.println("my thread interrupted");
	     }
	    
	   }


	
    
}
