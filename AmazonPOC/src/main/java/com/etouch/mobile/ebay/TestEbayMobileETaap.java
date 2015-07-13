package com.etouch.mobile.ebay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.etouch.amazon.common.BaseTest;
//import com.etouch.amazon.common.TestAmazonUnit;
import com.etouch.amazon.pages.AmazonMainPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.tools.rally.VideoRecorder;
import com.etouch.taf.util.AppiumLauncher;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.util.TestUtil;
import com.etouch.taf.webui.selenium.MobileElement;
import com.etouch.taf.webui.selenium.MobileView;
import com.etouch.taf.webui.selenium.WebElement;
import com.etouch.taf.webui.selenium.WebPage;




public class TestEbayMobileETaap  extends BaseTest {
	
	static Log log=LogUtil.getLog(TestEbayMobile.class);
	
	/** The url. */
	//private String url = "http://www.amazon.com/";
	
	/** The web page. */
	private WebPage webPage;
	
	/** The main page. */
	private AmazonMainPage mainPage;

	//required for jira
	/** The url3. */
	String url3 =TestBedManager.INSTANCE.getDefectConfig().getUrl3();
	
	/** The issue url. */
	String issueUrl = TestBedManager.INSTANCE.getDefectConfig().getIssueUrl();
	
	/** The username. */
	String username = TestBedManager.INSTANCE.getDefectConfig().getUsername();
	
	/** The password. */
	String password = TestBedManager.INSTANCE.getDefectConfig().getPassword();
	
	/** The keys. */
	String keys = TestBedManager.INSTANCE.getDefectConfig().getKeys();
	
	//required for rally
	/** The Constant DEFECT_PROP. */
	private static final String DEFECT_PROP = null;
	
	/** The Constant STORY_ID. */
	private static final String STORY_ID = null;
	
	/** The project id. */
	String PROJECT_ID = TestBedManager.INSTANCE.getDefectConfig().getProjectId();
	
	/** The defect owner. */
	String DEFECT_OWNER =TestBedManager.INSTANCE.getDefectConfig().getDefectOwner();
	
	/** The workspace id. */
	String WORKSPACE_ID  =TestBedManager.INSTANCE.getDefectConfig().getWorkspaceId();
	
	VideoRecorder videoRecorder = null;
	
	AppiumDriver driver=null;
	
	MobileView mobileView=null;
	
	String emailID;
	TouchAction act1;
	
