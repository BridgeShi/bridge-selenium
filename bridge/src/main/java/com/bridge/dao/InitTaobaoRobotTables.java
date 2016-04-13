package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InitTaobaoRobotTables {

	private static final String TB_ORDER_TABLE = "create table TB_ORDER_HISTORY ("
			+ "orderid VARCHAR(100), orderdate VARCHAR(100), seller VARCHAR(100), "
			+ "totalprice VARCHAR(100), orderstatus VARCHAR(100), "
			+ "itemname VARCHAR(100), itemid VARCHAR(100), itemprice VARCHAR(100), itemqty VARCHAR(100), "
			+ "shipno VARCHAR(100), shipper VARCHAR(100), shipstatus VARCHAR(100) ) "
			+ "DEFAULT CHARSET=utf8";

	public static Connection getConnection() throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost/tbrobot?useUnicode=true&characterEncoding=utf-8";
		String username = "root";
		String password = "123456";
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
			stmt.executeUpdate(TB_ORDER_TABLE);
			
			String query = "set names utf8";
			stmt.execute(query);
		    
			query = " insert into TB_ORDER_HISTORY (orderid, orderdate, seller, totalprice, orderstatus, "
					+ "itemname, itemid, itemprice, itemqty) " + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    
			String orderid = "1787738400287533";
			String orderdate = "2016-04-08";
			String seller = "妮妮童装外贸折扣店";
			String totalprice = "19.99";
			String orderstatus = "交易关闭";
			String itemname = "童装夏季短袖T恤 可爱佩佩猪t 恤19.9 包邮 Kids t shirt";
			String itemid = "38980346719";
			String itemprice = "19.99";
			String itemqty = "1";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString (1, orderid);
			preparedStmt.setString (2, orderdate);
			preparedStmt.setString (3, seller);
			preparedStmt.setString (4, totalprice);
			preparedStmt.setString (5, orderstatus);
			preparedStmt.setString (6, itemname);
			preparedStmt.setString (7, itemid);
			preparedStmt.setString (8, itemprice);
			preparedStmt.setString (9, itemqty);
			
			preparedStmt.execute();
			
			query = "SELECT itemname FROM TB_ORDER_HISTORY";

		       
		      // execute the query, and get a java resultset
		     ResultSet rs = stmt.executeQuery(query);
		       
		      // iterate through the java resultset
		      while (rs.next())
		      {
		        String item_name = rs.getString("itemname");
		         
		        // print the results
		        System.out.format("itemname: %s\n", item_name);
		      }
		      
			
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
