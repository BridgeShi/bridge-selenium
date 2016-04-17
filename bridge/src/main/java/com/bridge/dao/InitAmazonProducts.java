package com.bridge.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InitAmazonProducts {
	
	private static final String AMAZON_PRO_INV = "create table AMAZON_PRO_INV ("
			+ "producturl VARCHAR(500), inventory VARCHAR(100), timestamp VARCHAR(100) ) "
			+ "DEFAULT CHARSET=utf8";
	
	public static Connection getConnection() throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost/tbrobot?useUnicode=true&characterEncoding=utf-8";
		String username = "root";
		String password = "bridge1234";
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
	
	public static void main(String args[]) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(AMAZON_PRO_INV);
			
			String query = "set names utf8";
			stmt.execute(query);
		    
			query = " insert into AMAZON_PRO_INV (producturl, inventory, timestamp) " + "values (?, ?, ?)";
		    
			String producturl = "http://www.amazon.com/gp/product/B00UDMHBJS/ref=ox_sc_act_title_1?ie=UTF8&psc=1&smid=ACA0XT15ZHVG4";
			String inventory = "";
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestamp = sdf.format(date);
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, producturl);
			preparedStmt.setString (2, inventory);
			preparedStmt.setString (3, timestamp);
			preparedStmt.execute();
			
			producturl = "http://www.amazon.com/dp/B00IA1H554?psc=1";

			preparedStmt.setString (1, producturl);
			preparedStmt.execute();
			
			producturl = "http://www.amazon.com/dp/B011NVXC5O?psc=1";
			
			preparedStmt.setString (1, producturl);
			preparedStmt.execute();
			
			System.out.println("CreateEmployeeTableMySQL: main(): table created.");
		} catch (ClassNotFoundException e) {
			System.out.println("error: failed to load MySQL driver.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("error: failed to create a connection object.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("other error:");
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
