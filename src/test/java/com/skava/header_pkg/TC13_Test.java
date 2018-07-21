package com.skava.header_pkg;

import com.skava.reusable.components.TestComponents;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC13_Test extends TestComponents
{
	@BeforeClass(alwaysRun = true)
	public void initTest() throws IOException
	{		
		System.out.println(Thread.currentThread().getStackTrace()[1]);
		//driver = initiTest(this.getClass().getSimpleName());
		//driver.maximizeBrowser();
	}
	
	@Test(groups = {"Smoke"})
	public void TC13() throws Exception
	{
		System.out.println(Thread.currentThread().getStackTrace()[1]);
		//System.out.println(Thread.currentThread().getId());
		logPass("This is test case 13 package Header");
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
