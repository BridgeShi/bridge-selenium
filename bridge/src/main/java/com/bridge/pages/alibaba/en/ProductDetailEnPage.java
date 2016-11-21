package com.bridge.pages.alibaba.en;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class ProductDetailEnPage extends BasePage{
	
	
	@FindBy(css="h1.ma-title")
	WebElement productTitle;
	
	@FindBy(css="td.name.J-name")
	List<WebElement> attributes;
	
	@FindBy(xpath="//td[contains(., 'Packaging Details')]/following-sibling::td")
	WebElement packageDetail;
	
	private static final Log LOG = LogFactory.getLog(ProductDetailEnPage.class);
	
	public ProductDetailEnPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getProductInfo(String[] product){
		WebDriverUtil.waitForElementPresent(driver, By.className("main-inner"), 10);

		String productName = productTitle.getText();
		LOG.info("产品名称："+productName);
		
    	JSONArray attributesArray = new JSONArray();
		for(WebElement attr : attributes){
			String attributeName = attr.getText().replace(":", "");
			String attributeValue = attr.findElement(By.xpath("./following-sibling::td[1]")).getText();
			//LOG.info(attributeName+attributeValue);
	    	JSONObject mainObject = new JSONObject();
	    	mainObject.put(attributeName, attributeValue);
	    	attributesArray.add(mainObject);
		}
		
		LOG.info("商品描述："+attributesArray);
		
		String packageDetail = "None";
		try{
			packageDetail = this.packageDetail.getText();
		}catch(NoSuchElementException NE){
			LOG.info("package detail is none");
		}
		LOG.info("Packaging Details:"+packageDetail);
		
		aliEnProductDAO.insert(product[0], product[1], 
				product[2], product[3], product[4], 
				attributesArray.toJSONString(), packageDetail
		);
	}


	
}
