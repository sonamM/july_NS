/*
 * 
 */
package com.etouch.taf.mobile.appium;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.android.AndroidDriver;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.logging.Log;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.internal.thread.ThreadUtil;

import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedConfig;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.exception.DriverException;
import com.etouch.taf.core.resources.DeviceType;
import com.etouch.taf.core.resources.PlatformType;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ConfigUtil;
import com.etouch.taf.util.LogUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class AppiumDriver.
 */
public class AppiumDriver {

	/** The log. */
	private static Log log = LogUtil.getLog(AppiumDriver.class);
	
	/**
	 * Builds the driver.
	 *
	 * @param testBed the test bed
	 * @return the remote web driver
	 * @throws DriverException the driver exception
	 */
	
	// for IOS driver
	public static IOSDriver buildIOSDriver(TestBed testBed) throws DriverException{
		IOSDriver driver = null;
		 TestBedManagerConfiguration tbMgrConfig=TestBedManagerConfiguration.getInstance();
		// startServer();
		try{
			DesiredCapabilities cap=createCapabilities(testBed);
			//startServer(testBed);
			
			CommonUtil.sop(" TestBed NNNAAAAAMMMMMEEEE : " + testBed.getTestBedName());
			//driver = new IOSDriver (new URL("http://"+ tbMgrConfig.getMobileConfig().getHub() +":" + tbMgrConfig.getMobileConfig().getPort() + "/wd/hub"),cap);
			String currentPort=testBed.getPort();
			
			
			/** If the testBed doesn't have a individual port, then get the common port from mobileConfig */
			if(currentPort==null || currentPort.length()== 0){
				currentPort=tbMgrConfig.getMobileConfig().getPort();
			}
			
			System.out.println(" Current Port is ---------------- " + currentPort);
			driver = new IOSDriver (new URL("http://"+ tbMgrConfig.getMobileConfig().getHub() +":" + currentPort + "/wd/hub"),cap);
			
		
		}catch (MalformedURLException e) {
			log.error("failed to create ios driver : " + e.getMessage());
			throw new DriverException("Could not create ios driver :: " + e.getMessage());
		}
		return driver;
	}	
	
