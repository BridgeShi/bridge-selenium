package com.bridge.pages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.bridge.dao.TaobaoDAO;

public class BasePage {
	private static final Log LOG = LogFactory.getLog(BasePage.class);
	
	protected WebDriver driver;
	
	protected TaobaoDAO tbDAO = new TaobaoDAO();
	
	public BasePage(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public static String getItemId(String url){
		Pattern p=Pattern.compile("(?<=id=|id_num=)\\d+");
	    Matcher m=p.matcher(url);
	    String findString = "";
	    if(m.find()){
	    	findString = m.group(0);
	    }
		return findString;
	}
	
	public static int getTotalPages(String totalPages){
		Pattern p=Pattern.compile("\\d+");
	    Matcher m=p.matcher(totalPages);
	    String findString = "";
	    if(m.find()){
	    	findString = m.group(0);
	    }
	    LOG.info("total pages:"+findString);
		return Integer.parseInt(findString);
	}


}
