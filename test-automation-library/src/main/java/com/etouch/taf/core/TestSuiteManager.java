/*
 * 
 */
package com.etouch.taf.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.testng.ISuiteListener;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlMethodSelector;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.xml.sax.SAXException;

import com.etouch.taf.core.config.MobileConfig;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.config.TestngConfig;
import com.etouch.taf.core.config.TestngClass;
import com.etouch.taf.core.config.WebConfig;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ConfigUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.TestBedUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
// TODO: Auto-generated Javadoc
/**
 * The Enum TestSuiteManager.
 */
public enum TestSuiteManager {
	
	/** The instance. */
	INSTANCE;

	/** The log. */
	static Log log = LogUtil.getLog(TestSuiteManager.class);
	
	/** The test bed manager config. */
	TestBedManagerConfiguration testBedManagerConfig=TestBedManagerConfiguration.getInstance();
	
	/** The testng config. */
	TestngConfig testngConfig = testBedManagerConfig.getTestngConfig();
	

	/**
	 * This method will read testNG information from TestBedManagerConfiguration
	 * and Will create a first testSuite and will createRest of the testSuites, 
	 * based on the given testbed details .
	 *
	 * @return the test ng
	 */
	public TestNG buildTestSuites() {
		TestNG testng = new TestNG();
		
		/** Creates first testBed */
		List<XmlSuite> existingSuiteList = new ArrayList<XmlSuite>();
		existingSuiteList.add(createTestNGSuite());
	
		
		testng.setXmlSuites(existingSuiteList);
		testng.setParallel("tests");
		
		XmlSuite suite=existingSuiteList.get(0);
		suite.addListener(testngConfig.getSuiteListener());
		suite.addListener(testngConfig.getTestListener());
		//suite.addListener("com.etouch.amazon.listener.SuiteListener");
		
		for(XmlSuite s:existingSuiteList){
			CommonUtil.sop(s.toXml());
		}
		
		return testng;
	}
	
	
	/**
	 * 
	 * Creates the first TestSuite based on the information given in 
	 * devConfig.yml under testngConfig
	 *
	 * @return the list
	 */
	private XmlSuite createTestNGSuite() {
		

		// log.info("Start - reading TestNGFile");
		
		XmlSuite testSuite=new XmlSuite();
		testSuite.setPreserveOrder("true");
		testSuite.setParallel(testBedManagerConfig.getTestngConfig().getParallelMode());


		if(ConfigUtil.isWebTestTypeEnabled()){
			WebConfig webConfig=testBedManagerConfig.getWebConfig();
			createXmlTests(testSuite,webConfig.getCurrentTestBeds(),webConfig.getTestngClass());
		}
		
		if(ConfigUtil.isMobileTestTypeEnabled()){
			MobileConfig mobileConfig=testBedManagerConfig.getMobileConfig();
			createXmlTests(testSuite, mobileConfig.getCurrentTestBeds(),mobileConfig.getTestngClass());
			
		}
		
		log.info("Start - building TestSuits according to the configuration");
		
		return testSuite;

	}


/**
 * Creates the Tests
 *
 * @param testngSuiteList the testng suite list
 * @return the list
 */
	private void createXmlTests(XmlSuite testSuite, String[] testBeds, List<TestngClass> testngClassList) {
		
		if(testSuite!=null){
			
			for (String testBedName : testBeds) {
				testSuite.addTest(createXMLTest(testSuite,testBedName,testngClassList));
				}
		}
	}
	


	/**
	 * Creates the xml test.
	 *
	 * @param xmlSuite the xml suite
	 * @param testBedName the test bed name
	 * @return the list
	 */
	private XmlTest createXMLTest(XmlSuite xmlSuite,String testBedName,List<TestngClass> testngClassList) {
		XmlTest test = new XmlTest();
		
		try {
			//String portValue=null;
			TestBed testBed=TestBedUtil.loadTestBedDetails(testBedName);
			
			addXmlClassToTest(test,  testngClassList);
					
			test.setName(testBedName + " Test");
			test.setVerbose(1);
			test.setPreserveOrder("true");
			test.setSuite(xmlSuite);
			
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("testBedName", testBedName);
			if(testBed!=null && testBed.getPort()!=null){
				paramsMap.put("port", testBed.getPort());
			}
			test.setParameters(paramsMap);

		
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Error occured while creating first testng Suite");
		}
		return test;
	}
	
	
	
	/**
	 * Adds the xml class to test.
	 *
	 * @param test the test
	 */
	private void addXmlClassToTest(XmlTest test,List<TestngClass> testngClassList) {
		if(test!=null){
			test.setClasses(createXmlClass( testngClassList));
		}
	}


	
	/**
	 * Creates the xml class.
	 *
	 * @param className the class name
	 * @return the list
	 */
	private List<XmlClass> createXmlClass(List<TestngClass> classList){
		
		List<XmlClass> xmlClassList=null;
		if(classList!=null && classList.size()>0){
			xmlClassList=new ArrayList<XmlClass>();
		
		for(TestngClass testngClass:classList){
			XmlClass xmlClass=new XmlClass();
			Class<?> clazz=null;
			try{
				clazz=Class.forName(testngClass.getClassName());
			}catch(ClassNotFoundException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			xmlClass.setClass(clazz);
			xmlClass.setName(testngClass.getClassName());
			xmlClass=addMethodIncludes(xmlClass, testngClass.getMethodList());
			
			xmlClassList.add(xmlClass);
			
		}
		}//end-if
		return xmlClassList;
		}
	
	
	
	private XmlClass addMethodIncludes(XmlClass xmlClass,List<String> methodList){
		List<XmlInclude> xmlIncludes=new ArrayList<XmlInclude>();
		XmlInclude include=null;
		
		if(methodList==null){
			// if the methodList is null for a class. 
			methodList= getTestMethodList(xmlClass.getClass().getName());
		}
		
		if(methodList!=null){
			for(String methodName:methodList){
				include=new XmlInclude(methodName);
				include.setXmlClass(xmlClass);
				xmlIncludes.add(include);
			}
			xmlClass.setIncludedMethods(xmlIncludes);
		}
		
		return xmlClass;
	}
	
	
	private ArrayList<String> getTestMethodList(String className) {
		 ArrayList<String> methods = new ArrayList<String>();
//	    	Reflections reflections = new Reflections(packageName);
//	    	reflections.get
	    	Class cls;
			try {
				cls = Class.forName(className);
		    	List<Method> methodList = getMethodsAnnotatedWith(cls,org.testng.annotations.Test.class);
		    	for (Method m:methodList) {
		    		methods.add(className+"#"+m.getName());
		    	}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	return methods;
	    }
	
	 public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
	        final List<Method> methods = new ArrayList<Method>();
	        Class<?> klass = type;
	        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
	            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
	            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));       
	            for (final Method method : allMethods) {
	                if (annotation == null || method.isAnnotationPresent(annotation)) {
	                    // TODO process annotInstance
	                    methods.add(method);
	                }
	            }
	            // move to the upper class in the hierarchy in search for more methods
	            klass = klass.getSuperclass();
	        }
	        return methods;
	    }

}


