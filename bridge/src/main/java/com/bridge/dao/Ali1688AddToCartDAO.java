package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ali1688AddToCartDAO {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;
	private ResultSet rs = null;

	public String[][] getProductsToBeOrder() throws SQLException{

		String sql = "SELECT productUrl,firstOption,secondOption,amount FROM ali1688_to_be_ordered_list";
		String[][] productsToBeOrder = null;
		int resultRow = 0 ;
		try {
			conn = DBManager.getConnection();
			
			/*String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);*/
			
			preparedStmt = conn.prepareStatement(sql);
			
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				resultRow++;
			}
			productsToBeOrder = new String[resultRow][4];
			
			rs = preparedStmt.executeQuery();
			for (int i = 0; rs.next(); i++) {
				for (int j = 0; j < 4; j++) {
					productsToBeOrder[i][j] = rs.getString(j + 1);
				}
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
		}
		
		return productsToBeOrder;
	}
	
public void updateAddToCart(String productUrl,boolean orderStatus) throws Exception {
		
		String sql = "update ali1688_to_be_ordered_list"
				+ " set orderSuccess = ?"
				+ " where productUrl = ?";

		
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
