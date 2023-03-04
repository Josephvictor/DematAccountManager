package com.amazon.dmataccountmanager.Services;

import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.Config;
import com.amazon.dmataccountmanager.DAO.ShareDAO;
import com.amazon.dmataccountmanager.DAO.TransactionLogDAO;
import com.amazon.dmataccountmanager.DAO.UserSharesDAO;
import com.amazon.dmataccountmanager.DAO.UsersDAO;
import com.amazon.dmataccountmanager.model.Share;
import com.amazon.dmataccountmanager.model.TransactionLog;
import com.amazon.dmataccountmanager.model.UserShares;
import com.amazon.dmataccountmanager.model.Users;

public class TransactionService {
	
	UsersDAO usersDAO = new UsersDAO();
	TransactionLogDAO transactionLogDAO = new TransactionLogDAO();
	ShareDAO shareDAO = new ShareDAO();
	UserSharesDAO userSharesDAO = new UserSharesDAO();
	UserServices userServices = UserServices.getInstance();
	ShareService shareService = ShareService.getInstance();
	Scanner scanner = new Scanner(System.in);
	
	private TransactionService() {
		
	}
	
	private static TransactionService transactionService = new TransactionService();
	
	public static TransactionService getInstance() {
		return transactionService;
	}
	
	public void buyTransaction(Users user) {
		
		shareService.viewAllShares();
		
		System.out.println("Enter the TICKER SYMBOL:");
		String tickerSymbol = scanner.nextLine();
		
		
		String sql = "SELECT * FROM Share WHERE tickerSymbol = '"+tickerSymbol+"'";
		List<Share> shareObjects = shareDAO.retrieve(sql);
		
		if(shareObjects.size() > 0) {
			
			Share share = new Share();
			share.id = shareObjects.get(0).id;
			share.companyName = shareObjects.get(0).companyName;
			share.tickerSymbol = shareObjects.get(0).tickerSymbol;
			share.price = shareObjects.get(0).price;
			share.numberOfShares = shareObjects.get(0).numberOfShares;
			share.marketCap = shareObjects.get(0).marketCap;
			
			System.out.println("Enter the No of Shares to buy:");
			int quantity = Integer.parseInt(scanner.nextLine());
			
			TransactionLog transactionLog = createTransactionLog(user,share,quantity,1);
			//double totalPrice = share.price * quantity;

			if(user.accountBalance > transactionLog.totalAmount ) {
				
				String getUserSharesByAccNum = "SELECT * FROM UserShares WHERE userAccNum = "+user.accountNumber+" AND shareId = "+share.id+"";
				List<UserShares> userSharesobjects = userSharesDAO.retrieve(getUserSharesByAccNum);

				if(userSharesobjects.size() > 0) {
					System.out.println("***UserShare Updation***");
					
					UserShares userShares = new UserShares();
					userShares.id = userSharesobjects.get(0).id;
					userShares.userAccNum = userSharesobjects.get(0).userAccNum;
					userShares.shareId = userSharesobjects.get(0).shareId;
					userShares.companyName = userSharesobjects.get(0).companyName;
					userShares.shareCount = userSharesobjects.get(0).shareCount + quantity;
					double newBuyPrice = averagingBuyPrice(share,userSharesobjects,quantity); // MAKE AVERAGING
					userShares.buyPrice = newBuyPrice;
					
					int updateUserShare = userSharesDAO.update(userShares);
					if(updateUserShare > 0) {
						System.out.println("----UserShares details Updated---");
						int result = transactionLogDAO.insert(transactionLog);
						if(result > 0) {
							System.out.println("Transaction log created");
						}
						user.accountBalance = user.accountBalance - transactionLog.totalAmount;
						int balanceUpdation = usersDAO.update(user);
						if(balanceUpdation > 0) {
							System.out.println("Amount:Rs "+transactionLog.totalAmount+"has been deducted and your remaining account balance: Rs"+user.accountBalance);
						}else {
							System.err.println("Amount deduction failed.");
						}
					}else {
						System.err.println("UserShares updation failed :(");
					}
				}else {
					System.out.println("***UserShare Insertion***");
					
					UserShares userShares = new UserShares();
					userShares.userAccNum = user.accountNumber;
					userShares.shareId = share.id;
					userShares.companyName = share.companyName;
					userShares.shareCount = quantity;
					userShares.buyPrice = share.price;
					
					int insertUserShare = userSharesDAO.insert(userShares);
					if(insertUserShare > 0) {
						System.out.println("***UserShares details inserted***");
						int result = transactionLogDAO.insert(transactionLog);
						if(result > 0) {
							System.out.println("Transaction log created");
						}
						user.accountBalance = user.accountBalance - transactionLog.totalAmount;
						int balanceUpdation = usersDAO.update(user);
						if(balanceUpdation > 0) {
							System.out.println("Amount:Rs "+transactionLog.totalAmount+"has been deducted and your remaining account balance: Rs"+user.accountBalance);
						}else {
							System.err.println("Amount deduction failed.");
						}
					}else {
						System.err.println("UserShares insertion failed :(");
					}
				}
		}else {
				System.err.println("Your Account Balance is not sufficient to carry out this transaction by:RS"+(transactionLog.totalAmount-user.accountBalance));
				System.out.println("Press '1' to navigate to add balance and Try Again or Press A to Exit");
				int choice = Integer.parseInt(scanner.nextLine());
				
				if(choice == 1) {
					userServices.depositMoney(user);
					buyTransaction(user);
				}else {
					System.out.println("Going back....");
				}
			}
			
			
			
		}else {
			System.err.println("Invalid TICKER SYMBOL");
		}
	}
	
