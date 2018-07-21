package com.skava.header_pkg;

import com.skava.reusable.components.TestComponents;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC10_Test extends TestComponents
{
	@BeforeClass(alwaysRun = true)
	public void initTest() throws IOException
	{		
		driver = initiTest(this.getClass().getSimpleName());
		//driver.maximizeBrowser();
	}
	
	@Test (groups = {"Smoke" , "Sanity"})
	public void TC10() throws Exception
	{
		//System.out.println(Thread.currentThread().getId());
		long val = Thread.currentThread().getId();
		logInfo("The thread id is : "  + val);
		logPass("This is test case 10 package Header");
		logSkip("This is test case 10 package TC");
	}
	
	@AfterClass(alwaysRun = true)
	public synchronized void tearDown()
	{		
		if (driver != null) 
		{
			driver.quit();
		}
	}
}
