package com.etouch.amazon.listener;

import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.internal.annotations.IListeners;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.CurrentTestBedOld;
import com.etouch.taf.core.config.TestBedConfig;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.driver.DriverManager;
import com.etouch.taf.core.exception.DefectException;
import com.etouch.taf.core.exception.DriverException;
import com.etouch.taf.util.AppiumLauncher;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ConfigUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.TestBedUtil;

public class TestListener implements ITestListener{

	public static HashMap<String, Thread> myThreads=new HashMap<String, Thread>(); 
	
	/** The log. */
	static Log log = LogUtil.getLog(TestListener.class);
	
	/** The page ur ls. */
	static public Properties pageURLs = null;
	
	/** The rally property file. */
	static public Properties rallyPropertyFile = null;
	
	/** The is initialize. */
	static boolean isInitialize=false;

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println(" resutls is here" + result);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println(" resutls is here" + result);
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void onStart(ITestContext context) {
		String testBedName=context.getCurrentXmlTest().getAllParameters().get("testBedName");
		//synchronized (this) {
	
    	try {
    		TestBedManager.INSTANCE.createDefectManager();
    		TestBedUtil.loadTestBedDetails(testBedName);
			Thread.sleep(5000);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	CommonUtil.sop(" Thread started running and came out of sync block");
		//Moved Driver created to CurrentTestBed Class	
		
	}

	@Override
	public void onFinish(ITestContext context) {
		log.info("Test Name :"+ context.getName() + " - End");
    	log.info("********Results*******");
		
	}
	

}
