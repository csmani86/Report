package com.skava.framework.action;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.reporting.BaseClass;
import com.skava.frameworkutils.Constants;
import com.skava.frameworkutils.loggerUtils;

public class SeleniumActionEngine implements ActionEngine 
{
	private WebDriver driver;
	public static final String saucelabAPI_Web = "http://"+BaseClass.properties.getProperty("SaucelabUserName")+":"+BaseClass.properties.getProperty("SaucelabAccessKey")+"@ondemand.saucelabs.com:80/wd/hub";
	public static final String saucelabAPI_US = "https://us1.appium.testobject.com/wd/hub";
	public static final String saucelabAPI_EU = "https://eu1.appium.testobject.com/wd/hub";
	ArrayList<String> tabs;

	public SeleniumActionEngine(int browserType) 
	{
		init(browserType);
	}

	private void init(int browserType) 
	{
		//System.out.println(Thread.currentThread().getStackTrace()[5].getMethodName());
		String testCaseName = Thread.currentThread().getStackTrace()[5].getClassName().substring(20);
		String strBrowserName=(String) BaseClass.json.getJSONArray(testCaseName).get(2);
		System.out.println("Excel Browser Name : " + strBrowserName);
        String BrowserName = System.getProperty("BrowserName");
        System.out.println("Jenkins Browser Name : " + BrowserName);
		String strBrowserVersion=(String) BaseClass.json.getJSONArray(testCaseName).get(3);
		String strDeviceName=(String) BaseClass.json.getJSONArray(testCaseName).get(5);
		String strDeviceOS=(String) BaseClass.json.getJSONArray(testCaseName).get(6);
		
		DesiredCapabilities caps;
		
		switch (browserType) 
		{
			case Constants.LOCAL_BROWSER_FIREFOX:
				System.setProperty("webdriver.gecko.driver", BaseClass.UserDir+BaseClass.properties.getProperty("DriversPath")+"geckodriver.exe");
				this.driver = new FirefoxDriver();

				break;
			
			case Constants.LOCAL_BROWSER_CHROME:
				System.setProperty("webdriver.chrome.driver", BaseClass.UserDir+BaseClass.properties.getProperty("DriversPath")+"chromedriver.exe");
				this.driver = new ChromeDriver();
				
				break;
				
			case Constants.LOCAL_BROWSER_IE:
				System.setProperty("webdriver.ie.driver", BaseClass.UserDir+BaseClass.properties.getProperty("DriversPath")+"IEDriver.exe");
				this.driver = new InternetExplorerDriver();
				
				break;
				
			case Constants.LOCAL_BROWSER_SAFARI:
				this.driver = new SafariDriver();
				
				break;
				
			case Constants.LOCAL_BROWSER_EDGE:
				System.setProperty("webdriver.edge.driver", BaseClass.UserDir+BaseClass.properties.getProperty("DriversPath")+"MicrosoftEdgeDriver.exe");
				this.driver = new EdgeDriver();
				
				break;
				
			case Constants.SAUCE_DESKTOP_BROWSER_CHROME:
				caps = DesiredCapabilities.chrome();
				caps.setCapability("platform", strDeviceOS.trim());
				caps.setCapability("version", strBrowserVersion.trim());
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				try 
				{
					driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_Web), caps);
				} 
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}
				break;	
				
			case Constants.SAUCE_DESKTOP_BROWSER_FIREFOX:
				caps = DesiredCapabilities.firefox();
				caps.setCapability("platform", strDeviceOS.trim());
				caps.setCapability("version", strBrowserVersion.trim());
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				try 
				{
					driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_Web), caps);
				} 
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}
				break;
				
			case Constants.SAUCE_DESKTOP_BROWSER_IE:
				caps = DesiredCapabilities.internetExplorer();
				caps.setCapability("platform", strDeviceOS.trim());
				caps.setCapability("version", strBrowserVersion.trim());
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				try 
				{
					driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_Web), caps);
				} 
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}
				break;
				
			case Constants.SAUCE_DESKTOP_BROWSER_EDGE:
				caps = DesiredCapabilities.edge();
				caps.setCapability("platform", strDeviceOS.trim());
				caps.setCapability("version", strBrowserVersion.trim());
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				try 
				{
					driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_Web), caps);
				} 
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}
				break;
				
			case Constants.SAUCE_DESKTOP_BROWSER_SAFARI:
				caps = DesiredCapabilities.safari();
				caps.setCapability("platform", strDeviceOS.trim());
				caps.setCapability("version", strBrowserVersion.trim());
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				try 
				{
					driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_Web), caps);
				} 
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}
				break;
			
			case Constants.SAUCE_MOBILE_ANDROID_BROWSER_CHROME:
				caps = new DesiredCapabilities();
				caps.setCapability("testobjectApiKey", BaseClass.properties.getProperty("TestobjectApiKey"));
				caps.setCapability("platformName", "Android");
				caps.setCapability("platformVersion", strDeviceOS.replace("Android ", ""));
				caps.setCapability("deviceName", strDeviceName);
				caps.setCapability("privateDevicesOnly", "false	");
				caps.setCapability("deviceType", "mobile");
				caps.setCapability("testobject_app_id", "1");
				caps.setCapability("browserName", "Chrome");
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				caps.setCapability("testobject_session_creation_timeout", "900000");
				caps.setCapability("noReset", "false");
				caps.setCapability("appiumVersion", "1.7.1");
				try 
				{
					if(BaseClass.properties.getProperty("RealDeviceDataCenter").equalsIgnoreCase("US"))
						driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_US), caps);
					else
						driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_EU), caps);
				}
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}   
				break;
				
			case Constants.SAUCE_MOBILE_EMULATOR_ANDROID_BROWSER_CHROME:
				caps = DesiredCapabilities.android();
				caps.setCapability("appiumVersion", "1.7.1");
				caps.setCapability("deviceName",strDeviceName);
				caps.setCapability("deviceOrientation", "portrait");
				caps.setCapability("deviceType", "mobile");
				caps.setCapability("browserName", strBrowserName.trim());
				caps.setCapability("platformVersion",strDeviceOS.replace("Android ", ""));
				caps.setCapability("platformName","Android");
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				try 
				{
					driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_Web), caps);
				} 
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}
				break;
				  
			case Constants.SAUCE_MOBILE_IOS_BROWSER_SAFARI:
				caps = new DesiredCapabilities();
				caps.setCapability("testobjectApiKey", BaseClass.properties.getProperty("TestobjectApiKey"));
				caps.setCapability("platformName", "iOS");
				caps.setCapability("platformVersion", strDeviceOS.replace("iOS ", ""));
				caps.setCapability("deviceOrientation", "portrait");
				caps.setCapability("deviceName", strDeviceName);
				caps.setCapability("testobject_app_id", "1");
				caps.setCapability("browserName", strBrowserName.trim());
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				caps.setCapability("testobject_session_creation_timeout", "900000");
				caps.setCapability("appiumVersion", "1.7.1");
				try 
				{
					if(BaseClass.properties.getProperty("RealDeviceDataCenter").equalsIgnoreCase("US"))
						driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_US), caps);
					else
						driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_EU), caps);
				}
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}   
				break;
				
			case Constants.SAUCE_MOBILE_SIMULATOR_IOS_BROWSER_SAFARI:
				caps = DesiredCapabilities.iphone();
				caps.setCapability("appiumVersion", "1.7.1");
				caps.setCapability("deviceName",strDeviceName);
				caps.setCapability("deviceOrientation", "portrait");
				caps.setCapability("platformVersion",strDeviceOS.replace("iOS ", ""));
				caps.setCapability("platformName", "iOS");
				caps.setCapability("browserName", strBrowserName.trim());
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				try 
				{
					driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_Web), caps);
				} 
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}
				break;
				
			case Constants.SAUCE_TABLET_EMULATOR_ANDROID_BROWSER_CHROME:
				caps = DesiredCapabilities.android();
				caps.setCapability("appiumVersion", "1.7.1");
				caps.setCapability("deviceName",strDeviceName);
				caps.setCapability("deviceOrientation", "portrait");
				caps.setCapability("deviceType", "tablet");
				caps.setCapability("browserName", strBrowserName.trim());
				caps.setCapability("platformVersion",strDeviceOS.replace("Android ", ""));
				caps.setCapability("platformName","Android");
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				try 
				{
					driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_Web), caps);
				} 
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}
				break;
				
			case Constants.SAUCE_TABLET_ANDROID_BROWSER_CHROME:
				caps = new DesiredCapabilities();
				caps.setCapability("testobjectApiKey", BaseClass.properties.getProperty("TestobjectApiKey"));
				caps.setCapability("platformName", "Android");
				caps.setCapability("platformVersion", strDeviceOS.replace("Android ", ""));
				caps.setCapability("deviceName", strDeviceName);
				caps.setCapability("privateDevicesOnly", "false	");
				caps.setCapability("deviceType", "tablet");
				caps.setCapability("testobject_app_id", "1");
				caps.setCapability("browserName", "Chrome");
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				caps.setCapability("testobject_session_creation_timeout", "900000");
				caps.setCapability("noReset", "false");
				caps.setCapability("appiumVersion", "1.7.1");
				try 
				{
					if(BaseClass.properties.getProperty("RealDeviceDataCenter").equalsIgnoreCase("US"))
						driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_US), caps);
					else
						driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_EU), caps);
				}
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}   
				break;
				
			case Constants.SAUCE_TABLET_IOS_BROWSER_SAFARI:
				caps = new DesiredCapabilities();
				caps.setCapability("testobjectApiKey", BaseClass.properties.getProperty("TestobjectApiKey"));
				caps.setCapability("platformName", "iOS");
				caps.setCapability("platformVersion", strDeviceOS.replace("iOS ", ""));
				caps.setCapability("deviceOrientation", "portrait");
				caps.setCapability("deviceName", strDeviceName);
				caps.setCapability("testobject_app_id", "1");
				caps.setCapability("browserName", strBrowserName.trim());
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				caps.setCapability("testobject_session_creation_timeout", "900000");
				caps.setCapability("appiumVersion", "1.7.1");
				try 
				{
					if(BaseClass.properties.getProperty("RealDeviceDataCenter").equalsIgnoreCase("US"))
						driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_US), caps);
					else
						driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_EU), caps);
				}
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}   
				break;
				
			case Constants.SAUCE_TABLET_SIMULATOR_IOS_BROWSER_SAFARI:
				caps = DesiredCapabilities.ipad();
				caps.setCapability("appiumVersion", "1.7.1");
				caps.setCapability("deviceName",strDeviceName);
				caps.setCapability("deviceOrientation", "portrait");
				caps.setCapability("platformVersion",strDeviceOS.replace("iOS ", ""));
				caps.setCapability("platformName", "iOS");
				caps.setCapability("browserName", strBrowserName.trim());
				caps.setCapability("name", BaseClass.properties.getProperty("ProjectName")+"_"+testCaseName);
				try 
				{
					driver = new RemoteWebDriver(new java.net.URL(saucelabAPI_Web), caps);
				} 
				catch (MalformedURLException e) 
				{
					loggerUtils.stackTracePrint(e);
				}
				break;
				
			default:
				//throw new InvalidBrowserTypeException();
			break;
		}
	}


	@Override
	public boolean enterText(By textField, String textValue,String elementName)
	{
		Boolean valid = false;
		 try
		 {
			Boolean visibility = explicitWaitforVisibility(textField,10,elementName);
			if( visibility && !textValue.equals(""))
			{
				WebElement element = this.driver.findElement(textField);
				element.clear();
				element.sendKeys(textValue);
				valid = true;
				loggerUtils.passLog(elementName+" is entered in the field");
			}
		 }
		 catch(Exception e)
		 {
			 loggerUtils.stackTracePrint(e);
			 loggerUtils.failLog(elementName+"is not present");
			 takeScreenShot(elementName);
		 }
		 return valid;
	}

	@Override
	public boolean clickElement(By buttonField,String elementName)
	{
		Boolean valid = false;
		try
		{
			if(explicitWaitforVisibility(buttonField,10,elementName))
			{
				explicitWaitforClickable(buttonField,30, elementName);
				WebElement element = this.driver.findElement(buttonField);
				element.click();
				valid = true;
				loggerUtils.passLog(elementName+" is clicked");
			}
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
			loggerUtils.failLog(elementName+"is not present");
			takeScreenShot(elementName);
		}
		return valid;
	}

	@Override
	public boolean isElementPresent(By path,String elementName)
	{
		return !this.driver.findElements(path).isEmpty();
	}

	@Override
	public boolean compareElementText(By path,String expectedText,String elementName)
	{
		Boolean valid = false;
			try
			{
				if(explicitWaitforVisibility(path,10,elementName))
				{
					String currentText = getText(path, elementName);
					expectedText=Jsoup.parse(expectedText).text();
					if(currentText.equals(expectedText))
					{
						loggerUtils.passLog(elementName+" is verified");
						valid = true;
					}
					else
					{
						//Logging
						loggerUtils.failLog(elementName+" is mismatched");
					}
				}
			}
			catch(Exception e)
			{
				loggerUtils.stackTracePrint(e);
				loggerUtils.failLog(elementName+" is not present");
				takeScreenShot(elementName);
			}
			return valid;
	}

	@Override
	public boolean explicitWaitforVisibility(By path,int seconds,String elementName)
	{
			try
			{
				WebDriverWait wait = new WebDriverWait(this.driver,seconds);
				wait.until(ExpectedConditions.visibilityOfElementLocated(path));
				return true;
			}
			catch(Exception e)
			{
				loggerUtils.stackTracePrint(e);
				loggerUtils.failLog(elementName+" is not present");
				takeScreenShot(elementName);
				return false;
			}
	}

	@Override
	public boolean isElementDisplayed(By path,int seconds,String elementName)
	{
		Boolean valid = false;
			try
			{
				if(explicitWaitforVisibility(path,10,elementName))
				{
					WebElement element = this.driver.findElement(path);
					valid = element.isDisplayed();
					if(valid)
						loggerUtils.passLog(elementName+" is displayed");
					else
						loggerUtils.failLog(elementName+" is not displayed");
				}
			}
			catch(Exception e)
			{
				loggerUtils.stackTracePrint(e);
				loggerUtils.failLog(elementName+" is not present");
				takeScreenShot(elementName);
			}
			return valid;
	}

	@Override
	public boolean isElementNotDisplayed(By path,String elementName)
	{
		Boolean valid = true;
			if(isElementPresent(path, elementName))
			{
				WebElement element = this.driver.findElement(path);
				valid = !element.isDisplayed();
				if(!valid)
					takeScreenShot(elementName);
			}
		return valid;
	}

	@Override
	public boolean isElementEnabled(By path,String elementName)
	{
		Boolean valid = false;
		try
		{
			if(explicitWaitforVisibility(path,10,elementName))
			{
				WebElement element = this.driver.findElement(path);
				valid = element.isEnabled();
				if(valid)
					loggerUtils.passLog(elementName+" is enabled");
				else
					loggerUtils.failLog(elementName+" is not enabled");
			}
		}
		catch (Exception e)
		{
			loggerUtils.stackTracePrint(e);
			loggerUtils.failLog(elementName+" is not present");
			takeScreenShot(elementName);
		}
		return valid;
	}

	@Override
	public String getText(By path,String elementName)
	{
		String text = "";
			try
			{
				if(explicitWaitforVisibility(path,10,elementName))
				{
					WebElement element = this.driver.findElement(path);
					text = element.getText();
				}
			}
			catch(Exception e)
			{
				loggerUtils.stackTracePrint(e);
				loggerUtils.failLog(elementName+" is not present");
				takeScreenShot(elementName);
			}
			return text;
	}

	@Override
	public String getElementAttribute(By path,String attrName,String elementName)
	{
		String text = "";
			try
			{
				if(explicitWaitforVisibility(path,10,elementName))
				{
					WebElement element = this.driver.findElement(path);
					text = element.getAttribute(attrName);
				}
			}
			catch(Exception e)
			{
				loggerUtils.stackTracePrint(e);
				loggerUtils.failLog(elementName+" is not present");
				takeScreenShot(elementName);
			}
			return text;
	}

	@Override
	public boolean compareElementAttribute(By path,String attrName,String expectedText,String elementName)
	{
		Boolean valid = false;
			try
			{
				if(explicitWaitforVisibility(path,10,elementName))
				{
					WebElement element = this.driver.findElement(path);
					if(element.getAttribute(attrName).equals(expectedText))
					{
						valid = true;
						loggerUtils.passLog(elementName+" is verified");
					}
					else
					{
						//Logging
						loggerUtils.failLog(elementName+" is mismatched");
					}
				}
			}
			catch(Exception e)
			{
				loggerUtils.stackTracePrint(e);
				loggerUtils.failLog(elementName+" is not present");
				takeScreenShot(elementName);
			}
		return valid;
	}

	@Override
    public void takeScreenShot(String elementName)
    {
      try
      {
    	  DateFormat dateFormat = new SimpleDateFormat("h_m_s");
		  Date date = new Date();
    	  TakesScreenshot scrShot =((TakesScreenshot)this.driver);
    	  File sourceFile=scrShot.getScreenshotAs(OutputType.FILE);
    	  
    	  FileUtils.copyFile(sourceFile,new File(BaseClass.currentRunReportPath+"/"+elementName+"_"+dateFormat.format(date)+".png"));
      }
      catch (Exception e)
      {
    	  loggerUtils.stackTracePrint(e);
      }
    }

	@Override
    public void takeScreenshotWithPath(String path,String elementName)
    {
      try
      {
    	  DateFormat dateFormat = new SimpleDateFormat("h_m_s");
		  Date date = new Date();
    	  TakesScreenshot scrShot =((TakesScreenshot)this.driver);
    	  File sourceFile=scrShot.getScreenshotAs(OutputType.FILE);
    	  FileUtils.copyFile(sourceFile,new File(path+elementName+"_"+dateFormat.format(date)+".png"));
      }
      catch (Exception e)
      {
    	  loggerUtils.stackTracePrint(e);
      }
    }


	@Override
	public int generateRandomNumber(int limit)
	{
		Random rand = new Random();
		return rand.nextInt(limit);
	}

	@Override
	public int generateRandomNumberWithLimit(int maximum,int minimum)
	{
		Random rand = new Random();
		return rand.nextInt(maximum) + minimum;
	}

	@Override
	public boolean scrollToElement(By path,String elementName)
	{
		Boolean valid = false;
		try
		{
			if(explicitWaitforVisibility(path,10,elementName))
			{
				WebElement element = this.driver.findElement(path);
				((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView();", element);
				valid = true;
				loggerUtils.passLog("scrolled to "+elementName);
			}
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
			loggerUtils.failLog(elementName+" is not present");
			takeScreenShot(elementName);
			valid = false;
		}
		return valid;
	}

	@Override
	public void maximizeBrowser() 
	{
		driver.manage().window().maximize();
	}

	@Override
	public void navigateToUrl(String url) 
	{
		driver.get(url);
		loggerUtils.passLog(url+" is opened");
	}
	
	@Override
	public boolean enterTextAndSubmit(By path,String value,String elementName)
	{
		 boolean valid=false;
		 try
		 {
			 if(explicitWaitforVisibility(path,10,elementName) && !value.equals(""))
			 {
				 WebElement element = driver.findElement(path);
				 element.clear();
				 element.sendKeys(value);
				 element.sendKeys(Keys.ENTER);
				 valid = true;
				 loggerUtils.passLog(value+" is entered in "+elementName);
			 }
		 }
		 catch(Exception e)
		 {
			 loggerUtils.stackTracePrint(e);
			 loggerUtils.failLog(elementName+"is not present");
			 takeScreenShot(elementName);
		 }
		return valid;
	}

	@Override
	public Object findElement(By txtusername) 
	{
		return null;
	}

	@Override
	public void quit() 
	{
		driver.quit();
	}

	@Override
	public String getCurrentUrl()
	{
		return driver.getCurrentUrl();
	}
	
	@Override
	public int getSize(By path,String elementName)
	{
			return driver.findElements(path).size();
	}
	
	@Override
	public boolean selectByIndex(By path,int indexField,String elementName)
	{
		Boolean valid = false;
		try
		{	
				WebElement element = this.driver.findElement(path);
				Select dropdown= new Select(element);
				dropdown.selectByIndex(indexField);
				valid = true;
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
			takeScreenShot(elementName);
		}
		return valid;
	}
	
	@Override
	public Boolean tabKey(By buttonField,String elementName)
	{
		Boolean valid = false;
		try
		{	
			if(explicitWaitforVisibility(buttonField,30,elementName))
			{
				WebElement element = this.driver.findElement(buttonField);
				element.sendKeys(Keys.TAB);
				valid = true;
			}
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
			takeScreenShot(elementName);
		}
		return valid;
	}
	
	@Override
	public boolean waitForPageLoad(int secs)
	{
		boolean valid=false;
		try
		{
			driver.manage().timeouts().pageLoadTimeout(secs, TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
		}
		
		return valid;
	}

	@Override
	public boolean appendText(By textField, String textValue,String elementName)
	{
		Boolean valid = false;
		 try
		 {
			Boolean visibility = explicitWaitforVisibility(textField,10,elementName);
			if( visibility && !textValue.equals(""))
			{
				WebElement element = this.driver.findElement(textField);
				element.sendKeys(textValue);
				valid = true;
			}
		 }
		 catch(Exception e)
		 {
			 loggerUtils.stackTracePrint(e);
			 takeScreenShot(elementName);
		 }
		 return valid;
	}
	
	@Override
	public boolean moveToElementAndClick(By path, String elementName)
	{
		Boolean valid = false;
		try
		{
			WebElement element = driver.findElement(path);
			Actions act = new Actions(this.driver);
			act.moveToElement(element).click().build().perform();
			valid = true;
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
            takeScreenShot(elementName);
            
    	}
		return valid;
	}
	
	@Override
	public boolean jsClickElement(By buttonField,String elementName)
	{
		boolean valid=false;
		try
		{
			 WebElement element = driver.findElement(buttonField);
			 JavascriptExecutor executor = (JavascriptExecutor) driver;
			 executor.executeScript("arguments[0].click();", element);
			 valid=true;
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
			takeScreenShot(elementName);
		}
		
		return valid;
	}
	
	@Override
	public boolean alertPopupVerification()
	{ 
		    try 
		    { 
		        driver.switchTo().alert(); 
		        //System.out.println(driver.switchTo().alert().getText());
		        return true; 
		    }    
		    catch (Exception e) 
		    { 
		    	takeScreenShot("Chrome popup");
		        return false; 
		    }     
	}
	
	
	@Override
	public boolean waitforAlertPopup() 
	{
		boolean valid = false;
		try
		{
			 int i=0;
			   while(i++<5)
			   {
			        try
			        {
			            Alert alert = driver.switchTo().alert();
			            valid = true;
			            break;
			        }
			        catch(NoAlertPresentException e)
			        {
				        Thread.sleep(1000);
				        valid = false;
				        continue;
			        }
			   }
		}
		catch (Exception e)
		{
			takeScreenShot("Chrome popup");
			valid = false;
		}
		
		return valid;
	}
	
	@Override
	public String alertPopupGetText()
	{
		String text = "";
			    try 
			    { 
			        driver.switchTo().alert(); 
			        text = (driver.switchTo().alert().getText());
			    }    
			    catch (Exception e) 
			    { 
			    	takeScreenShot("Chrome popup");
			    }     
	    return text; 
	}
	
	
	@Override
	public boolean alertPopupAccept()
	{ 
		    try 
		    { 
		        driver.switchTo().alert().accept();; 
		        return true; 
		    }    
		    catch (Exception e) 
		    { 
		    	takeScreenShot("Chrome popup");
		        return false; 
		    }     
	}	 
	
	@Override
	public boolean explicitWaitforClickable(By path,int seconds,String elementName)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(this.driver,seconds);
			wait.until(ExpectedConditions.elementToBeClickable(path));
			return true;
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
			takeScreenShot(elementName);
			return false;
		}
	}
	
	@Override
	public boolean SwitchtoIframe(By path,int seconds,String elementName)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(this.driver,seconds);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(path));
			return true;
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
			takeScreenShot(elementName);
			return false;
		}
	}
	
	@Override
	public boolean SwitchtoParentFrame(String elementName)
	{
		try
		{
			driver.switchTo().parentFrame();
			return true;
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
			takeScreenShot(elementName);
			return false;
		}
	}
	
	@Override
	public boolean clearText(By path, int seconds, String elementName)
	{
		Boolean valid = false;
		try
		{
			if(explicitWaitforVisibility(path,10,elementName))
			 {
				WebElement element  = driver.findElement(path);
				element.clear();
				valid  = true;
			 }
		}
		catch(Exception e)
		{
			loggerUtils.stackTracePrint(e);
            takeScreenShot(elementName);
            
    	}
		
		return valid;
	}
	
	public String GetCookie(String CookieName)
	{
		String CookieValue="";
		try
		{
			CookieValue=driver.manage().getCookieNamed(CookieName).getValue();
		}
		catch(Exception e)
		{
			CookieValue="NOTFOUND";
		}
		
		return CookieValue;
	}
	
	@Override
	public void changeFocus(int tabNumber)
	{
		try
		{
			tabs = new ArrayList<String> (driver.getWindowHandles());
		    driver.switchTo().window(tabs.get(tabNumber));
		}
		catch (Exception e)
		{
			loggerUtils.stackTracePrint(e);
			takeScreenShot("Tab focus");
		}
	}
	
	@Override
	public void changeFocusToDefaultTab()
	{
		try
		{
		    driver.close();
		    driver.switchTo().window(tabs.get(0));
		}
		catch (Exception e)
		{
			loggerUtils.stackTracePrint(e);
			takeScreenShot("Tab focus to default");
		}
	}
	
	@Override
	public void driverMode(String view)
	{
		if(view.equalsIgnoreCase("mobile"))
		{
			driver.manage().window().setSize(new Dimension(500,650));
		}
		else if(view.equalsIgnoreCase("tablet"))
		{
			driver.manage().window().setSize(new Dimension(1000,650));
		}
	}
}
