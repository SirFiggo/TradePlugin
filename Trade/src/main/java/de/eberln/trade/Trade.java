package de.eberln.trade;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Trade extends JavaPlugin {

	TradePlayerController tPC;
	
	public void onEnable() {
		
		tPC = new TradePlayerController();
		InventoryStorage.initializeItems();
		
		getServer().getPluginManager().registerEvents(new InventoryClickListener(tPC), this);
		getServer().getPluginManager().registerEvents(new TradePlayerJoinLeaveListener(tPC), this);
		
	}
	
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;

			if(cmd.getName().equalsIgnoreCase("trade")) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("accept")) {
						
						acceptTrade(p);
						return true;
						
					}else if(args[0].equalsIgnoreCase("decline")) {
						
						declineTrade(p);
						return true;
						
					}else {
						sendTradeRequest(p, args[0]);
						return true;
					}
					
				}else {
					p.sendMessage("Richtige Syntax: /trade <Spielername> bzw [accept/decline]");
					return true;
				}
			}
		}else {
			sender.sendMessage("Der Command ist nur für Spieler verfügbar!");
			return true;
		}
		return false;
	}

	private void sendTradeRequest(Player request, String targetName) {
		Player target = Bukkit.getPlayer(targetName);
		
		if(target != null) {
		
		TradePlayer tradeTarget = tPC.getTradePlayer(target.getUniqueId());
		TradePlayer tradeRequester = tPC.getTradePlayer(request.getUniqueId());
		
			if(!tradeTarget.isBlocked) {
				if(!tradeRequester.isBlocked) {
					
					tradeTarget.setTradeStatus(true);
					tradeRequester.setTradeStatus(true);
					
					tradeTarget.setRequestStatus(true);
					
					tradeRequester.setPartner(tradeTarget);
					tradeTarget.setPartner(tradeRequester);
					
					request.sendMessage("Anfrage gesendet. Warte bis sie akzeptiert wird");
					target.sendMessage("Du hast einen Anfrage erhalten. Annehmen mit /trade accept");
					
					
					TradeStatusResetter tSR = new TradeStatusResetter(tradeRequester, tradeTarget);
					tradeTarget.setTradeStatusResetter(tSR);
					Bukkit.getServer().getScheduler().runTaskAsynchronously(this, tSR);
					
				}else {
					request.sendMessage("Du befindest dich bereits in einem anderen Tausch");
				}
			}else {
				request.sendMessage("Der Spieler mit dem du handeln willst befindet sich bereits in einem Tausch. Warte kurz und probiere es dann nocheinmal");
			}
		}else {
			request.sendMessage("Der Spieler " + targetName + " wurde nicht gefunden");
		}
	}
	
	public void acceptTrade(Player accepter) {
		
		TradePlayer tradeAccepter = tPC.getTradePlayer(accepter.getUniqueId());
		
		if(tradeAccepter.isRequested) {
			tradeAccepter.tSR.resetStatus = false;
			
			tradeAccepter.setIsInInvStatus(true);
			tradeAccepter.partner.setIsInInvStatus(true);

			tradeAccepter.openInventory();
			tradeAccepter.partner.openInventory();
			
		}else {
			accepter.sendMessage("Du hast momentan keine Anfrage");
		}
	}
	
	
	
	public void declineTrade(Player decliner) {
		
		TradePlayer tradeDecliner = tPC.getTradePlayer(decliner.getUniqueId());
		if(tradeDecliner.isRequested) {
			
			TradePlayer partner = tradeDecliner.partner;
			
			resetAll(partner);
			resetAll(tradeDecliner);
			decliner.sendMessage("Du hast die Anfrage von " + partner.player.getDisplayName() + " abgelehnt");
			partner.player.sendMessage("Deine Anfrage an " + decliner.getDisplayName() + " wurde abgelehnt");
			
		}else {
			decliner.sendMessage("Du hast keine laufende Anfrage");
		}
	}
	
	
	
	public void resetAll(TradePlayer reseter) {
		
		if(reseter != null) {
			if(reseter.tSR != null) {
				reseter.tSR.resetStatus = false;
			}
			
			reseter.setPartner(null);
			reseter.setRequestStatus(false);
			reseter.setTradeStatus(false);
			reseter.setIsInInvStatus(false);
		}
		
	}
	
}
