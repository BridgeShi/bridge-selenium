package com.bridge.pages.alibaba;

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

import com.bridge.dao.Ali1688DAO;
import com.bridge.dao.TaobaoDAO;
import com.bridge.pages.alibaba.ShipStatusPage;
import com.bridge.util.WebDriverUtil;

public class ItemListPage {
	private static final Log LOG = LogFactory.getLog(ItemListPage.class);
	
	private static String goodsRowXpath = ".//td[@class='detail']//tr[.//img]";
	
	private Ali1688DAO aliDAO = new Ali1688DAO();
	
	protected WebDriver driver;
	
	@FindBy(className="item-active")
	List<WebElement> orderList;
	
	@FindBy(css=".layout_iframe iframe")
	WebElement iframe;
	
	public ItemListPage(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void getItemInfo(){
		WebDriverUtil.waitForElementPresent(driver, By.className("layout_iframe"), 30);
		WebDriverUtil.switchToIframe(driver, iframe);
		WebDriverUtil.waitForElementPresent(driver, By.id("listBox"), 30);

		//获得有多少个订单，然后遍历
		System.out.println(orderList.size());
		for(WebElement order : orderList){
			
			String orderStatus = order.findElement(By.cssSelector(".s7")).getText();
			
			String orderid = order.findElement(By.className("title-order")).getText();
			LOG.info("订单号："+orderid);
			
			String orderdate = order.findElement(By.className("date")).getText();
			LOG.info("订单日期："+orderdate);
			
			String seller = order.findElement(By.cssSelector(".seller-name > a")).getText();
			LOG.info("商家名："+seller);
			
			String orderprice = order.findElement(By.cssSelector(".s6 .total")).getText();
			LOG.info("订单总价："+orderprice);
			
			LOG.info("订单状态："+orderStatus);
			
			//获得订单中有多少个商品
			List<WebElement> goodsList = order.findElements(By.xpath(goodsRowXpath));
			for(WebElement good : goodsList){
				String itemname = good.findElement(By.className("productName")).getText();
				LOG.info("商品名称："+itemname);
				
				String itemid = getItemId(good.findElement(By.className("productName")).getAttribute("href"));
				LOG.info("商品ID："+itemid);
				
				String itemprice = good.findElement(By.cssSelector("td.s3 strong")).getText();
				LOG.info("商品价格："+itemprice);
				
				String itemquantity = good.findElement(By.className("s4")).getText();
				LOG.info("商品数量："+itemquantity);
				
				aliDAO.insert(orderid, orderdate, seller, orderprice, orderStatus, itemname, itemid, itemprice, itemquantity);

			}
			
			if(verifyShipInfoExist(order, By.linkText("查看物流")) && orderStatus.equals("等待买家确认收货")){
				//点击物流详情查看物流信息
				order.findElement(By.linkText("查看物流")).click();
				String parentHanle = driver.getWindowHandle();
				WebDriverUtil.switchWindows(driver);
				ShipStatusPage shipPage = new ShipStatusPage(driver);
				shipPage.getShipInfo(orderid);
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
