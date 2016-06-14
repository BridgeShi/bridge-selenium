package com.bridge.tests;

import com.bridge.pages.baidulecai.PK10Page;

public class lecai extends BaseTest{

	public static void main(String args[]){
		getWebDriver();

		getLatestDraw("http://baidu.lecai.com/lottery/draw/view/557/559167?agentId=5563");
	}
	
	public static void getLatestDraw(String url){
		PK10Page pk10 = new PK10Page(driver);

		LOG.info("open url:"+url);
		driver.get(url);
		
		pk10.getLatestDraw();
		
	}
}
