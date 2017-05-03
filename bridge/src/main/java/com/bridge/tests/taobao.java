package com.bridge.tests;

import org.openqa.selenium.By;

import com.bridge.pages.taobao.ItemListPage;
import com.bridge.util.WebDriverUtil;

public class taobao extends BaseTest{
	
	public static void main(String args[]){
		getWebDriver();
		
		taobaoLogin();
		
		//传递需要获取多少页的数据
		getBaoBeiInfo(2);
		
		closeWebDriver();
	}
	

	
	//@Test(dependsOnMethods="taobaoLogin")
	public static void getBaoBeiInfo(int pages){
		
		WebDriverUtil.waitForElementPresent(driver, By.linkText("已买到宝贝"), 15);
		
		driver.findElement(By.linkText("已买到宝贝")).click();
		
		ItemListPage itemPage = new ItemListPage(driver);
		
		itemPage.getItemInfo(pages);
	}
}
