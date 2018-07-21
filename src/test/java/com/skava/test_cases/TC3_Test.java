package com.skava.test_cases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.skava.reusable.components.TestComponents;

public class TC3_Test extends TestComponents{
	
	@BeforeTest
	public void initTest() throws IOException
	{		
		System.out.println(Thread.currentThread().getStackTrace()[1]);
	}	
	
	@Test(groups = {"Smoke"})
	public void TC3() throws Exception {
		System.out.println(Thread.currentThread().getStackTrace()[1]);
		//System.out.println(Thread.currentThread().getId());
		long val = Thread.currentThread().getId();
		logInfo("The thread id is : "  + val);
		logFail("This is test case 3 package TC");
	}
	
	@AfterTest
	public synchronized void tearDown()
	{
		System.out.println(Thread.currentThread().getStackTrace()[1]);
		if (driver != null) {
		driver.quit();	
		}
	}
}
