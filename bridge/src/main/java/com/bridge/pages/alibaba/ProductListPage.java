package com.bridge.pages.alibaba;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.util.WebDriverUtil;


public class ProductListPage extends CategoryPage{
	
	@FindBy(css=".fui-paging-total")
	WebElement totalPages;
	
	@FindBy(css="#sm-offer-list > li[id^=offer]")
	List<WebElement> offerList;
	
	@FindBy(css="a.fui-next")
	WebElement nextPage;
	
	private static final Log LOG = LogFactory.getLog(ProductListPage.class);
	
	private static Set<String> companyUrlSet = new HashSet<String>();

	public ProductListPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public Set<String> getProductInfo(int pages){
		int totalPages = getTotalPages(this.totalPages.getText());

		if(totalPages < pages)
			pages = totalPages;
		
		for(int i=1;i<=pages;i++)
		{
			getProductInfoFromSignlePage();
			
			if(i != pages){
				//获取第一个订单的ID来判断翻页是否完成
				String firstOfferName = offerList.get(0).findElement(By.cssSelector(".s-widget-offershopwindowtitle > a")).getText();
				
				WebDriverUtil.scrollPage(driver, "0", "-20000");
				
				nextPage.click();
				
				WebDriverUtil.waitForElementNotVisible(driver, By.xpath("//a[text()='"+firstOfferName+"']"), 15);
			}
		}
		return companyUrlSet;
	}
	
	public void getProductInfoFromSignlePage(){
		WebDriverUtil.waitForElementPresent(driver, By.cssSelector(".sm-widget-path"), 10);

		try{
			srcollDown();
		}catch(NoSuchElementException NE){
			LOG.info("after scroll down can't found more products,maybe reach the max");
		}

		LOG.info("total offers:"+offerList.size());
		for(WebElement offer : offerList){
			
			String productName = offer.findElement(By.cssSelector(".s-widget-offershopwindowtitle > a")).getText();
			LOG.info("产品名称："+productName);
			
			String price = offer.findElement(By.cssSelector(".sm-offer-priceNum.sw-dpl-offer-priceNum")).getText();
			LOG.info("价格："+price);
			
			String productUrl = offer.findElement(By.cssSelector(".s-widget-offershopwindowtitle > a")).getAttribute("href");
			if(!productUrl.contains("detail.1688.com")){
				String offerId = offer.findElement(By.cssSelector("a.sm-offer-companyName")).getAttribute("offerid");
				productUrl = "https://detail.1688.com/offer/"+offerId+".html";
			}
			LOG.info("产品链接："+productUrl);
			
			String companyName = offer.findElement(By.cssSelector("a.sm-offer-companyName")).getText();
			LOG.info("工厂名称："+companyName);
			
			String companyUrl = offer.findElement(By.cssSelector("a.sm-offer-companyName")).getAttribute("href");
			companyUrl = getStringByRegex(companyUrl,"^(https?:\\/\\/)([\\da-z\\.-]+)\\/");
			LOG.info("工厂链接："+companyUrl);
			
			companyUrlSet.add(companyUrl);
			
			aliOfferDAO.insertOffer(productName, productUrl, price, companyName, companyUrl);
		}
		
	}
	
	public void srcollDown() throws NoSuchElementException{
		
		WebDriverUtil.scrollPage(driver, "0", "3500");

		WebDriverUtil.waitForElementPresent(driver, By.id("offer21"), 10);
		
		WebDriverUtil.scrollPage(driver, "0", "3000");

		WebDriverUtil.waitForElementPresent(driver, By.id("offer41"), 10);

	}
}
