package com.github.xzzpig.pigrpg.chat;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.chests.*;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

public class ChatListener implements Listener{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
		event.setCancelled(true);
		User user = User.getUser(event.getPlayer());
		user.setJustSay(event.getMessage());
		for(Player p:Bukkit.getOnlinePlayers()){
			User.getUser(p).sendChatMessage(user);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSetClick(InventoryClickEvent event)
	{
		if(event.getInventory().getTitle().contains("频道接受界面"))
			event.setCancelled(true);
		else
			return;
		if(event.getInventory().getItem(event.getRawSlot()) != null){
			ItemStack is = event.getInventory().getItem(event.getRawSlot());
			String cn = is.getItemMeta().getDisplayName().replaceAll(TString.Color(3), "");
			ChatChannel c = ChatChannel.getFromName(cn);
			if(is.getTypeId() == 50)
				User.getUser((Player)event.getWhoClicked()).addAcceptChatChannel(c);
			if(is.getTypeId() == 76)
				User.getUser((Player)event.getWhoClicked()).delAcceptChatChannel(c);
			event.getWhoClicked().closeInventory();
			event.getWhoClicked().openInventory(ChatChannelChest.getAcceptInventory(User.getUser((Player) event.getWhoClicked())));
		}
	}
	
	@EventHandler
	public void onChangeClick(InventoryClickEvent event)
	{
		if(event.getInventory().getTitle().contains("频道选择界面"))
			event.setCancelled(true);
		else
			return;
		if(event.getInventory().getItem(event.getRawSlot()) != null){
			ItemStack is = event.getInventory().getItem(event.getRawSlot());
			String cn = is.getItemMeta().getDisplayName().replaceAll(TString.Color(3), "");
			ChatChannel c = ChatChannel.getFromName(cn);
			User.getUser((Player)event.getWhoClicked()).setChatchannel(c);
			event.getWhoClicked().closeInventory();
			event.getWhoClicked().openInventory(ChatChannelChest.getChooseInventory((User.getUser((Player) event.getWhoClicked()))));
		}
	}
}