	private static boolean startServer(TestBed testBed){
		try {
			Runtime.getRuntime().exec("/bin/bash export ANDROID_HOME=$HOME/Downloads/adt-bundle-mac-x86_64/sdk/");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 //CommandLine command = new CommandLine("/Applications/Appium.app/Contents/Resources/node/bin/node");
		//command.addArgument("/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js", false);
		CommandLine command =new CommandLine("/");
		command.addArgument("/Users/eTouch/.nvm/versions/v0.12.2/bin");
		command.addArgument("/Users/eTouch/node_modules/appium/bin/appium.js",false);
		command.addArgument("--address", false);
		command.addArgument("127.0.0.1");
		command.addArgument("--port", false);
		command.addArgument(testBed.getPort());
		command.addArgument("-bp", false);
		int bpPort= Integer.parseInt(testBed.getPort());
		bpPort=bpPort+2;
		command.addArgument(String.valueOf(bpPort));
		command.addArgument("-U",false);
		command.addArgument(testBed.getDevice().getUdid());
		command.addArgument("--no-reset");
		
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		try {
			executor.execute(command, resultHandler);
			CommonUtil.sop(resultHandler.getException().toString());
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return true;
		
	}
	
	///for android driver 
	
	public static  AndroidDriver buildAndroidDriver(TestBed testBed) throws DriverException{
		AndroidDriver driver = null;
		 TestBedManagerConfiguration tbMgrConfig=TestBedManagerConfiguration.getInstance();
		 
		try{
			DesiredCapabilities cap=createCapabilities(testBed);
			//startServer(testBed);
			CommonUtil.sop(" TestBed NNNAAAAAMMMMMEEEE : " + testBed.getTestBedName());
			String currentPort=testBed.getPort();
					//TestBedManager.INSTANCE.getCurrentTestBed().getPort();
			
			/** If the testBed doesn't have a individual port, then get the common port from mobileConfig */
			if(currentPort==null || currentPort.length()== 0){
				currentPort=tbMgrConfig.getMobileConfig().getPort();
			}
			driver = new AndroidDriver (new URL("http://"+ tbMgrConfig.getMobileConfig().getHub() +":" + currentPort + "/wd/hub"),cap);
			
		
		}catch (MalformedURLException e) {
			log.error("failed to create android driver : " + e.getMessage());
			throw new DriverException("Could not create android driver :: " + e.getMessage());
		}
		return driver;
	}
	/**
	 * Creates the capabilities.
	 *
	 * @param testBed the test bed
	 * @return the desired capabilities
	 */
	private static DesiredCapabilities createCapabilities(TestBed testBed) {
		DesiredCapabilities capabilities=null;
		if(testBed.getPlatform().getName().equalsIgnoreCase(PlatformType.Android.getName())){
			capabilities= createAndroidDriver(testBed);
		
		}else if(testBed.getPlatform().getName().equalsIgnoreCase(PlatformType.iOS.getName())){
			capabilities= createiOSDriver(testBed);
		}
		return capabilities;
		
	}

	/**
	 * Createi os driver.
	 *
	 * @param testBed the test bed
	 * @return the desired capabilities
	 */
	private static DesiredCapabilities createiOSDriver(TestBed testBed) {
		
		DesiredCapabilities capabilities=new DesiredCapabilities();
		
		capabilities.setCapability(TafCapabilityType.BUNDLE_ID, testBed.getApp().getBundleId());
		capabilities.setCapability(TafCapabilityType.BROWSER_NAME, testBed.getBrowser().getName());
		capabilities.setCapability(TafCapabilityType.VERSION, testBed.getBrowser().getVersion());
		capabilities.setCapability(TafCapabilityType.PLATFORM_NAME, testBed.getPlatform().getName());
		capabilities.setCapability("U", "auto");
		if(testBed.getApp().getAppPath()!=null){
			capabilities.setCapability(TafCapabilityType.APP, testBed.getApp().getAppPath());
		}
		
		
		if((testBed.getDevice().getType()!=null) && (testBed.getDevice().getType().equalsIgnoreCase(DeviceType.Simulator.toString())))
		{
			updateiOSSimulator(testBed,capabilities);
		}else {
			updateiOSDevice(testBed, capabilities);
			
		}
		return capabilities;
		
	}

	/**
	 * Updatei os device.
	 *
	 * @param testBed the test bed
	 * @param capabilities the capabilities
	 */
	private static void updateiOSDevice(TestBed testBed,DesiredCapabilities capabilities) {
		capabilities.setCapability(TafCapabilityType.UDID,testBed.getDevice().getUdid());
		capabilities.setCapability(TafCapabilityType.DEVICE_NAME,testBed.getDevice().getName());
	}

	/**
	 * Updatei os simulator.
	 *
	 * @param testBed the test bed
	 * @param capabilities the capabilities
	 */
	private static void updateiOSSimulator(TestBed testBed,DesiredCapabilities capabilities) {
		capabilities.setCapability(TafCapabilityType.DEVICE_NAME, testBed.getDevice().getName());
		
	}

	
	
	
	
	
	/**
	 * Creates the android driver.
	 *
	 * @param testBed the test bed
	 * @return the desired capabilities
	 */
	private static DesiredCapabilities createAndroidDriver(TestBed testBed) {
		
		DesiredCapabilities capabilities=new DesiredCapabilities();
		
		//Added for Android web testing
		capabilities.setCapability(TafCapabilityType.BROWSER_NAME, testBed.getBrowser().getName());
		capabilities.setCapability(TafCapabilityType.VERSION, testBed.getBrowser().getVersion());
		capabilities.setCapability(TafCapabilityType.ACCEPT_SSL_CERTS, true);
		
		capabilities.setCapability(TafCapabilityType.PLATFORM_NAME, testBed.getPlatform().getName());
		capabilities.setCapability(TafCapabilityType.APP_PACKAGE, testBed.getApp().getAppPackage()); 
		capabilities.setCapability(TafCapabilityType.APP_ACTIVITY, testBed.getApp().getAppActivity());
		if(testBed.getDevice().getType().equalsIgnoreCase(DeviceType.Emulator.toString()))
		{
			updateAndroidEmulator(testBed,capabilities);
		}else {
			updateAndroidDevice(testBed,capabilities);
		}
	
		return capabilities;
		
	}

	/**
	 * Update android device.
	 *
	 * @param testBed the test bed
	 * @param capabilities the capabilities
	 */
	private static void updateAndroidDevice(TestBed testBed,DesiredCapabilities capabilities) {
		capabilities.setCapability(TafCapabilityType.DEVICE_NAME,testBed.getDevice().getName());
		
		
	}

	/**
	 * Update android emulator.
	 *
	 * @param testBed the test bed
	 * @param capabilities the capabilities
	 */
	private static void updateAndroidEmulator(TestBed testBed,DesiredCapabilities capabilities) {
		capabilities.setCapability(TafCapabilityType.DEVICE_NAME,testBed.getDevice().getName());
		// have to find out a way how to prioritize the given device 
		capabilities.setCapability(TafCapabilityType.DEVICE_ID,testBed.getDevice().getId());
		
		
	}
	
	
	 private static TestBed loadTestBedDetails(String testBedName){
			
			TestBed currentTestBed=null;
			TestBedManagerConfiguration testBedMgrConfig=TestBedManagerConfiguration.getInstance();
			if(ConfigUtil.isWebTestTypeEnabled()){
				for(TestBedConfig tbConfig:testBedMgrConfig.getWebConfig().getTestBeds()){
					
					CommonUtil.sop(" Current TestBedName " + testBedName + "tbConfig TestBedName " +tbConfig.getTestBedName() );
					if(tbConfig.getTestBedName().equalsIgnoreCase(testBedName)){
						currentTestBed=copyTestBedDetails(tbConfig);
						break;
					}
				}
			}
			if(ConfigUtil.isMobileTestTypeEnabled()){
			if(currentTestBed == null){
					for(TestBedConfig tbConfig:testBedMgrConfig.getMobileConfig().getTestBeds()){
						if(tbConfig.getTestBedName().equalsIgnoreCase(testBedName)){
							currentTestBed=copyTestBedDetails(tbConfig);
							break;
					}
				}
			}
			}
			return currentTestBed;
		}
		
	  
	    
	    
		/**
		 * Copy test bed details.
		 *
		 * @param testBedConfig the test bed config
		 * @return the test bed
		 */
		private static TestBed copyTestBedDetails(TestBedConfig testBedConfig){
			TestBed currentTestBed = new TestBed();
			if(testBedConfig!=null){
				
				currentTestBed.setTestBedName(testBedConfig.getTestBedName());
				
				currentTestBed.setPlatform(testBedConfig.getPlatform());
				currentTestBed.setBrowser(testBedConfig.getBrowser());
				currentTestBed.setApp(testBedConfig.getApp());
				currentTestBed.setDevice(testBedConfig.getDevice());
				currentTestBed.setTestBedName(testBedConfig.getTestBedName());
				currentTestBed.setTestbedClassName(testBedConfig.getTestbedClassName());
				currentTestBed.setPort(testBedConfig.getPort());

				
			}
			
			return currentTestBed;
		}
	
//	public static io.appium.java_client.AppiumDriver buildAppiumDriver(DesiredCapabilities capabilities) throws DriverException
//	{
//		
//		io.appium.java_client.AppiumDriver appiumDriver=new io.appium.java_client.AppiumDriver(new URL(""), capabilities);
//		
//		
//	}


}
