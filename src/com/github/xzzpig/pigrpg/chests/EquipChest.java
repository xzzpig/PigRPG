package com.github.xzzpig.pigrpg.chests;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.equip.EquipType;
import com.github.xzzpig.pigrpg.equip.Equipment;

public class EquipChest {
	public static Inventory getInventory(User user) {
		Inventory inv = Bukkit.createInventory(null,
				(((EquipType.values().length) / 9) * 2 + 2) * 9,
				TString.Color(5) + user.getPlayer().getName() + "的装备栏");
		for (EquipType type : EquipType.values()) {
			if (!type.isShow())
				continue;
			int loc = inv.firstEmpty();
			inv.setItem(
					loc,
					new Equipment(ItemForChest.customItem(TString.Color(3)
							+ type, type.getItemTypeId(), null), user
							.getPlayer()).setEquiptype(type));
			inv.setItem(loc + 9, user.getEquip(type));
		}
		return inv;
	}
}
