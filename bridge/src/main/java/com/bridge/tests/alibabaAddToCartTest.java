package com.bridge.tests;


import java.sql.SQLException;
import java.util.ArrayList;

//import org.testng.annotations.Test;

import com.bridge.dao.OrderListDAO;
import com.bridge.dao.PreOrderList;
import com.bridge.pages.alibaba.ProductDetailPage;

public class alibabaAddToCartTest extends alibaba{
	
	private static OrderListDAO rldao = new OrderListDAO();
	
	public static void main(String args[]) {
		getWebDriver();
		
		aliLogin();

		try {
			ArrayList<PreOrderList> pol = rldao.getPreOrderList();
			for (int i = 0; i < pol.size(); i++) {
				addToCart(pol.get(i).getUrl(),pol.get(i).getFirstOption(),pol.get(i).getSecondOption(),pol.get(i).getQty());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//关闭webdriver
		closeWebDriver();
	}
	
	public static void addToCart(String url,String firstOption,String secondOption,String amount){
		
		ProductDetailPage productPage = new ProductDetailPage(driver);

		LOG.info("open url:"+url);
		driver.get(url);
		
		try {
			productPage.addToCart(firstOption,secondOption,amount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
