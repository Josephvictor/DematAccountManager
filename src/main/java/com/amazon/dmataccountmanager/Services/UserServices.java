package com.amazon.dmataccountmanager.Services;

import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.DAO.UserSharesDAO;
import com.amazon.dmataccountmanager.DAO.UsersDAO;
import com.amazon.dmataccountmanager.model.UserShares;
import com.amazon.dmataccountmanager.model.Users;

public class UserServices {
	
	private UserServices() {
		
	}
	
	private static UserServices userServices = new UserServices();
	
	public static UserServices getInstance() {
		return userServices;
	}
	
	Scanner scanner = new Scanner(System.in);
	UsersDAO usersDAO = new UsersDAO();
	UserSharesDAO userSharesDAO = new UserSharesDAO();
	
	public void getAccountDetails(Users user) {
		
		user.getDetails();
	}
	
	public void depositMoney(Users user) {
		System.out.println("Please enter amount to be added");
		int amount = Integer.parseInt(scanner.nextLine());
		user.accountBalance += amount;
		
		int result = usersDAO.update(user);
		
		if(result > 0) {
			System.out.println("Deposit money Successfull");
		}else {
			System.err.println("Deposit money failed.");
		}
	}
	
	public void withdrawMoney(Users user) {
		System.out.println("Please enter the amount to be withdrawn:");
		int amount = Integer.parseInt(scanner.nextLine());
		user.accountBalance -= amount;
		
		int result = usersDAO.update(user);
		
		if(result > 0) {
			System.out.println("Amount successfully withdrawn");
		}else {
			System.err.println("Withdrawal failed");
		}
	}
	
	public void viewUserShares(int accountNumber) {
		
		String sql = "SELECT * FROM UserShares WHERE userAccNum = "+accountNumber+"";
		
		List<UserShares> objects = userSharesDAO.retrieve(sql);
		
		if(objects.size() > 0) {
			
			for(UserShares object : objects) {
				object.getDetails();
			}
		}else {
			System.err.println("Your Account has no shares.");
		}
	}

}
