package com.amazon.dmataccountmanager.Services;

import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.DAO.UsersDAO;
import com.amazon.dmataccountmanager.model.Users;


public class AuthenticationService {
	Scanner scanner = new Scanner(System.in);
	UsersDAO userDAO = new UsersDAO();
	
	private static AuthenticationService authenticationService = new AuthenticationService();
	
	public static AuthenticationService getInstance() {
		return authenticationService;
	}
	
	private AuthenticationService() {
		
	}
	
	public Users createAccount() {
		
		Users object = new Users();
		
		object.setDetails(false);
		
		int result = userDAO.insert(object);
		
		if(result > 0) {
			System.out.println("Account created successfully :)");
			String sql = "SELECT * FROM Users WHERE userName = '"+object.userName+"'";
			List<Users> objects = userDAO.retrieve(sql);
			
			if(objects.size() > 0) {
				
				object.accountNumber = objects.get(0).accountNumber;
				object.accountBalance = objects.get(0).accountBalance;
				object.lastUpdatedOn = objects.get(0).lastUpdatedOn;
				return object;
			}
		}else 
			System.err.println("Account creation failed :(");
		
		return null;
	}
	
	public Users login() {
		
		Users object = new Users();
		
		System.out.println("Enter the Account Number :");
		object.accountNumber = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Enter the Password :");
		object.password = scanner.nextLine();
		
		String sql = "SELECT * FROM Users WHERE accountNumber = '"+object.accountNumber+"' AND password = '"+object.password+"'";
		List<Users> objects = userDAO.retrieve(sql);
		
		if(objects.size() > 0) {
			
			object.userName = objects.get(0).userName;
			object.accountNumber = objects.get(0).accountNumber;
			object.password = objects.get(0).password;
			object.accountBalance = objects.get(0).accountBalance;
			object.lastUpdatedOn = objects.get(0).lastUpdatedOn;
			return object;
		}
		return null;
	}
}
