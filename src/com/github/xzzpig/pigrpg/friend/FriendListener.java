package com.github.xzzpig.pigrpg.friend;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.chest.FriendListChest;


public class FriendListener implements Listener{
	public static final FriendListener self = new FriendListener();
	
	@EventHandler
	public void onChestClick(InventoryClickEvent event){
		if (event.getInventory().getTitle().contains("好友列表"))
			event.setCancelled(true);
		else
			return;
		if (event.getInventory().getItem(event.getRawSlot()) != null) {
			String friend = event.getInventory().getItem(event.getRawSlot())
					.getItemMeta().getDisplayName()
					.replaceAll(TString.Color(3), "");
			Player player = (Player) event.getWhoClicked();
			player.openInventory(FriendListChest.getFriendSubInventory(friend));
		}
	}
	
	@EventHandler
	public void onSubInvClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().contains("的好友菜单"))
			event.setCancelled(true);
		else
			return;
		if (event.getInventory().getItem(event.getRawSlot()) != null) {
			if (event.getInventory().getItem(event.getRawSlot()).getItemMeta()
					.getDisplayName()
					.equalsIgnoreCase(TString.Color(3) + "删除好友"))
				Friend.delFriendQue((Player) event.getWhoClicked(), event
						.getInventory().getTitle().replaceAll("的好友菜单", "")
						.replaceAll(TString.Color(5), ""));
			// else if (event.getInventory().getItem(event.getRawSlot())
			// .getItemMeta().getDisplayName()
			// .equalsIgnoreCase(TString.Color(3) + "私聊"))
			// User.getUser((Player) event.getWhoClicked()).setSelfChat(
			// User.getUser(Bukkit.getPlayer(event.getInventory()
			// .getTitle().replaceAll("的好友菜单", "")
			// .replaceAll(TString.Color(5), ""))));
			event.getWhoClicked().closeInventory();
		}
	}
}
