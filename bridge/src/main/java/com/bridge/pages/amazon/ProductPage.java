package com.bridge.pages.amazon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class ProductPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(ProductPage.class);

	@FindBy(id="add-to-cart-button")
	WebElement addToCartBtn;
		
	@FindBy(id="hlb-view-cart-announce")
	WebElement viewCartBtn;
	
	public ProductPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getUrl(String url){
		LOG.info("open ur: "+url);
		driver.get(url);
	}
	
	public CartPage addToCart(){
		//点击add-to-cart
		LOG.debug("click add to cart btn");
		WebDriverUtil.waitForElementPresent(driver, By.id("add-to-cart-button"), 10);
		addToCartBtn.click();
		//点击cart
		WebDriverUtil.waitForElementPresent(driver, By.id("hlb-view-cart-announce"), 10);
		viewCartBtn.click();

		return new CartPage(driver);
	}
	
}
