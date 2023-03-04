package com.amazon.dmataccountmanager.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amazon.dmataccountmanager.DB.DB;
import com.amazon.dmataccountmanager.model.Users;

public class UsersDAO implements DAO<Users> {
	
	DB db = DB.getInstance();

	public int insert(Users object) {
		
		String sql = "INSERT INTO Users (userName,password) VALUES ('"+object.userName+"', '"+object.password+"')";
		int result = db.executeSQL(sql);
		
		return result;
	}

	public int update(Users object) {
		String sql = "UPDATE Users SET accountBalance = "+object.accountBalance+",lastUpdatedOn = CURRENT_TIMESTAMP WHERE accountNumber = "+object.accountNumber+"";
		return db.executeSQL(sql);
	}

	public int delete(Users object) {
		String sql = "DELETE FROM Users WHERE id = "+object.accountNumber+"";
		return db.executeSQL(sql);
	}

	public List<Users> retrieve() {
		
		ArrayList<Users> objects = new ArrayList<Users>();
		
		String sql = "SELECT * FROM Users";
		
		ResultSet set = db.executeQuery(sql);
		
		try {
			
			while(set.next()) {
				
				Users object = new Users();
				object.userName = set.getString("userName");
				object.accountNumber = set.getInt("accountNumber");
				object.password = set.getString("password");
				object.accountBalance = set.getInt("accountBalance");
				object.lastUpdatedOn = set.getString("lastUpdatedOn");
				
				objects.add(object);
			}
			
		} catch (SQLException e) {
			System.out.println("Something Went Wrong "+e);
		}
		
		return objects;
	}

	public List<Users> retrieve(String sql) {
		
		ArrayList<Users> objects = new ArrayList<Users>();
		
		ResultSet set = db.executeQuery(sql);
		
		try {
			
			while(set.next()) {
				Users object = new Users();
				object.userName = set.getString("userName");
				object.accountNumber = set.getInt("accountNumber");
				object.password = set.getString("password");
				object.accountBalance = set.getInt("accountBalance");
				object.lastUpdatedOn = set.getString("lastUpdatedOn");
				//System.out.println(object.userName);
				objects.add(object);
			}
		}catch(Exception e) {
			System.out.println("Something went Wrong"+e);
		}
		return objects;
	}

}
