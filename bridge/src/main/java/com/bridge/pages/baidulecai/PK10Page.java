package com.bridge.pages.baidulecai;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;

public class PK10Page extends BasePage{
	private static final Log LOG = LogFactory.getLog(PK10Page.class);

	@FindBy(id="jq_latest_draw_phase")
	WebElement latestDrawPhase;
	
	@FindBy(id="jq_latest_draw_result")
	WebElement latestDrawNumber;
	
	public PK10Page(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void getLatestDraw(){
		LOG.info("最新一期: "+latestDrawPhase.getText());
		LOG.info("中奖号码: "+latestDrawNumber.getText());
		
		lecaiDAO.insert(latestDrawPhase.getText(), latestDrawNumber.getText());
	}
}
