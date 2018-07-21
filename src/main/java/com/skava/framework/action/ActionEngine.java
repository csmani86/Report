package com.skava.framework.action;

import org.openqa.selenium.By;

public interface ActionEngine 
{
	public void maximizeBrowser();

	public void navigateToUrl(String url);

	public boolean enterText(By textField, String textValue, String elementName);

	public boolean clickElement(By buttonField, String elementName);

	public Object findElement(By txtusername);

	public boolean isElementPresent(By elementPath, String elementName);

	public boolean compareElementText(By path, String expectedText, String elementName);

	public boolean explicitWaitforVisibility(By path, int seconds, String elementName);

	public boolean isElementDisplayed(By path, int seconds, String elementName);

	public String getText(By path, String elementName);

	public String getElementAttribute(By path, String attrName, String elementName);

	public boolean compareElementAttribute(By path, String attrName, String expectedText, String elementName);

	public int generateRandomNumber(int limit);

	public int generateRandomNumberWithLimit(int maximum, int minimum);

	public boolean scrollToElement(By path, String elementName);

	public boolean isElementNotDisplayed(By path, String elementName);

	public boolean isElementEnabled(By path, String elementName);

	public void takeScreenShot(String elementName);

	public boolean enterTextAndSubmit(By path, String value, String elementName);

	public void quit();

	public void takeScreenshotWithPath(String path, String elementName);

	public String getCurrentUrl();

	public int getSize(By path, String elementName);
	
	public boolean selectByIndex(By path, int indexField, String elementName);
	
	public Boolean tabKey(By buttonField, String elementName);
	
	public boolean waitForPageLoad(int secs);
	
	public boolean appendText(By textField, String textValue, String elementName);
	
	public boolean moveToElementAndClick(By path, String elementName);
	
	public boolean jsClickElement(By buttonField, String elementName);
	
	public boolean alertPopupVerification();
	
	public boolean waitforAlertPopup();
	
	public String alertPopupGetText();
	
	public boolean alertPopupAccept();
	
	public boolean explicitWaitforClickable(By path,int seconds,String elementName);
	
	public boolean SwitchtoIframe(By path,int seconds,String elementName);
	
	public boolean SwitchtoParentFrame(String elementName);
	
	public boolean clearText(By path, int seconds, String elementName);
	
	public String GetCookie(String CookieName);
	
	public void changeFocus(int tabNumber);
	
	public void changeFocusToDefaultTab();
	
	public void driverMode(String view);
	
}