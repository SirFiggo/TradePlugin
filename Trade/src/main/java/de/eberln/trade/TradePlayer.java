package de.eberln.trade;

import java.util.ArrayList;
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
	public boolean hasConfirmed;
	
	public Inventory inv;
	
	public ArrayList<ItemStack> offer = new ArrayList<ItemStack>();
	
	public RunnableTradeStatusResetter tSR;
	
	public TradePlayer partner;
	
	public int[] ownIndizes = {19,20,28,29,37,38};
	public int[] partnerIndizes = {24,25,33,34,42,43};
	
	public TradePlayer(UUID id) {
		player = Bukkit.getPlayer(id);
		isBlocked = false;
		isRequested = false;
		isCurrentlyinInv = false;
		hasConfirmed = false;
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
	
	public void setTradeStatusResetter(RunnableTradeStatusResetter t) {
		tSR = t;
	}
	
	public void setPartner(TradePlayer t) {
		partner = t;
	}
	
	public void setIsInInvStatus(boolean isInInv) {
		isCurrentlyinInv = isInInv;
	}
	
	public void setConfirmedStatus(boolean confirmed) {
		hasConfirmed = confirmed;
		if(confirmed) {
			inv.setItem(2, InventoryStorage.items.get("confirmed"));
		}else {
			inv.setItem(2, InventoryStorage.items.get("confirm"));
		}
		
		if(hasConfirmed && partner.hasConfirmed) {
			
			for(int i=0;i<partner.offer.size();i++) {
				player.getInventory().addItem(partner.offer.get(i));
			}
			for(int i=0;i<offer.size();i++) {
				partner.player.getInventory().addItem(offer.get(i));
			}
			
			player.sendMessage("§6Handel §7| §fHandel wurde erfolgreich abgeschlossen!");
			partner.player.sendMessage("§6Handel §7| §fHandel wurde erfolgreich abgeschlossen!");
			
			partner.resetAll();
			resetAll();
			
		}
	}
	
	public void setPartnerConfirmedStatus(boolean confirmed) {
		if(confirmed) {
			inv.setItem(6, InventoryStorage.items.get("green"));
		}else {
			inv.setItem(6, InventoryStorage.items.get("orange"));
		}
		
	}
	
	public void openInventory() {
		
		inv.setItem(4, InventoryStorage.items.get("border"));
		inv.setItem(13, InventoryStorage.items.get("border"));
		inv.setItem(22, InventoryStorage.items.get("border"));
		inv.setItem(31, InventoryStorage.items.get("border"));
		inv.setItem(40, InventoryStorage.items.get("border"));
		inv.setItem(49, InventoryStorage.items.get("border"));
		
		inv.setItem(0, InventoryStorage.items.get("cancel"));
		
		inv.setItem(2, InventoryStorage.items.get("confirm"));
		
		inv.setItem(6, InventoryStorage.items.get("orange"));
		
		player.openInventory(inv);

	}
	
	public boolean addItemToTrade(ItemStack istack, int index) {
		
		for(int i=0;i<6;i++) {
			if(inv.getItem(ownIndizes[i]) == null) {
				inv.setItem(ownIndizes[i], istack);
				
				partner.addItemToPartnerTrade(istack);
				
				if(index >= 54 && index <= 80) {
					player.getInventory().clear(index-45);
				}else if(index >= 81 && index <= 89){
					player.getInventory().clear(index-81);
				}
				
				offer.add(istack);
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
	
	
	public void removeItemFromTrade(ItemStack istack, int index) {
		
		inv.clear(index);
		player.getInventory().addItem(istack);
		offer.remove(istack);
		player.updateInventory();
		
	}
	
	public void removeItemFromPartnerTrade(ItemStack istack) {
		
		inv.clear(getIndexFromItemStack(istack));
		player.updateInventory();
		
	}
	
	public int getIndexFromItemStack(ItemStack istack) {
		for(int i=0;i<6;i++) {
			if(inv.getItem(partnerIndizes[i]) != null && inv.getItem(partnerIndizes[i]).equals(istack)) {
				return partnerIndizes[i];
			}
		}
		return -1;
	}
	
	public boolean isInOwnIndizes(int index) {
		for(int i=0;i<ownIndizes.length;i++) {
			if(index == ownIndizes[i]) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInPartnerIndizes(int index) {
		for(int i=0;i<partnerIndizes.length;i++) {
			if(index == partnerIndizes[i]) {
				return true;
			}
		}
		return false;
	}
	
	public void resetAll() {
		
		if(tSR != null) {
			tSR.resetStatus = false;
		}
		
		setPartner(null);
		setRequestStatus(false);
		setTradeStatus(false);
		setIsInInvStatus(false);
		setConfirmedStatus(false);
		setPartnerConfirmedStatus(false);
		offer.clear();
		player.closeInventory();
		inv.clear();
		
		}
	
}
