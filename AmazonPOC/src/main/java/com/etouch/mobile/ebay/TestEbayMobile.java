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
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.etouch.amazon.common.BaseTest;
//import com.etouch.amazon.common.TestAmazonUnit;
import com.etouch.amazon.pages.AmazonMainPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.tools.rally.VideoRecorder;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;



public class TestEbayMobile  extends BaseTest {
	
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
	
	String emailID;
	TouchAction act1;
	
	DesiredCapabilities capabilities;
	Logger logger = Logger.getLogger(TestEbayMobile.class.getName());
	String propFilePath ="/Users/eTouch/eTaapGit/test-automation-version1/AmazonPOC/src/test/resources/signInRegistration.properties";
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
			
			
			TestBed testBed=TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			Thread.sleep(9000);
			if(testBed.getDevice().getName().startsWith("i")){
				driver=(IOSDriver)(testBed.getDriver());	
			}
			else{
				driver=(AndroidDriver)(testBed.getDriver());
			}
			
			
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
			WebElement registerButton;
			WebElement signInButton;
			
			
			
			WebElement firstNameTextBox;
			WebElement lastNameTextBox;
			WebElement emailTextBox;
			WebElement passwordTextBox;
			WebElement confirmPasswordTextBox;
			WebElement submitButton;
			if(deviceName.equals("iPhone")){
				for(int i=0;i<15;i++){
					CommonUtil.sop("iPHONEEEEEEEEEEEEEEEEE");
				}
				
				 WebElement element = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIASearchBar[1]"));
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
				 AssertJUnit.assertEquals("Starbucks" ,mapElement.getText());
				
			}
			
			
			//if this is not samsung tab
			if(deviceName.equals("Nexus_5")){
				//clicking on register button
				log.info("Clicking on register button on ebay app home page");
				registerButton = driver.findElementById("com.ebay.mobile:id/button_register");
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
				Thread.sleep(5000);			
			}
			if (deviceName.equals("HTC One_M8")){
				
				//clicking on register button
				log.info("Clicking on register button on ebay app home page");
				//act1.tap(730, 985).perform();
				//Thread.sleep(5000);
				List<WebElement> buttons = driver.findElementsByClassName("android.widget.Button");
				System.out.println("text is - "+buttons.get(1).getAttribute("text"));
				act1.tap(buttons.get(1)).perform();
				buttons.get(1).click();	
			    //registerButton = (WebElement) driver.findElementByAndroidUIAutomator("UiSelector().text(\"Register\")");
			   // registerButton.click();
				Thread.sleep(5000);
				
				//clicking on get started button
				log.info("Clicking on Get Started button on sign in page");			
				//act1.tap(24, 741).perform();
				List<WebElement> buttons2 = driver.findElementsByClassName("android.widget.Button");
				buttons2.get(0).click();				
				Thread.sleep(5000);	
				
				//enter first name
				log.info("Entering First Name on sign in registration page");
				
				firstNameTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[3]/android.widget.EditText[1]");
				Thread.sleep(5000);
				firstNameTextBox.sendKeys(props.getProperty("FIRSTNAME"));
				Thread.sleep(5000);
				
				//enter last name
				log.info("Entering Last Name on sign in registration page");
				lastNameTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[6]/android.widget.EditText[1]");
				Thread.sleep(5000);
				lastNameTextBox.sendKeys(props.getProperty("LASTNAME"));
				Thread.sleep(5000);
				
				
				//enter email id
				log.info("Entering Email ID on sign in registration page");
				emailTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[9]/android.widget.EditText[1]");
				driver.hideKeyboard();
				Thread.sleep(5000);
				Random randomno = new Random();
				emailID = "TEST_"+randomno.nextInt(1000000)+"@gmail.com";
				emailTextBox.sendKeys(emailID);
				Thread.sleep(5000);
				
				//enter password
				log.info("Entering Password on sign in registration page");
				passwordTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[12]/android.widget.EditText[1]");
				driver.hideKeyboard();
				Thread.sleep(5000);
				passwordTextBox.sendKeys(props.getProperty("PASSWORD"));
				driver.hideKeyboard();
				Thread.sleep(5000);
				
				//enter confirm password
				log.info("Entering Confirm Password on sign in registration page");
				confirmPasswordTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[16]/android.widget.EditText[1]");
				Thread.sleep(5000);
				confirmPasswordTextBox.sendKeys(props.getProperty("PASSWORD"));
				driver.hideKeyboard();
				Thread.sleep(5000);
				
				//click on submit button 
				log.info("Clicking on submit button on sign in registration page");
				submitButton = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[18]/android.widget.Button[1]");
				submitButton.click();
				Thread.sleep(5000);
			}
			if (deviceName.equals("SM-T210R")){
				//clicking on register button
				log.info("Clicking on register button on ebay app home page");
				//act1.tap(448, 402).perform();
				registerButton = driver.findElementById("com.ebay.mobile:id/button_register");
				//registerButton = (WebElement) driver.findElementByAndroidUIAutomator("UiSelector().text(\"Register\")");
				registerButton.click();
				Thread.sleep(5000);
				
				//clicking on get started button
				log.info("Clicking on Get Started button on sign in page");			
				//act1.tap(33, 255).perform();
				//signInButton = driver.findElementById("com.ebay.mobile:id/register_button");
				
				//signInButton = (WebElement) driver.findElementByAndroidUIAutomator("UiSelector().text(\"Get started\")");
				//signInButton.click();
				Thread.sleep(5000);	
				
				//enter first name
				log.info("Entering Fist Name on sign in registration page");
				
				firstNameTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[3]/android.widget.EditText[1]");
				Thread.sleep(5000);
				firstNameTextBox.sendKeys(props.getProperty("FIRSTNAME"));
				Thread.sleep(5000);
				
				//enter last name
				log.info("Entering Last Name on sign in registration page");
				lastNameTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[6]/android.widget.EditText[1]");
				Thread.sleep(5000);
				lastNameTextBox.sendKeys(props.getProperty("LASTNAME"));
				Thread.sleep(5000);
				
				
				//enter email id
				log.info("Entering Email ID on sign in registration page");
				emailTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[9]/android.widget.EditText[1]");
				driver.hideKeyboard();
				Thread.sleep(5000);
				Random randomno = new Random();
				emailID = "TEST_"+randomno.nextInt(1000000)+"@gmail.com";
				emailTextBox.sendKeys(emailID);
				Thread.sleep(5000);
				
				//enter password
				log.info("Entering Password on sign in registration page");
				passwordTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[12]/android.widget.EditText[1]");
				driver.hideKeyboard();
				Thread.sleep(5000);
				passwordTextBox.sendKeys(props.getProperty("PASSWORD"));
				driver.hideKeyboard();
				Thread.sleep(5000);
				
				//enter confirm password
				log.info("Entering Confirm Password on sign in registration page");
				confirmPasswordTextBox = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[16]/android.widget.EditText[1]");
				Thread.sleep(5000);
				confirmPasswordTextBox.sendKeys(props.getProperty("PASSWORD"));
				driver.hideKeyboard();
				Thread.sleep(5000);
				
				//click on submit button 
				log.info("Clicking on submit button on sign in registration page");
				submitButton = driver.findElementByXPath("//android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.View[1]/android.view.View[18]/android.widget.Button[1]");
				submitButton.click();
				Thread.sleep(5000);				
			}

