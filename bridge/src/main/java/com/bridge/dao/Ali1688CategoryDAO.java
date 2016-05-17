package com.bridge.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class Ali1688CategoryDAO {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;

	public void insertFirstLevel(Object[] categoryDate) {

		String sql = "insert into ali1688_first_level_category "
				+ "(maincategory, fisrtlevelcategoryname, firstlevelcategoryitem, url, totalproducts)"
				+ " values (?, ?, ?, ?, ?)";
		
		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(sql);
			ArrayList<String[]> categoryList = (ArrayList<String[]>) categoryDate[1];
			preparedStmt.setString (1, categoryDate[0].toString());
			preparedStmt.setString (2, categoryList.get(0)[0]);
			preparedStmt.setString (3, categoryList.get(0)[1]);
			preparedStmt.setString (4, categoryDate[2].toString());
			preparedStmt.setInt (5, (Integer) categoryDate[3]);
			
			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void insertSecondLevel(Object[] categoryData) {

		String sql = "insert into ali1688_second_level_category "
				+ "(maincategory, fisrtlevelcategoryname, firstlevelcategoryitem,secondlevelcategoryname,"
				+ " secondlevelcategoryitem, url, totalproducts,"
				+ " keywords, categoryId, categoryName, featureId)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(sql);
			List<String[]> categoryList = (ArrayList<String[]>) categoryData[1];
			String pageConfig[] = (String[]) categoryData[4];
			preparedStmt.setString (1, categoryData[0].toString());
			preparedStmt.setString (2, categoryList.get(0)[0]);
			preparedStmt.setString (3, categoryList.get(0)[1]);
			preparedStmt.setString (4, categoryList.get(1)[0]);
			preparedStmt.setString (5, categoryList.get(1)[1]);
			preparedStmt.setString (6, categoryData[2].toString());
			preparedStmt.setInt (7, (Integer) categoryData[3]);
			preparedStmt.setString (8, pageConfig[0]);
			preparedStmt.setString (9, pageConfig[1]);
			preparedStmt.setString (10, pageConfig[2]);
			preparedStmt.setString (11, pageConfig[3]);
			
			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertThirdLevel(Object[] categoryData) {

		String sql = "insert into ali1688_third_level_category "
				+ "(maincategory, fisrtlevelcategoryname, firstlevelcategoryitem,"
				+ " secondlevelcategoryname, secondlevelcategoryitem,"
				+ " thirdlevelcategoryname, thirdlevelcategoryitem, url, totalproducts,"
				+ " keywords, categoryId, categoryName, featureId)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
				
		try {
			conn = DBManager.getConnection();
			
			String query_set_utf8 = "set names utf8";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(query_set_utf8);
			
			preparedStmt = conn.prepareStatement(sql);
			List<String[]> categoryList = (ArrayList<String[]>) categoryData[1];
			String pageConfig[] = (String[]) categoryData[4];

			preparedStmt.setString (1, categoryData[0].toString());
			preparedStmt.setString (2, categoryList.get(0)[0]);
			preparedStmt.setString (3, categoryList.get(0)[1]);
			preparedStmt.setString (4, categoryList.get(1)[0]);
			preparedStmt.setString (5, categoryList.get(1)[1]);
			preparedStmt.setString (6, categoryList.get(2)[0]);
			preparedStmt.setString (7, categoryList.get(2)[1]);
			preparedStmt.setString (8, categoryData[2].toString());
			preparedStmt.setInt (9, (Integer) categoryData[3]);
			preparedStmt.setString (10, pageConfig[0]);
			preparedStmt.setString (11, pageConfig[1]);
			preparedStmt.setString (12, pageConfig[2]);
			preparedStmt.setString (13, pageConfig[3]);
			preparedStmt.execute();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
