package de.eberln.trade;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Trade extends JavaPlugin {

	TradePlayerController tPC;
	
	public void onEnable() {
		
		tPC = new TradePlayerController();
		InventoryStorage.initializeItems();
		
		getServer().getPluginManager().registerEvents(new TradePlayerInventoryClickListener(tPC), this);
		getServer().getPluginManager().registerEvents(new TradePlayerJoinLeaveListener(tPC), this);
		getServer().getPluginManager().registerEvents(new TradePlayerInventoryCloseListener(tPC), this);
		getServer().getPluginManager().registerEvents(new TradePlayerDeathListener(tPC), this);
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			tPC.addTradePlayer(p.getUniqueId());
		}
		
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
		Player target = findPlayer(targetName);
		
		if(target != null) {
			if(target != request) {
			
				TradePlayer tradeTarget = tPC.getTradePlayer(target.getUniqueId());
				TradePlayer tradeRequester = tPC.getTradePlayer(request.getUniqueId());
				
					if(!tradeTarget.isBlocked) {
						if(!tradeRequester.isBlocked) {
							
							tradeTarget.setTradeStatus(true);
							tradeRequester.setTradeStatus(true);
							
							tradeTarget.setRequestStatus(true);
							
							tradeRequester.setPartner(tradeTarget);
							tradeTarget.setPartner(tradeRequester);
							
							request.sendMessage("§6Handel §7| §fDu hast eine Handelsanfrage an " + target.getDisplayName() + " gesendet.");
							
							target.sendMessage("§6Handel §7| §fDu hast eine Handelsanfrage von " + request.getDisplayName() + " erhalten! " + 
									"Um die Anfrage anzunehmen, schreibe §6/trade accept§f." + 
									"Um die Anfrage abzulehnen, schreibe §6/trade decline§f." + 
									"Diese Anfrage wird nach §6120 Sekunden §fungültig.");
							
							
							RunnableTradeStatusResetter tSR = new RunnableTradeStatusResetter(tradeRequester, tradeTarget);
							tradeTarget.setTradeStatusResetter(tSR);
							Bukkit.getServer().getScheduler().runTaskAsynchronously(this, tSR);
							
						}else {
							request.sendMessage("§6Handel §7| §cDu hast bereits eine ausstehende Handelsanfrage.");
						}
					}else {
						request.sendMessage("§6Handel §7| §cDer angefragte Spieler befindet sich bereits in einem Handel. Warte kurz und probiere es dann noch einmal.");
					}
				
			}else {
				request.sendMessage("Du kannst nicht mit dir selber handeln");
			}
		}else {
			request.sendMessage("§6Handel §7| §cDer Spieler " + targetName + " wurde nicht gefunden!");
		}
	}
	
	public void acceptTrade(Player accepter) {
		
		TradePlayer tradeAccepter = tPC.getTradePlayer(accepter.getUniqueId());
		
		if(tradeAccepter.isRequested) {
			tradeAccepter.tSR.resetStatus = false;
			
			tradeAccepter.setIsInInvStatus(true);
			tradeAccepter.partner.setIsInInvStatus(true);
			
			accepter.sendMessage("§6Handel §7| §fHandelsanfrage von " + tradeAccepter.partner.player.getDisplayName()  + " wurde angenommen.");
			tradeAccepter.partner.player.sendMessage("§6Handel §7| §fDeine Handelsanfrage an " + accepter.getDisplayName()  + " wurde von ihm angenommen.");

			tradeAccepter.openInventory();
			tradeAccepter.partner.openInventory();
			
		}else {
			accepter.sendMessage("§6Handel §7| §cDu hast keine ausstehende Anfrage!");
		}
	}
	
	
	
	public void declineTrade(Player decliner) {
		
		TradePlayer tradeDecliner = tPC.getTradePlayer(decliner.getUniqueId());
		if(tradeDecliner.isRequested) {
			
			TradePlayer partner = tradeDecliner.partner;
			
			partner.resetAll();
			tradeDecliner.resetAll();
			decliner.sendMessage("§6Handel §7| §fDu hast die Anfrage von " + partner.player.getDisplayName() + " abgelehnt");
			partner.player.sendMessage("§6Handel §7| §fDeine Anfrage an " + decliner.getDisplayName() + " wurde abgelehnt");
			
		}else {
			decliner.sendMessage("§6Handel §7| §cDu hast keine ausstehende Anfrage!");
		}
	}
	
	private Player findPlayer(String displayName) {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(p.getDisplayName().equals(displayName)) {
				return p;
			}
		}
		return null;
	}
	
}
