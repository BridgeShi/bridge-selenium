package com.bridge.tests.alibaba;



import org.openqa.selenium.By;

import com.bridge.pages.alibaba.SimlerOfferPage;
import com.bridge.util.WebDriverUtil;

public class alibabaSimilerOffer extends alibaba{
		
	public static void main(String args[]) {
		//插件路径
		//getWebDriver();
		getChromeDriverWithExtension("resources/taohuoyuan_v3.3.crx");
		
		//aliLogin();
		
		taobaoLogin();
		
		openSmilerOffer("https://item.taobao.com/item.htm?spm=a21bo.50862.201867-rmds-1.1.9TpSDc&scm=1007.12807.73594.100200300000002&id=536210376480&pvid=2669c998-72fa-4551-9883-381c02f1b2ba");
		
		closeWebDriver();
	}
	
	public static void openSmilerOffer(String url){
		driver.get(url);
		String similerOfferUrl;
		
		By sameSourcesSelector = By.cssSelector("div.jhycontent.taobao .same-sources");
		if(WebDriverUtil.verifyElementExist(driver, sameSourcesSelector))
			similerOfferUrl = driver.findElement(sameSourcesSelector).getAttribute("href");
		else
		{
			LOG.warn("没有同款货源");
			return;
		}
		
		//String parentWindow = switchWindows(driver);
		
		driver.get(similerOfferUrl.replace("//s.1688.com/", "https://s.1688.com/"));
		
		SimlerOfferPage offerPage = new SimlerOfferPage(driver);
		
		offerPage.getOfferInfo();
		
		//switchBackToParentWindow(driver, parentWindow);

	}
	

}
