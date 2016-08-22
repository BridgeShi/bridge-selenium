package com.bridge.tests;


import java.sql.SQLException;

import com.bridge.dao.Ali1688AddToCartDAO;

//import org.testng.annotations.Test;

import com.bridge.pages.alibaba.ProductDetailPage;

public class alibabaAddToCartTest extends alibaba{
	
	private static Ali1688AddToCartDAO addToCartDAO = new Ali1688AddToCartDAO();
	
	public static void main(String args[]) {
		getWebDriver();
		
		aliLogin();
		
		String[][] productsToBeOrder;
		try {
			productsToBeOrder = addToCartDAO.getProductsToBeOrder();
			for (int i = 0; i < productsToBeOrder.length; i++) {
				addToCart(productsToBeOrder[i][0],productsToBeOrder[i][1],productsToBeOrder[i][2],productsToBeOrder[i][3]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//for (int i = 0; i < productsToBeOrder.length; i++)
		
		//关闭webdriver
		closeWebDriver();
	}
	
	public static void addToCart(String url,String firstOption,String secondOption,String amount){
		
		ProductDetailPage productPage = new ProductDetailPage(driver);

		LOG.info("open url:"+url);
		driver.get(url);
		
		try {
			productPage.addToCart(firstOption,secondOption,amount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
