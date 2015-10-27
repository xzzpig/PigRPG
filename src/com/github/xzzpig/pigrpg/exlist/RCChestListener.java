package com.github.xzzpig.pigrpg.exlist;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;

import com.github.xzzpig.BukkitTools.TEntity;
import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.chests.*;
import com.github.xzzpig.pigrpg.friend.Friend;

public class RCChestListener implements Listener
{
	@EventHandler
	public void onInteraction(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		if(!player.isSneaking())
			return;
		if(event.getRightClicked().getType() != EntityType.PLAYER)
			return;
		Player target = (Player)event.getRightClicked();
		player.openInventory(RCChest.getInventory(target,event.getPlayer()));
		event.setCancelled(true);
	}
	@EventHandler
	public void onBanClick(InventoryClickEvent event)
	{
		if(event.getInventory().getTitle().contains("的右键菜单"))
			event.setCancelled(true);
		if(event.getRawSlot() == 1){
			if(event.getInventory().getItem(1).getItemMeta().getDisplayName().equalsIgnoreCase(TString.Color(3)+"添加好友"))
				Friend.addFriendQue((Player) event.getWhoClicked(), TEntity.toPlayer(event.getInventory().getTitle().replaceAll("的右键菜单", "").replaceAll(TString.Color(5), "")));
		}
		((Player) event.getWhoClicked()).sendMessage(event.getInventory().getTitle().replaceAll("的右键菜单", "").replaceAll(TString.Color(5), ""));
			
	}
}
