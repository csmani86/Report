package com.skava.reusable.components;

import com.framework.reporting.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import com.skava.frameworkutils.ExcelReader;
import com.skava.object.repository.HomePage;

public class HeaderComponents extends GeneralComponents 
{	
	public void navigateToSignInPage() 
	{
		driver.clickElement(HomePage.linkSignIn,"SignInLink");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Navigate to signin page");
	}
	
	public void enterSearchString() 
	{
		driver.enterTextAndSubmit(HomePage.searchInputField, ExcelReader.getData("General_Data","SearchString"),"searchField");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Search product");
	}
}