			//scroll to continue button
			if (!deviceName.equals("SM-T210R") && !deviceName.equals("iPhone")){
				log.info("Scrolling to continue button on sign in registration page");
				driver.swipe(15, 1464, 15, 100, 2000);
				Thread.sleep(5000);	
			}
			//click on continue button 
			log.info("Clicking on continue button on Welcome Registeration page");
			driver.findElementByName("Continue Link").click();
			Thread.sleep(5000);
			//driver.findElementByXPath("//android.widget.FrameLayout[1]//android.widget.FrameLayout[1]//android.webkit.WebView[1]//android.view.View[1]/android.view.View[3]//android.view.View[1]").click();
			
			//click on back button
			log.info("Clicking on back button");
			//driver.sendKeyEvent(AndroidKeyCode.BACK);
			Thread.sleep(5000);
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
	
	//@Test(priority=2, enabled=true)
	public void ebaySignInTest() {
		log.info("ebaySignInTest test case is in progress...");
		CommonUtil.sop(" Current Device Name " + deviceName);
		try{
			if(deviceName.equals("iPhone")){
				for(int i=0;i<10;i++){
					System.out.println("ebaySignInTest");
				}
			}
			else{
			//click on sign in button
			log.info("Clicking on Sign In button on ebay app home page");
			WebElement signInButton = driver.findElementById("com.ebay.mobile:id/button_sign_in");
			Thread.sleep(5000);
			signInButton.click();
			Thread.sleep(5000);
			
			//enter email id
			log.info("Entering Email ID on sign in page");
			WebElement emailTextBox = driver.findElementById("com.ebay.mobile:id/sign_in_username_entry");
			emailTextBox.click();
			Thread.sleep(5000);
			emailTextBox.clear();
			Thread.sleep(5000);
			emailTextBox.sendKeys(emailID);
			Thread.sleep(5000);
			
			//enter password
			log.info("Entering Password on sign in page");
			WebElement passwordTextBox = driver.findElementById("com.ebay.mobile:id/sign_in_password_entry");
			passwordTextBox.click();
			Thread.sleep(5000);
			passwordTextBox.clear();
			Thread.sleep(5000);
			passwordTextBox.sendKeys(props.getProperty("PASSWORD"));
			Thread.sleep(5000);
			
			//click on sign in button
			log.info("Clicking on Sign In button on sign in page");
			WebElement sgnInButton = driver.findElementById("com.ebay.mobile:id/sign_in_button");
			Thread.sleep(5000);
			sgnInButton.click();
			Thread.sleep(5000);
			}
			
		}catch(Exception e){
			log.info("ebaySignInTest method as an exception");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
		
		}
		log.info("ebaySignInTest test case is done...");
	}	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//@Test(priority=3, enabled=true)
	public void ebaySearchProductByKeyword() {
		log.info("ebaySearchProductByKeyword test case is in progress...");
		CommonUtil.sop(" Current Device Name " + deviceName);
		try {
			if(deviceName.equals("iPhone")){
				for(int i=0;i<10;i++){
					System.out.println("ebaySearchProductByKeyword");
				}
			}
			else{
			//typing in search box
			log.info("Searching some valid keyword");
			WebElement searchTextBox = driver.findElementById("com.ebay.mobile:id/home_search_text");
			Thread.sleep(5000);
			searchTextBox.click();
			Thread.sleep(5000);
			searchTextBox = driver.findElementById("android:id/search_src_text");
			Thread.sleep(5000);
			searchTextBox.sendKeys("samsung 4");
			Thread.sleep(5000);
			
			//click on search go button
			log.info("Clicking on search go button on ebay app home page");
			WebElement searchGoImage = driver.findElementById("android:id/search_go_btn");
			Thread.sleep(5000);
			searchGoImage.click();
			Thread.sleep(5000);

			//click on fist product on searched result page
			log.info("Clicking on fist product on searched result page");
			WebElement searchFirstProduct = driver.findElementById("com.ebay.mobile:id/image");
			Thread.sleep(5000);
			searchFirstProduct.click();
			Thread.sleep(5000);
			
			//clicking the product image
			log.info("Clicking the product image");
			WebElement productImage = driver.findElementById("com.ebay.mobile:id/collection_bounds");
			Thread.sleep(5000);
			productImage.click();
			Thread.sleep(5000);
			
			//Swiping product image
			log.info("Swiping product image");
			if (deviceName.equals("SM-T210R")){	
				driver.swipe(500, 500, 0, 500, 1000);
				Thread.sleep(5000);
				driver.swipe(500, 500, 0, 500, 1000);
			}
			else{				
				driver.swipe(1000, 100, 0, 100, 1000);
				Thread.sleep(5000);
				driver.swipe(1000, 100, 0, 100, 1000);
			}
			//zooming and pinching the product image
			log.info("Zooming the product image");
			WebElement prodImage = driver.findElementById("com.ebay.mobile:id/photo_gallery");
			Thread.sleep(5000);
			driver.zoom(prodImage);
			Thread.sleep(5000);
			log.info("Pinching the product image");
			driver.pinch(prodImage);
			Thread.sleep(5000);
			
			log.info("Going back to home page");
			
			//driver.sendKeyEvent(AndroidKeyCode.BACK);
			Thread.sleep(5000);
			//driver.sendKeyEvent(AndroidKeyCode.BACK);
			Thread.sleep(5000);
			//driver.sendKeyEvent(AndroidKeyCode.BACK);
			Thread.sleep(5000);
			}
		}catch(Exception e){
			log.info("ebaySearchProductByKeyword method as an exception");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
		
		}		
		log.info("ebaySearchProductByKeyword test case is done...");
	}
	
	
	
	
	
	
//@Test(priority=4, enabled=true)
	public void ebaySettingTest() {
		log.info("ebaySettingTest test case is in progress...");
		CommonUtil.sop(" Current Device Name " + deviceName);
		try{
			if(deviceName.equals("iPhone")){
				for(int i=0;i<10;i++){
					System.out.println("ebaySettingTest");
				}
			}
			else{
			//scroll to settings
			log.info("Scroll to settings on ebay app home page");
			driver.scrollTo("Settings");
			Thread.sleep(5000);
		
			//clicking on  settings
			log.info("Clicking on settings on ebay app home page");
			WebElement settingsTextBox = driver.findElementById("com.ebay.mobile:id/home_settings");
			settingsTextBox.click();
			Thread.sleep(5000);
			
			//tapping on country and region
			log.info("Tapping on country region option");
			WebElement countryRegionTextBox = driver.findElementByName("Country / region");
			countryRegionTextBox.click();
			Thread.sleep(5000);
			//TouchAction act1 = new TouchAction((MobileDriver) driver);
            act1.tap(driver.findElementById("android:id/switchWidget")).perform();
            Thread.sleep(5000);
            act1.tap(driver.findElementById("android:id/switchWidget")).perform();
            Thread.sleep(5000);
            
            //clicking on back button
            log.info("Clicking on back button");
           // driver.sendKeyEvent(AndroidKeyCode.BACK);
            Thread.sleep(5000);
            
            //clicking on privacy
            log.info("Clicking on privacy option");
            WebElement privacyTextBox = driver.findElementByName("Privacy");
            privacyTextBox.click();
            Thread.sleep(5000);
            
            //clicking on ebay link
            log.info("Now we are moving from Native to WebView Mode");
            if(deviceName.equals("HTC One_M8")){
            	act1.tap(186, 513).perform();
            }
            if(deviceName.equals("SM-T210R")){
            	act1.tap(430, 138).perform();
            }
            if(deviceName.equals("Nexus_5")){
	            WebElement ebayLink = driver.findElementByName("eBay Link");
	            //WebElement ebayLink = (WebElement) driver.findElementByAndroidUIAutomator("UiSelector().description(\"eBay Link\")");
	            ebayLink.click();
            }
            Thread.sleep(6000);

            
            //clicking on search box
            log.info("Clicking on Search Text Box in Browser");
            WebElement mobileWebSearchTextBox = driver.findElementByClassName("android.widget.EditText");
            mobileWebSearchTextBox.click();
            Thread.sleep(5000);
            
            //searching some valid keyword
            if(!deviceName.equals("SM-T210R")){
	            log.info("Searching some valid keyword");
	            mobileWebSearchTextBox = driver.findElementByClassName("android.widget.EditText");
	            mobileWebSearchTextBox.click();
	            Thread.sleep(5000);
            }
	        mobileWebSearchTextBox.sendKeys("iphone");
	        Thread.sleep(5000);
		  //  driver.sendKeyEvent(AndroidKeyCode.ENTER);
		    Thread.sleep(6000);
	        
            //navigating back in browser
            log.info("Navigating back in browser");
            driver.navigate().back();
            Thread.sleep(5000);
            log.info("Navigating back in browser");
            driver.navigate().back();
            Thread.sleep(5000);
            
            //switching back to ebay app
            log.info("Switching back to eBay app");
            driver.context("NATIVE_APP");
            Thread.sleep(5000);
            
            //click on back button
            log.info("Navigating back in browser");
            //driver.sendKeyEvent(AndroidKeyCode.BACK);
            Thread.sleep(5000);
            
            //click on back button
            log.info("Click on back button");
            //driver.sendKeyEvent(AndroidKeyCode.BACK);
            Thread.sleep(5000);
            
            if(deviceName.equals("Nexus_5")){
            	//click on back button
                log.info("Click on back button");
              //  driver.sendKeyEvent(AndroidKeyCode.BACK);
                Thread.sleep(5000);
            }
			}
		}catch(Exception e){
			log.info("ebaySettingTest method as an exception");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
		
		}		
		log.info("ebaySettingTest test case is done...");
	}

	
	
	
	
	
	
	
	//@Test(priority=5, enabled=false)
	public void ebaySignInFaildTest() throws Exception {	
		log.info("ebaySignInFaildTest test case is in progress...");
		CommonUtil.sop(" Current Device Name " + deviceName);
		try{
			if(deviceName.equals("iPhone")){
				for(int i=0;i<10;i++){
					System.out.println("ebaySignInFaildTest");
				}
			}
			else{
			//clicking on item which is not available on page
			log.info("Clicking on wrong Sign In button on ebay app home page");
			WebElement signInButton = driver.findElementById("com.ebay.mobile:id/button_sign_in23413545");
			Thread.sleep(5000);
			signInButton.click();
			Thread.sleep(5000);
			}
		}catch(Exception e){
			log.info("ebaySignInFaildTest method as an exception");
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally
		{
		
		}		
		log.info("ebaySignInFaildTest test case is done...");
	}
	
	
	
	
	
	
	
	
//	@Test(priority=6, enabled=true)
	public void ebaySignOutTest() {
		log.info("ebaySignOutTest test case is in progress...");
		CommonUtil.sop(" Current Device Name " + deviceName);
		try{
			if(deviceName.equals("iPhone")){
				for(int i=0;i<10;i++){
					System.out.println("ebaySignOutTest");
				}
			}
			else{
			//if this is not samsung tab
			if(!deviceName.equals("SM-T210R")){	
				//click on top dotted image
				log.info("Clicking on right corner dotted image");
				WebElement topRightCornerImage = driver.findElementByClassName("android.widget.ImageButton");
				Thread.sleep(5000);
				topRightCornerImage.click();
				Thread.sleep(5000);
				
				//click on sign out text
				log.info("Clicking on Sign Out text");
				WebElement signOutText = driver.findElementByXPath("//android.widget.ListView[1]/android.widget.LinearLayout[4]/android.widget.RelativeLayout[1]/android.widget.TextView[1]");
				Thread.sleep(5000);
				signOutText.click();
				Thread.sleep(5000);
			}else{
					//clicking on email id
					log.info("Clicking on email id  on ebay app home page");
					WebElement userEmail;
					try{
						userEmail = driver.findElementById("com.ebay.mobile:id/layout_user_details");
					}
					catch(Exception e){
						log.info("Scroll to settings on ebay app home page");
						driver.scrollTo("Settings");						
						userEmail = driver.findElementById("com.ebay.mobile:id/layout_user_details");
					}
					Thread.sleep(5000);
					userEmail.click();
					Thread.sleep(5000);
					
					//clicking on email id
					log.info("Clicking on sign out button on ebay app home page");
					WebElement signOutButton = driver.findElementById("com.ebay.mobile:id/sign_out_btn");
					Thread.sleep(5000);
					signOutButton.click();
					Thread.sleep(5000);
			}
			
			//click on alert box
			log.info("Clicking on OK on Alert Box");
			WebElement okAlertBox = driver.findElementById("android:id/button1");
			Thread.sleep(5000);
			okAlertBox.click();
			Thread.sleep(5000);
			
			log.info("Rotating the screen in landscape mode");
			driver.rotate(ScreenOrientation.LANDSCAPE);
			Thread.sleep(5000);
			
			log.info("Rotating the screen in portrait mode");
			driver.rotate(ScreenOrientation.PORTRAIT);
			Thread.sleep(5000);
			
			}
		}catch(Exception e){
			log.info("ebaySignOutTest method as an exception");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
		
		}		
		log.info("ebaySignOutTest test case is done...");
	}

	
}


