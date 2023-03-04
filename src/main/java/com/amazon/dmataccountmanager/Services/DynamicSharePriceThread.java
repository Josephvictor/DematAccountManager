package com.amazon.dmataccountmanager.Services;

import java.util.List;
import com.amazon.dmataccountmanager.DAO.ShareDAO;
import com.amazon.dmataccountmanager.model.Share;

public class DynamicSharePriceThread extends Thread{
	
	ShareDAO shareDAO = new ShareDAO();
	ShareService shareService = ShareService.getInstance();
	
	public static volatile boolean threadExit = false;
	
	public void run() {
		System.out.println("Thread Started");
		while(!threadExit) {
			List<Share> objects = shareDAO.retrieve();
			if(objects.size() > 0) {
				for(Share object : objects) {
					
					Share share = new Share();
					share.id = object.id;
					share.companyName = object.companyName;
					share.tickerSymbol = object.tickerSymbol;
					share.price = object.price;
					share.numberOfShares = object.numberOfShares;
					share.marketCap = object.marketCap;
					
					shareService.dynamicPrice(share);
			}
				
			}else {
				System.err.println("No shares found..");
				exitThread();
			}
			try {
				Thread.sleep(5000);
			}catch(Exception e) {
				System.err.println("Something went wrong");
			}
		}
	}
	
	public void exitThread() {
		threadExit = true;
	}
}
