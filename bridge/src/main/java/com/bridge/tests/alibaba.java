package com.bridge.tests;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bridge.pages.alibaba.LoginPage;
import com.bridge.dao.Account;
import com.bridge.dao.AccountDAO;
import com.bridge.pages.alibaba.ItemListPage;
import com.bridge.util.WebDriverUtil;

public class alibaba {

	private static WebDriver driver;
	
	private static Account account = null;
	private static AccountDAO accountDAO = null;
	
	@BeforeTest
	public void getWebDriver(){
		alibaba.driver = new FirefoxDriver();
	}
	
	@Test
	public void aliLogin(){
		
		//driver.get("https://www.taobao.com/");
		//driver.findElement(By.linkText("亲，请登录")).click();
		
		//step1
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage.getUrl();
		
		accountDAO = new AccountDAO();
		account = accountDAO.getAccount("ali1688");
		
		loginPage.login(account.getAccount(), account.getPassword());
	}
	
	@Test(dependsOnMethods="aliLogin")
	public void getInfo(){
		
		WebDriverUtil.waitForElementPresent(driver, By.linkText("我的阿里"), 15);
		
		WebDriverUtil.hoverOnElement(driver.findElement(By.linkText("我的阿里")), driver);
		
		driver.findElement(By.linkText("已买到货品")).click();
		
		ItemListPage itemPage = new ItemListPage(driver);
		
		itemPage.getItemInfo(2);
	}
}
