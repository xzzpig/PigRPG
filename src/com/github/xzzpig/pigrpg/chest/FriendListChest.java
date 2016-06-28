package com.github.xzzpig.pigrpg.chest;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.Vars;

public class FriendListChest {

	@SuppressWarnings("deprecation")
	public static Inventory getFriendSubInventory(String friend, User opener) {
		Inventory inv = Bukkit.createInventory(null, 9, TString.Color(5)
				+ friend + "的好友菜单");
		inv.addItem(ItemForChest.friendInform(friend));
		if (Bukkit.getOfflinePlayer(friend).isOnline()) {
			// inv.addItem(ItemForChest.customItem("私聊", 1, null));
			inv.addItem(ItemForChest.customItem("删除好友", 1, null));
			User friendUser = User.getUser(friend);
			while (Vars.pdata.getBoolean("tel.friend.enbale")) {
				if ((friendUser.getPlayer().getWorld() != opener.getPlayer()
						.getWorld())
						&& (!Vars.pdata.getBoolean("tel.friend.overworld"))) {
					break;
				}
				if(Vars.pdata.getBoolean("tel.friend.allworld")||Vars.pdata.getList("tel.friend.enableworld").contains(friendUser.getPlayer().getWorld().getName())){
					inv.addItem(ItemForChest.friendTel(friendUser));
				}
				break;
			}
		}
		return inv;
	}

	public static Inventory getInventory(User user) {
		List<String> fl = user.getData().getList("friend.list");
		Inventory inv = Bukkit.createInventory(null, (fl.size() / 9 + 1) * 9,
				TString.Color(5) + "好友列表");
		for (String p : fl) {
			if(p.equalsIgnoreCase(""))
				continue;
			inv.addItem(ItemForChest.friendInform(p));
		}
		return inv;
	}
}
