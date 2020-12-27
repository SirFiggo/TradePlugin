package de.eberln.trade;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TradePlayerController {

	private Map<UUID, TradePlayer> tradePlayers;
	
	public TradePlayerController() {
		
		tradePlayers = new HashMap<UUID, TradePlayer>();
		
	}
	
	public void addTradePlayer(UUID id) {
		tradePlayers.put(id, new TradePlayer(id));
	}
	
	public void removeTradePlayer(UUID id) {
		tradePlayers.remove(id);
	}
	
	public TradePlayer getTradePlayer(UUID id) {
		return tradePlayers.get(id);
	}
	
}
