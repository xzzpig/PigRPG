package com.github.xzzpig.pigrpg.chests;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import com.github.xzzpig.BukkitTools.TString;

public class FriendListChest
{
	public static Inventory getInventory(Player player)
	{
		Inventory inv =Bukkit.createInventory(null, 9, TString.Color(5)+"好友列表");
		
		return inv;
	}
}
