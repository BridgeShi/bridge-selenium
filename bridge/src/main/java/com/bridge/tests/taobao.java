package com.bridge.tests;

import org.openqa.selenium.By;


import com.bridge.dao.Account;
import com.bridge.dao.AccountDAO;
import com.bridge.pages.taobao.ItemListPage;
import com.bridge.pages.taobao.LoginPage;
import com.bridge.util.WebDriverUtil;

public class taobao extends BaseTest{

	private static Account account = null;
	private static AccountDAO accountDAO = null;
	
	public static void main(String args[]){
		getWebDriver();
		
		taobaoLogin();
		
		//传递需要获取多少页的数据
		getBaoBeiInfo(2);
		
		closeWebDriver();
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
	
	//@Test(dependsOnMethods="taobaoLogin")
	public static void getBaoBeiInfo(int pages){
		
		WebDriverUtil.waitForElementPresent(driver, By.linkText("已买到宝贝"), 15);
		
		driver.findElement(By.linkText("已买到宝贝")).click();
		
		ItemListPage itemPage = new ItemListPage(driver);
		
		itemPage.getItemInfo(pages);
	}
}
