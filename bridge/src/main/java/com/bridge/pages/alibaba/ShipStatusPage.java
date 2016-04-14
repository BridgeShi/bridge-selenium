package com.bridge.pages.alibaba;

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
		
	@FindBy(xpath="//*[./*='物流公司：']/*[2]")
	WebElement shipperName;
	
	@FindBy(xpath="//*[./*='运单号码：']/*[2]")
	WebElement shipNumber;
	
	@FindBy(xpath="//*[./*='物流跟踪：']//p[@class='fd-clr'] | //*[./*='物流跟踪：']/*[2]//li[last()]")
	WebElement latestStatus;
		
	public ShipStatusPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getShipInfo(String orderid){
		LOG.debug("get ship Info");
		WebDriverUtil.waitForElementPresent(driver, By.xpath("//*[./*='运单号码：']/*[2]"), 15);
		LOG.info("运单号码: "+shipNumber.getText());
		LOG.info("物流公司: "+shipperName.getText());
		LOG.info("最新物流状态: "+latestStatus.getText());	
		
		aliDAO.update(shipNumber.getText(), shipperName.getText(), latestStatus.getText(), orderid);
	}
}
