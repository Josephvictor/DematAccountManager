package com.amazon.dmataccountmanager.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.dmataccountmanager.DB.DB;
import com.amazon.dmataccountmanager.model.UserShares;

public class UserSharesDAO implements DAO<UserShares> {
	
	DB db = DB.getInstance();

	public int insert(UserShares object) {
		String sql = "INSERT INTO UserShares (userAccNum,shareId,companyName,shareCount,buyPrice,currentPrice,profitLoss) VALUES ("+object.userAccNum+","+object.shareId+",'"+object.companyName+"', "+object.shareCount+","+object.buyPrice+","+object.currentPrice+","+object.profitLoss+")";
		return db.executeSQL(sql);
	}

	public int update(UserShares object) {
		String sql = "UPDATE UserShares SET userAccNum = "+object.userAccNum+", shareId = "+object.shareId+", companyName = '"+object.companyName+"', shareCount = "+object.shareCount+" WHERE id = "+object.id+"";
		return db.executeSQL(sql);
	}
	
	public int dynamicPriceUpdation(int shareId, double profitLoss, double livePrice) {
		String sql = "UPDATE UserShares SET currentPrice = "+livePrice+", profitLoss = "+profitLoss+" WHERE shareId = "+shareId+"";
		return db.executeSQL(sql);
	}
	
	public int update(int id, double currentPrice, double profitLoss) {
		String sql = "UPDATE UserShares SET currentPrice = "+currentPrice+", profitLoss = "+profitLoss+" WHERE shareId = "+id+"";
		return db.executeSQL(sql);
	}

	public int delete(UserShares object) {
		String sql = "DELETE FROM UserShares WHERE id = "+object.id+"";
		return db.executeSQL(sql);
	}

	public List<UserShares> retrieve() {
		
		ArrayList<UserShares> objects = new ArrayList<UserShares>();
		
		String sql = "SELECT * FROM UserShares";
		ResultSet set = db.executeQuery(sql);
		
		try {
			while(set.next()) {
				
				UserShares object = new UserShares();
				object.id = set.getInt("id");
				object.userAccNum = set.getInt("userAccNum");
				object.shareId = set.getInt("shareId");
				object.companyName = set.getString("companyName");
				object.shareCount = set.getInt("shareCount");
				
				objects.add(object);
			}
		}
		catch(Exception e) {
			
		}
		return objects;
	}

	public List<UserShares> retrieve(String sql) {
		
		ArrayList<UserShares> objects = new ArrayList<UserShares>();
		
		ResultSet set = db.executeQuery(sql);
		
		try {
			while(set.next()) {
				
				UserShares object = new UserShares();
				object.id = set.getInt("id");
				object.userAccNum = set.getInt("userAccNum");
				object.shareId = set.getInt("shareId");
				object.companyName = set.getString("companyName");
				object.shareCount = set.getInt("shareCount");
				object.buyPrice = set.getDouble("buyPrice");
				object.currentPrice = set.getDouble("currentPrice");
				object.profitLoss = set.getDouble("profitLoss");
				objects.add(object);
			}
		}
		catch(Exception e) {
			
		}
		return objects;
	}

}
