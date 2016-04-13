package com.bridge.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitTestingAccounts {
	
	private static final String TB_1688_Accounts = "create table TB_1688_Accounts ("
			+ "account VARCHAR(100), password VARCHAR(100), type VARCHAR(100) ) "
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
			stmt.executeUpdate(TB_1688_Accounts);
			
			String query = "set names utf8";
			stmt.execute(query);
		    
			query = " insert into TB_1688_Accounts (account, password, type) " + "values (?, ?, ?)";
		    
			String account = "策融1";
			String password = "SH61504007";
			String type = "taobao";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, account);
			preparedStmt.setString (2, password);
			preparedStmt.setString (3, type);
			preparedStmt.execute();
			
//			account = "krsourcing1@163.com";
//			
//			preparedStmt = conn.prepareStatement(query);
//			preparedStmt.setString (1, account);
//			preparedStmt.setString (2, password);
//			preparedStmt.setString (3, type);
//			preparedStmt.execute();
//			
//			type = "ali1688";
//			
//			preparedStmt = conn.prepareStatement(query);
//			preparedStmt.setString (1, account);
//			preparedStmt.setString (2, password);
//			preparedStmt.setString (3, type);
//			preparedStmt.execute();
			
			type = "ali1688";
			
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, account);
			preparedStmt.setString (2, password);
			preparedStmt.setString (3, type);
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
