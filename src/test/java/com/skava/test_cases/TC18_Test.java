package com.skava.test_cases;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.skava.reusable.components.TestComponents;

public class TC18_Test extends TestComponents
{
	@BeforeClass(alwaysRun = true)
	public void initTest() throws IOException
	{		
		/*driver = initiTest(this.getClass().getSimpleName());
		driver.maximizeBrowser();*/
	}
	
	@Test
	public void TC18() throws Exception 
	{
		//System.out.println(Thread.currentThread().getId());
		Thread.sleep(3000);
		long val = Thread.currentThread().getId();
		logInfo("The thread id is : "  + val);
		logPass("This is test case 18 package TC");
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