	DesiredCapabilities capabilities;
	Logger logger = Logger.getLogger(TestEbayMobile.class.getName());
	String propFilePath ="..\\AmazonPOC\\src\\test\\resources\\signInRegistration.properties";
	Properties props;
	String deviceName;
	String plateformVersion;
	String platformName;
	String udid;
	String appPackage;
	String appActivity;
	String appiumHost;
	String appiumPortNumber;		
	String apkPath;
	String testBedName;
	TestBed testBed;
	
	
	
	
	/**
	 * Prepare before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public synchronized void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			
			videoRecorder = new VideoRecorder("..\\AmazonPOC\\src\\test\\resources\\testdata\\videos\\");
			
			testBedName=context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
		
			
			
			testBed=TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			Thread.sleep(20000);
			if(testBed.getDevice().getName().startsWith("i")){
				driver=(IOSDriver)(testBed.getDriver());
				
			}
			else{
				driver=(AndroidDriver)(testBed.getDriver());
			}
			mobileView=new MobileView(context);
			
			props = new Properties();	
			props.load(new FileInputStream(propFilePath));
			
			deviceName =testBed.getDevice().getName();
			
			CommonUtil.sop("DEVICE NAME IN TESTEBAYMOBILE IS " + deviceName);
			
			act1 = new TouchAction((MobileDriver) driver);
		}

		catch (Exception e) {
			CommonUtil.sop("errr is for" +testBedName + " -----------"+ e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}
	
	
	
	
	
	@Test
	public void ebayRegisterInTest() {

		try{
			log.info("ebayRegisterInTest test case is in progress...");
			
			CommonUtil.sop(" Current Device Name " + deviceName);
			Thread.sleep(12000);			
			WebElement registerButton=null;
			WebElement signInButton;
			
			
			
			WebElement firstNameTextBox;
			WebElement lastNameTextBox;
			WebElement emailTextBox;
			WebElement passwordTextBox;
			WebElement confirmPasswordTextBox;
			WebElement submitButton;
			if(deviceName.equals("iPadNative")){
				mobileView.findObjectByxPath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIASearchBar[1]").sendKeys("Coffee");
				//mobileView.findObjectByxPath("//UIApplication[1]/UIAWindow[2]/UIAKeyboard[1]/UIAButton[4]").click();
				/* WebElement element = driver.findElement(By.xpath());
				 element.sendKeys("Coffee!");
				 Thread.sleep(2000);
				 
				 
				 //This code is with eTaap- which needs to be updated
				 WebElement button= driver.findElement(By.xpath(
							"//UIApplication[1]/UIAWindow[2]/UIAKeyboard[1]/UIAButton[4]"));
				button.click();

				 
				 WebElement searchElement=driver.findElement(By.xpath("//UIApplication[1]/UIAWindow[2]/UIAKeyboard[1]/UIAButton[4]"));
				 searchElement.click();
				 
				 Thread.sleep(2000);
				 
				 driver.findElement(By.xpath(
							"//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[6]/UIAStaticText[1]")).click();
				// WebElement resultElement=driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[6]/UIAStaticText[1]"));
				// resultElement.click();
				 
				 Thread.sleep(2000);
				 WebElement mapElement=driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAMapView[1]/UIAPopover[1]/UIAStaticText[1]"));
				 mapElement.click();
				 CommonUtil.sop("map Result " + mapElement.getText());
				 AssertJUnit.assertEquals("Starbucks" ,mapElement.getText());*/
				
			}
			
			
			//if this is not samsung tab
				if(deviceName.equals("Nexus_5")){
				//clicking on register button
				log.info("Clicking on register button on ebay app home page");
				mobileView.findObjectById("com.ebay.mobile:id/button_register").tap(1, 3);
				Thread.sleep(3000);
				
				//Singin Button
				mobileView.findObjectById("com.ebay.mobile:id/register_button").tap(1, 3);
				Thread.sleep(5000);
				
				//FirstName
				firstNameTextBox=(MobileElement) mobileView.findObjectByxPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[3]/android.widget.EditText[1]");
				Thread.sleep(5000);
				firstNameTextBox.sendKeys(props.getProperty("FIRSTNAME"));
					
				Thread.sleep(3000);
				
				lastNameTextBox=(MobileElement) mobileView.findObjectByxPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[6]/android.widget.EditText[1]");
				Thread.sleep(5000);
				firstNameTextBox.sendKeys(props.getProperty("LASTNAME"));
					
				Thread.sleep(3000);
				
				emailTextBox=(MobileElement) mobileView.findObjectByxPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[9]/android.widget.EditText[1]");
				Thread.sleep(3000);
				mobileView.getDriver().hideKeyboard();
				
				Thread.sleep(5000);
				Random randomno = new Random();
				emailID = "TEST_"+randomno.nextInt(1000000)+"@gmail.com";
				emailTextBox.sendKeys(emailID);
				Thread.sleep(5000);
				
				passwordTextBox=(MobileElement) mobileView.findObjectByxPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[12]/android.widget.EditText[1]");
				mobileView.getDriver().hideKeyboard();
				Thread.sleep(5000);
				passwordTextBox.sendKeys(props.getProperty("PASSWORD"));
				mobileView.getDriver().hideKeyboard();
				Thread.sleep(5000);
				
				/*mobileView.findObjectById("com.ebay.mobile:id/button_register").click();
				Thread.sleep(3000);
				
				mobileView.findObjectById("com.ebay.mobile:id/button_register").click();
				Thread.sleep(3000);
				
				mobileView.findObjectById("com.ebay.mobile:id/button_register").click();
				Thread.sleep(3000);
				
				mobileView.findObjectById("com.ebay.mobile:id/button_register").click();
				Thread.sleep(3000);*/
				
				/*registerButton = driver.findElementById("com.ebay.mobile:id/button_register");
				//registerButton = (WebElement) driver.findElementByAndroidUIAutomator("UiSelector().text(\"Register\")");
				registerButton.click();
				Thread.sleep(5000);
				
				//clicking on get started button
				log.info("Clicking on Get Started button on sign in page");			
				signInButton = driver.findElementById("com.ebay.mobile:id/register_button");
				//signInButton = (WebElement) driver.findElementByAndroidUIAutomator("UiSelector().text(\"Get started\")");
				signInButton.click();
				Thread.sleep(5000);				
				
				//enter first name
				log.info("Entering Fist Name on sign in registration page");
				
				firstNameTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[3]/android.widget.EditText[1]");
				Thread.sleep(5000);
				firstNameTextBox.sendKeys(props.getProperty("FIRSTNAME"));
				Thread.sleep(5000);
				
				//enter last name
				log.info("Entering Last Name on sign in registration page");
				lastNameTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[6]/android.widget.EditText[1]");
				Thread.sleep(5000);
				lastNameTextBox.sendKeys(props.getProperty("LASTNAME"));
				Thread.sleep(5000);
				
				
				//enter email id
				log.info("Entering Email ID on sign in registration page");
				emailTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[9]/android.widget.EditText[1]");
				driver.hideKeyboard();
				Thread.sleep(5000);
				Random randomno = new Random();
				emailID = "TEST_"+randomno.nextInt(1000000)+"@gmail.com";
				emailTextBox.sendKeys(emailID);
				Thread.sleep(5000);
				
				//enter password
				log.info("Entering Password on sign in registration page");
				passwordTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[12]/android.widget.EditText[1]");
				driver.hideKeyboard();
				Thread.sleep(5000);
				passwordTextBox.sendKeys(props.getProperty("PASSWORD"));
				driver.hideKeyboard();
				Thread.sleep(5000);
				
				//enter confirm password
				log.info("Entering Confirm Password on sign in registration page");
				confirmPasswordTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[16]/android.widget.EditText[1]");
				Thread.sleep(5000);
				confirmPasswordTextBox.sendKeys(props.getProperty("PASSWORD"));
				driver.hideKeyboard();
				Thread.sleep(5000);
				
				//scroll to submit button
				log.info("Scrolling to sign in button on sign in registration page");
				driver.swipe(15, 1464, 15, 900, 2000);
				Thread.sleep(5000);
				
				//click on submit button 
				log.info("Clicking on submit button on sign in registration page");
				submitButton = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.view.View[18]/android.widget.Button[1]");
				submitButton.click();
				Thread.sleep(5000);		*/	
			}
		
			//click on continue button 
			log.info("Clicking on continue button on Welcome Registeration page");
		}catch(Exception e){
			log.info("ebayRegisterInTest method as an exception");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
		
		}
		log.info("ebayRegisterInTest test case is done...");
	}
	
	//@AfterClass
	public void after()
	{
		TestUtil.closeMobileDriver(mobileView.getDriver());
		AppiumLauncher.closeAppiumSession();
		
	}
	

	
}




