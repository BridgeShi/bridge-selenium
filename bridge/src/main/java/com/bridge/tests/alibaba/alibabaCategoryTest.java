package com.bridge.tests.alibaba;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;

//import org.testng.annotations.Test;


import com.bridge.pages.alibaba.CategoryPage;
import com.bridge.tests.BaseTest;

public class alibabaCategoryTest extends BaseTest{
	
	private static final Log LOG = LogFactory.getLog(BaseTest.class);
	
	public static void main(String args[]){
		getWebDriver();
		
		getAllLevelCategory("https://s.1688.com/selloffer/-E8A4D9A4B7FE-1037024.html");
		
		//关闭webdriver
		closeWebDriver();
		
	}
	
	public static void getAllLevelCategory(String url){
		Object[][] firstLevelCategoryData = getCategoryByLevel(url,1,null);
		for(int i = 0 ;i < firstLevelCategoryData.length ; i++){
			Object[][] secondLevelCategoryData = getCategoryByLevel(url,2,firstLevelCategoryData[i]);
			
			//LOG.info("second level length:"+secondLevelCategoryData.length);
			if(secondLevelCategoryData != null){
				for(int j = 0 ;j < secondLevelCategoryData.length ; j++){
					Object[][] thirdLevelCategoryData = getCategoryByLevel(url,3,secondLevelCategoryData[j]);
					
					if(thirdLevelCategoryData != null){
						for(int k=0;k < thirdLevelCategoryData.length;k++){
							getCategoryByLevel(url,4,thirdLevelCategoryData[k]);
						}
					}
				}
			}
		}
	}
	
	public static Object[][] getCategoryByLevel(String url,int level,Object[] lastLevelCategoryDate){
			
		CategoryPage catePage = new CategoryPage(driver);
		
		Object[][] categoryData = null;
		
		if(level == 1){
			LOG.info("open url:"+url);
			driver.get(url);
			
			categoryData = catePage.getMostCategoryInfo(1,null);
		//大于第一层
		}else if(level > 1){
			String secondLevelUrl = (String) lastLevelCategoryDate[2];
			LOG.info("open url:"+secondLevelUrl);
			driver.get(secondLevelUrl);
			
			categoryData = catePage.getMostCategoryInfo(level, lastLevelCategoryDate);	
		}
		
		return categoryData;
	}
}
