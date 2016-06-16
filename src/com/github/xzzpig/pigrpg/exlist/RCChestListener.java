package com.github.xzzpig.pigrpg.exlist;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chests.RCChest;
import com.github.xzzpig.pigrpg.friend.Friend;
import com.github.xzzpig.pigrpg.team.TeamQue;
import com.github.xzzpig.pigrpg.trade.PlayerTrade;

public class RCChestListener implements Listener {
	@EventHandler
	public void onBanClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().contains("的右键菜单"))
			event.setCancelled(true);
		else
			return;
		if (event.getInventory().getItem(event.getRawSlot()) != null) {
			User clicker = User.getUser((Player) event.getWhoClicked());
			User target = User.getUser(TEntity.toPlayer(event.getInventory()
					.getTitle().replaceAll("的右键菜单", "")
					.replaceAll(TString.Color(5), "")));
			if (event.getInventory().getItem(event.getRawSlot()).getItemMeta()
					.getDisplayName()
					.equalsIgnoreCase(TString.Color(3) + "添加好友"))
				Friend.addFriendQue(
						(Player) event.getWhoClicked(),
						TEntity.toPlayer(event.getInventory().getTitle()
								.replaceAll("的右键菜单", "")
								.replaceAll(TString.Color(5), "")));
			else if (event.getInventory().getItem(event.getRawSlot())
					.getItemMeta().getDisplayName()
					.equalsIgnoreCase(TString.Color(3) + "申请交易"))
				new PlayerTrade((Player) event.getWhoClicked(), event
						.getInventory().getTitle().replaceAll("的右键菜单", "")
						.replaceAll(TString.Color(5), ""));
			else if (event.getInventory().getItem(event.getRawSlot())
					.getItemMeta().getDisplayName()
					.equalsIgnoreCase(TString.Color(3) + "组队信息"))
				new TeamQue(clicker, target);
			event.getWhoClicked().closeInventory();
		}
	}

	@EventHandler
	public void onInteraction(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (!player.isSneaking())
			return;
		if (event.getRightClicked().getType() != EntityType.PLAYER)
			return;
		Player target = (Player) event.getRightClicked();
		player.openInventory(RCChest.getInventory(target, event.getPlayer()));
		event.setCancelled(true);
	}
}
