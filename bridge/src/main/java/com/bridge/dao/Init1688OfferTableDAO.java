package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Init1688OfferTableDAO {

	private static final String Ali1688_OFFERS = "create table Ali1688_Offers ("
			+ "productname VARCHAR(100), productUrl VARCHAR(500), price VARCHAR(100), "
			+ "companyname VARCHAR(100), companyurl VARCHAR(500)) "
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
			stmt.executeUpdate(Ali1688_OFFERS);
			
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
