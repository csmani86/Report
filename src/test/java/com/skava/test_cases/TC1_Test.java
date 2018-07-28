package com.skava.test_cases;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.skava.reusable.components.TestComponents;

public class TC1_Test extends TestComponents
{
	@BeforeClass(alwaysRun = true)
	public void initTest() throws IOException
	{		
		/*driver = initiTest(this.getClass().getSimpleName());
		driver.maximizeBrowser();*/
	}
	
	@Test(groups = {"Smoke"})
	public void TC1() throws Exception 
	{
		//System.out.println(Thread.currentThread().getId());
		long val = Thread.currentThread().getId();
		logInfo("The thread id is : "  + val);
		Thread.sleep(3000);
		logPass("This is test case 1 package TC");
	}
	
	@AfterClass(alwaysRun = true)
	public synchronized void tearDown()
	{		
		System.out.println(Thread.currentThread().getStackTrace()[1]);
		if (driver != null) 
		{
			driver.quit();
		}
	}
}
