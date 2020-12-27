package de.eberln.trade;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryStorage {

	public static HashMap<String, ItemStack> items = new HashMap<String, ItemStack>();
	
	public static void initializeItems() {
		
		ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta bordermeta = border.getItemMeta();
		bordermeta.setDisplayName(" ");
		border.setItemMeta(bordermeta);
		items.put("border", border);
		
		ItemStack cancel = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
		ItemMeta cancelmeta = cancel.getItemMeta();
		cancelmeta.setDisplayName("Handel abbrechen");
		cancel.setItemMeta(cancelmeta);
		items.put("cancel", cancel);
		
		ItemStack confirm = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE, 1);
		ItemMeta confirmmeta = confirm.getItemMeta();
		confirmmeta.setDisplayName("Bestätigen");
		confirm.setItemMeta(confirmmeta);
		items.put("confirm", confirm);
		
		ItemStack confirmed = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
		ItemMeta confirmedmeta = confirmed.getItemMeta();
		confirmedmeta.setDisplayName("Du hast den Handel bestätigt");
		confirmed.setItemMeta(confirmedmeta);
		items.put("confirmed", confirmed);
	
		ItemStack greenPartner = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
		ItemMeta greenmeta = greenPartner.getItemMeta();
		greenmeta.setDisplayName("Dein Handelspartner hat bereits bestätigt");
		greenPartner.setItemMeta(greenmeta);
		items.put("green", greenPartner);
		
		ItemStack orangePartner = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE, 1);
		ItemMeta orangemeta = orangePartner.getItemMeta();
		orangemeta.setDisplayName("Dein Handelspartner hat den Handel noch nicht bestätigt");
		orangePartner.setItemMeta(orangemeta);
		items.put("orange", orangePartner);
	
	}
	
	
}
