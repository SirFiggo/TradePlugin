package de.eberln.trade;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class TradePlayerInventoryClickListener implements Listener{

	private TradePlayerController tPC;
	
	public TradePlayerInventoryClickListener(TradePlayerController tPC) {
		
		this.tPC = tPC;
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		TradePlayer clicker = tPC.getTradePlayer(e.getWhoClicked().getUniqueId());
		
		if(clicker != null && clicker.isCurrentlyinInv) {
			e.setCancelled(true);
			
			ItemStack clickedItem = e.getCurrentItem();
			
			if(e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.PLAYER) {
				
				clicker.addItemToTrade(clickedItem, e.getRawSlot());
				
				clicker.setConfirmedStatus(false);
				clicker.partner.setConfirmedStatus(false);
				clicker.setPartnerConfirmedStatus(false);
				clicker.partner.setPartnerConfirmedStatus(false);
				
			}else {
				if(!InventoryStorage.items.containsValue(clickedItem) && clickedItem != null) {
					if(clicker.isInOwnIndizes(e.getRawSlot())) {
						
						clicker.setConfirmedStatus(false);
						clicker.partner.setConfirmedStatus(false);
						clicker.setPartnerConfirmedStatus(false);
						clicker.partner.setPartnerConfirmedStatus(false);
						
						clicker.removeItemFromTrade(clickedItem, e.getRawSlot());
						clicker.partner.removeItemFromPartnerTrade(clickedItem);
						
					}
				}else if(clickedItem != null && clickedItem.equals(InventoryStorage.items.get("confirm"))) {
					
					clicker.setConfirmedStatus(true);
					if(clicker.partner != null) clicker.partner.setPartnerConfirmedStatus(true);
					
				}else if(clickedItem != null && clickedItem.equals(InventoryStorage.items.get("cancel"))) {
					
					clicker.player.closeInventory();
					clicker.partner.player.closeInventory();
					
					for(int i=0;i<clicker.offer.size();i++) {
						clicker.player.getInventory().addItem(clicker.offer.get(i));
					}
					for(int i=0; i<clicker.partner.offer.size(); i++) {
						clicker.partner.player.getInventory().addItem(clicker.partner.offer.get(i));
					}
					
					clicker.player.sendMessage("Du hast den Handel abgebrochen");
					clicker.partner.player.sendMessage("Dein Hanelspartner hat den Handel abgebrochen");
					
					clicker.partner.resetAll();
					clicker.resetAll();
				}
			}
		}
	}
}
