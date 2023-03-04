package com.amazon.dmataccountmanager.model;

public class TransactionLog {
	
	public int id;
	public int userAccNum;
	public int shareId;
	public int shareCount;
	public int type;						//1.Buy 2.Sell
	public double pricePerShare;
	public double totalSharePrice; 			// pricePerShare * shareCount
	public double transactionCharges;
	public double sttCharges;
	public double totalCharges;				// transactionCharges + sttCharges
	public double totalAmount; 				// totalCharge + totalSharePrice
	public String transactedOn;
	
	public TransactionLog() {
	}
	
	public void getTransactionLog() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Transaction ID:\t\t"+id);
		System.out.println("Share ID:\t\t"+shareId);
		if(type == 1) {
			System.out.println("Transaction Type:\tBUY");
		}else {
			System.out.println("Transaction Type:\tSELL");
		}
		System.out.println("Share Count:\t\t"+shareCount);
		System.out.println("Price per Share:\t"+pricePerShare);
		System.out.println("Total Share price:\t"+totalSharePrice);
		System.out.println("Total Charges:\t\t"+totalCharges);
		System.out.println("Transaction Amount:\t"+totalAmount);
		System.out.println("Date of Transaction:\t"+transactedOn);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Override
	public String toString() {
		return "TransactionLog [id=" + id + ", userAccNum=" + userAccNum + ", shareId=" + shareId + ", shareCount="
				+ shareCount + ", type=" + type + ", pricePerShare=" + pricePerShare + ", totalSharePrice="
				+ totalSharePrice + ", transactionCharges=" + transactionCharges + ", sttCharges=" + sttCharges
				+ ", totalCharges=" + totalCharges + ", totalAmount=" + totalAmount + ", transactedOn=" + transactedOn
				+ "]";
	}

	
}
