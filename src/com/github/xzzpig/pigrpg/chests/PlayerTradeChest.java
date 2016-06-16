package com.github.xzzpig.pigrpg.chests;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.pigapi.bukkit.TString;

public class PlayerTradeChest {
	public static Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 9 * 5, TString.Color(5)
				+ "玩家交易界面");
		inv.setItem(18,
				ItemForChest.customItem(TString.Color(3) + "↑确认交易↑", 50, null));
		inv.setItem(26,
				ItemForChest.customItem(TString.Color(3) + "↓确认交易↓", 50, null));
		return inv;
	}

}
