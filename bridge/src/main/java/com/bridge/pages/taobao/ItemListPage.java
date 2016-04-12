package com.bridge.pages.taobao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.util.WebDriverUtil;

public class ItemListPage {
	private static final Log LOG = LogFactory.getLog(ItemListPage.class);

	private static String orderIdXpath = "//div[@class='trade-order-mainClose']//span[contains(.,'订单号')]/following-sibling::span[2]";
	
	private static String orderDateXpath = "//div[@class='trade-order-mainClose']//strong[@title]";
	
	private static String sellerXpath = "//div[@class='trade-order-mainClose']//a[@class='tp-tag-a'][@title][contains(@href,'user_number_id')]";
	
	private static String goodsNameXpath = "//div[@class='trade-order-mainClose']//a[contains(@href,'item.htm?id')]/span | //div[@class='trade-order-mainClose']//a[contains(@href,'item_id_num')]/span";
	
	private static String goodsIdXpath = "//div[@class='trade-order-mainClose']//a[contains(@href,'item.htm?id')]| //div[@class='trade-order-mainClose']//a[contains(@href,'item_id_num')]";
	
	private static String goodsPriceXpath = "//div[@class='trade-order-mainClose']//tr[.//img]/td[2]/div[@style='font-family:verdana;font-style:normal;']/p[not(./del)]";
	
	private static String goodsNumXpath = "//div[@class='trade-order-mainClose']//tr[.//img]/td[3]//div";
	
	private static String orderPriceXpath = "//div[@class='trade-order-mainClose']//td[.//p/strong]";
	
	private static String orderStatusXpath = "//div[@class='trade-order-mainClose']//table[2]//tr[2]//td[6]/div/div[1]";
	
	protected WebDriver driver;
	
	@FindBy(className="trade-order-mainClose")
	List<WebElement> orderList;
		
	public ItemListPage(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void getItemInfo(){
		WebDriverUtil.waitForElementPresent(driver, By.className("trade-order-mainClose"), 10);
		for(WebElement order : orderList){
			
		}
		LOG.info("订单号："+driver.findElement(By.xpath(orderIdXpath)).getText());
		LOG.info("订单日期："+driver.findElement(By.xpath(orderDateXpath)).getText());
		LOG.info("商家名："+driver.findElement(By.xpath(sellerXpath)).getText());
		LOG.info("订单总价："+driver.findElement(By.xpath(orderPriceXpath)).getText());
		LOG.info("订单状态："+driver.findElement(By.xpath(orderStatusXpath)).getText());
		LOG.info("商品名称："+driver.findElement(By.xpath(goodsNameXpath)).getText());
		LOG.info("商品名称："+getItemId(driver.findElement(By.xpath(goodsIdXpath)).getAttribute("href")));
		LOG.info("商品价格："+driver.findElement(By.xpath(goodsPriceXpath)).getText());
		LOG.info("商品数量："+driver.findElement(By.xpath(goodsNumXpath)).getText());
	}
	
	public static String getItemId(String url){
		Pattern p=Pattern.compile("(?<=id=|id_num=)\\d+");
	    Matcher m=p.matcher(url);
	    String findString = "";
	    if(m.find()){
	    	findString = m.group(0);
	    }
	    System.out.println(findString);
		return findString;
	}
}
