package com.bridge.tests;


import org.openqa.selenium.By;
//import org.testng.annotations.Test;

import com.bridge.dao.OrderListDAO;
import com.bridge.dao.PreOrderList;
import com.bridge.pages.alibaba.ProductDetailPage;

public class alibabaAddToCartTest extends alibaba{
	
	
	public static void main(String args[]){
		getWebDriver();
		
		aliLogin();
		
		OrderListDAO rldao = new OrderListDAO();
		PreOrderList pol = rldao.getPreOrderList();
		
		addToCart(pol.getUrl(),pol.getFirstOption(),pol.getSecondOption(),pol.getQty());
		
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
