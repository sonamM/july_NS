package com.etouch.taf.util;

//package com.etouch.amazon.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;





import io.appium.java_client.android.AndroidDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.config.TestBedManagerConfiguration;

//import com.etouch.taf.mobile.appium.TafCapabilityType;

//import com.etouch.taf.core.TestBed;
//import com.etouch.taf.util.CommonUtil;

public class AppiumLauncher {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static void main(String args[]){
		System.out.println(getBootstrapPort("4723"));
	}
	
	public static boolean launchAppiumSession(TestBed testBed){
		try {
				CommandLine command = null;
				//CommandLine command = commandForWindows("4723", "4727", "0736c0c40b304c25");
				if(isWindows())
					command = commandForWindows(testBed);	
				
				else if(isMac() || isUnix() || isSolaris())				
					command = commandForMac(testBed);				
				
				if(command!=null)
					executeCommand(command);		
		
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
		
		return true;		
	}
	
	public static boolean closeAppiumSession()
	{
		CommonUtil.sop("Inside close Appium Session");
		try {
				CommandLine command = null;
				
				if(isWindows())
					command = killWindowsCommand();	
				
				else if(isMac() || isUnix() || isSolaris())				
					command = killMacCommand();				
				
				if(command!=null)
					executeCommand(command);		
	
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
	
	
		return true;		
	}
	
	private static void executeCommand(CommandLine command) {
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		try {
			
			executor.execute(command, resultHandler);			
			Thread.sleep(2000);
			
			 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
	}
	
	private static void testAndroidDriver() throws MalformedURLException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("version","43.0.2357.93");
		
		capabilities.setCapability("appium-version", "1.4.1");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "4.4.2");
		capabilities.setCapability("deviceName", "HT45FWM07753");
		//capabilities.setCapability("androidPackage","com.apple.samplecode.MapSearch");
		//capabilities.setCapability("androidActivity","com.apple.samplecode.MapSearch");
		
		//AppiumDriver driver = new IOSDriver (new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		AppiumDriver driver = new AndroidDriver (new URL("http://127.0.0.1:4733/wd/hub"),capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(3000);
		
		driver.get("http://www.amajon.com");
		
		//WebElement element = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIASearchBar[1]"));
		 //element.sendKeys("Coffee!");
		
	}
	private static void testIOSDriver() throws MalformedURLException, InterruptedException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("appium-version", "1.3.4");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "7.0.4");
		//capabilities.setCapability("deviceName", "eTouch's iPhone");
		capabilities.setCapability("deviceName", "iPad"); 
		//capabilities.setCapability("bundleId","com.apple.samplecode.MapSearch");
		capabilities.setCapability("bundleId","com.example.apple-samplecode.Recipes");
		
		//AppiumDriver driver = new IOSDriver (new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		AppiumDriver driver = new IOSDriver (new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(3000);
		
		//WebElement element = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIASearchBar[1]"));
		 //element.sendKeys("Coffee!");
	}
	
	private static CommandLine commandForWindows(TestBed testBed)
	{
		CommandLine command = new CommandLine("cmd");
		command.addArgument("/c");
		command.addArgument("\"" + TestBedManagerConfiguration.INSTANCE.getMobileConfig().getAppiumConfig().getNodePath() + "\"");
		command.addArgument("\"" + TestBedManagerConfiguration.INSTANCE.getMobileConfig().getAppiumConfig().getAppiumJSPath() + "\"");
		//command.addArgument("\"C:/Lavanya/Software/Appium/node.exe\"");
		//command.addArgument("\"C:/Lavanya/Software/Appium/node_modules/appium/bin/Appium.js\"");
		command.addArgument("--address",false);
		command.addArgument("127.0.0.1");
		command.addArgument("--port", false);
		command.addArgument(testBed.getPort());
		command.addArgument("-bp", false);
		command.addArgument(getBootstrapPort(testBed.getPort()));
		command.addArgument("-U",false);
		command.addArgument(testBed.getDevice().getUdid());
		command.addArgument("--no-reset",false);
		
		return command;
	}
	
	private static CommandLine killWindowsCommand()
	{
		CommandLine command = new CommandLine("cmd");
		command.addArgument("/c");
		command.addArgument("taskkill");
		command.addArgument("/F");
		command.addArgument("/IM");
		command.addArgument("node.exe");
		
		return command;
	}
	
	private static CommandLine killMacCommand()
	{
		CommandLine command = new CommandLine("/bin/bash");
		command.addArgument("killall");
		command.addArgument("node");
		return command;
	}
	
	private static CommandLine commandForMac(TestBed testBed)
	{
		//CommandLine command = new CommandLine("/Applications/Appium.app/Contents/Resources/node/bin/node");
		//command.addArgument("/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js");
		
		CommandLine command = new CommandLine(TestBedManagerConfiguration.INSTANCE.getMobileConfig().getAppiumConfig().getMacNodePath());
		command.addArgument(TestBedManagerConfiguration.INSTANCE.getMobileConfig().getAppiumConfig().getMacAppiumJSPath());
		
		//command.addArgument("/usr/local/bin/ideviceinstaller");
		command.addArgument("--address", false);
		command.addArgument("127.0.0.1");
		command.addArgument("--port", false);
		command.addArgument(testBed.getPort());
		command.addArgument("-bp", false);
		command.addArgument(getBootstrapPort(testBed.getPort()));
		command.addArgument("-U",false);
		//command.addArgument("b260151b7058491bd4dcd99396da691f045c1bbc");
		//command.addArgument("33399d5ab4d538526e42dc77db0b964904d4728a");
		command.addArgument(testBed.getDevice().getId());
		command.addArgument("--no-reset",false);
		
		return command;
	}

	private static String getBootstrapPort(String port) {
		// TODO Auto-generated method stub
		Integer bpPort =  Integer.valueOf(port) + 2;
		return bpPort.toString();
		
	}
	
	public static boolean isWindows() {
		 
		return (OS.indexOf("win") >= 0);
 
	}
 
	public static boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public static boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public static boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}		
	
}


