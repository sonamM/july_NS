package com.etouch.taf.webui;

import org.openqa.selenium.WebElement;
import org.testng.ITestContext;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating WebElement objects.
 */
public class WebElementFactory implements ITafElementFactory {

	/* (non-Javadoc)
	 * @see com.etouch.taf.webui.ITafElementFactory#createElement(org.openqa.selenium.WebElement)
	 */
	public ITafElement createElement(WebElement webElement, ITestContext context) {
		// TODO Auto-generated method stub
		return new com.etouch.taf.webui.selenium.WebElement(webElement,context);
	}

	

}
