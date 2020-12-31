package de.eberln.trade;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TradePlayerDeathListener implements Listener {

	private TradePlayerController tPC;
	
	public TradePlayerDeathListener(TradePlayerController tPC) {
		this.tPC = tPC;
	}
	
	@EventHandler
	public void onTradePlayerDeath(PlayerDeathEvent e) {
		
		TradePlayer tradeDeather = tPC.getTradePlayer(e.getEntity().getUniqueId());
		
		if(tradeDeather.isCurrentlyinInv) {
			
			for(int i=0;i<tradeDeather.offer.size();i++) {
				tradeDeather.player.getInventory().addItem(tradeDeather.offer.get(i));
			}
			for(int i=0; i<tradeDeather.partner.offer.size(); i++) {
				tradeDeather.partner.player.getInventory().addItem(tradeDeather.partner.offer.get(i));
			}
			
			tradeDeather.player.sendMessage("§6Handel §7| §cDer Handel mit " + tradeDeather.partner.player.getDisplayName() + " wurde abgebrochen.");
			tradeDeather.partner.player.sendMessage("§6Handel §7| §cDein Handelspartner ist während des Handels gestorben. Der Handel wird abgebrochen.");
			
			tradeDeather.partner.resetAll();
			tradeDeather.partner.player.closeInventory();
			tradeDeather.resetAll();
			
		}
		
	}
	
	
}
