package com.bridge.pages.amazon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class CartPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(CartPage.class);

	@FindBy(id="dropdown1_9")
	WebElement quantityTenPlus;
		
	@FindBy(id="a-autoid-2-announce")
	WebElement quantityDropDownMenu;
	
	@FindBy(name="quantityBox")
	WebElement quantityInputBox;
	
	@FindBy(css=".sc-quantity-update-message .a-size-base")
	WebElement quantityMessage;
	
	@FindBy(css=".a-declarative>input")
	WebElement deleteInput;
	
	public CartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public String getStock(){
		LOG.debug("click dropdown");
		WebDriverUtil.waitForElementPresent(driver, By.id("activeCartViewForm"), 30);
		WebDriverUtil.waitForElementPresent(driver, By.id("a-autoid-2-announce"), 15);
		quantityDropDownMenu.click();
		
		LOG.debug("select 10+");
		WebDriverUtil.waitForElementPresent(driver, By.id("dropdown1_9"), 10);
		quantityTenPlus.click();
		
		LOG.debug("input 999");
		WebDriverUtil.waitForElementVisible(driver, quantityInputBox, 15);
		quantityInputBox.sendKeys("999");
		quantityInputBox.sendKeys(Keys.ENTER);
		
		WebDriverUtil.waitForElementPresent(driver, By.cssSelector(".sc-quantity-update-message .a-size-base"), 10);
		LOG.info(quantityMessage.getText());
		String inventory = getStringByRegex(quantityMessage.getText(),"\\d+");
		LOG.info("库存有: "+inventory);
		
		return inventory;
	}
	
	public void deleteItem(){
		LOG.debug("delete item...");
		deleteInput.click();
		WebDriverUtil.waitForElementNotVisible(driver, By.cssSelector(".a-fixed-left-grid-inner"), 10);
	}
	
}
