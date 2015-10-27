package com.github.xzzpig.pigrpg.chests;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.*;

import com.github.xzzpig.BukkitTools.TString;

public class RCChest 
{
	public static Inventory getInventory(Player player,Player opener)
	{
		Inventory inv =Bukkit.createInventory(null, 9,TString.Color(5)+ player.getName()+"的右键菜单");
		inv.addItem(ItemForChest.playerInform(player));
		inv.addItem(ItemForChest.AddFriend(opener.getName(), player.getName()));
		return inv;
	}
	
}
