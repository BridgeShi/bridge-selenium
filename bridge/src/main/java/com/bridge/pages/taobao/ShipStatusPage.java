package com.bridge.pages.taobao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class ShipStatusPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(ShipStatusPage.class);	
	
	@FindBy(xpath="//label[contains(.,'物流公司')]/following-sibling::span")
	WebElement shipperName;
	
	@FindBy(xpath="//label[contains(.,'运单号码')]/following-sibling::span")
	WebElement shipNumber;
	
	@FindBy(css="li.latest")
	WebElement latestStatus;
		
	public ShipStatusPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getShipInfo(String orderid){
		LOG.debug("get ship Info");
		if(driver.getTitle().contains("物流详情")){
			WebDriverUtil.waitForElementPresent(driver, By.className("package-status"), 15);
			LOG.info("运单号码: "+shipNumber.getText());
			LOG.info("物流公司: "+shipperName.getText());
			LOG.info("最新物流状态: "+latestStatus.findElement(By.cssSelector(".date")).getText()+" "
					+latestStatus.findElement(By.cssSelector(".week")).getText()+" "
					+latestStatus.findElement(By.cssSelector(".time")).getText()+" "
					+latestStatus.findElement(By.cssSelector(".text")).getText()
			);
			tbDAO.update(shipNumber.getText(), shipperName.getText(), latestStatus.findElement(By.cssSelector(".date")).getText()+" "
					+latestStatus.findElement(By.cssSelector(".week")).getText()+" "
					+latestStatus.findElement(By.cssSelector(".time")).getText()+" "
					+latestStatus.findElement(By.cssSelector(".text")).getText(), orderid);
		}else
		{
			WebDriverUtil.waitForElementPresent(driver, By.className("wl-orderDesc"), 15);
			LOG.info(driver.findElement(By.cssSelector("div.wl-orderDesc")).getText());
		}
	}
	
}
