package de.eberln.trade;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TradePlayerJoinLeaveListener implements Listener{

	TradePlayerController tradePlayerController;
	
	public TradePlayerJoinLeaveListener(TradePlayerController tradePlayerController) {
		this.tradePlayerController = tradePlayerController;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		UUID id = e.getPlayer().getUniqueId();
		tradePlayerController.addTradePlayer(id);
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		
		UUID id = e.getPlayer().getUniqueId();
		tradePlayerController.removeTradePlayer(id);
		
	}
	
}
