package com.amazon.dmataccountmanager;

import java.util.Scanner;

import com.amazon.dmataccountmanager.Services.ShareService;
import com.amazon.dmataccountmanager.Services.TransactionService;
import com.amazon.dmataccountmanager.Services.UserServices;
import com.amazon.dmataccountmanager.model.Users;

public class Menu {
	
	Scanner scanner = new Scanner(System.in);
	UserServices userServices = UserServices.getInstance();
	TransactionService transactionService = TransactionService.getInstance();
	ShareService shareService = ShareService.getInstance();
	
	public void showMenu(Users user) {
		
		boolean exit = false;
		
		while(true) {
			
			System.out.println("1 - Display Demat Account Details");
			System.out.println("2 - Deposit Money");
			System.out.println("3 - Withdraw Money");
			System.out.println("4 - Buy Shares");
			System.out.println("5 - Sell Shares");
			System.out.println("6 - View Transaction Report");
			System.out.println("0 - Quit");
			System.out.println();
			System.out.println("Select any Option to Proceed");
			
			int choice = Integer.parseInt(scanner.nextLine());
			
			switch(choice) {
			case 0:
				System.out.println("Thanks for Visiting.. Come Back Again..");
				exit = true;
				break;
			case 1:
				userServices.getAccountDetails(user);
				break;
			case 2:
				userServices.depositMoney(user);
				break;
			case 3:
				userServices.withdrawMoney(user);
				break;
			case 4:
				transactionService.buyTransaction(user);
				break;
			case 5:
				transactionService.sellTransaction(user);
				break;
			case 6:
				System.out.println("1. View Transaction Report between given date range");
				System.out.println("2. View Transaction Report for a given Company");
				int transanctionChoice = Integer.parseInt(scanner.nextLine());
				shareService.getTransactionLog(transanctionChoice,user);
				break;
			default:
				break;
			}
			
			if(exit) {
				break;
			}
		}
	}
}
