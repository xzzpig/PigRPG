package com.github.xzzpig.pigrpg.chests;

import java.util.*;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class ItemForChest
{
	protected static ItemStack playerInform(Player player)
	{
		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(14);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("玩家信息");
		List<String> lore = new ArrayList<String>();
		lore.add("昵名:"+player.getDisplayName());
		lore.add("位置:"+player.getWorld().getName()+","+player.getLocation().getBlockX()+","+player.getLocation().getBlockY()+","+player.getLocation().getBlockZ());
		lore.add("游戏等级:"+((float)player.getLevel()+player.getExp()));
		if(player.isOp())
			lore.add("OP");
		if(player.getAllowFlight())
			lore.add("运行飞行");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	protected static ItemStack friendInform(String player)
	{
		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(14);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(player);
		List<String> lore = new ArrayList<String>();
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
