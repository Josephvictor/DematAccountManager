package com.amazon.dmataccountmanager.model;

import com.amazon.dmataccountmanager.Services.ShareService;

public class UserShares {
	
	ShareService shareService = ShareService.getInstance();
	
	public int id;
	public int userAccNum;
	public int shareId;
	public String companyName;
	public int shareCount;
	public double buyPrice;
	public double currentPrice;
	public double profitLoss;
	
	public UserShares() {
	}
	
	public void getDetails() {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		//System.out.println("id:\t\t"+id);
		//System.out.println("User ACC:\t"+userAccNum);
		//System.out.println("Share ID:\t"+shareId);
		System.out.println("Company Name:\t"+companyName);
		
		Share share = shareService.getSingleShareDetails(shareId);
		System.out.println("Ticker SYMBOL:\t"+share.tickerSymbol);
		
		System.out.println("Share Count:\t"+shareCount);
		System.out.println("Buy Price:\t"+buyPrice);
		System.out.println("Current Price:\t"+currentPrice);
		System.out.println("Profit/Loss:\t"+profitLoss);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Override
	public String toString() {
		return "UserShares [id=" + id + ", userId=" + userAccNum + ", shareId=" + shareId + ", companyName=" + companyName
				+ ", shareCount=" + shareCount + ", buyPrice=" + buyPrice + ", currentPrice=" + currentPrice
				+ ", profitLoss=" + profitLoss + "]";
	}
}
