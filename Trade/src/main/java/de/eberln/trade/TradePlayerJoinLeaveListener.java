package de.eberln.trade;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TradePlayerJoinLeaveListener implements Listener{

	TradePlayerController tPC;
	
	public TradePlayerJoinLeaveListener(TradePlayerController tradePlayerController) {
		this.tPC = tradePlayerController;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		UUID id = e.getPlayer().getUniqueId();
		tPC.addTradePlayer(id);
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		
		TradePlayer leaver = tPC.getTradePlayer(e.getPlayer().getUniqueId());
		
		if(leaver.isCurrentlyinInv) {
			leaver.partner.player.sendMessage("§6Handel §7| §fDer Handelspartner hat das Spiel verlassen.");
			leaver.partner.player.closeInventory();
			leaver.partner.resetAll();
		}
		
		UUID id = e.getPlayer().getUniqueId();
		tPC.removeTradePlayer(id);
		
	}
	
}
