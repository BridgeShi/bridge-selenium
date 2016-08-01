package com.bridge.pages.alibaba;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bridge.util.WebDriverUtil;


public class ProductDetailPage extends CategoryPage{
	
	@FindBy(css=".fui-paging-total")
	WebElement totalPages;
	
	@FindBy(css="tr.price > td[class^=ladder]")
	List<WebElement> priceLadderList;
	
	@FindBy(css="h1.d-title")
	WebElement productTitle;
	
	@FindBy(css="span.parcel-unit-weight")
	WebElement weight;
	
	@FindBy(css="i.icon-arrow")
	WebElement iconArrow;
	
	@FindBy(css="p.bargain-number > a")
	WebElement bargainCount;
	
	@FindBy(css="ul > li.tab-trigger")
	List<WebElement> imgTab;
	
	@FindBy(id="mod-detail-attributes")
	WebElement attributes;
	
	@FindBy(css=".de-description-detail")
	WebElement description;
	
	@FindBy(css=".de-description-detail p img")
	List<WebElement> descriptionImgs;
	
	@FindBy(css="ul.list-leading li > div > a")
	List<WebElement> skuTabs;
	
	@FindBy(css="table.table-sku tr")
	List<WebElement> subSkuList;
	
	//@FindBy(css="table.table-sku td:not(:last-of-type)")
	//List<WebElement> skuTable;
	
	private static final Log LOG = LogFactory.getLog(ProductDetailPage.class);
	

	public ProductDetailPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getProductInfo(){
		WebDriverUtil.waitForElementPresent(driver, By.id("mod-detail"), 10);

		String productName = productTitle.getText();
		LOG.info("产品名称："+productName);
		
		for(WebElement ladder : priceLadderList){
			String priceLadder = ladder.getAttribute("data-range");
			LOG.info("价格区间："+priceLadder);
		}
		
		String bargains = bargainCount.getText();
		LOG.info("成交数量："+bargains);
		
		getAllSku();
		
		iconArrow.click();
		WebDriverUtil.waitForElementPresent(driver, By.cssSelector("span.parcel-unit-weight"), 10);
		String weight = this.weight.getText();
		LOG.info("重量："+weight);
		
		String attributes = this.attributes.getText();
		LOG.info("详细信息："+attributes);
		
    	JSONArray imgArray = new JSONArray();
		for(WebElement img : imgTab){
			String imgData = img.getAttribute("data-imgs");
			LOG.info("图片链接："+imgData);
			imgArray.add(JSON.parseObject(imgData));
		}
		
		LOG.info(imgArray);
		srcollDown();
		String description = this.description.getText();
		LOG.info("描述信息："+description);
		
		for(WebElement img : descriptionImgs){
			String imgData = img.getAttribute("data-lazyload-src");
			if(imgData == null){
				imgData = img.getAttribute("src");
			}
			LOG.info("描述内图片链接："+imgData);
		}
		
		
	}
	
	public JSONObject getAllSku(){
    	JSONObject allSku = new JSONObject();
		for(WebElement sku : skuTabs){
	    	JSONArray array = new JSONArray();
	    	JSONObject skuObject = new JSONObject();
	    	
	    	sku.click();
	    	//WebDriverUtil.waitForElementAttributeToChange(driver, sku, 10, "class", "image selected");
	    	for(WebElement subSku : subSkuList){
	    		List<WebElement> skuTable = subSku.findElements(By.cssSelector("td:not(:last-of-type)"));
	    		for(WebElement skuTd : skuTable){
		    		skuObject.put(skuTd.getAttribute("class"), skuTd.getText());
	    		}
		    	array.add(skuObject);
		    	LOG.debug(array);
	    	}
	    	
	    	allSku.put(sku.getAttribute("title"), array);
	    	
		}
		LOG.info(allSku);
		return allSku;
	}
	
	public void srcollDown() throws TimeoutException{
		
		WebDriverUtil.scrollPage(driver, "0", "3500");

		WebDriverUtil.waitForElementNotVisible(driver, By.xpath("//div[@id='de-description-detail'][contains(.,'加载中')]"), 15);

	}

}
