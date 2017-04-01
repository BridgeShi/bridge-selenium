package com.bridge.pages.alibaba;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class SimlerOfferPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(SimlerOfferPage.class);
	
	private static final String lastOfferElementCss = "#sm-offer-list > li:last-of-type";
	
	@FindBy(css="#sm-offer-list > li")
	List<WebElement> offerList;
	
	public SimlerOfferPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void scrollPageUtilAllOfferShow(){
		
		//如果存在更多货源
		while(WebDriverUtil.verifyElementExist(driver, By.id("sm-offer-trigger"))){
			//滚动下拉
			String lastIndex = driver.findElement(By.cssSelector(lastOfferElementCss)).getAttribute("clicklistindex");
			
			WebDriverUtil.scrollPage(driver, "0", "3500");
			
			WebDriverUtil.waitForElementPresent(driver, 
					By.xpath("//ul[@id=\"sm-offer-list\"]/li[@clicklistindex > '"+lastIndex+"']"), 30);

		}
	}
	
	public void getOfferInfo(){
		
		WebDriverUtil.waitForElementPresent(driver, By.cssSelector("#sm-offer-list"), 10);
		
		scrollPageUtilAllOfferShow();
		
		for(WebElement offer : offerList){
			
			String productName = offer.findElement(By.cssSelector(".sm-offer-title.sw-dpl-offer-title > a")).getText();
			LOG.info("产品名称："+productName);
			
			String price = offer.findElement(By.cssSelector(".sm-offer-priceNum.sw-dpl-offer-priceNum")).getText();
			LOG.info("价格："+price);
			
			String productUrl = offer.findElement(By.cssSelector(".sm-offer-title.sw-dpl-offer-title > a")).getAttribute("href");
			LOG.info("产品链接："+productUrl);
			
			aliOfferDAO.insertSimlerOffer(productName, productUrl, price);
		}
	}
}
