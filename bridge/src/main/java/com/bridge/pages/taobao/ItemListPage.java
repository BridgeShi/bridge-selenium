package com.bridge.pages.taobao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.util.WebDriverUtil;

public class ItemListPage {
	private static final Log LOG = LogFactory.getLog(ItemListPage.class);

	private static String orderIdXpath = ".//span[contains(.,'订单号')]/following-sibling::span[2]";
	
	private static String orderDateXpath = ".//strong[@title]";
	
	private static String sellerXpath = ".//a[@class='tp-tag-a'][@title][contains(@href,'user_number_id')]";
	
	private static String goodsNameXpath = ".//a[contains(@href,'item.htm?id')]/span | .//a[contains(@href,'item_id_num')]/span";
	
	private static String goodsIdXpath = ".//a[contains(@href,'item.htm?id')] | .//a[contains(@href,'item_id_num')]";
	
	private static String goodsPriceXpath = "./td[2]/div[@style='font-family:verdana;font-style:normal;']/p[not(./del)]";
	
	private static String goodsNumXpath = "./td[3]//div";
	
	private static String orderPriceXpath = ".//td[.//p/strong]";
	
	private static String orderStatusXpath = ".//table[2]//tr[2]//td[6]/div/div[1]";
	
	private static String goodsRowXpath = ".//table[2]//tr[.//img]";
	
	private static String shipLinkXpath = ".//a[@class='tp-tag-a'][contains(@href,'wuliu')]";
	
	protected WebDriver driver;
	
	@FindBy(css="div[class^=trade-order-main]")
	List<WebElement> orderList;
		
	public ItemListPage(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void getItemInfo(){
		WebDriverUtil.waitForElementPresent(driver, By.className("trade-order-mainClose"), 10);
		//获得有多少个订单，然后遍历
		for(WebElement order : orderList){
			
			String orderStatus = order.findElement(By.xpath(orderStatusXpath)).getText();
			LOG.info("订单号："+order.findElement(By.xpath(orderIdXpath)).getText());
			LOG.info("订单日期："+order.findElement(By.xpath(orderDateXpath)).getText());
			LOG.info("商家名："+order.findElement(By.xpath(sellerXpath)).getText());
			LOG.info("订单总价："+order.findElement(By.xpath(orderPriceXpath)).getText());
			LOG.info("订单状态："+orderStatus);
			
			//获得订单中有多少个商品
			List<WebElement> goodsList = order.findElements(By.xpath(goodsRowXpath));
			for(WebElement good : goodsList){
				LOG.info("商品名称："+good.findElement(By.xpath(goodsNameXpath)).getText());
				LOG.info("商品ID："+getItemId(good.findElement(By.xpath(goodsIdXpath)).getAttribute("href")));
				LOG.info("商品价格："+good.findElement(By.xpath(goodsPriceXpath)).getText());
				LOG.info("商品数量："+good.findElement(By.xpath(goodsNumXpath)).getText());
			}
			
			if(verifyShipInfoExist(order, By.xpath(shipLinkXpath)) && orderStatus.contains("物流")){
				//点击物流详情查看物流信息
				order.findElement(By.xpath(shipLinkXpath)).click();
				String parentHanle = driver.getWindowHandle();
				WebDriverUtil.switchWindows(driver);
				ShipStatusPage shipPage = new ShipStatusPage(driver);
				shipPage.getShipInfo();
				driver.close();
				WebDriverUtil.switchBackToParentWindow(driver, parentHanle);
			}
		}
	}
	
	public static String getItemId(String url){
		Pattern p=Pattern.compile("(?<=id=|id_num=)\\d+");
	    Matcher m=p.matcher(url);
	    String findString = "";
	    if(m.find()){
	    	findString = m.group(0);
	    }
		return findString;
	}
	
	public boolean verifyShipInfoExist(WebElement order, By elementLocator){

		if (order.findElements(elementLocator).size() > 0) {
			LOG.info("element: " + elementLocator.toString()+" found");
			return true;
		}else
		{
			LOG.info("element: " + elementLocator.toString() +" was not found on current page");
			return false;
		}
	}
}
