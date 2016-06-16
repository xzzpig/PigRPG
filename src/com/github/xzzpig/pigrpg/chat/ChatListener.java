package com.github.xzzpig.pigrpg.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import com.github.xzzpig.pigapi.bukkit.TPlayer;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chests.ChatChannelChest;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener {
	private void atsolve(PlayerChatEvent event) {
		User user = User.getUser(event.getPlayer());
		String[] messages = event.getMessage().replaceAll("@", "").split(" ");
		if (messages[0].equalsIgnoreCase("?")) {
			user.sendPluginMessage("&3@[玩家] -发起与该玩家的私聊");
		} else {
			if (messages[0].equalsIgnoreCase(""))
				messages[0] = messages[1];
			user.setSelfChat(User.getUser(Bukkit.getPlayer(messages[0])));
		}
	}

	@EventHandler
	public void onChangeClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().contains("频道选择界面"))
			event.setCancelled(true);
		else
			return;
		User user = User.getUser((Player) event.getWhoClicked());
		if (user.getChatManager().ismute()) {
			user.sendPluginMessage("&4你已被禁言，无法切换频道");
			user.getPlayer().closeInventory();
			return;
		}
		if (event.getInventory().getItem(event.getRawSlot()) != null) {
			ItemStack is = event.getInventory().getItem(event.getRawSlot());
			String cn = is.getItemMeta().getDisplayName()
					.replaceAll(TString.Color(3), "");
			ChatChannel c = ChatChannel.getFromName(cn);
			user.setChatchannel(c);
			event.getWhoClicked().closeInventory();
			event.getWhoClicked().openInventory(
					ChatChannelChest.getChooseInventory((User
							.getUser((Player) event.getWhoClicked()))));
			user.buildScore();
		}
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		event.setCancelled(true);
		if (event.getMessage().startsWith("@"))
			atsolve(event);
		User user = User.getUser(event.getPlayer());
		user.setJustSay(event.getMessage());
		for (Player p : TPlayer.getAllPlayers()) {
			User.getUser(p).sendChatMessage(user);
		}
		System.out.println(user.getPlayer().getName() + ":"
				+ event.getMessage());
	}

	@EventHandler
	public void onSetClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().contains("频道接受界面"))
			event.setCancelled(true);
		else
			return;
		try {
			if (event.getInventory().getItem(event.getRawSlot()) != null) {
				ItemStack is = event.getInventory().getItem(event.getRawSlot());
				String cn = is.getItemMeta().getDisplayName()
						.replaceAll(TString.Color(3), "");
				ChatChannel c = ChatChannel.getFromName(cn);
				if (is.getTypeId() == 50)
					User.getUser((Player) event.getWhoClicked())
							.addAcceptChatChannel(c);
				if (is.getTypeId() == 76)
					User.getUser((Player) event.getWhoClicked())
							.delAcceptChatChannel(c);
				event.getWhoClicked().closeInventory();
				event.getWhoClicked().openInventory(
						ChatChannelChest.getAcceptInventory(User
								.getUser((Player) event.getWhoClicked())));
			}
		} catch (Exception e) {
		}
	}
}
