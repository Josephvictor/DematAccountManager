package com.amazon.dmataccountmanager.DB;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	
	Connection connection;
	Statement statement;
	
	private static DB db = new DB();
	
	public static DB getInstance() {
		return db;
	}
	
	private DB() {
		
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("[DB] Driver Loaded Successfully");
			
			createConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createConnection() {
		
        try {
        	
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Demat_Account;user=sa;password=1234567890;trustServerCertificate=true";

			connection = DriverManager.getConnection(url);
			System.out.println("[DB] Connection created Successfully");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int executeSQL(String sql) {
		
		int result = 0;
		
		try {
			
			//System.out.println("[DB] Executing SQL statement : "+sql);
			statement = connection.createStatement();
			
			result = statement.executeUpdate(sql);
			
			//System.out.println("[DB] SQL command executed successfully");
			
		}catch(Exception e) {
			System.out.println("Something Went Wrong "+e);
		}
		
		return result;
	}
	
	public ResultSet executeQuery(String sql) {
		
		ResultSet set = null;
		
		try {
			
			//System.out.println("[DB] Executing SQL statement : "+sql);
			statement = connection.createStatement();
			
			set = statement.executeQuery(sql);
			
			if(set != null) {
				//System.out.println("[DB] SQL command executed successfully");
			}
		}catch(Exception e) {
			System.out.println("Something Went Wrong "+e);
		}
		
		return set;
	}
	
	public void closeConnection() {
		
		try {
			connection.close();
			System.out.println("[DB] Connection closed.");
		}catch(Exception e) {
			System.out.println("Something Went Wrong "+e);
		}
	}
	
}
