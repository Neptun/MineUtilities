package de.Chrome.MineUtilities.GameMode;

import java.io.Serializable;

import org.bukkit.enchantments.Enchantment;

public class GMEnchantment implements Serializable {
	private static final long serialVersionUID = 8973856768102665381L;
	 
    private final int id;
 
    public GMEnchantment(Enchantment enchantment) {
        this.id = enchantment.getId();
    }
 
    public Enchantment unbox() {
        return Enchantment.getById(this.id);
    }
}
