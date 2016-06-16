package com.github.xzzpig.pigrpg.chest;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chest.ItemForChest;

public class FriendListChest {

	public static Inventory getInventory(User user) {
		List<String> fl = user.getData().getList("friend.list");
		Inventory inv = Bukkit.createInventory(null, (fl.size() / 9 + 1) * 9,
				TString.Color(5) + "好友列表");
		for (String p : fl) {
			inv.addItem(ItemForChest.friendInform(p));
		}
		return inv;
	}
	@SuppressWarnings("deprecation")
	public static Inventory getFriendSubInventory(String friend) {
		Inventory inv = Bukkit.createInventory(null, 9, TString.Color(5)
				+ friend + "的好友菜单");
		inv.addItem(ItemForChest.friendInform(friend));
		if (Bukkit.getOfflinePlayer(friend).isOnline()) {
			inv.addItem(ItemForChest.customItem("私聊", 1, null));
			inv.addItem(ItemForChest.customItem("删除好友", 1, null));
		}
		return inv;
	}
}
