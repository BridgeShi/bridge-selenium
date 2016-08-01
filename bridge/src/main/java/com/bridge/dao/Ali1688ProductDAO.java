package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;

import com.alibaba.fastjson.JSONArray;

import java.sql.PreparedStatement;

public class Ali1688ProductDAO {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;

	private String insertquery = " insert into Ali1688_PRODUCT_INFOS (productName, productUrl, price, weight, bargainCount, "
			+ "descripText, imgUrl, descripImg, skuInfo, detail) " + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public void insert(String productName, String productUrl, String price, String weight, String bargainCount,
			String descripText, String imgUrl, String descripImg, String skuInfo,String detail) {

		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(insertquery);
			preparedStmt.setString (1, productName);
			preparedStmt.setString (2, productUrl);
			preparedStmt.setString (3, price);
			preparedStmt.setString (4, weight);
			preparedStmt.setString (5, bargainCount);
			preparedStmt.setString (6, descripText);
			preparedStmt.setString (7, imgUrl);
			preparedStmt.setString (8, descripImg);
			preparedStmt.setString (9, skuInfo);
			preparedStmt.setString (10, detail);
			
			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
