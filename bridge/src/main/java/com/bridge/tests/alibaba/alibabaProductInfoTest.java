package com.bridge.tests.alibaba;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import org.testng.annotations.Test;


import com.bridge.pages.alibaba.ProductDetailPage;
import com.bridge.tests.BaseTest;

public class alibabaProductInfoTest extends BaseTest{
	
	private static final Log LOG = LogFactory.getLog(BaseTest.class);
	
	public static void main(String args[]){
		getWebDriver();
				
		getProductDetail("https://detail.1688.com/offer/520456163869.html");

		//关闭webdriver
		closeWebDriver();
	}
	
	public static void getProductDetail(String url){
		ProductDetailPage productPage = new ProductDetailPage(driver);

		LOG.info("open url:"+url);
		driver.get(url);
		
		productPage.getProductInfo();

	}
}
