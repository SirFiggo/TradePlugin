package de.eberln.trade;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TradePlayer{

	public Player player;
	
	public boolean isBlocked;
	public boolean isRequested;
	public boolean isCurrentlyinInv;
	
	public TradeStatusResetter tSR;
	
	public TradePlayer partner;
	
	public TradePlayer(UUID id) {
		player = Bukkit.getPlayer(id);
		isBlocked = false;
		isRequested = false;
		isCurrentlyinInv = false;
		tSR = null;
		partner = null;
	}
	
	public void setTradeStatus(boolean isInTrade) {
		isBlocked = isInTrade;
	}
	
	public void setRequestStatus(boolean receivedRequest) {
		isRequested = receivedRequest;
	}
	
	public void setTradeStatusResetter(TradeStatusResetter t) {
		tSR = t;
	}
	
	public void setPartner(TradePlayer t) {
		partner = t;
	}
	
	public void setCurrentStatus(boolean isInInv) {
		isCurrentlyinInv = isInInv;
	}
	
}
