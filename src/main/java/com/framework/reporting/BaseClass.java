package com.framework.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.net.ntp.TimeStamp;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.skava.framework.action.ActionEngine;
import com.skava.framework.action.ActionEngineFactory;
import com.skava.frameworkutils.Constants;
import com.skava.frameworkutils.ExcelReader;
import com.skava.frameworkutils.loggerUtils;

import net.sf.json.JSONObject;

public class BaseClass 
{	
	public ActionEngine driver;
	public static Properties properties = new Properties();
	public static String UserDir=Paths.get(".").toAbsolutePath().normalize().toString();
	String ConfigFilePath=Paths.get(".").toAbsolutePath().normalize().toString()+ "\\Resources\\Config.ini";
	public static JSONObject json = new JSONObject();
	public static String currentRunReportPath;
	public String packageName = "";
	
	public static synchronized ActionEngine initiTest(String tcName) throws IOException 
	{		
		return ActionEngineFactory.getActionEngine(BaseClass.getBrowser(tcName));
	}
	
	public void setDriver(ActionEngine driver) 
	{
		this.driver = driver;
	}
	
	public static int getBrowser(String testCaseName) 
	{
		String strExectuionChannel=(String) json.getJSONArray(testCaseName).get(1);
		//String strBrowserName=(String) json.getJSONArray(testCaseName).get(2);
		// Code for Jenkins Jobs
		String strBrowserName= "";
		if(System.getProperty("Browser")==null)
    	{
    		strBrowserName=(String) json.getJSONArray(testCaseName).get(2);
    		System.out.println("Excel Browser Name : " + strBrowserName);
    	}
    	else
    	{
    		strBrowserName = System.getProperty("Browser");
            System.out.println("Jenkins Browser Name : " + strBrowserName);
    	}
		String strDeviceType=(String) json.getJSONArray(testCaseName).get(4);		
		String strDeviceName=(String) json.getJSONArray(testCaseName).get(5);
		int return_type = 0;
		
		if(strExectuionChannel.equalsIgnoreCase("LOCAL"))
		{
			switch (strBrowserName) 
			{
				case "Chrome" :
					return_type = Constants.LOCAL_BROWSER_CHROME;
					break;
					
				case "Firefox" :
					return_type = Constants.LOCAL_BROWSER_FIREFOX;
					break;
					
				case "InternetExplorer":
					return_type = Constants.LOCAL_BROWSER_IE;
					break;
					
				case "Safari":
					return_type = Constants.LOCAL_BROWSER_SAFARI;
					break;
					
				case "MicrosoftEdge":
					return_type = Constants.LOCAL_BROWSER_EDGE;
					break;
					
				default :
					return_type = Constants.LOCAL_BROWSER_CHROME;
			}
		}
		else
		{
			if(strDeviceType.equalsIgnoreCase("Desktop"))
			{
				switch (strBrowserName) 
				{
					case "Chrome" :
						return_type = Constants.SAUCE_DESKTOP_BROWSER_CHROME;
						break;
						
					case "Firefox" :
						return_type = Constants.SAUCE_DESKTOP_BROWSER_FIREFOX;
						break;
						
					case "InternetExplorer":
						return_type = Constants.SAUCE_DESKTOP_BROWSER_IE;
						break;
						
					case "Safari":
						return_type = Constants.SAUCE_DESKTOP_BROWSER_SAFARI;
						break;
						
					case "MicrosoftEdge":
						return_type = Constants.SAUCE_DESKTOP_BROWSER_EDGE;
						break;
						
					default :
						return_type = Constants.SAUCE_DESKTOP_BROWSER_CHROME;	
						break;
				}	
			}
			else if(strDeviceType.equals("Mobile"))
			{
				if(strDeviceName.contains("iPhone"))
				{
					if(strDeviceName.contains("Simulator"))
						return_type = Constants.SAUCE_MOBILE_SIMULATOR_IOS_BROWSER_SAFARI;
					else
						return_type = Constants.SAUCE_MOBILE_IOS_BROWSER_SAFARI;		
				}
				else
				{
					if(strDeviceName.contains("Emulator"))
						return_type = Constants.SAUCE_MOBILE_EMULATOR_ANDROID_BROWSER_CHROME;
					else
						return_type = Constants.SAUCE_MOBILE_ANDROID_BROWSER_CHROME;
				}
			}
			else if(strDeviceType.equalsIgnoreCase("Tablet"))
			{
				if(strDeviceName.contains("iPad"))
				{
					if(strDeviceName.contains("Simulator"))
						return_type = Constants.SAUCE_TABLET_SIMULATOR_IOS_BROWSER_SAFARI;
					else
						return_type = Constants.SAUCE_TABLET_IOS_BROWSER_SAFARI;		
				}
				else
				{
					if(strDeviceName.contains("Emulator"))
						return_type = Constants.SAUCE_TABLET_EMULATOR_ANDROID_BROWSER_CHROME;
					else
						return_type = Constants.SAUCE_TABLET_ANDROID_BROWSER_CHROME;	
				}
			}
		}
		return return_type;
	}
	
