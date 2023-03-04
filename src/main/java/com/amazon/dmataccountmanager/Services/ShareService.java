package com.amazon.dmataccountmanager.Services;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.amazon.dmataccountmanager.DAO.ShareDAO;
import com.amazon.dmataccountmanager.DAO.TransactionLogDAO;
import com.amazon.dmataccountmanager.DAO.UserSharesDAO;
import com.amazon.dmataccountmanager.model.Share;
import com.amazon.dmataccountmanager.model.TransactionLog;
import com.amazon.dmataccountmanager.model.UserShares;
import com.amazon.dmataccountmanager.model.Users;

public class ShareService {
	
	UserSharesDAO userSharesDAO = new UserSharesDAO();
	TransactionLogDAO transactionLogDAO = new TransactionLogDAO();
	ShareDAO shareDAO = new ShareDAO();
	Random random = new Random();
	Scanner scanner = new Scanner(System.in);
	private ShareService() {
		
	}
	
	private static ShareService shareService = new ShareService();
	
	public static ShareService getInstance() {
		return shareService;
	}
	
	private double getRandomNumber() {
		
		double priceChange = random.nextDouble() * 10 - 5;
		
		return priceChange;
	}
	
	public void dynamicPrice(Share share) {
		
		double random = getRandomNumber();
		double livePrice = 0;
		if(share.price > 4) {
			share.price = share.price + random;
			share.marketCap = share.numberOfShares * share.price;
			shareDAO.update(share);
			livePrice = share.price;
		}
		
		String sql = "SELECT * FROM UserShares WHERE shareId = "+share.id+"";
		List<UserShares> objects = userSharesDAO.retrieve(sql);
		
		if(objects.size() > 0) {
			
			int shareCount = objects.get(0).shareCount;
			double buyPrice = objects.get(0).buyPrice;
			double profitLoss = (share.price - buyPrice) * shareCount;
			
			if(share.price > 4) {
				userSharesDAO.dynamicPriceUpdation(share.id,profitLoss,livePrice);
			}
		}
	}
	
	public void viewAllShares() {
		
		List<Share> objects = shareDAO.retrieve();
		
		if(objects.size() > 0) {
			for(Share object : objects) {
				object.getDetails();
			}
		}
	}
	
	public Share getSingleShareDetails(int shareId) {
		
		Share object = new Share();
		
		String sql = "SELECT * FROM Share WHERE id = "+shareId+"";
		List<Share> objects = shareDAO.retrieve(sql);
		
		if(objects.size() > 0) {
			object.id = objects.get(0).id;
			object.companyName = objects.get(0).companyName;
			object.tickerSymbol = objects.get(0).tickerSymbol;
			object.price = objects.get(0).price;
			object.numberOfShares = objects.get(0).numberOfShares;
		}
		return object;
	}
	
	public void getTransactionLog(int choice,Users user) {
		
		if(choice == 1) {
			System.out.println("Please enter the starting date range in the specified format: (yyyy-mm-dd)");
			String startRange = scanner.nextLine();
			System.out.println("Please enter the starting date range in the specified format: (yyyy-mm-dd)");
			String endRange = scanner.nextLine();
			
			startRange = startRange + " 00:00:00";
			endRange = endRange + " 23:59:59";
			//System.out.println(startRange +" "+endRange);
			
			String sql = "SELECT * FROM TransactionLog WHERE userAccNum = "+user.accountNumber+" AND transactedOn BETWEEN '"+startRange+"' AND '"+endRange+"'";
			List<TransactionLog> objects = transactionLogDAO.retrieve(sql);
			
			if(objects.size() > 0) {
				for(TransactionLog object : objects) {
					object.getTransactionLog();
				}
			}else {
				System.err.println("No Specific transactions done in the given date range!!");
			}
		}else if(choice == 2) {
			System.out.println("Enter the Ticker Symbol of the Company:");
			String tickerSymbol = scanner.nextLine();
			
			String sql = "SELECT * FROM Share WHERE tickerSymbol = '"+tickerSymbol+"'";
			List<Share> shares = shareDAO.retrieve(sql);
			
			int shareId = shares.get(0).id;
			String sql1 = "SELECT * FROM TransactionLog WHERE shareId = "+shareId+" AND userAccNum = "+user.accountNumber+"";
			
			List<TransactionLog> objects = transactionLogDAO.retrieve(sql1);
			
			if(objects.size() > 0) {
				for(TransactionLog object : objects) {
					object.getTransactionLog();
				}
			}else {
				System.err.println("No Specific transactions done in the given date range!!");
			}
		}
	}
}
