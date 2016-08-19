package com.bridge.tests;


import org.openqa.selenium.By;
//import org.testng.annotations.Test;

import com.bridge.pages.alibaba.ProductDetailPage;

public class alibabaAddToCartTest extends alibaba{
	
	
	public static void main(String args[]){
		getWebDriver();
		
		aliLogin();
		
		addToCart("https://detail.1688.com/offer/530768935244.html?spm=a26e3.8027625.1999173159.1.2rTUAq#","蓝色","","1");
		
		//关闭webdriver
		closeWebDriver();
	}
	
	public static void addToCart(String url,String firstOption,String secondOption,String amount){
		
		ProductDetailPage productPage = new ProductDetailPage(driver);

		LOG.info("open url:"+url);
		driver.get(url);
		
		productPage.addToCart(firstOption,secondOption,amount);
	}
	

}
