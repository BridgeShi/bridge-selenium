package com.bridge.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderListDAO {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;
	private ResultSet rs = null;
	
	public ArrayList<PreOrderList> getPreOrderList() throws SQLException {
		
		String query = "SELECT url, firstOption, secondOption, qty FROM Ali1688_PreOrder_List";
		ArrayList<PreOrderList> objList = new ArrayList<PreOrderList>();
		
		try {

			conn = DBManager.getConnection();

			preparedStmt = conn.prepareStatement(query);

			// execute the query, and get a java resultset
			rs = preparedStmt.executeQuery();
			
			// iterate through the java resultset
			while (rs.next()) {
				PreOrderList obj = new PreOrderList();
				
				obj.setUrl(rs.getString("url"));
				obj.setFirstOption(rs.getString("firstOption"));
				obj.setSecondOption(rs.getString("secondOption"));
				obj.setQty(rs.getString("qty"));

				objList.add(obj);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			if (preparedStmt != null) {
				preparedStmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
			return objList;
		}

	}

	
public void updateAddToCart(String productUrl,boolean orderStatus) throws Exception {
		
		String sql = "update Ali1688_PreOrder_List"
				+ " set result = ?"
				+ " where url = ?";

		try {
			conn = DBManager.getConnection();
			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setBoolean(1, orderStatus);
			preparedStmt.setString(2, productUrl);
			preparedStmt.executeUpdate();
		}finally {
			if (preparedStmt != null) {
				preparedStmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
}
