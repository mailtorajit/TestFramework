package com.myjava.test;

import org.testng.annotations.Test;

import com.myjava.utils.LocalBrowserManager;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class NewTest {
	WebDriver driver;

	@Test(dataProvider = "dp")
	public void f(Integer n, String s) throws InterruptedException {
		// Launch the Online Store Website
		driver.get("http://www.store.demoqa.com");
Thread.sleep(5000);
		// Print a Log In message to the screen
		System.out.println("Successfully opened the website www.Store.Demoqa.com");
		driver.get("http://www.google.com");
		Thread.sleep(5000);
		// Wait for 5 Sec
	}
	
	@Test(dataProvider = "dp2")
	public void f2(Integer n, String s) throws InterruptedException {
		// Launch the Online Store Website
		driver.get("http://www.store.demoqa.com");
		Thread.sleep(5000);
		// Print a Log In message to the screen
		System.out.println("Successfully opened the website www.Store.Demoqa.com");
		driver.get("http://www.google.com");
		Thread.sleep(5000);
		// Wait for 5 Sec
	}

	@DataProvider(name="dp", parallel=true)
	public Object[][] dp() {
		return new Object[][] { new Object[] { 1, "a" }, new Object[] { 2, "b" }, };
	}
	
	@DataProvider(name="dp2", parallel=true)
	public Object[][] dp2() {
		return new Object[][] { new Object[] { 1, "a" }, new Object[] { 2, "b" }, };
	}

	@BeforeTest
	@Parameters({"browser"})
	public void beforeTest(String sBrowser) {
		LocalBrowserManager.initializeDriver(sBrowser);
		driver = LocalBrowserManager.getLocalDriver();

	}

	@AfterMethod
	public void afterTest() {

		// Close the driver
		driver.quit();
	}

}
