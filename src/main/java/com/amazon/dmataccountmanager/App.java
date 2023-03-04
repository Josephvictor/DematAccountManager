package com.amazon.dmataccountmanager;

import java.util.Scanner;

import com.amazon.dmataccountmanager.DB.DB;
import com.amazon.dmataccountmanager.Services.AuthenticationService;
import com.amazon.dmataccountmanager.Services.DynamicSharePriceThread;
import com.amazon.dmataccountmanager.model.Users;

public class App 
{
    public static void main( String[] args )
    {
    	DB db = DB.getInstance();
    	Scanner scanner = new Scanner(System.in);
    	Menu menu = new Menu();
    	AuthenticationService authenticationService = AuthenticationService.getInstance();
    	
    	DynamicSharePriceThread thread = new DynamicSharePriceThread();
    	thread.setDaemon(true);
    	thread.start();

        Users user = new Users();
        boolean exit = false;
        
        while(!exit) {
        	System.out.println("****************************");
            System.out.println("Welcome to Demat Account");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("0. Exit");
        	System.out.println("****************************");
        	System.out.println("Enter your choice:");
        	int choice = Integer.parseInt(scanner.nextLine());
			switch(choice) {
			case 0:
				System.out.println("Thanks for Visiting.. Come Back Again..");
				exit = true;
				
				try {
					DynamicSharePriceThread.threadExit = true;
					thread.join();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				scanner.close();
				db.closeConnection();
				break;
			case 1:
				user = authenticationService.createAccount();
				if (user != null) {
					System.out.println("Logging in..");
					menu.showMenu(user);
				}else 
					System.err.println("Login Failed");
				break;
			case 2:
				user = authenticationService.login();
				if (user != null) {
					System.out.println("Logged in Successfully!!");
					menu.showMenu(user);
				}else System.err.println("Login Failed");
				break;
			default:
				System.err.println("Wrong choice..");
				break;
			}
        }
    }
}
