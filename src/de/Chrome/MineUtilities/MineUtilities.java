package de.Chrome.MineUtilities;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.Chrome.MineUtilities.GameMode.GMListener;
import de.Chrome.MineUtilities.HiddenLever.HLPlayerListener;

public class MineUtilities extends JavaPlugin {
	
	Logger log;
	
	@Override
    public void onEnable() {
		log = this.getLogger();
		
		// Regestriere die EventListener
		PluginManager pma = getServer().getPluginManager();
    	pma.registerEvents(new HLPlayerListener(this), this);
		pma.registerEvents(new GMListener(this), this);
		
		log.info("has been enabled!");
	}
	
	@Override
    public void onDisable() { 
    	log.info("has been disabled!");
    }
	
}
