package com.etouch.taf.webui.selenium.test;

import org.apache.commons.logging.Log;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;




import org.testng.ITestContext;

import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.exception.DriverException;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.core.resources.TestTypes;
import com.etouch.taf.core.test.util.TafTestUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.TestUtil;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.qtp.QTPElement;
import com.etouch.taf.webui.selenium.MobileElement;
import com.etouch.taf.webui.selenium.WebPage;

@RunWith(MockitoJUnitRunner.class)
public class WebPageTest {
	
	
	
	/** The log. */
	private static Log log = LogUtil.getLog(WebPageTest.class);
	
	private static WebPage webpage = null;
	
	private static WebDriver driverObj = null;
	
	@Mock
	ITestContext mockContext;
	
	@BeforeClass
	public static void before() throws DriverException
	{
		TafTestUtil.initialize();
			
		//TestBed currentTestbed = TafTestUtil.loadTestBedDetails("Firefox");
		TestBedManager.INSTANCE.getCurrentTestBeds().get("Firefox");		
		
		driverObj = TestUtil.createDriver(true);	
		
		webpage = new WebPage();
		webpage.loadPage("http://www.amazon.com");
	}
	
	@Test
	public void testFindObjectWithSelenium() throws DriverException {		
		WebElement webElement = new RemoteWebElement();
		
		Mockito.when(mockContext.getCurrentXmlTest().getAllParameters().get("testBedName")).thenReturn("Android");

		 ITafElement webElement1 = new com.etouch.taf.webui.selenium.WebElement(webElement, mockContext);
		 ITafElement mobElement = new MobileElement(webElement, mockContext);
		
		try{
				ITafElement element = webpage.findObjectById("nav-link-yourAccount");
				
				String testBedName=mockContext.getCurrentXmlTest().getAllParameters().get("testBedName");
				
				TestBed testBed=TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
				
				if(testBed.getTestType()==TestTypes.WEB.getTestType())
					Assert.assertTrue(element.getClass()==webElement1.getClass());
				else if(testBed.getTestType()==TestTypes.MOBILE.getTestType())
				{
					Assert.assertTrue(element.getClass()==mobElement.getClass());
				}
			}catch(PageException pe)
			{
				log.error(pe.getMessage());
			}
		
	}
	
	@Test
	public void testFindObjectWithQTP() throws DriverException {
		
		WebElement webElement = new RemoteWebElement();
		ITafElement qtpElement = new QTPElement(webElement, mockContext);			
		try{
				ITafElement element = webpage.findObjectById("nav-link-yourAccount");
				Assert.assertTrue(element.getClass()==qtpElement.getClass());
			}catch(PageException pe)
			{
				log.error(pe.getMessage());
			}
		
	}
	
	@AfterClass
	public static void tearDown()
	{
		TestUtil.closeDriver(driverObj);
	}

}
