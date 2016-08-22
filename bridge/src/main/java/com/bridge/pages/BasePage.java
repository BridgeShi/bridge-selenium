package com.bridge.pages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.bridge.dao.Ali1688AddToCartDAO;
import com.bridge.dao.Ali1688CategoryDAO;
import com.bridge.dao.Ali1688DAO;
import com.bridge.dao.Ali1688OfferDAO;
import com.bridge.dao.Ali1688ProductDAO;
import com.bridge.dao.AmazonDAO;
import com.bridge.dao.BaiduLecaiDAO;
import com.bridge.dao.TaobaoDAO;

public class BasePage {
	private static final Log LOG = LogFactory.getLog(BasePage.class);
	
	protected WebDriver driver;
	
	protected TaobaoDAO tbDAO = new TaobaoDAO();
	
	protected Ali1688DAO aliDAO = new Ali1688DAO();

	protected Ali1688CategoryDAO aliCategoryDAO = new Ali1688CategoryDAO();
	
	protected AmazonDAO amazonDAO = new AmazonDAO();
	
	protected BaiduLecaiDAO lecaiDAO = new BaiduLecaiDAO();

	protected Ali1688OfferDAO aliOfferDAO = new Ali1688OfferDAO();

	protected Ali1688ProductDAO aliProductDAO = new Ali1688ProductDAO();

	protected Ali1688AddToCartDAO addToCartDAO = new Ali1688AddToCartDAO();

	
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
	
	public static String getStringByRegex(String baseString,String regex){
		Pattern p=Pattern.compile(regex);
	    Matcher m=p.matcher(baseString);
	    String findString = "";
	    if(m.find()){
	    	findString = m.group(0);
	    }
		return findString;
	}
}
