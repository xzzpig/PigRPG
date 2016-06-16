package com.github.xzzpig.pigrpg.chests;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chat.ChatChannel;

public class ChatChannelChest {
	public static Inventory getAcceptInventory(User user) {
		Inventory inv = Bukkit.createInventory(null, 9, TString.Color(5)
				+ "频道接受界面");
		List<ChatChannel> list = ChatChannel.DefList();
		list.remove(ChatChannel.Disable);
		for (ChatChannel c : list) {
			if (user.isAcceptChatChannel(c))
				inv.addItem(ItemForChest.customItem(
						TString.Color(3) + c.getName(), 76, null));
			else
				inv.addItem(ItemForChest.customItem(
						TString.Color(3) + c.getName(), 50, null));
		}
		return inv;
	}

	public static Inventory getChooseInventory(User user) {
		Inventory inv = Bukkit.createInventory(null, 9, TString.Color(5)
				+ "频道选择界面");
		List<ChatChannel> list = ChatChannel.DefList();
		list.remove(ChatChannel.Disable);
		list.remove(ChatChannel.Self);
		for (ChatChannel c : list) {
			if (user.getChatchannel() == c)
				inv.addItem(ItemForChest.customItem(
						TString.Color(3) + c.getName(), 76, null));
			else
				inv.addItem(ItemForChest.customItem(
						TString.Color(3) + c.getName(), 50, null));
		}
		return inv;
	}
}
