package com.myjava.utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DefaultWebDriverSupplier {
	private String browser;
	public static WebDriver driver;
	private String driverpath;
	
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * Setting the Expected DriverPath having the path details for the Browser Driver binaries on local system
	 * @param driverPath
	 * driverPath is provided at Test_Config of Wiki Page of Fitnesse
	 */
	public void setPath(String driverpath) {
		this.driverpath = driverpath;
	}
	
	/**
	 * Set the test case name
	 * 
	 * @param testname
	 * @return
	 */
	public String setTestName(String testname) {
		// this.testname = testname;
		return testname;
	}

	/*
	 * newWebdriver method is which is returning webdriver depends upon running
	 * in Sauce or local environment.
	 */

	public WebDriver newWebDriver() throws Exception {
		if (browser.contains("remote")) {
			driver = new RemoteBrowserManager(browser).get();
			((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
		} else {
			LocalBrowserManager.initializeDriver(browser);
			driver = LocalBrowserManager.getLocalDriver();
		}
		return driver;

	}

	public String getProjectPath() {
		String path = System.getProperty("user.dir");
		path = path + "/target/test-classes";
		System.out.println(path + "path");
		return path;
	}

	public static boolean closeSession() {
		try {
			if (driver != null) {
				driver.quit();
			}
			return true;
		} catch (Exception e) {
			Log.error("Error while quitting the browser", e);
			return false;
		}
	}

}
