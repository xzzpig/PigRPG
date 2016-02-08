package com.github.xzzpig.pigrpg.chests;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.Premissions;
import com.github.xzzpig.pigrpg.User;

public class RCChest {
	public static Inventory getInventory(Player player, Player opener) {
		Inventory inv = Bukkit.createInventory(null, 9, TString.Color(5)
				+ player.getName() + "的右键菜单");
		inv.addItem(ItemForChest.playerInform(player));
		inv.addItem(ItemForChest.AddFriend(opener.getName(), player.getName()));
		if (User.getUser(player).hasPremission(
				Premissions.pigrpg_command_friend_default)) {
			inv.addItem(ItemForChest.tradeQue());
		} else
			player.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限发起交易");
		inv.addItem(ItemForChest.teamAsk(player, opener));
		return inv;
	}

}
