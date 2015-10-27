package com.github.xzzpig.pigrpg.chests;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class RCChest 
{
	public static Inventory getInventory(Player player)
	{
		Inventory inv =Bukkit.createInventory(null, 9, player.getName());
		inv.addItem(ItemForChest.playerInform(player));
		return inv;
	}
	
}
