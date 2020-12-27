package de.eberln.trade;

import java.util.UUID;

public class TradeStatusResetter implements Runnable{

	private TradePlayer target;
	private TradePlayer requester;
	private Trade t;
	
	public boolean resetStatus;
	
	public TradeStatusResetter(TradePlayer requester, TradePlayer target) {
		
		this.requester = requester;
		this.target = target;
		resetStatus = true;
	}
	
	public void run() {
		
		try {
			Thread.sleep(120*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(resetStatus) {
			
			requester.player.sendMessage("Deine Anfrage an " + target.player.getDisplayName() + " ist abgelaufen");
			target.player.sendMessage("Die an dich gerichtete Anfrage von " + requester.player.getDisplayName() + " ist abgelaufen");
			
			if(requester != null) {
				requester.setTradeStatus(false);
			}
			if(target != null) {
				target.setTradeStatus(false);
				target.setRequestStatus(false);
			}
		}
	}	
}
