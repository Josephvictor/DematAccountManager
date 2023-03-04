package com.amazon.dmataccountmanager.model;

public class Share {
	
	public int id;
	public String companyName;
	public String tickerSymbol;
	public double price;
	public int numberOfShares;
	public double marketCap;
	public String lastUpdatedOn;
	
	public Share() {
		
	}
	
	
	public void getDetails() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		//System.out.println("Share ID:\t\t"+id);
		System.out.println("Company Name:\t"+companyName);
		System.out.println("Ticker Symbol:\t"+tickerSymbol);
		System.out.println("Total Floating Shares:\t"+numberOfShares);
		System.out.println("Price per Share:\t"+price);
		System.out.println("Market Capitalization:\t"+marketCap);
		//System.out.println("lastUpdatedOn:\t"+lastUpdatedOn);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

	}


	@Override
	public String toString() {
		return "Share [id=" + id + ", tickerSymbol=" + tickerSymbol + ", companyName=" + companyName + ", price="
				+ price + ", numberOfShares=" + numberOfShares + ", marketCap=" + marketCap + ", lastUpdatedOn="
				+ lastUpdatedOn + "]";
	}

}
