package com.bridge.tests;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import com.bridge.dao.AmazonDAO;
import com.bridge.pages.amazon.CartPage;
import com.bridge.pages.amazon.ProductPage;


public class AmazonTests {

	private static WebDriver driver;
	
	private static AmazonDAO amazonDAO = new AmazonDAO();
	
	@BeforeTest
	public void getWebDriver(){
		AmazonTests.driver = new FirefoxDriver();
	}
	
	public static void main(String args[]){
		driver = new FirefoxDriver();
		
		List<String> productUrlList = amazonDAO.getUrlList(2);
		
		for(String productUrl : productUrlList){
			getInventory(productUrl);
		}
		
		//关闭webdriver
		driver.close();
		
		driver.quit();
	}
	
	//@Test
	public static void getInventory(String url){
		
		ProductPage productPage = new ProductPage(driver);
		
		productPage.getUrl(url);
		
		CartPage cartPage = productPage.addToCart();
		
		String inventory = cartPage.getStock();
		
		amazonDAO.update(inventory, url);

		cartPage.deleteItem();
		//loginPage.login("", "");
	}

}
