package com.bridge.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {

	public Account getAccount(String type) {
		
		String query = "SELECT account, password FROM TB_1688_Accounts WHERE type= ? ";
		
		try {
			Connection conn;

			conn = DBManager.getConnection();

			PreparedStatement preparedStmt = null;
			
			preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, type);

			// execute the query, and get a java resultset
			ResultSet rs = preparedStmt.executeQuery();

			// iterate through the java resultset
			while (rs.next()) {
				Account accountobj = new Account();
				
				accountobj.setAccount(rs.getString("account"));
				accountobj.setPassword(rs.getString("password"));

				return accountobj;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
