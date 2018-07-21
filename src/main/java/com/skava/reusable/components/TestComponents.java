package com.skava.reusable.components;

import org.testng.Assert;

import com.framework.reporting.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import com.skava.object.repository.Home_Page;
import com.skava.object.repository.LogIn_Page;

public class TestComponents extends LoginComponents
{
	public void method1() 
	{

		ExtentTestManager.getTest().log(LogStatus.PASS, "Log from threadId: " + Thread.currentThread().getId());
	    ExtentTestManager.getTest().log(LogStatus.INFO, "Log from threadId: " + Thread.currentThread().getId());
	    Assert.assertEquals(ExtentTestManager.getTest().getRunStatus(), LogStatus.PASS);
	    //ExtentTestManager.getTest().log(LogStatus.INFO, "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture("D:\\Screenshots"));
	}
	
	public void method2() throws Exception 
	{

		ExtentTestManager.getTest().log(LogStatus.FAIL, "Log from threadId: " + Thread.currentThread().getId());
	    ExtentTestManager.getTest().log(LogStatus.INFO, "Log from threadId: " + Thread.currentThread().getId());
	   
	    throw new Exception("intentional failure");
	}
	
	
	public void LogIn() 
	{
		driver.enterText(LogIn_Page.txtbx_UserName, properties.getProperty("UserName"),"Username");
		driver.enterText(LogIn_Page.txtbx_Password, properties.getProperty("Password"),"Password");
		driver.clickElement(LogIn_Page.btn_LogIn,"Login button");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Logged in as test user");
	}
	
	public void verifyLoginSuccessful() 
	{
		
		if(driver.isElementDisplayed(Home_Page.lnk_LogOut, 10, "My account"))
		{
			ExtentTestManager.getTest().log(LogStatus.PASS, "Login successful");
		}
		else 
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Login failed");
			//ExtentTestManager.getTest().log(LogStatus.INFO, "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(ExtentManager.screenshotPath + "\\test.jpeg"));
		}	
	}
	public void LogOut() 
	{
		driver.clickElement(Home_Page.lnk_LogOut,"Logout link");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Logged out");
	}
}
