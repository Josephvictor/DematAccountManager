package com.amazon.dmataccountmanager.model;

import java.util.Scanner;

import com.amazon.dmataccountmanager.Services.UserServices;

/*
 	CREATE TABLE User(
 		accountNumber INT PRIMARY KEY IDENTITY(1,1),
 		userName NVARCHAR(50) NOT NULL UNIQUE,
 		password NVARCHAR NOT NULL,
 		accountBalance INT,
 		lastUpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP
 	)
 */

public class Users {
	
	Scanner scanner = new Scanner(System.in);
	UserServices userServices = UserServices.getInstance();
	
	public String userName;
	public int accountNumber;
	public String password;
	public double accountBalance;
	public String lastUpdatedOn;
	
	public Users() {
	}
	
	public void setDetails(boolean updateMode) {
		
		
		
		if(!updateMode) {
			System.out.println("Please enter your Email address (Will be your User Name) : ");
			userName = scanner.nextLine();
		}
		
		if(updateMode) {
			System.out.println("Enter your accountNumber");
			accountNumber = scanner.nextInt();
		}
		
		System.out.println("Enter Password : ");
		password = scanner.nextLine();
		
	}
	
	public void getDetails() {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Name:\t\t\t"+userName);
		System.out.println("Account Number:\t\t"+accountNumber);
		System.out.println("Account Balance:\t"+accountBalance);
		userServices.viewUserShares(accountNumber);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", accountNumber=" + accountNumber + ", password="
				+ password + ", accountBalance=" + accountBalance + ", lastUpdatedOn=" + lastUpdatedOn + "]";
	}
	
}
