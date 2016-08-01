package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Init1688ProductDetailDAO {

	private static final String Ali1688_OFFERS = "create table ali1688_product_infos ("
			+ "productname VARCHAR(500), productUrl VARCHAR(500), price VARCHAR(500), "
			+ "weight VARCHAR(500), bargainCount VARCHAR(500), descripText LONGTEXT, "
			+ "imgUrl VARCHAR(5500), descripImg VARCHAR(6000), skuInfo LONGTEXT, detail VARCHAR(6000)) "
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
