package com.skava.reusable.components;

import com.framework.reporting.BaseClass;
import com.framework.reporting.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import com.skava.frameworkutils.loggerUtils;

public class GeneralComponents extends BaseClass
{
	public void launchUrl() 
	{
		driver.maximizeBrowser();
		driver.navigateToUrl(properties.getProperty("ApplicationUrl"));
		ExtentTestManager.getTest().log(LogStatus.INFO, "Navigated to application url");
		loggerUtils.passLog("Logger");
	}

	/**
	 * Log the pass into Extent report with comments
	 *
	 * @param text
	 */
	public void logPass(String text) {
		ExtentTestManager.getTest().log(LogStatus.PASS, text);
	}

	/**
	 * Log the fail into extent report with comments
	 *
	 * @param text
	 */
	public void logFail(String text) {
		ExtentTestManager.getTest().log(LogStatus.FAIL, text);
	}


	/**
	 * Log the info into extent report with comments
	 *
	 * @param text
	 */
	public void logInfo(String text)
	{
		ExtentTestManager.getTest().log(LogStatus.INFO, text);
	}

	/**
	 * Log the Warning into Extent report with comments
	 *
	 * @param text
	 */
	public void logWarning(String text)
	{
		ExtentTestManager.getTest().log(LogStatus.WARNING, text);
	}
	
	/**
	 * Log the pass into Extent report with comments
	 *
	 * @param text
	 */
	public void logSkip(String text) {
		ExtentTestManager.getTest().log(LogStatus.SKIP, text);
	}
}