package com.bridge.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.BeforeTest;

public class BaseTest {

	protected static WebDriver driver;
	
	private static final Log LOG = LogFactory.getLog(BaseTest.class);

	
	@BeforeTest
	public static void getWebDriver(){
		FirefoxProfile profile = new FirefoxProfile();
		
		//关闭css加载
		profile.setPreference("permissions.default.stylesheet", 2);

		LOG.info("start......");
		
		driver = new FirefoxDriver(profile);
	}
	
	public static void closeWebDriver(){
		//关闭webdriver
		driver.close();
		
		driver.quit();
		
		LOG.info("close browser......");
	}
}
