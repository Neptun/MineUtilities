package de.Chrome.MineUtilities.GameMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

public class GMSaveLoadMap {
	
	public static void saveMap(Map<String, ItemStack[]> map, String path) {
		Map<String, GMItemStack[]> saveMap = new HashMap<String, GMItemStack[]>();
		
		for(String pName : map.keySet()) {
			// Konvertieren der Stacks
			ItemStack[] oldStack = map.get(pName);
			GMItemStack[] newStack = new GMItemStack[oldStack.length];
			
			for (int i = 0; i < oldStack.length; i++) {
					if (oldStack[i] != null)
						newStack[i] = new GMItemStack(oldStack[i]);	
			}
			
			saveMap.put(pName, newStack);
		}
		
		try {
			save(saveMap, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, ItemStack[]> loadMap(String path) {
		// Überprüfen, ob Datei vorhanden
		File checkFile = new File(path);
		if(checkFile.exists()) {
		
			Map<String, GMItemStack[]> loadMap = new HashMap<String, GMItemStack[]>();
			Map<String, ItemStack[]> map = new HashMap<String, ItemStack[]>();
	
			try {
				loadMap = (HashMap<String, GMItemStack[]>) load(path);
			} catch (Exception e) {
				e.printStackTrace();
				return new HashMap<String, ItemStack[]>();
			}
	
			for(String pName : loadMap.keySet()) {
				// Konvertieren der Stacks
				GMItemStack[] oldStack = loadMap.get(pName);
				ItemStack[] newStack = new ItemStack[oldStack.length];
				
				for (int i = 0; i < oldStack.length; i++) {
					if (oldStack[i] != null)
						newStack[i] = oldStack[i].unbox();			
				}
				
				map.put(pName, newStack);
			}
			
			return map;
		} else {
			return new HashMap<String, ItemStack[]>();
		}
	}
	
	private static void save(Object obj,String path) throws Exception {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}
			
	private static Object load(String path) throws Exception {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			Object result = ois.readObject();
			ois.close();
			return result;
	}
}
