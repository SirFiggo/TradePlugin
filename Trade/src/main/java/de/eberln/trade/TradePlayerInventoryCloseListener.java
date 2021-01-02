package de.eberln.trade;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class TradePlayerInventoryCloseListener implements Listener{

	private TradePlayerController tPC;
	
	public TradePlayerInventoryCloseListener(TradePlayerController tPC) {
		this.tPC = tPC; 
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		
		TradePlayer closer = tPC.getTradePlayer(e.getPlayer().getUniqueId());
		
		if(closer != null) {
			if(closer.isCurrentlyinInv) {
				
				for(int i=0;i<closer.offer.size();i++) {
					closer.player.getInventory().addItem(closer.offer.get(i));
				}
				for(int i=0; i<closer.partner.offer.size(); i++) {
					closer.partner.player.getInventory().addItem(closer.partner.offer.get(i));
				}
				
				closer.player.sendMessage("§6Handel §7| §cDu hast den Handel mit " + closer.partner.player.getDisplayName() + " abgebrochen.");
				closer.partner.player.sendMessage("§6Handel §7| §c " + closer.player.getDisplayName() + " hat den Handel mit dir abgebrochen!");
				
				closer.partner.resetAll();
				closer.partner.player.closeInventory();
				closer.resetAll();
				
			}
		}
	}
	
}
