package com.github.xzzpig.pigrpg.chests;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.sale.Sale;

public class SaleChest {
	public static Inventory getInventory(int page) {
		if (page < 1)
			page = 1;
		Inventory inv = Bukkit.createInventory(null, 54, TString.Color(5)
				+ "拍卖行(第" + page + "页)");
		for (int i = 0; ((i + (page - 1) * 52) < Sale.items.size() && i < 52); i++) {
			inv.setItem(i, Sale.items.get(i + (page - 1) * 52));
		}
		inv.setItem(52, ItemForChest.customItem("上一页", 360, null));
		inv.setItem(53, ItemForChest.customItem("下一页", 382, null));
		return inv;
	}
}
