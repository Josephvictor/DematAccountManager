package com.amazon.dmataccountmanager.DAO;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.amazon.dmataccountmanager.DB.DB;
import com.amazon.dmataccountmanager.model.Share;

public class ShareDAO implements DAO<Share>{
	
	DB db = DB.getInstance();
	
	public int insert(Share object) {
		
		String sql = "INSERT INTO Share (tickerSymbol,companyName,price) VALUES ('"+object.tickerSymbol+"', '"+object.companyName+"', "+object.price+")";
		return db.executeSQL(sql);
	}

	public int update(Share object) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		object.lastUpdatedOn = dateFormatter.format(date);
		
		String sql = "UPDATE Share SET price = "+object.price+", numberOfShares = "+object.numberOfShares+",lastUpdatedOn = '"+object.lastUpdatedOn+"' WHERE id = "+object.id+"";
		return db.executeSQL(sql);
	}
	
	public int updatePrice(Share object) {
		
		String sql = "UPDATE Shares SET price = "+object.price+", lastUpdatedOn = 'CURRENT_TIMESTAMP' WHERE tickerSymbol = '"+object.tickerSymbol+"'";
		return db.executeSQL(sql);
	}

	public int delete(Share object) {
		
		//String sql = "DELETE FROM Share WHERE id = "+object.id+"";
		//return db.executeSQL(sql);
		return 0;
	}

	public List<Share> retrieve() {
		
		ArrayList<Share> objects = new ArrayList<Share>();
		
		String sql = "SELECT * FROM Share";
		ResultSet set = db.executeQuery(sql);
		
		try {
			while(set.next()) {
				
				Share object = new Share();
				object.id = set.getInt("id");
				object.tickerSymbol = set.getString("tickerSymbol");
				object.companyName = set.getString("companyName");
				object.price = set.getInt("price");
				object.numberOfShares = set.getInt("numberOfShares");
				object.marketCap = set.getInt("marketCap");
				object.lastUpdatedOn = set.getString("lastUpdatedOn");
				
				objects.add(object);
			}
		}
		catch(Exception e) {
			
		}
		return objects;
	}

	public List<Share> retrieve(String sql) {
		
		ArrayList<Share> objects = new ArrayList<Share>();
		
		ResultSet set = db.executeQuery(sql);
		
		try {
			while(set.next()) {
				
				Share object = new Share();
				object.id = set.getInt("id");
				object.tickerSymbol = set.getString("tickerSymbol");
				object.companyName = set.getString("companyName");
				object.price = set.getInt("price");
				object.marketCap = set.getInt("marketCap");
				object.lastUpdatedOn = set.getString("lastUpdatedOn");
				
				objects.add(object);
			}
		}
		catch(Exception e) {
			
		}
		return objects;
	}

}
