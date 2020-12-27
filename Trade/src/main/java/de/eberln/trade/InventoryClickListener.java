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
			
			if(e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.PLAYER) {
				
				clicker.addItemToTrade(clickedItem);
				
			}else {
				if(!InventoryStorage.items.containsValue(clickedItem) && clickedItem != null) {
					if(clicker.getIndexFromItemStack(clickedItem) != -1) {
						
						clicker.removeItemFromTrade(clickedItem, clicker.getIndexFromItemStack(clickedItem));
						clicker.partner.removeItemFromPartnerTrade(clickedItem);
						
					}
				}
			}
		}
	}
}
