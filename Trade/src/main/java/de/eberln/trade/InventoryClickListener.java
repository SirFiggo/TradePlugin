package de.eberln.trade;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryClickListener implements Listener{

	private TradePlayerController tPC;
	
	public InventoryClickListener(TradePlayerController tPC) {
		
		this.tPC = tPC;
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		TradePlayer clicker = tPC.getTradePlayer(e.getWhoClicked().getUniqueId());
		
		if(clicker.isCurrentlyinInv) {
			e.setCancelled(true);
			
			ItemStack clickedItem = e.getCurrentItem();
			
			if(e.getClickedInventory().getType() == InventoryType.PLAYER) {
				e.getWhoClicked().sendMessage("player");
			}else {
				e.getWhoClicked().sendMessage("chest");
			}
		}
		
	}
	
}
