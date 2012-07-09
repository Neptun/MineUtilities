package de.Chrome.MineUtilities.GameMode;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
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
	
	// Wegwerfen verbieten
	@EventHandler
	public void onPlayerDropItem (PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		
		if (player.getGameMode() == GameMode.CREATIVE) {
			event.setCancelled(true);		
			player.sendMessage(ChatColor.RED + "Du darfst im Creative Modus keine Items wegwerfen!");
		}
	}
	
	// Eierwerferfen verbieten
	@EventHandler
	public void onPlayerThrowEgg(PlayerEggThrowEvent event) {
		Player player = event.getPlayer();
		
		if (player.getGameMode() == GameMode.CREATIVE) {
			event.setHatching(false);
			player.sendMessage(ChatColor.RED + "Du darfst im Creative Modus keine Items wegwerfen!");
		}
	}
	
	// Spawner Eierwerfen verbieten - Allgemein
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() == SpawnReason.SPAWNER_EGG) {
			event.setCancelled(true);
		}
	}
}
