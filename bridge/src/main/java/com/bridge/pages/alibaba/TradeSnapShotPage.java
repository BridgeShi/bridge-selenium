package com.bridge.pages.alibaba;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class TradeSnapShotPage extends BasePage{
	//private static final Log LOG = LogFactory.getLog(TradeSnapShotPage.class);

	@FindBy(linkText="查看货品最新详情")
	WebElement goodsLink;
	
	public TradeSnapShotPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public String getGoodsLink(){
		WebDriverUtil.waitForElementPresent(driver, By.className("cell-header"), 10);
		String goodsUrl = goodsLink.getAttribute("href");
		return goodsUrl;
	}
}
