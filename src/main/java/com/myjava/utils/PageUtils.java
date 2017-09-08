package com.myjava.utils;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

public class PageUtils {
	public static int WAITTIME = 180;
	public static Map locatorMap;
	private static final String USER_AGENT = "Mozilla/5.0";

	static {
		ClassLoader classLoader = new PageUtils().getClass().getClassLoader();
		ObjectMapper jsonMapper = new ObjectMapper();
		String contents;
		try {
			contents = new Scanner(classLoader.getResourceAsStream("Object_Repository.json"), "UTF8")
					.useDelimiter("\\A").next();
			locatorMap = jsonMapper.readValue(contents, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Explicit wait method to wait for the element to be visible
	 * 
	 * @param driver
	 * @param by
	 * @return an instance of WebElement
	 */
	public static void waitForElementVisible(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	/**
	 * Explicit wait method to wait for the element not to be visible
	 * 
	 * @param driver
	 * @param by
	 * @return an instance of WebElement
	 */
	public static void waitForElementNotToBeVisible(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
		 wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	public static void waitForElementNotToBeVisible(WebDriver driver, By by, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		 wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	/**
	 * Explicit wait method to wait for the element to be visible
	 * 
	 * @param driver
	 * @param by
	 * @return an instance of WebElement
	 */
	public static void waitForElementVisible(WebDriver driver, By by, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/**
	 * Explicit wait method to wait for the element to be click-able
	 * 
	 * @param driver
	 * @param by
	 * @return an instance of WebElement
	 */
	public static void waitForElementClickable(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	/**
	 * @param driver
	 * @param by
	 * @param seconds
	 */
	public static void waitForElementClickable(WebDriver driver, By by, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	/**
	 * @param driver
	 * @param by
	 * @return
	 */
	public static boolean waitForElementSelectable(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
		return wait.until(ExpectedConditions.elementToBeSelected(by));
	}

	/**
	 * Explicit wait method to wait for the text to be visible
	 * 
	 * @param driver
	 * @param by
	 * @param text
	 * @return
	 */
	public static boolean waitForTextToBe(WebDriver driver, By by, String text) {
		WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
		return wait.until(ExpectedConditions.textToBe(by, text));
	}

	/**
	 * Explicit wait method to wait for the element to be located at the
	 * provided locator
	 * 
	 * @param driver
	 * @param by
	 * @return an instance of WebElement
	 */
	public static WebElement waitForElementLocated(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, WAITTIME);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	/**
	 * Explicit wait method to wait for the element to be visible within 5sec
	 * 
	 * @param driver
	 * @param by
	 * @return an instance of WebElement
	 */
	public static WebElement waitForElementVisibleWithin5sec(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/**
	 * This method will take a String input having locator name from the JSON
	 * file
	 * 
	 * @param page
	 *            : Page on which Locator can be found
	 * @param locatorName
	 *            : Name of the locator in JSON file
	 * @return an instance of By
	 * @throws CustomException
	 * @throws PageNotFoundException
	 * @throws LocatorNotFoundException
	 */

	public By getLocator(String page, String locatorName)
			throws Exception {
		Map locator = (Map) locatorMap.get(page);

		if (locator == null) {
			Log.error("No page " + page + " found");
			throw new Exception("No page " + page + " found");
		}

		if (locatorMap.get(page) instanceof Map) {
			locator = (Map) locator.get(locatorName);
			if (locator == null) {
				Log.error("No locator " + locatorName + " found");
				throw new Exception("No locator " + locatorName + " found");
			}
		} else {
			Log.error("locator not instance of Map");
		}

		String locatorType, locatorValue;
		locatorType = (String) locator.get("type");
		locatorValue = (String) locator.get("value");
		// Return a instance of By class based on type of locator
		return getInstanceOfByClassBasedOnTypeOfLocator(locatorType, locatorValue, "");

	}

	/**
	 * Wait for page to load
	 * 
	 * @param driver
	 */
	public static void waitForLoad(WebDriver driver) {
		new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
				.executeScript("return document.readyState").equals("complete"));
	}

	/**
	 * This method will take a dynamic String input having locator name from the
	 * JSON file
	 * 
	 * @param page
	 * @param locatorName
	 * @param dynamicText
	 * @return
	 * @throws CustomException
	 * @throws PageNotFoundException
	 * @throws LocatorNotFoundException
	 */
	public By getLocator(String page, String locatorName, String dynamicText)
			throws Exception {
		Map locator = (Map) locatorMap.get(page);

		if (locator == null) {
			Log.error("No page " + page + " found");
			throw new Exception("No page " + page + " found");
		}

		if (locatorMap.get(page) instanceof Map) {
			locator = (Map) locator.get(locatorName);
			if (locator == null) {
				Log.error("No locator " + locatorName + " found");
				throw new Exception("No locator " + locatorName + " found");
			}
		} else {
			Log.error("locator not instance of Map");
		}

		String locatorType, locatorValue;
		locatorType = (String) locator.get("type");
		locatorValue = (String) locator.get("value");
		// Return a instance of By class based on type of locator
		return getInstanceOfByClassBasedOnTypeOfLocator(locatorType, locatorValue, dynamicText);
	}

	/**
	 * returns an instance on by class
	 * 
	 * @param locatorType
	 * @param locatorValue
	 * @param dynamicText
	 * @return
	 * @throws CustomException
	 */
	public By getInstanceOfByClassBasedOnTypeOfLocator(String locatorType, String locatorValue, String dynamicText)
			throws Exception {
		// Return a instance of By class based on type of locator
		switch (locatorType) {
		case "id":
			return By.id(locatorValue);
		case "name":
			return By.name(locatorValue);
		case "classname":
			return By.className(locatorValue);
		case "class":
			return By.className(locatorValue);
		case "tagname":
			return By.tagName(locatorValue);
		case "tag":
			return By.tagName(locatorValue);
		case "linktext":
			return By.linkText(locatorValue);
		case "link":
			return By.linkText(locatorValue);
		case "partiallinktext":
			return By.partialLinkText(locatorValue);
		case "cssselector":
			return By.cssSelector(locatorValue);
		case "css":
			return By.cssSelector(locatorValue);
		case "xpath":
			return By.xpath(locatorValue);
		case "TEXT":
			return By.xpath(locatorValue.replace("textToReplace", dynamicText));
		case "TEXTCSS":
			return By.cssSelector(locatorValue.replace("textToReplace", dynamicText));
		default:
			throw new Exception("Locator type '" + locatorType + "' not defined!!");
		}
	}

	/**
	 * Wait for the angular page to load
	 * 
	 * @param driver
	 * @throws Exception
	 */
	public static void waitForAngularPageToLoad(WebDriver driver) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 15, 100);
		Thread.sleep(2000);
	}

	/**
	 * @param driver
	 * @param element
	 * @param sub_locator
	 * @throws Exception
	 */
	public static void waitForAllElement(WebDriver driver, WebElement element, By sub_locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 15, 100);
		wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, sub_locator));

	}

	/**
	 * @param driver
	 * @return
	 */
	public static boolean waitForPageLoadJS(WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		try {
			new WebDriverWait(driver, 30).until(new Predicate<WebDriver>() {
				public boolean apply(WebDriver webDriver) {
					return ("complete").equals(executor.executeScript("return document.readyState"));
				}
			}

			);
			return ("complete").equals(executor.executeScript("return document.readyState"));
		} catch (Exception e) {
			Log.error("Page did not load");
			return false;
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param timeoutSeconds
	 * @return
	 */
	public static WebElement findElement(WebDriver driver, By locator, int timeoutSeconds) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeoutSeconds, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);

		return wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
	}
	/**
	 * @param driver
	 * @param locator
	 * @return
	 */
	public static WebElement findElement(WebDriver driver, By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(WAITTIME, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);

		return wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
	}
	
	/**
	 * @param waitTime
	 */
	public static void explicitWait(int waitTime) {
		try {
			Thread.sleep(waitTime);
		} catch (Exception e) {
			Log.error("Error in explicitWait method" + e);
		}
	}

	/**
	 * @param driver
	 * @param milliseconds
	 * @throws Exception
	 */
	public static void waitForAngularPageToLoad(WebDriver driver, long milliseconds) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30, 100);
		sleep((int) milliseconds / 1000);
	}

	/**
	 * @return
	 */
	public static ExpectedCondition<Boolean> angularHasFinishedProcessing() {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return Boolean.valueOf(((JavascriptExecutor) driver)
						.executeScript(
								"return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)")
						.toString());
			}
		};
	}

	/**
	 * @param durationInSeconds
	 */
	public static void sleep(int durationInSeconds) {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(new Duration(durationInSeconds, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}	

}
