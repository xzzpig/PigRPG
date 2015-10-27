package com.github.xzzpig.pigrpg.chests;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class FriendListChest
{
	public static Inventory getInventory(Player player)
	{
		Inventory inv =Bukkit.createInventory(null, 9, player.getName());
		
		return inv;
	}
}
