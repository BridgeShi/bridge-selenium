package com.bridge.tests.alibaba.en;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bridge.pages.alibaba.en.ProductDetailEnPage;
import com.bridge.pages.alibaba.en.ProductListEnPage;
import com.bridge.tests.BaseTest;

public class alibabaEnProductInfoTest extends BaseTest{
	
	private static final Log LOG = LogFactory.getLog(alibabaEnProductInfoTest.class);
	
	public static void main(String args[]){
		getWebDriver();
			
		getProductFromList("https://www.alibaba.com/Door-Window-Hinges_pid15380307",1);

		//关闭webdriver
		closeWebDriver();
	}
	
	public static void getProductFromList(String url,int pages){
		ProductListEnPage productListPage = new ProductListEnPage(driver);

		LOG.info("open url:"+url);
		driver.get(url);
		
		ArrayList<String> urlList = productListPage.getProduct(pages);
		
		for(String productUrl:urlList){
			getProductDetail(productUrl);
		}
	}
	
	public static void getProductDetail(String url){
		ProductDetailEnPage productDetailPage = new ProductDetailEnPage(driver);

		driver.get(url);
		productDetailPage.getProductInfo();
		
	}
}
