package de.eberln.trade;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TradePlayer{

	public Player player;
	
	public boolean isBlocked;
	public boolean isRequested;
	public boolean isCurrentlyinInv;
	
	public Inventory inv;
	
	public TradeStatusResetter tSR;
	
	public TradePlayer partner;
	
	public int[] ownIndizes = {19,20,28,29,37,38};
	public int[] partnerIndizes = {24,25,33,34,42,43};
	
	public TradePlayer(UUID id) {
		player = Bukkit.getPlayer(id);
		isBlocked = false;
		isRequested = false;
		isCurrentlyinInv = false;
		tSR = null;
		partner = null;
		inv = Bukkit.getServer().createInventory(player, 54);
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
	
	public void setIsInInvStatus(boolean isInInv) {
		isCurrentlyinInv = isInInv;
	}
	
	public void openInventory() {
		
		inv.setItem(4, InventoryStorage.items.get("border"));
		inv.setItem(13, InventoryStorage.items.get("border"));
		inv.setItem(22, InventoryStorage.items.get("border"));
		inv.setItem(31, InventoryStorage.items.get("border"));
		inv.setItem(40, InventoryStorage.items.get("border"));
		inv.setItem(49, InventoryStorage.items.get("border"));
		
		inv.setItem(0, InventoryStorage.items.get("cancel"));
		
		player.openInventory(inv);

	}
	
	public boolean addItemToTrade(ItemStack istack) {
		
		for(int i=0;i<6;i++) {
			if(inv.getItem(ownIndizes[i]) == null) {
				inv.setItem(ownIndizes[i], istack);
				partner.addItemToPartnerTrade(istack);
				player.getInventory().remove(istack);
				player.updateInventory();
				return true;
			}
		}
		return false;
		
	}
	
	public boolean addItemToPartnerTrade(ItemStack istack) {
		
		for(int i=0;i<6;i++) {
			if(inv.getItem(partnerIndizes[i]) == null) {
				inv.setItem(partnerIndizes[i], istack);
				player.updateInventory();
				return true;
			}
		}
		
		return false;
		
	}
	
	
	public void removeItemFromTrade(ItemStack istack, int i) {
		
		inv.clear(i);
		player.getInventory().addItem(istack);
		player.updateInventory();
		
		partner.removeItemFromPartnerTrade(istack);
		
	}
	
	public void removeItemFromPartnerTrade(ItemStack istack) {
		
		inv.remove(istack);
		player.updateInventory();
		
	}
	
	public int getIndexFromItemStack(ItemStack istack) {
		for(int i=0;i<6;i++) {
			int[] indizes = {19,20,28,29,37,38};
			if(inv.getItem(indizes[i]) != null && inv.getItem(indizes[i]).equals(istack)) {
				return indizes[i];
			}
		}
		return -1;
	}
}
