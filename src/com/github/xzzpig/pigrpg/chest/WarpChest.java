package com.github.xzzpig.pigrpg.chest;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.teleport.Warp;

public class WarpChest {
	public static Inventory getInventory(User user) {
		Inventory inv = Bukkit.createInventory(null,
				(Warp.getWarps().length / 9 + 1) * 9, TString.Color(5)
						+ "Warp列表");
		for (Warp warp : Warp.getWarps()) {
			if (!(user.getPlayer().hasPermission("pigrpg.teleport.warp.*") || user
					.getPlayer().hasPermission(
							"pigrpg.teleport.warp." + warp.getName()))) {
				continue;
			}
			inv.addItem(ItemForChest.warpInfo(warp));
		}
		return inv;
	}
}
