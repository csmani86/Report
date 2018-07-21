package com.framework.reporting;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager 
{
	static ExtentReports extent;
    static String filePath;
    public static String screenshotPath;
    
    public synchronized static ExtentReports getReporter() 
    {
        if (extent == null) 
        {
            extent = new ExtentReports(filePath, true);
        }
        
        return extent;
    }
}