	public void sellTransaction(Users user) {
		
		userServices.viewUserShares(user.accountNumber);
		
		System.out.println("Enter the TICKER SYMBOL:");
		String tickerSymbol = scanner.nextLine();
		
		
		String shareSql = "SELECT * FROM Share WHERE tickerSymbol = '"+tickerSymbol+"'";
		List<Share> shareObjects = shareDAO.retrieve(shareSql);
		
		if(shareObjects.size() > 0) {
			
			Share share = new Share();
			share.id = shareObjects.get(0).id;
			share.companyName = shareObjects.get(0).companyName;
			share.tickerSymbol = shareObjects.get(0).tickerSymbol;
			share.price = shareObjects.get(0).price;
			share.numberOfShares = shareObjects.get(0).numberOfShares;
			share.marketCap = shareObjects.get(0).marketCap;
			
			System.out.println("Please enter the Share quantity to sell:");
			int quantity = Integer.parseInt(scanner.nextLine());
			
			//double totalSellAmount = share.price * quantity;
			
			String userShareSql = "SELECT * FROM UserShares WHERE userAccNum = "+user.accountNumber+" AND shareId = "+share.id+"";
			List<UserShares> userSharesObjects = userSharesDAO.retrieve(userShareSql);
			
			if(userSharesObjects.size() > 0) {
				
				UserShares userShares = new UserShares();
				userShares.id = userSharesObjects.get(0).id;
				userShares.userAccNum = userSharesObjects.get(0).userAccNum;
				userShares.shareId = userSharesObjects.get(0).shareId;
				userShares.companyName = userSharesObjects.get(0).companyName;
				userShares.shareCount = userSharesObjects.get(0).shareCount;
				userShares.buyPrice = userSharesObjects.get(0).buyPrice;
				userShares.currentPrice = userSharesObjects.get(0).currentPrice;
				userShares.profitLoss = userSharesObjects.get(0).profitLoss;
				
				if(quantity < userShares.shareCount) {
					
					userShares.shareCount = userShares.shareCount - quantity;
					int updateuserShares = userSharesDAO.update(userShares);
					if(updateuserShares > 0) {
					TransactionLog transactionLog = createTransactionLog(user,share,quantity,2);
					int transactionLogResult = transactionLogDAO.insert(transactionLog);
						if(transactionLogResult > 0) {
							System.out.println("Transaction Log created");
							user.accountBalance = user.accountBalance + transactionLog.totalAmount;
							int balanceUpdation = usersDAO.update(user);
							if(balanceUpdation > 0) {
								System.out.println("Account balance has been updated successfully");
							}else {
								System.err.println("Account balance updation failed");
							}
						}else {
							System.err.println("Failed to create transaction log");
						}
					}else {
						System.err.println("UserShare sell updation failed");
					}
				}else if (quantity == userShares.shareCount) {
					int deleteUserShares = userSharesDAO.delete(userShares);
					if(deleteUserShares > 0) {
						TransactionLog transactionLog = createTransactionLog(user,share,quantity,2);
						int transactionLogResult = transactionLogDAO.insert(transactionLog);
						if(transactionLogResult > 0) {
							System.out.println("Transaction Log created");
							user.accountBalance = user.accountBalance + transactionLog.totalAmount;
							int balanceUpdation = usersDAO.update(user);
							if(balanceUpdation > 0) {
								System.out.println("Account balance has been updated successfully");
							}else {
								System.err.println("Account balance updation failed");
							}
				
						}else {
							System.err.println("Failed to create transaction log");
						}
					}else {
						System.err.println("UserShare sell deletion failed");
					}
				}else {
					System.err.println("Quantity is invalid number");
				}
			}
		}else {
			System.err.println("No shares in this Ticker Symbol:"+tickerSymbol);
		}
		
		
	}
	
	private TransactionLog createTransactionLog(Users user, Share share, int quantity,int type) {
		
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.userAccNum = user.accountNumber;
		transactionLog.shareId = share.id;
		transactionLog.shareCount = quantity;
		transactionLog.type = type;
		transactionLog.pricePerShare = share.price;
		transactionLog.totalSharePrice = transactionLog.pricePerShare * transactionLog.shareCount;
		
		transactionLog.transactionCharges = transactionLog.totalSharePrice * (Config.TransactionCharge/100);
		if(transactionLog.transactionCharges < Config.MinimumTransactionCharge) {
			transactionLog.transactionCharges = Config.MinimumTransactionCharge;
		}
		
		transactionLog.sttCharges = transactionLog.totalSharePrice * (Config.SecuritiesTransferTax/100);
		transactionLog.totalCharges = transactionLog.transactionCharges + transactionLog.sttCharges;
		if(type==1) {
			transactionLog.totalAmount = transactionLog.totalCharges + transactionLog.totalSharePrice;
		}else if(type == 2){
			transactionLog.totalAmount = transactionLog.totalSharePrice - transactionLog.totalCharges;
		}
		return transactionLog;
	}
	
	private double averagingBuyPrice(Share share,List<UserShares> userSharesobjects,int quantity) {
		
		double averageBuyPrice = 0;
		
		int oldShareCount = userSharesobjects.get(0).shareCount;
		int newShareCount = quantity;
		double oldSharePrice = userSharesobjects.get(0).buyPrice;
		double newSharePrice = share.price;
		
		averageBuyPrice = (oldShareCount * oldSharePrice) + (newShareCount * newSharePrice) / (newShareCount + oldShareCount);
		
		return averageBuyPrice;
	}

}
