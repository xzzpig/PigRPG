package com.github.xzzpig.pigrpg.rclist;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.Vars;
import com.github.xzzpig.pigrpg.chest.RCChest;
import com.github.xzzpig.pigrpg.friend.Friend;
import com.github.xzzpig.pigrpg.trade.PlayerTrade;

public class RCListListener implements Listener {
	public static final RCListListener self = new RCListListener();

	@EventHandler
	public void onChestClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().contains("的右键菜单"))
			event.setCancelled(true);
		else
			return;
		if (event.getInventory().getItem(event.getRawSlot()) != null) {
			User clicker = User.getUser((Player) event.getWhoClicked());
			User target = User.getUser(TEntity.toPlayer(event.getInventory()
					.getTitle().replaceAll("的右键菜单", "")
					.replaceAll(TString.Color(5), "")));
			String itemname = event.getInventory().getItem(event.getRawSlot())
					.getItemMeta().getDisplayName();
			if (itemname.equalsIgnoreCase(TString.Color(3) + "添加好友"))
				Friend.addFriendQue(clicker.getPlayer(), target.getPlayer());
			else if (itemname.equalsIgnoreCase(TString.Color(3) + "申请交易"))
				new PlayerTrade((Player) event.getWhoClicked(), event
						.getInventory().getTitle().replaceAll("的右键菜单", "")
						.replaceAll(TString.Color(5), ""));
			// else if (event.getInventory().getItem(event.getRawSlot())
			// .getItemMeta().getDisplayName()
			// .equalsIgnoreCase(TString.Color(3) + "组队信息"))
			// new TeamQue(clicker, target);
			event.getWhoClicked().closeInventory();
		}
	}

	@EventHandler
	public void onClickPlayer(PlayerInteractEntityEvent event) {// 右键玩家打开右键菜单
		if (!(event.getRightClicked() instanceof Player))// 非玩家返回
			return;
		Player player = event.getPlayer(), target = (Player) event
				.getRightClicked();
		if (!player.isSneaking())// 玩家未潜行
			return;
		if (!player.hasPermission("pigrpg.rclist.open.player")) {
			player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
					+ "你沒有权限打开玩家右键菜单");
		}
		player.openInventory(RCChest.getInventory(target, player));
		if (Vars.pdata.getBoolean("config.relist.canel"))
			event.setCancelled(true);
	}
}
