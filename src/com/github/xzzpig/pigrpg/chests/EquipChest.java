package com.github.xzzpig.pigrpg.chests;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.chests.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.*;

public class EquipChest
{
	public static Inventory getInventory(User user)
	{
		Inventory inv =Bukkit.createInventory(null, (((EquipType.values().length)/9)*2+2)*9,TString.Color(5)+ user.getPlayer().getName()+"的装备栏");
		for(EquipType type:EquipType.values()){
			if(!type.isShow())
				continue;
			int loc = inv.firstEmpty();
			inv.setItem(loc,ItemForChest.customItem(TString.Color(3)+type,1,null));
			inv.setItem(loc+9,user.getEquip(type));
		}
		return inv;
	}
}
