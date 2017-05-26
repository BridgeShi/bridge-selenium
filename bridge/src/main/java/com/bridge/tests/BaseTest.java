package com.bridge.tests;

import java.io.File;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;

import com.bridge.dao.Account;
import com.bridge.dao.AccountDAO;
import com.bridge.pages.taobao.LoginPage;

public class BaseTest {

	protected static WebDriver driver;
	
	protected static final Log LOG = LogFactory.getLog(BaseTest.class);

	private static Account account = null;
	private static AccountDAO accountDAO = null;
	
	@BeforeTest
	public static void getWebDriver(){
		
		ProfilesIni pi = new ProfilesIni();
		
		FirefoxProfile profile = pi.getProfile("default");
		
		//关闭css加载
		//profile.setPreference("permissions.default.stylesheet", 2);

		LOG.info("start......");
		
		driver = new FirefoxDriver(profile);
	}
	
	public static void closeWebDriver(){
		//关闭webdriver
		driver.close();
		
		driver.quit();
		
		LOG.info("close browser......");
	}
	
	public static void getChromeDriverWithExtension(String file){
		
		//设置自己电脑中chrome的路径
		System.setProperty("webdriver.chrome.driver", "/Users/bridge/Program/chromedriver");

		ChromeOptions options = new ChromeOptions ();

		options.addExtensions (new File(file));

		DesiredCapabilities capabilities = new DesiredCapabilities ();

		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		driver = new ChromeDriver(capabilities);

		//driver.get(url);
		LOG.info("start......");
		
		driver.manage().timeouts().implicitlyWait(60*1000, TimeUnit.SECONDS);
		//driver = new FirefoxDriver(profile);
	}
	
	protected static String switchWindows(WebDriver driver) {
		LOG.debug("switcing browser windows.");
		String parentHandle = driver.getWindowHandle();
		Set<String> allHandles = driver.getWindowHandles();
		// so for now i am assuming there are two windows
		for(String handle : allHandles) {
			if(!handle.equals(parentHandle)) {
				driver.switchTo().window(handle);
			}
		}
		return parentHandle;
	}
	
	protected static void switchBackToParentWindow(WebDriver driver, String parentHandle) {
		driver.switchTo().window(parentHandle);
	}
	
	//@Test
	public static void taobaoLogin(){
		
		//driver.get("https://www.taobao.com/");
		//driver.findElement(By.linkText("亲，请登录")).click();
		
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage.getUrl();
		
		accountDAO = new AccountDAO();
		account = accountDAO.getAccount("taobao");
		
		loginPage.login(account.getAccount(), account.getPassword());
		
		//loginPage.login("", "");
	}
}
