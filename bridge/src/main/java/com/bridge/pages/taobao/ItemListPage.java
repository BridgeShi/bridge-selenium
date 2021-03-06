package com.bridge.pages.taobao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class ItemListPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(ItemListPage.class);

	private static String orderIdXpath = ".//span[contains(.,'订单号')]/following-sibling::span[2]";
	
	//private static String orderDateXpath = ".//strong[@title]";
	
	private static String orderDateCss = "span[class^='bought-wrapper-mod__create-time']";

	private static String sellerXpath = ".//a[starts-with(@class,'seller-mod__name')][@title][contains(@href,'user_number_id')]";
	
	private static String goodsNameXpath = ".//a[contains(@href,'item.htm?id')]/span | .//a[contains(@href,'item_id_num')]/span";
	
	private static String goodsIdXpath = ".//a[contains(@href,'item.htm?id')] | .//a[contains(@href,'item_id_num')]";
	
	private static String goodsPriceXpath = "./td[2]/div[starts-with(@class,'price-mod__price')]/p[not(./del)]";
	
	private static String goodsNumXpath = "./td[3]//div";
	
	private static String orderPriceXpath = ".//td[.//p/strong]//strong";
	
	private static String orderStatusXpath = ".//table/tbody[2]/tr/td[6]/div/p";
	
	private static String goodsRowXpath = ".//tbody[2]//tr[.//img]";
	
	private static String shipLinkXpath = ".//a[@id='viewLogistic'][contains(@href,'wuliu')]";
	
	private static String skuXpath = ".//p[./span[starts-with(@class,'production')]]";
	
	private static String goodsUrlXpath = ".//a[contains(@href,'item.htm?id')] | .//a[contains(@href,'item_id_num')]";
	
	private static String goodsImgXpath = "./td//a[starts-with(@class,'production-mod__pic')]/img";
	
	@FindBy(css="div[class^=index-mod__order-container]")
	List<WebElement> orderList;
	
	@FindBy(css=".pagination-item.pagination-item-active")
	WebElement currentPage;
	
	@FindBy(xpath="//ul[@class='pagination ']/li[contains(@title,'最后一页')]")
	WebElement totalPages;
	
	@FindBy(xpath="//button[text()='下一页']")
	WebElement nextPage;
	
	@FindBy(className="login-info-nick")
	WebElement userNameElement;
	
	//private TaobaoDAO tbDAO = new TaobaoDAO();
	
	private String userName;
	
	public ItemListPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getItemInfo(int pages){
		
		WebDriverUtil.waitForElementPresent(driver, By.id("J_LoginInfo"), 15);
		
		userName = userNameElement.getText();
		
		WebDriverUtil.waitForElementPresent(driver, By.cssSelector("div[class^=index-mod__order-container]"), 10);
		//获得一共多少页
				
		int totalPages = getTotalPages(this.totalPages.getText());
		
		//如果总共页数小于要获取的
		if(totalPages < pages)
			pages = totalPages;
		
		for(int i=1;i<=pages;i++){
			//
			getOrderFromSinglePage();
			
			if(i != pages){
				//获取第一个订单的ID来判断翻页是否完成
				String firstOrderId = orderList.get(0).findElement(By.xpath(orderIdXpath)).getText();
				nextPage.click();
				WebDriverUtil.waitForElementNotVisible(driver, By.xpath("//span[text()='"+firstOrderId+"']"), 15);
			}
		}
	}
	
	public void getOrderFromSinglePage(){
		//获得有多少个订单，然后遍历
		for(WebElement order : orderList){
			
			String orderStatus = order.findElement(By.xpath(orderStatusXpath)).getText();

			String orderid = order.findElement(By.xpath(orderIdXpath)).getText();
			LOG.info("订单号："+orderid);
			
			String orderdate = order.findElement(By.cssSelector(orderDateCss)).getText();
			LOG.info("订单日期："+orderdate);
			
			String seller = order.findElement(By.xpath(sellerXpath)).getText();
			LOG.info("商家名："+seller);
			
			String orderprice = order.findElement(By.xpath(orderPriceXpath)).getText();
			LOG.info("订单总价："+orderprice);
			
			LOG.info("订单状态："+orderStatus);
			
			//获得订单中有多少个商品
			List<WebElement> goodsList = order.findElements(By.xpath(goodsRowXpath));
			for(WebElement good : goodsList){
				
				String itemname = good.findElement(By.xpath(goodsNameXpath)).getText();
				LOG.info("商品名称："+itemname);
				
				String itemid = getItemId(good.findElement(By.xpath(goodsIdXpath)).getAttribute("href"));
				LOG.info("商品ID："+itemid);
				
				String itemprice = good.findElement(By.xpath(goodsPriceXpath)).getText();
				LOG.info("商品价格："+itemprice);
				
				String itemquantity = good.findElement(By.xpath(goodsNumXpath)).getText();
				LOG.info("商品数量："+itemquantity);
				
				String itemSku = "";
				if(WebDriverUtil.verifyElementExistBasedOnElement(driver,good, By.xpath(skuXpath))){
					itemSku = good.findElement(By.xpath(skuXpath)).getText();
					LOG.info("SKU信息: "+itemSku);
				}
				
				String itemUrl = good.findElement(By.xpath(goodsUrlXpath)).getAttribute("href");
				LOG.info("商品链接："+itemUrl);
				
				String itemImgUrl = good.findElement(By.xpath(goodsImgXpath)).getAttribute("src");
				LOG.info("图片链接："+itemImgUrl);
				
				tbDAO.insert(orderid, orderdate, seller, orderprice, orderStatus, 
						itemname, itemid, itemprice, itemquantity,itemSku
						,itemUrl,itemImgUrl,userName);
			}
			
			if(WebDriverUtil.verifyElementExistBasedOnElement(driver,order, By.xpath(shipLinkXpath)) && orderStatus.contains("物流")){
				WebElement shipLink = order.findElement(By.xpath(shipLinkXpath));
				//shipLink.click();
				//获取链接地址打开新窗口查看
				String href = shipLink.getAttribute("href");
				String parentHanle = driver.getWindowHandle();

				if(driver.getWindowHandles().size() >= 2){
					WebDriverUtil.switchWindows(driver);
					driver.get(href);

				}else{
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("window.open('" + href + "')");
					WebDriverUtil.switchWindows(driver);
				}

				ShipStatusPage shipPage = new ShipStatusPage(driver);
				shipPage.getShipInfo(orderid);
				WebDriverUtil.switchBackToParentWindow(driver, parentHanle);
			}
		}
	}
	
}
