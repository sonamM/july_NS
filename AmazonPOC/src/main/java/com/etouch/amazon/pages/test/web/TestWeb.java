/*
 * 
 */
package com.etouch.amazon.pages.test.web;

import org.testng.annotations.Test;


import com.etouch.amazon.common.BaseTest;
import com.etouch.taf.util.CommonUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class TestWeb.
 */
public class TestWeb {
	
	/**
	 * Test web.
	 */
	@Test
	public void testWeb(){
		CommonUtil.sop(" Message from TestWeb");
	}

	
	@Test
	public void testOne(){
		CommonUtil.sop(" Message from TestOne");
	}
}