	public static synchronized JSONObject initBatchExec() throws IOException 
	{	
		Map<String, List> hashMap = new LinkedHashMap<String, List>();
		hashMap=ExcelReader.getBatchExecInfo(UserDir+properties.getProperty("TestRunnerPath"), properties.getProperty("RunConfigSheetName"));
		json.accumulateAll(hashMap);
		return json;
	}
	
	@BeforeSuite (alwaysRun = true)
	public void beforeSuite() throws IOException 
	{
		//Load properties file		
		 File file = new File(ConfigFilePath);		  
		 FileInputStream fileInput = null;
			try 
			{
				fileInput = new FileInputStream(file);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}				
			
			try 
			{
				properties.load(fileInput);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			System.out.println("Suite Name : " + System.getProperty("suiteXmlFile"));
			System.out.println("Browser Name : " + System.getProperty("Browser"));
			
			//Read test runner file for test run configurations
			initBatchExec();
			
			//Setup reports path for current run 
			TimeStamp time=new TimeStamp(System.currentTimeMillis());	
			currentRunReportPath=UserDir+ properties.getProperty("ReportPath")+time;
			File files = new File(currentRunReportPath+"\\Screenshots");
			files.mkdirs();
			ExtentManager.filePath=currentRunReportPath+"\\Summary.html";
			ExtentManager.screenshotPath=currentRunReportPath+"\\Screenshots";
			loggerUtils.setLogFile();
	}
	
	@BeforeMethod (alwaysRun = true)
    public void beforeMethod(Method method) 
	{
		String[] val = this.getClass().getPackage().toString().split("\\.");
		packageName = WordUtils.capitalize(val[val.length-1].replaceAll("_", " "));
		ExtentTest parent = ExtentTestManager.createParent(packageName);
		ExtentTest child =  ExtentTestManager.startTest(method.getName());
		parent.appendChild(child);
    }
    
    @AfterMethod (alwaysRun = true)
    protected void afterMethod(ITestResult result) 
    {
    	if (result.getStatus() == ITestResult.FAILURE) 
        {
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
        } 
        else if (result.getStatus() == ITestResult.SKIP) 
        {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } 
        else 
        {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
        }
        
        try
        {
        	ExtentTestManager.endTest();
        	ExtentManager.getReporter().flush();
        }
        catch (Exception e) 
        {
			e.printStackTrace();
		}
    }
    
    @AfterSuite(alwaysRun = true)
    public void afterSuite()
    {
    	for(ExtentTest parentTest : ExtentTestManager.parentList.values())
    	{
    		ExtentManager.getReporter().endTest(parentTest);
    	}
    	ExtentManager.getReporter().flush();
    	ExtentManager.getReporter().close();
    }
    
    protected String getStackTrace(Throwable t) 
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}