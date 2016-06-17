package com.github.xzzpig.pigrpg.trade;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.chest.ItemForChest;

public class PlayerTradeListener implements Listener {
	public static final PlayerTradeListener self = new PlayerTradeListener();
	
	@EventHandler
	public void onChangeTradeState(InventoryClickEvent event) {
		if (!event.getInventory().getTitle().contains("玩家交易界面"))
			return;
		Inventory inv = event.getInventory();
		PlayerTrade trade = PlayerTrade.getTrade(inv);
		if (trade == null) {
			for (HumanEntity player : inv.getViewers()) {
				((Player) player).sendMessage(TString.Prefix("PigRPG", 4)
						+ "未知交易错误");
				player.closeInventory();
			}
			event.setCancelled(true);
			return;
		}
		event.setCancelled(true);
		Player clicker = (Player) event.getWhoClicked();
		int type = 0;
		int item = event.getRawSlot();
		if (clicker == trade.getLauncher())
			type = 1;
		else if (clicker == trade.getTarget())
			type = 2;
		if (type == 0) {
			for (HumanEntity player : inv.getViewers()) {
				((Player) player).sendMessage(TString.Prefix("PigRPG", 4)
						+ "未知交易错误");
				player.closeInventory();
			}
			event.setCancelled(true);
			return;
		}
		if (!event.getAction().equals(InventoryAction.PICKUP_ALL))
			return;
		if (type == 1
				&& item == 18
				&& inv.getItem(item).getItemMeta().getDisplayName()
						.contains("确认交易")) {
			inv.clear(18);
			inv.setItem(19, ItemForChest.customItem(
					TString.Color(3) + "↑更改交易↑", 76, null));
			trade.changeatTradeState(1, true);
		}
		if (type == 1
				&& item == 19
				&& inv.getItem(item).getItemMeta().getDisplayName()
						.contains("更改交易")) {
			inv.clear(19);
			inv.setItem(18, ItemForChest.customItem(
					TString.Color(3) + "↑确认交易↑", 50, null));
			trade.changeatTradeState(1, false);
		}
		if (type == 2
				&& item == 26
				&& inv.getItem(item).getItemMeta().getDisplayName()
						.contains("确认交易")) {
			inv.clear(26);
			inv.setItem(25, ItemForChest.customItem(
					TString.Color(3) + "↓更改交易↓", 76, null));
			trade.changeatTradeState(2, true);
		}
		if (type == 2
				&& item == 25
				&& inv.getItem(item).getItemMeta().getDisplayName()
						.contains("更改交易")) {
			inv.clear(25);
			inv.setItem(26, ItemForChest.customItem(
					TString.Color(3) + "↓确认交易↓", 50, null));
			trade.changeatTradeState(2, false);
		}
	}

	@EventHandler
	public void onClickItem(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		Player clicker = (Player) event.getWhoClicked();
		if (!event.getInventory().getTitle().contains("玩家交易界面"))
			return;
		if (!event.isLeftClick())
			return;
		PlayerTrade trade = PlayerTrade.getTrade(clicker);
		if (trade == null) {
			for (HumanEntity player : inv.getViewers()) {
				((Player) player).sendMessage(TString.Prefix("PigRPG", 4)
						+ "未知交易错误");
				player.closeInventory();
			}
			event.setCancelled(true);
			return;
		}
		int type = 0;
		if (clicker == trade.getLauncher())
			type = 1;
		else if (clicker == trade.getTarget())
			type = 2;
		if (type == 0) {
			for (HumanEntity player : inv.getViewers()) {
				((Player) player).sendMessage(TString.Prefix("PigRPG", 4)
						+ "未知交易错误");
				player.closeInventory();
			}
			event.setCancelled(true);
			return;
		}
		try {
			trade.addItem(type, event);
		} catch (Exception e) {
		}
		try {
			trade.delItem(type, event);
		} catch (Exception e) {
		}
	}

	@EventHandler
	public void onCloseInv(InventoryCloseEvent event) {
		if (!event.getInventory().getTitle().contains("玩家交易界面"))
			return;
		Inventory inv = event.getInventory();
		final PlayerTrade trade = PlayerTrade.getTrade(inv);
		if (trade == null)
			return;
		new Thread(new Runnable() {
			@Override
			public void run() {
				trade.returnItems();
			}
		}).start();
	}
}
