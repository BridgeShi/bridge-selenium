package com.bridge.tests;


import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.testng.annotations.Test;

import com.bridge.pages.alibaba.ProductListPage;
import com.bridge.pages.alibaba.CompanyOfferListPage;


public class alibabaShopListTest extends BaseTest{
	
	private static final Log LOG = LogFactory.getLog(BaseTest.class);

	public static void main(String args[]){
		getWebDriver();
		
		getProductInfo("https://s.1688.com/selloffer/offer_search.htm?keywords=%D2%C2%B7%FE&n=y&spm=a260k.635.1998096057.d1");
		//关闭webdriver
		closeWebDriver();

	}
	
	public static void getProductInfo(String url){
		ProductListPage productPage = new ProductListPage(driver);

		LOG.info("open url:"+url);
		driver.get(url);
		
		//传递需要取多少页
		Set<String> companyUrlSet = productPage.getProductInfo(2);
		
		for(String companyUrl:companyUrlSet){			
			getCompanyProducts(companyUrl);
		}
		
	}
	
	public static void getCompanyProducts(String companyUrl){
		CompanyOfferListPage compaynOfferListPage = new CompanyOfferListPage(driver);

		LOG.info("open url:"+companyUrl+"page/offerlist.htm");
		driver.get(companyUrl+"page/offerlist.htm");
		
		//传递需要取多少页
		compaynOfferListPage.getProductInfo(3);
	}

}
