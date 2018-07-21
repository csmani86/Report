package com.skava.test_cases;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.skava.reusable.components.TestComponents;

public class TC2_Test extends TestComponents
{	
	@BeforeClass(alwaysRun = true)
	public void initTest() throws IOException
	{		
		System.out.println(Thread.currentThread().getStackTrace()[1]);
	}	
	
	@Test(groups = {"Smoke"})
	public void TC2() throws Exception 
	{
		System.out.println(Thread.currentThread().getStackTrace()[1]);
		long val = Thread.currentThread().getId();
		logInfo("The thread id is : "  + val);
		logPass("This is test case 2 package TC");
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
