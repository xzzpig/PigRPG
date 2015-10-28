package com.github.xzzpig.pigrpg.chests;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;

import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.friend.Friend;

public class ItemForChest
{
	@SuppressWarnings("deprecation")
	protected static ItemStack customItem(String displayname,int type,List<String> lore)
	{
		ItemStack is = new ItemStack(type);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3)+ displayname);
		if(lore != null)
			im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
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
	
	@SuppressWarnings("deprecation")
	protected static ItemStack friendInform(String player)
	{
		ItemStack is = new ItemStack(397);
		MaterialData data = is.getData();
		data.setData((byte) 3);
		is.setData(data);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3)+player);
		List<String> lore = new ArrayList<String>();
		OfflinePlayer fplayer = Bukkit.getOfflinePlayer(player);
		if(fplayer.isBanned())
			lore.add(TString.Color(4)+"被BAN");
		if(fplayer.isOnline())
			lore.add(TString.Color(3)+"状态:在线");
		else
			lore.add(TString.Color(8)+"状态:离线");
		if(fplayer.isOp())
			lore.add(TString.Color(4)+"OP");
		if(Bukkit.hasWhitelist())
			if(fplayer.isWhitelisted())
				lore.add(TString.Color(3)+"白名单");
			else
				lore.add(TString.Color(8)+"未在白名单");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	protected static ItemStack AddFriend(String player,String target)
	{
		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(154);
		ItemMeta im = is.getItemMeta();
		if(Friend.hasFriend(player, target))
			im.setDisplayName(TString.Color(3)+"对方已是你的好友");
		else
			im.setDisplayName(TString.Color(3)+"添加好友");			
		List<String> lore = new ArrayList<String>();
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
