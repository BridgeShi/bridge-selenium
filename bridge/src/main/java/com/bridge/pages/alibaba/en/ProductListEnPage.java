package com.bridge.pages.alibaba.en;

import java.util.ArrayList;
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

public class ProductListEnPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(ProductListEnPage.class);
			
	@FindBy(className="item-main")
	List<WebElement> productList;
	
	@FindBy(css=".interim + *")
	WebElement totalPages;
	
	@FindBy(css="a.next")
	WebElement nextPage;
	
	private static ArrayList<String[]> productInfoList = new ArrayList<String[]>();
	
	public ProductListEnPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public ArrayList<String[]> getProduct(int pages){
	
		int totalPages = getTotalPages(this.totalPages.getText());

		LOG.info("total pages:"+totalPages);
		
		if(totalPages < pages)
			pages = totalPages;
		
		for(int i=1;i<=pages;i++){
			getProductInfoFromSignlePage();
			
			if(i != pages){
				//获取第一个订单的ID来判断翻页是否完成
				String firstProductName = productList.get(0).findElement(By.cssSelector("h2.title > a")).getText();
				
				WebDriverUtil.scrollPage(driver, "0", "3000");
								
				LOG.info("next page");
				
				nextPage.click();
				
				WebDriverUtil.waitForElementNotVisible(driver, By.xpath("//a[text()='"+firstProductName+"']"), 15);
			}
		}
		
		return productInfoList;
	}
	
	public void getProductInfoFromSignlePage(){
		WebDriverUtil.waitForElementPresent(driver, By.className("l-main-wrap"), 10);
		LOG.info("total products:"+productList.size());
		for(WebElement product : productList){
			String[] productInfo = new String[5];
			String productName = product.findElement(By.cssSelector("img.util-valign-inner")).getAttribute("alt");
			productInfo[0] = productName;
			LOG.info("产品名称："+productInfo[0]);
			
			String productUrl = product.findElement(By.cssSelector(".title > a")).getAttribute("href");
			productInfo[1] = productUrl;
			LOG.info("产品链接："+productInfo[1]);
			
			/*if(productInfoList.contains(productUrl)){
				LOG.info("该商品重复");
				continue;
			}*/
			
			String productImg = product.findElement(By.cssSelector("img.util-valign-inner")).getAttribute("data-jssrc");
			productInfo[2] = productImg;
			LOG.info("产品图片："+productInfo[2]);
			
			String price = "None";
			try{
				price = product.findElement(By.cssSelector(".price")).getText();
			}catch(NoSuchElementException NE){
				LOG.info("price is none");
			}
			productInfo[3] = price;
			LOG.info("价格："+productInfo[3]);
			
			String minOrder = product.findElement(By.cssSelector(".min-order>b")).getText();
			productInfo[4] = minOrder;
			LOG.info("最小订单："+productInfo[4]);
			
			productInfoList.add(productInfo);
		}
	}
}
