package com.skava.reusable.components;

import com.framework.reporting.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import com.skava.frameworkutils.ExcelReader;
import com.skava.object.repository.LoginPage;

public class LoginComponents extends HeaderComponents 
{
	public void enterLoginCredentials()
	{
		driver.enterText(LoginPage.txtEmailId,ExcelReader.getData("General_Data","Username"),"Email");
		driver.enterTextAndSubmit(LoginPage.txtPassword,ExcelReader.getData("General_Data","Password"),"Password" );
		ExtentTestManager.getTest().log(LogStatus.INFO, "Entered valid credentials");
	}
	
	public void verifyLoginSuccessful()
	{
		if(driver.compareElementText(LoginPage.lblSignInSuccess, ExcelReader.getData("General_Data","logInSuccessMessage"), "Login Success Message"))
			ExtentTestManager.getTest().log(LogStatus.PASS, "User sucessfully logged in");
		else
			ExtentTestManager.getTest().log(LogStatus.FAIL, "User login failed");
	}
	
	public void closeLoginPopUp()
	{
		driver.clickElement(LoginPage.loginPopUpClose, "PopUpCloseIcon");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Login popup closed");
	}
}
