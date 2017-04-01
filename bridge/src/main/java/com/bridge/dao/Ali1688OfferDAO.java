package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Ali1688OfferDAO {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;

	public void insertOffer(String productName,String productUrl,String price,String companyName,String companyUrl) {

		String sql = "insert into ali1688_offers "
				+ "(productname, productUrl, price, companyname, companyurl)"
				+ " values (?, ?, ?, ?, ?)";
		
		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString (1, productName);
			preparedStmt.setString (2, productUrl);
			preparedStmt.setString (3, price);
			preparedStmt.setString (4, companyName);
			preparedStmt.setString (5, companyUrl);
			
			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void insertCompanyOffer(String productName,String productUrl,String price,String companyName,String companyUrl,String counts) {

		String sql = "insert into ali1688_company_offers "
				+ "(productname, productUrl, price, companyname, companyurl, counts)"
				+ " values (?, ?, ?, ?, ?, ?)";
		
		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString (1, productName);
			preparedStmt.setString (2, productUrl);
			preparedStmt.setString (3, price);
			preparedStmt.setString (4, companyName);
			preparedStmt.setString (5, companyUrl);
			preparedStmt.setString (6, counts);

			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertSimlerOffer(String productName,String productUrl,String price) {

		String sql = "insert into Ali1688_Simler_Offers "
				+ "(productname, productUrl, price)"
				+ " values (?, ?, ?)";
		
		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString (1, productName);
			preparedStmt.setString (2, productUrl);
			preparedStmt.setString (3, price);

			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
