package com.bridge.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitPreOrderTable {
	
	private static final String Ali1688_PreOrder_List = "create table Ali1688_PreOrder_List ("
			+ "url VARCHAR(500), firstOption VARCHAR(500), secondOption VARCHAR(500), qty VARCHAR(100)) "
			+ "DEFAULT CHARSET=utf8";
	
	private static final String Ali1688_Order_Log = "create table Ali1688_Order_Log ("
			+ "log VARCHAR(1000)) "
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
			stmt.executeUpdate(Ali1688_PreOrder_List);
			stmt.executeUpdate(Ali1688_Order_Log);
			
			String query = "set names utf8";
			stmt.execute(query);
		    
			query = " insert into Ali1688_PreOrder_List (url, firstOption, secondOption, qty) " + "values (?, ?, ?, ?)";
		    
			String url = "https://detail.1688.com/offer/530768935244.html?spm=a26e3.8027625.1999173159.1.2rTUAq#";
			String firstOption = "蓝色";
			String secondOption = "";
			String qty = "1";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, url);
			preparedStmt.setString (2, firstOption);
			preparedStmt.setString (3, secondOption);
			preparedStmt.setString (4, qty);
			preparedStmt.execute();
			
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
