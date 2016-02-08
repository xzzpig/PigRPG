package com.github.xzzpig.pigrpg.friend;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chests.FriendListChest;

public class FriendEvent implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Friend.loadFriend(event.getPlayer().getName());
		User user = User.getUser(event.getPlayer());
		user.sendPluginMessage("&3你的好友列表已加载");
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (Friend.hasFriend(user.getPlayer().getName(), player.getName()))
				User.getUser(player).sendPluginMessage(
						"&3你的好友&2" + user.getPlayer().getName() + "&3已上线");
		}
	}

	@EventHandler
	public void onFriendInvClick(InventoryClickEvent event) {
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

	@SuppressWarnings("deprecation")
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
			else if (event.getInventory().getItem(event.getRawSlot())
					.getItemMeta().getDisplayName()
					.equalsIgnoreCase(TString.Color(3) + "私聊"))
				User.getUser((Player) event.getWhoClicked()).setSelfChat(
						User.getUser(Bukkit.getPlayer(event.getInventory()
								.getTitle().replaceAll("的好友菜单", "")
								.replaceAll(TString.Color(5), ""))));
		}
	}
}
