package com.bridge.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderListDAO {

	public PreOrderList getPreOrderList() {
		
		String query = "SELECT url, firstOption, secondOption, qty FROM Ali1688_PreOrder_List";
		
		try {
			Connection conn;

			conn = DBManager.getConnection();

			PreparedStatement preparedStmt = null;
			
			preparedStmt = conn.prepareStatement(query);

			// execute the query, and get a java resultset
			ResultSet rs = preparedStmt.executeQuery();

			// iterate through the java resultset
			while (rs.next()) {
				PreOrderList obj = new PreOrderList();
				
				obj.setUrl(rs.getString("url"));
				obj.setFirstOption(rs.getString("firstOption"));
				obj.setSecondOption(rs.getString("secondOption"));
				obj.setQty(rs.getString("qty"));

				return obj;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
