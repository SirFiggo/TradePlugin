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
	}
	
	
}
