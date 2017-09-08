package com.myjava.test;

import org.openqa.selenium.WebDriver;
import com.myjava.utils.LocalBrowserManager;

public class FirstTestCase2 {

	public static void main(String[] args) {
		
		// Create a new instance of the Firefox driver
		LocalBrowserManager.initializeDriver("chrome");
		WebDriver driver = LocalBrowserManager.getLocalDriver();
        //Launch the Online Store Website
		driver.get("http://www.store.demoqa.com");

        // Print a Log In message to the screen
        System.out.println("Successfully opened the website www.Store.Demoqa.com");

		//Wait for 5 Sec
	
		
        // Close the driver
        driver.quit();
    }
}