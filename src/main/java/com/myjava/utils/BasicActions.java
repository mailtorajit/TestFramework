package com.myjava.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class BasicActions extends PageUtils {
	private static final String USER_AGENT = "Mozilla/5.0";

	/**
	 * This method will takes the screenshot
	 * 
	 * @param driver
	 * @param imagefilename
	 * @throws Exception
	 */
	public static void getscreenshot(WebDriver driver, String imagefilename) throws Exception {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			org.apache.commons.io.FileUtils.copyFile(src,
					new File(System.getProperty("user.dir") + "/FitNesseRoot/files/images/" + imagefilename + ".jpeg"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * clears the text in the element
	 * 
	 * @param driver
	 * @param by
	 */
	public static void clearElement(WebDriver driver, By by) {
		WebElement element = waitForElementLocated(driver, by);
		element.clear();
	}

	/**
	 * This method will click on webElement
	 * 
	 * @param driver
	 * @param by
	 */
	public static void clickOnElement(WebDriver driver, By by) throws Exception {
		WebElement element = waitForElementLocated(driver, by);
		if (LocalBrowserManager.browserType.equalsIgnoreCase("edge")) {
			jsClick(driver, by);
		} else {
			element.click();
		}
		waitForAngularPageToLoad(driver, 3000);
	}

	/**
	 * This method will clear a textbox and enter text to the textbox
	 * 
	 * @param driver
	 * @param by
	 * @param string
	 */
	public static void clearAndEnterKey(WebDriver driver, By by, String string) {
		waitForElementVisible(driver, by);
		WebElement element = driver.findElement(by);
		element.clear();
		sleep(2);
		element.sendKeys(string);
	}
	
	/**
	 * This method will enters the textbox without clearing it
	 * 
	 * @param driver
	 * @param by
	 * @param string
	 */
	public static void enterKey(WebDriver driver, By by, String string) {
		waitForElementVisible(driver, by);
		WebElement element = driver.findElement(by);	
		sleep(2);
		element.sendKeys(string);
	}

	/**
	 * This method will clear a textbox and enter text to the textbox
	 * 
	 * @param driver
	 * @param by
	 * @param string
	 * @throws Exception
	 */
	public static void clearAndTypeEachKey(WebDriver driver, By by, String string) throws Exception {
		waitForElementVisible(driver, by);
		WebElement element = driver.findElement(by);
		element.clear();
		for (int i = 0; i < string.length(); i++) {
			element.sendKeys(Character.toString(string.charAt(i)));
			Thread.sleep(1000);
		}
	}

	/**
	 * check if element is present
	 * 
	 * @param driver
	 * @param by
	 * @return
	 */
	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			Log.info("Verifying element with path " + by + " is present or not");
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			Log.error("Element is not present with path " + by);
			return false;
		}
	}

	/**
	 * Checks if alert is present
	 * 
	 * @param driver
	 * @return
	 */
	public boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			Log.error("Alert is not present");
			return false;
		}
	}

	/**
	 * Select from drop down by value
	 * 
	 * @param driver
	 * @param by
	 * @param valueToBeSelected
	 */
	public static void selectByValue(WebDriver driver, By by, String valueToBeSelected) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			Select select = new Select(element);
			select.selectByValue(valueToBeSelected);
			waitForAngularPageToLoad(driver, 3000);

		} catch (Exception e) {
			Log.error("Error while selecting by value ", e);
		}
	}

	/**
	 * Returns the selected value
	 * 
	 * @param driver
	 * @param by
	 * @param valueToBeSelected
	 */
	public static String getSelectedValue(WebDriver driver, By by) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			Select select = new Select(element);
			return select.getAllSelectedOptions().get(0).getText();

		} catch (Exception e) {
			Log.error("Error while selecting by value ", e);
			return "";
		}
	}

	/**
	 * This method selects the option by its visible text
	 * 
	 * @param driver
	 * @param by
	 * @param valueToBeSelected
	 */
	public static void selectByVisibleText(WebDriver driver, By by, String valueToBeSelected) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			Select select = new Select(element);
			select.selectByVisibleText(valueToBeSelected);
			waitForAngularPageToLoad(driver, 3000);
		} catch (Exception e) {
			Log.error("Error while seleting by visible text", e);
		}
	}

	/**
	 * This method will help to select the option by its index
	 * 
	 * @param driver
	 * @param by
	 * @param index
	 */
	public static void selectByIndex(WebDriver driver, By by, int index) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			Select select = new Select(element);
			select.selectByIndex(index);
			waitForAngularPageToLoad(driver, 3000);
		} catch (Exception e) {
			Log.error("Locator not found", e);

		}
	}	

	/**
	 * This method will return the attribute value of the locator
	 * 
	 * @param driver
	 * @param by
	 * @param attribute
	 * @return
	 * @throws CustomException
	 */
	public static String getAttributeValue(WebDriver driver, By by, String attribute) throws Exception{
		try {
			WebElement element = waitForElementLocated(driver, by);
			return element.getAttribute(attribute);

		} catch (Exception e) {
			Log.error("Locator not found", e);
			throw new Exception("No locator " + by + " found");
		}

	}

	/**
	 * This method will return all the option values present in the select box
	 * 
	 * @param driver
	 * @param by
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getAllValuesOfSelect(WebDriver driver, By by) throws Exception {
		ArrayList<String> values = new ArrayList<String>();
		Select select = new Select(driver.findElement(by));
		List<WebElement> optionSelect = select.getOptions();

		for (int i = 0; i < optionSelect.size(); i++) {
			values.add(optionSelect.get(i).getText().trim());
		}
		return values;
	}

	/**
	 * This method will switches to parent window by closing all child windows
	 * 
	 * @param driver
	 */
	public static void switchToParentWithClose(WebDriver driver) {
		try {
			List<String> win = new ArrayList<String>(driver.getWindowHandles());

			for (int i = 1; i < win.size(); i++) {
				driver.switchTo().window(win.get(i));
				driver.close();
			}
			driver.switchTo().window(win.get(0));
			waitForAngularPageToLoad(driver, 1000);
		} catch (Exception e) {
			Log.error("Exception ", e);
		}
	}

	/**
	 * Switches to frame of given index
	 * 
	 * @param driver
	 * @param index
	 */
	public static void switchTo(WebDriver driver, int index) {
		try {
			List<String> win = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(win.get(index));
			waitForAngularPageToLoad(driver, 1000);
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Invalid Window Index : " + index);
		} catch (Exception e) {
			Log.error("Exception ", e);
		}

	}

	/**
	 * Switches to frame and enters the given text
	 * 
	 */
	public void switchToIFrameEnterText(WebDriver driver, By iframe, By element, String text) {
		WebElement elem = driver.findElement(iframe);
		driver.switchTo().frame(elem);
		WebElement body = driver.findElement(element);
		Actions act1 = new Actions(driver);
		act1.sendKeys(body, text).build().perform();
		driver.switchTo().parentFrame();
	}

	/**
	 * Switches to parent frame
	 * 
	 * @param driver
	 */
	public static void switchToParentFrame(WebDriver driver) {
		try {
			driver.switchTo().parentFrame();
			waitForAngularPageToLoad(driver, 1000);
		} catch (Exception e) {
			Log.error("Exception in switching to parent frame ", e);
		}
	}

	/**
	 * This method will switch to frame
	 * 
	 * @param driver
	 * @param by
	 */
	public static void switchToFrame(WebDriver driver, By by) {
		WebElement frameElement = waitForElementLocated(driver, by);
		try {
			driver.switchTo().frame(frameElement);

			waitForAngularPageToLoad(driver, 2000);
		} catch (Exception e) {
			Log.error("Error while switching the frame", e);
		}
	}

	/**
	 * Switch to default content of web page
	 * 
	 * @param driver
	 */
	public static void switchToDefaultContent(WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
			waitForAngularPageToLoad(driver, 1000);
		} catch (Exception e) {
			Log.error("Error while switching to default content ", e);
		}
	}

	/**
	 * Send keys to text box
	 * 
	 * @param driver
	 * @param by
	 * @param key
	 */
	public static void sendKeys(WebDriver driver, By by, Keys key) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			element.sendKeys(key);
			waitForAngularPageToLoad(driver, 1000);
		} catch (Exception e) {
			Log.error("Error while sending keys ", e);
		}
	}

	/**
	 * Get text for a web element
	 * 
	 * @param driver
	 * @param by
	 * @return
	 */
	public static String getText(WebDriver driver, By by) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			return element.getText().trim();

		} catch (Exception e) {
			Log.error("Locator not found", e);
			return null;
		}

	}

	/**
	 * Get parent window handles
	 * 
	 * @param driver
	 * @return
	 */
	public static String getParentWindowHandle(WebDriver driver) {
		return driver.getWindowHandle();
	}

	/**
	 * get child window handles
	 * 
	 * @param driver
	 * @return
	 */
	public static Set<String> getChildwindowHandles(WebDriver driver) {
		return driver.getWindowHandles();
	}

	/**
	 * Close all tab except the required tab
	 * 
	 * @param driver
	 * @param allTabs
	 * @param givenTab
	 */
	public static void closeAllTabsExceptGivenTab(WebDriver driver, Set<String> allTabs, String givenTab) {
		try {
			for (String currentTab : allTabs) {
				if (!currentTab.equals(givenTab)) {
					driver.switchTo().window(currentTab);
					driver.close();
					waitForAngularPageToLoad(driver, 1000);
				}
			}
		} catch (Exception e) {
			Log.error("Locator while closing all tabs except the given one", e);
		}
	}

	/**
	 * Enter text and select from drop down
	 * 
	 * @param driver
	 * @param by
	 * @param string
	 * @throws Exception
	 */
	public static void enterTextAndSelectDropDownValue(WebDriver driver, By by, String string) {
		WebElement element = waitForElementLocated(driver, by);
		try {
			element.clear();
			element.sendKeys(string);
			explicitWait(2000);
			element.sendKeys(Keys.ARROW_DOWN);
			explicitWait(2000);
			element.sendKeys(Keys.RETURN);
			// explicitWait(2000);
		} catch (Exception e) {
			Log.error("Error in enterTextAndSelectDropDownValue method" + e);

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
	 * @param milliseconds
	 * @throws Exception
	 */
	public static void waitForAngularPageToLoad(WebDriver driver, long milliseconds) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30, 100);
		sleep((int) milliseconds / 1000);
	}

	/**
	 * Clicks using javascript executor
	 * 
	 * @param driver
	 * @param by
	 * @throws Exception
	 */
	public static void jsClick(WebDriver driver, By by) throws Exception {
		try {
			Log.info("Clicking on element using jsClick");
			WebElement element = waitForElementLocated(driver, by);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			waitForAngularPageToLoad(driver, 3000);
		} catch (Exception e) {
			Log.error("Error in jsClick", e);
			throw new Exception(e.getStackTrace().toString());
		}

	}

	/**
	 * This method will return the text of selected from select box
	 * 
	 * @param driver
	 * @param by
	 * @return
	 * @throws Exception
	 */
	public String getSelectedOptionText(WebDriver driver, By by) throws Exception {
		Select select = new Select(driver.findElement(by));
		return select.getFirstSelectedOption().getText();
	}

	/**
	 * This method will return the date in specified format Example format::
	 * "dd-MM-yyyy"
	 * 
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String getDate(String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date = sdf.format(new Date());
		Log.info("Date is =====" + date);
		return date;
	}

	/**
	 * This method will return if the check box or radio button is selected
	 * 
	 * @param driver
	 * @param by
	 * @return
	 */
	public static boolean isSelected(WebDriver driver, By by) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			return element.isSelected();
		} catch (Exception e) {
			Log.info(String.format("Element not  found %s", by.toString()));
			Log.info(e.getStackTrace().toString());
			return false;
		}
	}

	/**
	 * This method will return if the locator is enabled
	 * 
	 * @param driver
	 * @param by
	 * @return
	 */
	public static boolean isEnabled(WebDriver driver, By by) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			return element.isEnabled();
		} catch (Exception e) {
			Log.info(String.format("Element not  found %s", by.toString()));
			Log.info(e.getStackTrace().toString());
			return false;
		}
	}

	/**
	 * gets the Json values
	 * 
	 * @param jsonString
	 * @return
	 */
	public static JSONObject getJson(String jsonString) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonString);
			return jsonObj;
		} catch (JSONException e) {
			e.printStackTrace();
			return jsonObj;
		}
	}

	/**
	 * Opens the url
	 * 
	 * @param driver
	 * @param url
	 * @throws Exception
	 */

	public static void openUrl(WebDriver driver, String url) throws Exception {
		PageUtils.waitForAngularPageToLoad(driver, 3000);
		driver.get(url);
		PageUtils.waitForAngularPageToLoad(driver, 3000);
	}

	// HTTP GET request
	/**
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String sendGet(String url, String username, String password) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		String userpass = username + ":" + password;
		byte[] bytesEncoded = Base64.encodeBase64(userpass.getBytes());
		String authEncoded = new String(bytesEncoded);
		String basicAuth = "Basic " + authEncoded;
		Log.info("Authorization is " + basicAuth);
		con.setRequestProperty("Authorization", basicAuth);

		int responseCode = con.getResponseCode();
		Log.info("\nSending 'GET' request to URL : " + url);
		Log.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		return response.toString();
	}

	// HTTP POST request
	/**
	 * @param url
	 * @param username
	 * @param password
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(String url, String username, String password, HashMap<String, String> params)
			throws Exception {

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		String userpass = username + ":" + password;
		byte[] bytesEncoded = Base64.encodeBase64(userpass.getBytes());
		String authEncoded = new String(bytesEncoded);
		String basicAuth = "Basic " + authEncoded;
		Log.info("Authorization is " + basicAuth);
		// add request header
		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-Agent", USER_AGENT);
		connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		connection.setRequestProperty("Authorization", basicAuth);

		// Send post request
		connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		JSONObject generatedJSONString = new JSONObject();
		Set<String> keys = params.keySet();
		for (String key : keys) {
			generatedJSONString.put(key, params.get(key));

		}
		String content = generatedJSONString.toString();
		byte[] postData = content.getBytes(Charset.forName("UTF-8"));
		wr.write(postData);
		wr.flush();
		wr.close();

		int responseCode = connection.getResponseCode();
		Log.info("\nSending 'POST' request to URL : " + url);
		Log.info("Post parameters : " + postData);
		Log.info("Response Code : " + responseCode);
		if (responseCode == 409) {
			Log.info("Slot is already booked");
		} else if (responseCode == 200) {
			Log.info("Appointment booked successfully");
		} else {

		}
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		Log.info(response.toString());
		return response.toString();
	}

	/**
	 * @param driver
	 * @param string
	 * @return
	 */
	public static boolean isTextPresent(WebDriver driver, String string) {
		try {
			driver.getPageSource().contains(string);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	/**
	 * @param text
	 * @param regExp
	 * @return
	 */
	public static Matcher getMatcher(String text, String regExp) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher match = pattern.matcher(text);
		return match;
	}

	/**
	 * @param driver
	 * @param by
	 * @return
	 */
	public static int getSize(WebDriver driver, By by) {
		return driver.findElements(by).size();
	}

	/**
	 * @param driver
	 * @param by
	 * @return
	 */
	public static String getValue(WebDriver driver, By by) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			return element.getAttribute("value");
		} catch (Exception e) {
			Log.error("Locator not found", e);
			return null;
		}
	}
	
	/**
	 * @param driver
	 * @param by
	 * @return
	 */
	public static String getClass(WebDriver driver, By by) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			return element.getAttribute("class");
		} catch (Exception e) {
			Log.error("Locator not found", e);
			return null;
		}
	}
	
	/**
	 * @param driver
	 * @param by
	 * @return
	 */
	public static String getTitle(WebDriver driver, By by) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			return element.getAttribute("title");
		} catch (Exception e) {
			Log.error("Locator not found", e);
			return null;
		}
	}

	/**
	 * @param driver
	 * @param by
	 * @param property
	 * @return
	 */
	public static String getCssValue(WebDriver driver, By by, String property) {
		try {
			WebElement element = waitForElementLocated(driver, by);
			return element.getCssValue(property);
		} catch (Exception e) {
			Log.error("Locator not found", e);
			return null;
		}
	}

	/**
	 * get child window handles
	 * 
	 * @param driver
	 * @return
	 */
	public static ArrayList<String> getAllwindows(WebDriver driver) {
		ArrayList<String> list = new ArrayList<String>();
		Set<String> windows = driver.getWindowHandles();
		for (String currentTab : windows) {
			list.add(currentTab);
		}
		return list;
	}

	/**
	 * @param driver
	 * @param by
	 * @return
	 */
	public static boolean isDisplayed(WebDriver driver, By by) {
		try {
			PageUtils.findElement(driver, by, 3).isDisplayed();
			return true;
		} catch (NoSuchElementException t) {
			Log.info("Unable to find element:" + by);
			return false;
		}
	}

	/**
	 * @param driver
	 * @param by
	 * @return
	 */
	public static boolean isVisible(WebDriver driver, By by) {
		try {
			PageUtils.findElement(driver, by, 3).isDisplayed();
			return true;
		} catch (ElementNotVisibleException t) {
			Log.info("Unable to find element:" + by);
			return false;
		}
	}

	/**
	 * @param driver
	 * @param by
	 */
	public static void actionClick(WebDriver driver, By by) {
		try {
			Actions action = new Actions(driver);
			action.click(driver.findElement(by)).build().perform();
		} catch (Exception e) {
			Log.error("Error while clicking on element using action class on element " + by, e);
		}
	}

	/**
	 * @param driver
	 * @param by
	 */
	public static void moveToElement(WebDriver driver, By by) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(by)).build().perform();
		} catch (Exception e) {
			Log.error("Error while moving to element using action class " + by, e);
		}
	}

	/**
	 * @param driver
	 * @param by
	 */
	public static void jsMouseOver(WebDriver driver, By by) {
		try {
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			((JavascriptExecutor) driver).executeScript(javaScript, driver.findElement(by));
		} catch (Exception e) {
			Log.error("Error while doing mouseover " + by, e);
		}
	}

	/**
	 * @param driver
	 * @param by
	 * @param message
	 */
	public static void verifyText(WebDriver driver, By by, String message) {
		String actualText = getText(driver, by);
		System.out.println("Expected Text:" + message);
		System.out.println("Actual Text:" + actualText);
		Assert.assertTrue(actualText.trim().equalsIgnoreCase(message));

	}

	/**
	 * @param driver
	 * @param by
	 * @param message
	 * @param property
	 * @throws Exception
	 */
	public static void verifyText(WebDriver driver, By by, String message, String property) throws Exception {
		waitForElementVisible(driver, by);
		String actualText = "";
		actualText = getAttributeValue(driver, by, property);
		System.out.println("Expected Text:" + message);
		System.out.println("Actual Text:" + actualText);
		Assert.assertTrue(actualText.equalsIgnoreCase(message));
	}

	/**
	 * @param driver
	 * @param by
	 * @throws Exception
	 */
	public static void scrollToElement(WebDriver driver, By by) throws Exception {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				PageUtils.findElement(driver, by, 30));
		waitForAngularPageToLoad(driver, 2000);
	}

	/**
	 * @param driver
	 * @param by
	 * @return
	 * @throws Exception
	 */
	public static String getInnerHtml(WebDriver driver, By by) throws Exception {
		WebElement element = driver.findElement(by);
		String contents = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;",
				element);
		return contents;
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

	/**
	 * @param length
	 * @return
	 */
	public static String generateRandomString(int length) {
		Random ran = new Random();

		char data = ' ';
		String randomString = "";

		for (int i = 0; i <= length; i++) {
			data = (char) (ran.nextInt(25) + 97);
			randomString = data + randomString;
		}
		return randomString;
	}

	/**
	 * @param length
	 * @return
	 */
	public static String generateRandomNumber(int length) {
		Random ran = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append((char) ('0' + ran.nextInt(10)));
		return sb.toString();
	}

	/**
	 * Method to get yesterday's date
	 * 
	 * @return
	 */
	private static Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * This method will return the date in specified format Example format::
	 * "dd-MM-yyyy"
	 * 
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String getYesterdayDateString(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(yesterday());
	}
	
	// HTTP GET request
		public static String sendGet(String url) throws Exception {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			Log.info("\nSending 'GET' request to URL : " + url);
			Log.info("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
			return response.toString();
		}

}
