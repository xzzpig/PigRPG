package com.github.xzzpig.pigrpg.friend;
import com.github.xzzpig.BukkitTools.*;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

import com.github.xzzpig.pigrpg.chests.*;
import com.github.xzzpig.pigrpg.*;

import org.bukkit.*;

public class FriendEvent implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Friend.loadFriend(event.getPlayer().getName());
		event.setJoinMessage(event.getJoinMessage()+"\n你的好友列表已加载");
	}
	@EventHandler
	public void onFriendInvClick(InventoryClickEvent event)
	{
		if(event.getInventory().getTitle().contains("好友列表"))
			event.setCancelled(true);
		else
			return;
		if(event.getInventory().getItem(event.getRawSlot()) != null){
			String friend = event.getInventory().getItem(event.getRawSlot()).getItemMeta().getDisplayName().replaceAll(TString.Color(3),"");
			Player player = (Player) event.getWhoClicked();
			player.openInventory(FriendListChest.getFriendSubInventory(friend));
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSubInvClick(InventoryClickEvent event)
	{
		if(event.getInventory().getTitle().contains("的好友菜单"))
			event.setCancelled(true);
		else
			return;
		if(event.getInventory().getItem(event.getRawSlot()) != null){
			if(event.getInventory().getItem(event.getRawSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(TString.Color(3)+"删除好友"))
				Friend.delFriendQue((Player) event.getWhoClicked(), event.getInventory().getTitle().replaceAll("的好友菜单", "").replaceAll(TString.Color(5), ""));
			else if(event.getInventory().getItem(event.getRawSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(TString.Color(3)+"私聊"))
				User.getUser((Player)event.getWhoClicked()).setSelfChat(User.getUser(Bukkit.getPlayer(event.getInventory().getTitle().replaceAll("的好友菜单", "").replaceAll(TString.Color(5), ""))));
		}
	}
}
