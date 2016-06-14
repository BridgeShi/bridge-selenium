package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class BaiduLecaiDAO {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;

	private String insertquery = " insert into pk10_draw (phase, result)"
			 + "values (?, ?)";
	
	public void insert(String orderid, String orderdate) {

		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(insertquery);
			preparedStmt.setString (1, orderid);
			preparedStmt.setString (2, orderdate);

			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
