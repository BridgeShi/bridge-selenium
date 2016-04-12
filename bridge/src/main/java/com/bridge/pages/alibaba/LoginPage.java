package com.bridge.pages.alibaba;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.util.WebDriverUtil;

public class LoginPage {
	private static final Log LOG = LogFactory.getLog(LoginPage.class);

	protected WebDriver driver;

	@FindBy(id="TPL_username_1")
	WebElement userName;
	
	@FindBy(id="TPL_password_1")
	WebElement passWord;
	
	@FindBy(id="J_SubmitStatic")
	WebElement submitBtn;
	
	@FindBy(css="#loginchina iframe")
	WebElement loginFrame;
	
	private String url = "https://login.1688.com/member/signin.htm";
	
	public LoginPage(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void getUrl(){
		driver.get(url);
	}
	
	public void login(String userName,String passWord){
		WebDriverUtil.waitForElementPresent(driver, By.id("loginchina"), 30);
		WebDriverUtil.switchToIframe(driver, loginFrame);
		LOG.debug("Entering username and password");
		this.userName.sendKeys(userName);
		this.passWord.sendKeys(passWord);
		LOG.debug("Submit login");
		submitBtn.click();
	}
	
}
