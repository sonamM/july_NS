/*
 * 
 */
package com.etouch.taf.core.driver.mobile;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.logging.Log;

import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.driver.DriverBuilder;
import com.etouch.taf.core.exception.DriverException;
import com.etouch.taf.core.resources.TestEnvironmentType;
import com.etouch.taf.core.resources.TestToolType;
import com.etouch.taf.mobile.appium.AppiumDriver;
import com.etouch.taf.mobile.experitest.cloud.ExperitestCloudDriver;
import com.etouch.taf.tools.rally.VideoRecorder;
import com.etouch.taf.util.AppiumLauncher;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ConfigUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;

// TODO: Auto-generated Javadoc
/**
 * The Class iOSDriver.
 */
public class IOSDriver extends DriverBuilder {

	/** The log. */
	private static Log log = LogUtil.getLog(IOSDriver.class);
	String testBedName;
	String deviceName;
	
	@BeforeClass
	public synchronized void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			
			
			testBedName=context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			
			
			TestBed testBed=TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			Thread.sleep(20000);
			driver=(AppiumDriver)(testBed.getDriver());
			deviceName =testBed.getDevice().getName();
		}

		catch (Exception e) {
			CommonUtil.sop("errr is for" +testBedName + " -----------"+ e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	/**
	 * Instantiates a new i os driver.
	 *
	 * @param testBed the test bed
	 * @throws DriverException the driver exception
	 */
	public IOSDriver(TestBed testBed) throws DriverException {

		super(testBed);		
	}


	/* (non-Javadoc)
	 * @see com.etouch.taf.core.driver.DriverBuilder#buildDriver()
	 */
	@Override
	public void buildDriver() throws DriverException {
		try {
			if (ConfigUtil.isLocalEnv()) {

				if (ConfigUtil.isAppium()) {
					AppiumLauncher.launchAppiumSession(testBed);
					driver = AppiumDriver.buildIOSDriver(testBed);
				}
				else if(ConfigUtil.isExperiTest())
				{
					driver = ExperitestCloudDriver.buildDriver(testBed);
				}

			} else if (ConfigUtil.isRemoteEnv()) {

				DesiredCapabilities browser = new DesiredCapabilities(testBed
						.getDevice().getType().toString(), "", Platform.MAC);
				driver = new RemoteWebDriver(new URL(hubLocation), browser);

			} else if (ConfigUtil.isBrowserStackEnv()) {

				capabilities = DesiredCapabilities.android();
				capabilities.setCapability("device", testBed.getDevice()
						.getName());
				buildBrowserstackCapabilities();

			}

		} catch (MalformedURLException e) {
			log.error("failed to create driver for android : " + e.getMessage());
			throw new DriverException("Could not create driver :: "
					+ e.getMessage());
		}

	}
	
	/* (non-Javadoc)
	 * @see com.etouch.taf.core.driver.DriverBuilder#getDriver()
	 */
	@Override
	public Object getDriver() throws DriverException {
		// TODO Auto-generated method stub
		return driver;
	}
}
