package com.skava.test_cases;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.skava.reusable.components.TestComponents;

public class TC16_Test extends TestComponents
{
	@BeforeClass(alwaysRun = true)
	public void initTest() throws IOException
	{		
		/*driver = initiTest(this.getClass().getSimpleName());
		driver.maximizeBrowser();*/
	}
	
	@Test
	public void TC16() throws Exception 
	{
		long val = Thread.currentThread().getId();
		logInfo("The thread id is : "  + val);
		//System.out.println(Thread.currentThread().getId());
		logPass("This is test case 16 package TC");
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
