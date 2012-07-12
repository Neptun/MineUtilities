package de.Chrome.MineUtilities.GameMode;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.Chrome.MineUtilities.MineUtilities;

public class GMListener implements Listener {
	
	private final MineUtilities plugin;
	
	private final String mapPath;
	private Map<String, ItemStack[]> inventories = new HashMap<String, ItemStack[]>();
	

	public GMListener(MineUtilities instance) {
		plugin = instance;
		mapPath = plugin.getDataFolder().getAbsolutePath() + "\\inventories.dat";
		
		// Lade gespeicherter Inventare
		inventories = GMSaveLoadMap.loadMap(mapPath);
	}
	
	// Löschen des Invenatrs
	@EventHandler
	public void onPlayerChangeGameMode(PlayerGameModeChangeEvent event) {
		GameMode newGameMode = event.getNewGameMode();
		Player player = event.getPlayer();
		
		if (newGameMode == GameMode.CREATIVE) {		
			// Inventar speichern und dann löschen
			inventories.put(player.getName(), player.getInventory().getContents());		
			GMSaveLoadMap.saveMap(inventories, mapPath);
			
			player.getInventory().clear();
			
			player.sendMessage(ChatColor.GOLD + "Dein Inventar wurde gespeichert!");
		} else {
			// Inventar wiederherstelen
			if (inventories.containsKey(player.getName())) {
				player.getInventory().setContents(inventories.get(player.getName()));
				
				inventories.remove(player.getName());
				GMSaveLoadMap.saveMap(inventories, mapPath);
			}
			else
				player.getInventory().clear();
			
			player.sendMessage(ChatColor.GOLD + "Dein Inventar wurde wiederhergestelt!");
		}
	}
	
	// Interagieren mit Truhe, ...
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (player.getGameMode() == GameMode.CREATIVE) {
			// Auf den Boden Schalter treten ignorieren
			if (event.getAction() == Action.PHYSICAL)
				return;

			if (event.getAction() == Action.LEFT_CLICK_BLOCK)
				return;
			
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				switch (event.getClickedBlock().getType()) {
					case WOODEN_DOOR:if (event.getAction() == Action.LEFT_CLICK_BLOCK)
						return;
					case FENCE_GATE:
					case TRAP_DOOR:
						return;
					case CHEST:
						event.setCancelled(true);
		                final Chest chest = (Chest) event.getClickedBlock().getState();
		                final Inventory i = this.plugin.getServer().createInventory(player, chest.getInventory().getSize());
		                i.setContents(chest.getInventory().getContents());
		                player.openInventory(i);
		                player.sendMessage(ChatColor.AQUA + "Du kannst nichts verändern!");
		                return;
	                default:
	                	player.sendMessage(ChatColor.RED + "Du darfst im Creative-Modus mit nicht interagieren!");
	                	break;
				}
			}
			
			event.setCancelled(true);
		}
    }
	
	// Wegwerfen verbieten
	@EventHandler
	public void onPlayerDropItem (PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		
		if (player.getGameMode() == GameMode.CREATIVE) {
			event.setCancelled(true);		
			player.sendMessage(ChatColor.RED + "Du darfst im Creative Modus keine Items wegwerfen!");
		}
	}
}
