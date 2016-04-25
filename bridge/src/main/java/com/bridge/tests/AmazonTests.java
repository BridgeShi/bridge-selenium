package com.bridge.tests;


import java.util.List;

import com.bridge.dao.AmazonDAO;
import com.bridge.pages.amazon.CartPage;
import com.bridge.pages.amazon.ProductPage;

public class AmazonTests extends BaseTest{
	
	private static AmazonDAO amazonDAO = new AmazonDAO();
	
	public static void main(String args[]){
		getWebDriver();
		
		List<String> productUrlList = amazonDAO.getUrlList(2);
		
		for(String productUrl : productUrlList){
			getInventory(productUrl);
		}
		
		closeWebDriver();
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
