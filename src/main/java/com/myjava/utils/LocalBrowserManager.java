package com.myjava.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class LocalBrowserManager {
	public static WebDriver localDriver;
	public static String browserType = "";
	public static String osVersion = "";
	public static boolean remoteBrowser = false;
	/*
	 * intitializing local webdriver with passing argument as browser name. Passing
	 * all driver paths inside set property file.
	 */

	public static void initializeDriver(String browser) {
		System.out.println(System.getProperty("browserNames"));
		Log.info("==================================" + browser);
		Log.info(System.getProperty("os.name"));
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				if (System.getProperty("os.name").toLowerCase().contains("mac")) {
					System.setProperty("webdriver.chrome.driver", "./src/main/resources/testdriver/chromedriver");
				} else {
					System.setProperty("webdriver.chrome.driver", "./src/main/resources/testdriver/chromedriver.exe");
				}
				localDriver = new ChromeDriver();
				localDriver.manage().window().maximize();
				localDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				browserType = "chrome";
				// return localDriver;
			} else if (browser.equalsIgnoreCase("ie")) {
				if (System.getProperty("os.name").toLowerCase().contains("mac")) {
					System.setProperty("webdriver.ie.driver", "./src/main/resources/testdriver/IEDriverServer");
				} else {
					System.setProperty("webdriver.ie.driver", "./src/main/resources/testdriver/IEDriverServer.exe");
				}
				localDriver = new InternetExplorerDriver();
				localDriver.manage().window().maximize();
				localDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				browserType = "ie";
				// return localDriver;
			} else if (browser.equalsIgnoreCase("firefox")) {
				if (System.getProperty("os.name").toLowerCase().contains("mac")) {
					System.setProperty("webdriver.gecko.driver", "./src/main/resources/testdriver/geckodriver");
				} else {
					System.setProperty("webdriver.gecko.driver", "./src/main/resources/testdriver/geckodriver.exe");
				}
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				FirefoxOptions options = new FirefoxOptions();

				options.addPreference("log", "{level: trace}");

				capabilities.setCapability("marionette", true);
				capabilities.setCapability("moz:firefoxOptions", options);

				localDriver = new FirefoxDriver(capabilities);				
				localDriver.manage().window().maximize();
				localDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				browserType = "firefox";
				// return localDriver;
			} else if (browser.equalsIgnoreCase("edge")) {
				if (System.getProperty("os.name").toLowerCase().contains("mac")) {
					System.setProperty("webdriver.edge.driver", "./src/main/resources/testdriver/MicrosoftWebDriver");
				} else {
					System.setProperty("webdriver.edge.driver",
							"./src/main/resources/testdriver/MicrosoftWebDriver.exe");
				}
				localDriver = new EdgeDriver();
				localDriver.manage().window().maximize();
				localDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				browserType = "edge";
				// return localDriver;
			} else {
				throw new WebDriverException("No Matching browser found. So Staring Chrome browser");
			}
		} catch (WebDriverException e) {
			Log.error("No Matching browser found", e);
			localDriver = startChromeDriver(browser);
			localDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}

	/*
	 * implimenting chromedriver and returning driver
	 */
	public static WebDriver startChromeDriver(String browser) {
		WebDriver driver;
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			System.setProperty("webdriver.chrome.driver", "./src/main/resources/testdriver/chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", "./src/main/resources/testdriver/chromedriver.exe");
		}
		driver = new ChromeDriver();
		browserType = "chrome";
		return driver;

	}

	public static WebDriver getLocalDriver() {
		return localDriver;
	}

	public static void closeLocalDriver() {
		Log.info("Closing the dirver");
		localDriver.close();
	}

	public static void quitDriver() {
		Log.info("Quitting the driver");
		localDriver.quit();
	}
}
