package com.bridge.pages.alibaba;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.pages.alibaba.ShipStatusPage;
import com.bridge.util.WebDriverUtil;

public class ItemListPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(ItemListPage.class);
	
	private static String goodsRowXpath = ".//td[@class='detail']//tr[.//img]";
		
	@FindBy(className="item-active")
	List<WebElement> orderList;
	
	@FindBy(css=".layout_iframe iframe")
	WebElement iframe;
	
	@FindBy(xpath="//div/span[contains(.,'共')]")
	WebElement totalPages;
	
	@FindBy(xpath="//a[text()='下一页']")
	WebElement nextPage;
	
	@FindBy(className="account-id")
	WebElement userNameElement;
	
	private String dataRegex = "(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01]) "
			+ "(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})";
	
	private String userName;
	
	public ItemListPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getItemInfo(int pages){
		WebDriverUtil.waitForElementPresent(driver, By.className("layout_iframe"), 30);
		userName = userNameElement.getText();
		LOG.info("user name is: "+userName);
		WebDriverUtil.switchToIframe(driver, iframe);
		WebDriverUtil.waitForElementPresent(driver, By.id("listBox"), 30);
		
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
				String firstOrderId = orderList.get(0).findElement(By.className("title-order")).getText();
				nextPage.click();
				WebDriverUtil.waitForElementNotVisible(driver, By.xpath("//span[text()='"+firstOrderId+"']"), 15);
			}
		}
	}
	
	public void getOrderFromSinglePage(){


		//获得有多少个订单，然后遍历
		for(WebElement order : orderList){
			
			String orderStatus = order.findElement(By.cssSelector(".s7")).getText();
			
			String orderid = order.findElement(By.className("title-order")).getText();
			orderid = getStringByRegex(orderid,"\\d+");
			LOG.info("订单号："+orderid);
			
			String orderdate = order.findElement(By.className("date")).getText();
			orderdate = getStringByRegex(orderdate,dataRegex);
			LOG.info("订单日期："+orderdate);
			
			String seller = order.findElement(By.cssSelector(".seller-name > a")).getAttribute("data-copytitle");
			LOG.info("商家名："+seller);
			
			String orderprice = order.findElement(By.cssSelector(".s6 .total")).getText();
			LOG.info("订单总价："+orderprice);
			
			LOG.info("订单状态："+orderStatus);
			
			//展开更多
			if(WebDriverUtil.verifyElementExistBasedOnElement(driver, order, By.cssSelector(".isShowMore"))){
				LOG.info("show more");
				order.findElement(By.xpath("//div[@class='isShowMore']/a[contains(.,'展开')]")).click();
				WebDriverUtil.waitForElementVisible(driver, order.findElement(By.xpath("//div[@class='isShowMore']/a[@class='folderclose']")), 5);
			}
			
			//获得订单中有多少个商品
			List<WebElement> goodsList = order.findElements(By.xpath(goodsRowXpath));
			for(WebElement good : goodsList){
				String itemname = good.findElement(By.className("productName")).getText();
				LOG.info("商品名称："+itemname);
								
				String itemUrl = getGoodsUrl(good);
				LOG.info("商品链接："+itemUrl);
				
				String itemid = getStringByRegex(itemUrl,"(?<=offer\\/)\\d+");
				LOG.info("商品ID："+itemid);
				
				String itemprice = good.findElement(By.cssSelector("td.s3 strong")).getText();
				LOG.info("商品价格："+itemprice);
				
				String itemquantity = good.findElement(By.className("s4")).getText();
				LOG.info("商品数量："+itemquantity);
				
				String itemSku = "";
				if(WebDriverUtil.verifyElementExistBasedOnElement(driver,good, By.className("trade-spec"))){
					itemSku = good.findElement(By.className("trade-spec")).getText();
					LOG.info("SKU信息: "+itemSku);
				}
				
				String itemImgUrl = good.findElement(By.cssSelector(".s1 img")).getAttribute("src").replace(".summ2", "");
				LOG.info("图片链接："+itemImgUrl);
				
				aliDAO.insert(orderid, orderdate, seller, orderprice, orderStatus, 
						itemname, itemid, itemprice, itemquantity,itemSku
						,itemUrl,itemImgUrl,userName);
			}
			
			if(WebDriverUtil.verifyElementExistBasedOnElement(driver,order, By.linkText("查看物流")) && orderStatus.equals("等待买家确认收货")){
				//点击物流详情查看物流信息
				
				String parentHanle = driver.getWindowHandle();
				if(driver.getWindowHandles().size() >= 2){
					String href = order.findElement(By.linkText("查看物流")).getAttribute("href");
					WebDriverUtil.switchWindows(driver);
					driver.get(href);
				}else{
					order.findElement(By.linkText("查看物流")).click();
					WebDriverUtil.switchWindows(driver);
				}
				ShipStatusPage shipPage = new ShipStatusPage(driver);
				shipPage.getShipInfo(orderid);
				WebDriverUtil.switchBackToParentWindow(driver, parentHanle);
				WebDriverUtil.switchToIframe(driver, iframe);
			}
		}
	}
	
	//获得URL
	public String getGoodsUrl(WebElement goods){
		goods.findElement(By.className("productName")).click();
		String parentHanle = driver.getWindowHandle();
		WebDriverUtil.switchWindows(driver);
		TradeSnapShotPage sanpShotPage = new TradeSnapShotPage(driver);
		String url = sanpShotPage.getGoodsLink();
		driver.close();
		WebDriverUtil.switchBackToParentWindow(driver, parentHanle);
		WebDriverUtil.switchToIframe(driver, iframe);
		return url;
	}
}
