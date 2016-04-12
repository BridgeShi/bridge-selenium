package com.bridge.tests;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bridge.pages.taobao.ItemListPage;
import com.bridge.pages.taobao.LoginPage;
import com.bridge.util.WebDriverUtil;

public class taobao {

	private static WebDriver driver;
	
	@BeforeTest
	public void getWebDriver(){
		this.driver = new FirefoxDriver();
	}
	
	
	@Test
	public void taobaoLogin(){
		
		//driver.get("https://www.taobao.com/");
		//driver.findElement(By.linkText("亲，请登录")).click();
		
		//step1
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage.getUrl();
		
		loginPage.login("", "");
	}
	
	@Test(dependsOnMethods="taobaoLogin")
	public void getBaoBeiInfo(){
		
		WebDriverUtil.waitForElementPresent(driver, By.linkText("已买到宝贝"), 15);
		
		driver.findElement(By.linkText("已买到宝贝")).click();
		
		ItemListPage itemPage = new ItemListPage(driver);
		
		itemPage.getItemInfo();
	}
}
