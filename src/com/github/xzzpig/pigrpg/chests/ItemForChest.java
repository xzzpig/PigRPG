package com.github.xzzpig.pigrpg.chests;

import java.util.*;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;

import com.github.xzzpig.BukkitTools.TString;

public class ItemForChest
{
	@SuppressWarnings("deprecation")
	protected static ItemStack playerInform(Player player)
	{
		ItemStack is = new ItemStack(397);
		MaterialData data = is.getData();
		data.setData((byte) 3);
		is.setData(data);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3)+ "玩家信息");
		List<String> lore = new ArrayList<String>();
		lore.add(TString.Color(2)+"昵名:"+player.getDisplayName());
		lore.add(TString.Color(2)+"位置:"+player.getWorld().getName()+","+player.getLocation().getBlockX()+","+player.getLocation().getBlockY()+","+player.getLocation().getBlockZ());
		lore.add(TString.Color(2)+"游戏等级:"+((float)player.getLevel()+player.getExp()));
		if(player.isOp())
			lore.add(TString.Color(4)+"OP");
		if(player.getAllowFlight())
			lore.add(TString.Color(8)+"允许飞行");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	protected static ItemStack friendInform(String player)
	{
		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(14);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3)+player);
		List<String> lore = new ArrayList<String>();
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
