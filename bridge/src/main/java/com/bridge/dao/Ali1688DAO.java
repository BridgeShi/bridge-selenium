package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class Ali1688DAO {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;

	private String insertquery = " insert into Ali1688_ORDER_HISTORY (orderid, orderdate, seller, totalprice, orderstatus, "
			+ "itemname, itemid, itemprice, itemqty, sku, itemurl, imgurl, username) " + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
	
	private String updatequery = "update Ali1688_ORDER_HISTORY set shipno = ?, shipper = ?, shipstatus = ? where orderid = ?";

	public void insert(String orderid, String orderdate, String seller, String totalprice, String orderstatus,
			String itemname, String itemid, String itemprice, String itemqty,String itemSku,String itemurl,String imgurl,String username) {

		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(insertquery);
			preparedStmt.setString (1, orderid);
			preparedStmt.setString (2, orderdate);
			preparedStmt.setString (3, seller);
			preparedStmt.setString (4, totalprice);
			preparedStmt.setString (5, orderstatus);
			preparedStmt.setString (6, itemname);
			preparedStmt.setString (7, itemid);
			preparedStmt.setString (8, itemprice);
			preparedStmt.setString (9, itemqty);
			preparedStmt.setString (10, itemSku);
			preparedStmt.setString (11, itemurl);
			preparedStmt.setString (12, imgurl);
			preparedStmt.setString (13, username);
			
			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(String shipno, String shipper, String shipstatus, String orderid) {

		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(updatequery);
			preparedStmt.setString (1, shipno);
			preparedStmt.setString (2, shipper);
			preparedStmt.setString (3, shipstatus);
			preparedStmt.setString (4, orderid);
			
			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
