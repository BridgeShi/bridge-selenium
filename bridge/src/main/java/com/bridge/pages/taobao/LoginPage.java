package com.bridge.pages.taobao;

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
	
	@FindBy(id="_n1z")
	WebElement captchaArrow;
	
	private String url = "https://login.taobao.com";
	
	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getUrl(){
		driver.get(url);
	}
	
	public void login(String userName,String passWord){
		LOG.debug("Entering username and password");
		
		if(WebDriverUtil.verifyElementExist(driver, By.id("J_Quick2Static")))
		{
			driver.findElement(By.id("J_Quick2Static")).click();
		}
		
		this.userName.clear();

		this.userName.sendKeys(userName);
		this.passWord.sendKeys(passWord);
		LOG.debug("Submit login");
		submitBtn.click();
	}
	
	public void captcha(){
		Actions actions = new Actions(driver);
		WebDriverUtil.waitForElementPresent(driver, By.id("_n1z"), 10);
		LOG.debug("captcha");
		actions.dragAndDropBy(captchaArrow, 258, 0);
		actions.perform();
	}
}
