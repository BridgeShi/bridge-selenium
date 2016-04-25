package com.bridge.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmazonDAO {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;
	
	private String updatequery = "update amazon_pro_inv set inventory = ?,timestamp = ? where producturl = ?";
	
	public List<String> getUrlList(int rows) {
		
		String query = "SELECT producturl FROM amazon_pro_inv order by timestamp desc LIMIT ?";
		
		try {
			Connection conn;

			conn = DBManager.getConnection();

			PreparedStatement preparedStmt = null;
			
			preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, rows);

			// execute the query, and get a java resultset
			ResultSet rs = preparedStmt.executeQuery();

			// iterate through the java resultset
			
			List<String> list = new ArrayList<String>();
			
			while (rs.next()) {
				list.add(rs.getString("producturl"));
			}
			
			return list;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void update(String inventory, String url) {

		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			
			preparedStmt = conn.prepareStatement(updatequery);
			preparedStmt.setString (1, inventory);
			preparedStmt.setString(2, df.format(new Date()));
			preparedStmt.setString (3, url);

			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
