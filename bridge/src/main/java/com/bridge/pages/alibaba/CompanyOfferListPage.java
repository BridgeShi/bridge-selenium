package com.bridge.pages.alibaba;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class CompanyOfferListPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(CompanyOfferListPage.class);
	
	private static String companyUrl;
	
	@FindBy(css="em.page-count")
	WebElement totalPages;
	
	@FindBy(css=".offer-list-row>li")
	List<WebElement> offerList;
	
	@FindBy(css="a.next")
	WebElement nextPage;
	
	@FindBy(css=".company-name")
	WebElement companyName;
	
	public CompanyOfferListPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getProductInfo(int pages){
		int totalPages = getTotalPages(this.totalPages.getText());

		companyUrl = getStringByRegex(driver.getCurrentUrl(),"^(https?:\\/\\/)([\\da-z\\.-]+)\\/");
		LOG.info("工厂链接："+companyUrl);
		
		LOG.info("工厂名称："+companyName.getText());
		
		if(totalPages < pages)
			pages = totalPages;
				
		for(int i=1;i<=pages;i++)
		{
			getProductInfoFromSignlePage();
						
			if(i != pages){
				//获取第一个订单的ID来判断翻页是否完成
				nextPage.click();
				
				WebDriverUtil.waitForElementPresent(driver, By.xpath("//a[@class='current'][text()='"+(i+1)+"']"), 15);
				
			}
		}
	}

	private void getProductInfoFromSignlePage() {
		WebDriverUtil.waitForElementPresent(driver, By.cssSelector(".offer-list-row"), 10);

		LOG.info("total offers:"+offerList.size());
		for(WebElement offer : offerList){
			
			String productName = offer.findElement(By.cssSelector(".title > a")).getText();
			LOG.info("产品名称："+productName);
			
			String price = offer.findElement(By.cssSelector(".price > em")).getText();
			LOG.info("价格："+price);
			
			String productUrl = offer.findElement(By.cssSelector(".title > a")).getAttribute("href");
			LOG.info("产品链接："+productUrl);
			
			String bookedCounts;
			try{
				bookedCounts = offer.findElement(By.cssSelector("span.booked-counts")).getText();
			}catch(NoSuchElementException NE){
				bookedCounts = "0";
			}
			LOG.info("成交数："+bookedCounts);
			
			aliOfferDAO.insertCompanyOffer(productName, productUrl, price, companyName.getText(), companyUrl, bookedCounts);
	
		}
	}
}
