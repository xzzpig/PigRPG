package com.github.xzzpig.pigrpg.sale;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.github.xzzpig.BukkitTools.TConfig;

public class Sale {
	public static List<ItemStack> items = new ArrayList<ItemStack>();

	public static void loadItems() {
		List<ItemStack> newitems = new ArrayList<ItemStack>();
		FileConfiguration config = TConfig.getConfigFile("PigRPG",
				"saleitems.yml");
		ItemStack item = null;
		int i = 0;
		while (true) {
			item = config.getItemStack("" + i);
			if (item == null)
				break;
			newitems.add(item);
			i = i + 1;
		}
		items = newitems;
	}

	public static void saveItems() {
		int i = 0;
		TConfig.delConfigFile("PigRPG", "saleiyems.yml");
		FileConfiguration config = TConfig.getConfigFile("PigRPG",
				"saleitems.yml");
		for (i = 0; i < items.size(); i++) {
			config.set("" + i, items.get(i));
		}
		TConfig.saveConfig("PigRPG", config, "saleitems.yml");
	}

	public static void addItem(ItemStack item) {
		items.add(item);
		saveItems();
	}

	public static void removeItem(ItemStack item) {
		items.remove(item);
		saveItems();
	}
}
