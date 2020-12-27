package de.eberln.trade;

import java.util.UUID;

public class TradeStatusResetter implements Runnable{

	private TradePlayer target;
	private TradePlayer requester;
	private Trade t;
	
	public boolean resetStatus;
	
	public TradeStatusResetter(TradePlayer requester, TradePlayer target, Trade t) {
		
		this.requester = requester;
		this.target = target;
		resetStatus = true;
		
		this.t = t;
		
	}
	
	public void run() {
		
		try {
			Thread.sleep(120*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.getLogger().info(resetStatus + "");
		
		if(resetStatus) {
			if(requester != null) {
				requester.setTradeStatus(false);
			}
			if(target != null) {
				target.setTradeStatus(false);
				target.setRequestStatus(false);
			}
		}
		t.getLogger().info("Thread stopped");
		
		
	}
	
}
