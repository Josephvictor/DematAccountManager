package com.amazon.dmataccountmanager.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amazon.dmataccountmanager.DB.DB;
import com.amazon.dmataccountmanager.model.TransactionLog;

public class TransactionLogDAO implements DAO<TransactionLog> {
	
	DB db = DB.getInstance();

	public int insert(TransactionLog object) {
		String sql = "INSERT INTO TransactionLog (userAccNum,shareId,shareCount,type,pricePerShare,totalSharePrice,transactionCharges,sttCharges,totalCharges,totalAmount) VALUES ("+object.userAccNum+","+object.shareId+","+object.shareCount+","+object.type+","+object.pricePerShare+","+object.totalSharePrice+","+object.transactionCharges+","+object.sttCharges+","+object.totalCharges+","+object.totalAmount+")";
		return db.executeSQL(sql);
	}

	public int update(TransactionLog object) {
		//String sql = "UPDATE TransactionLog SET ";
		//return db.executeSQL(sql);
		return 0;
	}

	public int delete(TransactionLog object) {
		String sql = "DELETE FROM TransactionLog WHERE id = "+object.id+"";
		return db.executeSQL(sql);
	}

	public List<TransactionLog> retrieve() {
		
		ArrayList<TransactionLog> objects = new ArrayList<TransactionLog>();
		
		String sql = "SELECT * FROM TransactionLog";
		
		ResultSet set = db.executeQuery(sql);
		
		try {
			
			while(set.next()) {
				
				TransactionLog object = new TransactionLog();
				
				object.id = set.getInt("id");
				object.userAccNum = set.getInt("userAccNum");
				object.shareId = set.getInt("shareId");
				object.shareCount = set.getInt("shareCount");
				object.type = set.getInt("type");
				object.pricePerShare = set.getInt("pricePerShare");
				object.totalSharePrice = set.getInt("totalSharePrice");
				object.transactionCharges = set.getInt("transactionCharges");
				object.sttCharges = set.getInt("sttCharges");
				object.totalCharges = set.getInt("totalCharges");
				object.totalAmount = set.getInt("totalAmount");
				object.transactedOn = set.getString("transactedOn");
				
				objects.add(object);
			}
			
		} catch (SQLException e) {
			System.out.println("Something Went Wrong "+e);
		}
		
		return objects;
	}

	public List<TransactionLog> retrieve(String sql) {
		
		ArrayList<TransactionLog> objects = new ArrayList<TransactionLog>();
		
		ResultSet set = db.executeQuery(sql);
		
		try {
			
			while(set.next()) {
				
				TransactionLog object = new TransactionLog();
				
				object.id = set.getInt("id");
				object.userAccNum = set.getInt("userAccNum");
				object.shareId = set.getInt("shareId");
				object.shareCount = set.getInt("shareCount");
				object.type = set.getInt("type");
				object.pricePerShare = set.getInt("pricePerShare");
				object.totalSharePrice = set.getInt("totalSharePrice");
				object.transactionCharges = set.getInt("transactionCharges");
				object.sttCharges = set.getInt("sttCharges");
				object.totalCharges = set.getInt("totalCharges");
				object.totalAmount = set.getInt("totalAmount");
				object.transactedOn = set.getString("transactedOn");
				
				objects.add(object);
			}
			
		} catch (SQLException e) {
			System.out.println("Something Went Wrong "+e);
		}
		
		return objects;
	}

}
