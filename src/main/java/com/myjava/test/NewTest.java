package com.myjava.test;

import org.testng.annotations.Test;

import com.myjava.utils.LocalBrowserManager;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class NewTest {
	

	@Test(dataProvider="dp")	
	public void f(String sBrowser,int i, String a) throws InterruptedException {
		WebDriver driver;
		LocalBrowserManager.initializeDriver(sBrowser);
		driver = LocalBrowserManager.getLocalDriver();
		// Launch the Online Store Website
		driver.get("http://www.store.demoqa.com");
		Thread.sleep(5000);
		// Print a Log In message to the screen
		System.out.println("Successfully opened the website www.Store.Demoqa.com from f");
		driver.get("http://www.google.com");
		Thread.sleep(5000);
		System.out.println("Successfully opened the website www.google.com from f");
		// Wait for 5 Sec
	}

	@Test(dataProvider="dp2")
	public void f2(String sBrowser,int i, String a) throws InterruptedException {
		WebDriver driver;
		LocalBrowserManager.initializeDriver(sBrowser);
		driver = LocalBrowserManager.getLocalDriver();
		// Launch the Online Store Website
		driver.get("http://www.store.demoqa.com");
		Thread.sleep(5000);
		// Print a Log In message to the screen
		System.out.println("Successfully opened the website www.Store.Demoqa.com from f2");
		driver.get("http://www.google.com");
		System.out.println("Successfully opened the website www.google.com from f2");
		Thread.sleep(5000);
		// Wait for 5 Sec
	}

	@DataProvider(name = "dp")	
	public Object[][] dp(ITestContext context) {
		String sBrowser = context.getCurrentXmlTest().getParameter("browser");
		System.out.println(sBrowser);
		return new Object[][] { new Object[] {sBrowser, 1, "a"}, new Object[] { sBrowser,2, "b" }};
	}

	
	@DataProvider(name = "dp2")	
	public Object[][] dp2(ITestContext context) {
		String sBrowser = context.getCurrentXmlTest().getParameter("browser");
		System.out.println(sBrowser);
		Object o[][] =  new Object[][] { new Object[] {sBrowser, 1, "a"}, new Object[] { sBrowser,2, "b" }};
		System.out.println(o[0][0]);
		return o;
	}

//	@BeforeMethod	
	public void setUp(String sBrowser) {		
		System.out.println("browser created");
	}

	@AfterMethod
	public void tearDown() {

		// Close the driver
		//driver.quit();
	}

}
