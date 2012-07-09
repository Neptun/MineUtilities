package de.Chrome.MineUtilities.GameMode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class GMItemStack implements Serializable {
	private static final long serialVersionUID = 729890133797629668L;
	 
    private final int type, amount;
    private final short damage;
    private final byte data;
 
    private final HashMap<GMEnchantment, Integer> enchants;
 
    public GMItemStack(ItemStack item) {
        this.type = item.getTypeId();
        this.amount = item.getAmount();
        this.damage = item.getDurability();
        this.data = item.getData().getData();
 
        HashMap<GMEnchantment, Integer> map = new HashMap<GMEnchantment, Integer>();
 
        Map<Enchantment, Integer> enchantments = item.getEnchantments();
 
        for(Enchantment enchantment : enchantments.keySet()) {
            map.put(new GMEnchantment(enchantment), enchantments.get(enchantment));
        }
 
        this.enchants = map;
    }
 
    public ItemStack unbox() {
        ItemStack item = new ItemStack(type, amount, damage, data);
 
        HashMap<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
 
        for(GMEnchantment enchantment : enchants.keySet()) {
            map.put(enchantment.unbox(), enchants.get(enchantment));
        }
 
        item.addUnsafeEnchantments(map);
 
        return item;
    }
}
