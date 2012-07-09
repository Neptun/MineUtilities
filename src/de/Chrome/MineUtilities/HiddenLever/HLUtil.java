package de.Chrome.MineUtilities.HiddenLever;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Lever;
import org.bukkit.material.Torch;

public class HLUtil {
	
	public static boolean checkRightBlock(Block block) {

        if (block == null) {
            return false;
        }

        switch (block.getType()) {
            case GLOWSTONE:
                return true;

            case TORCH:
                return true;

            default:
                return false;
        }
    }
	
	public static Block getLever(Block block, BlockFace face) {
        if (block == null) {
            return null;
        }
        
        // Lever ist unter dem Block
        //Block bDown = block.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ());
        Block bDown = block.getRelative(0, -1, 0);
        if (bDown.getType() == Material.LEVER) {
        	return bDown; 
        }

        // Lever ist über dem Block
        //Block bAbove = block.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
        Block bAbove = block.getRelative(0, 1, 0);
        if (bAbove.getType() == Material.LEVER) {
        	return bAbove; 
        }
        
        // Lever ist hinter dem Block
        Block bBehind = block.getRelative(face.getOppositeFace(), 1);
        if (bBehind.getType() == Material.LEVER) {
        	return bBehind; 
        }
        
        // Torch Spezial
        // Hinter dem plaziertem Block
        if (block.getType() == Material.TORCH) {
	        Torch torch = (Torch)block.getState().getData();
        	Block bBehind2 = block.getRelative(torch.getFacing().getOppositeFace(), 2);
	        if (block.getType() == Material.TORCH && bBehind2.getType() == Material.LEVER) {
	        	return bBehind2; 
	        }
        }
        
        return null;
    }
	
	public static void updateLever(Block bLever) {
		
		if (bLever.getType() != Material.LEVER) {
        	return; 
        }
		
		BlockState sLever = bLever.getState();
		
		Lever lever = (Lever)sLever.getData();	
		lever.setPowered(!lever.isPowered());
		
		sLever.setData(lever);
		sLever.update();
    }
}
