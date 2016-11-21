package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Ali1688EnProductDAO {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;

	private String insertquery = " insert into Ali1688_EN_PRODUCT_INFOS (productName, productUrl, imgUrl, price, minOrder, "
			+ "details, packageDetails) " + "values (?, ?, ?, ?, ?, ?, ?)";
	
	public void insert(String productName, String productUrl, String imgUrl, String price, String minOrder,
			String details, String packageDetails) {

		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(insertquery);
			preparedStmt.setString (1, productName);
			preparedStmt.setString (2, productUrl);
			preparedStmt.setString (3, imgUrl);
			preparedStmt.setString (4, price);
			preparedStmt.setString (5, minOrder);
			preparedStmt.setString (6, details);
			preparedStmt.setString (7, packageDetails);
			
			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
