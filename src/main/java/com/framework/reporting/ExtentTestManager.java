package com.framework.reporting;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentTestManager 
{
	static Map extentTestMap = new HashMap();
    static ExtentReports extent = ExtentManager.getReporter();
    static HashMap<String, ExtentTest> parentList = new HashMap();
    
    public static synchronized ExtentTest getTest() 
    {
    	return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() 
    {
    	extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName) 
    {
        return startTest(testName, "");
    }

    public static synchronized ExtentTest startTest(String testName, String desc) 
    {
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
    
    public static synchronized ExtentTest createParent(String packageName) 
    {
    	ExtentTest parent;
    	if(!parentList.containsKey(packageName))
    	{
	    	parent = extent.startTest(packageName);
	    	parentList.put(packageName, parent);
	   }
    	else
    	{
    		parent = parentList.get(packageName);
    		//System.out.println("Package Name : " + packageName);
    	}
    	return parent;
    }
    
    public static synchronized ExtentTest getParentTest(String packageName)
    {
    	return (ExtentTest) parentList.get(packageName);
    }
}