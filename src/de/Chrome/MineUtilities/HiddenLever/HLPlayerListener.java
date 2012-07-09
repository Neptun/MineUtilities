package de.Chrome.MineUtilities.HiddenLever;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.Chrome.MineUtilities.MineUtilities;

public class HLPlayerListener implements Listener {
	
	@SuppressWarnings("unused")
	private final MineUtilities plugin;
	
	public HLPlayerListener(MineUtilities instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {	
		// Nur Rechtsklicke akzeptieren
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		
		// Nur bestimmte Blöck erlauben
		if (!HLUtil.checkRightBlock(event.getClickedBlock())) {
			return;
		}
		
		// Lever bekommen
		Block lever = HLUtil.getLever(event.getClickedBlock(), event.getBlockFace());
		if (lever == null) {
			return;
		}
		
		// Lever ändern
		HLUtil.updateLever(lever);
		event.setCancelled(true);
	}
	
}
