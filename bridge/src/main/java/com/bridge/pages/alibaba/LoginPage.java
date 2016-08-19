package com.bridge.pages.alibaba;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class LoginPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(LoginPage.class);

	@FindBy(id="TPL_username_1")
	WebElement userName;
	
	@FindBy(id="TPL_password_1")
	WebElement passWord;
	
	@FindBy(id="J_SubmitStatic")
	WebElement submitBtn;
	
	@FindBy(css="#loginchina iframe")
	WebElement loginFrame;
	
	@FindBy(id="_n1z")
	WebElement captchaArrow;
	
	private String url = "https://login.1688.com/member/signin.htm";
	
	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getUrl(){
		driver.get(url);
	}
	
	public void login(String userName,String passWord){
		WebDriverUtil.waitForElementPresent(driver, By.id("loginchina"), 30);
		WebDriverUtil.switchToIframe(driver, loginFrame);
		LOG.debug("Entering username and password");
		this.userName.clear();
		this.userName.sendKeys(userName);
		this.passWord.sendKeys(passWord);
		//captcha();
		LOG.debug("Submit login");
		submitBtn.click();
		
		WebDriverUtil.waitForElementPresent(driver, By.cssSelector("a[title='"+userName+"']"), 15);
	}
	
	public void captcha(){
		Actions actions = new Actions(driver);
		WebDriverUtil.waitForElementPresent(driver, By.id("_n1z"), 10);
		LOG.debug("captcha");
		actions.dragAndDropBy(captchaArrow, 258, 0);
		actions.perform();
	}
}
