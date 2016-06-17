package com.github.xzzpig.pigrpg.trade;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.chest.PlayerTradeChest;

public class PlayerTrade {
	public static HashMap<Player, PlayerTrade> tradelist = new HashMap<Player, PlayerTrade>();
	public static HashMap<Inventory, PlayerTrade> tradelist2 = new HashMap<Inventory, PlayerTrade>();

	public static PlayerTrade getTrade(Inventory inv) {
		if (!tradelist2.containsKey(inv))
			return null;
		return tradelist2.get(inv);
	}
	public static PlayerTrade getTrade(Player player) {
		if (!tradelist.containsKey(player))
			return null;
		return tradelist.get(player);
	}
	private Player player1, player2;

	private Inventory inv;

	private boolean state1 = false, state2 = false;

	public PlayerTrade(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		startTradeQue(player1, player2);
	}

	@SuppressWarnings("deprecation")
	public PlayerTrade(Player player1, String player2) {
		if (!Bukkit.getOfflinePlayer(player2).isOnline())
			return;
		new PlayerTrade(player1, Bukkit.getPlayer(player2));
	}

	public void addItem(int playerid, InventoryClickEvent event)
			throws Exception {
		ItemStack item = event.getCurrentItem();
		if (event.getRawSlot() >= 0 && event.getRawSlot() < 45)
			return;
		int i = 0;
		if (playerid == 1) {
			for (i = 0; i < 18; i++) {
				if (this.inv.getItem(i) == null) {
					this.inv.setItem(i, item);
					player1.getInventory().clear(event.getSlot());
					event.setCancelled(true);
					return;
				}
			}
		} else if (playerid == 2) {
			for (i = 27; i < 45; i++) {
				if (this.inv.getItem(i) == null) {
					this.inv.setItem(i, item);
					player2.getInventory().clear(event.getSlot());
					event.setCancelled(true);
					return;
				}
			}
		}
		this.freshInv();
	}

	public void changeatTradeState(int playerid, boolean state) {
		if (playerid == 1) {
			this.state1 = state;
		} else if (playerid == 2) {
			this.state2 = state;
		}
		if (state1 && state2) {
			this.finishTrade();
		}
		this.freshInv();
	}

	public void delItem(int playerid, InventoryClickEvent event)
			throws Exception {
		ItemStack item = event.getCurrentItem();
		if (event.getRawSlot() >= 45 && event.getRawSlot() < 81)
			return;
		if (playerid == 1) {
			if (event.getRawSlot() >= 18)
				return;
			player1.getInventory().addItem(item);
			inv.clear(event.getRawSlot());
			event.setCancelled(true);
			return;
		} else if (playerid == 2) {
			if (event.getRawSlot() <= 26)
				return;
			player2.getInventory().addItem(item);
			inv.clear(event.getRawSlot());
			event.setCancelled(true);
			return;

		}
		this.freshInv();
	}

	public void finishTrade() {
		int i = 0;
		for (i = 0; i < 18; i++) {
			ItemStack item = inv.getItem(i);
			if (item != null)
				player2.getInventory().addItem(inv.getItem(i));
		}
		for (i = 26; i < 45; i++) {
			ItemStack item = inv.getItem(i);
			if (item != null)
				player1.getInventory().addItem(inv.getItem(i));
		}
		this.stopTrade();
	}

	@SuppressWarnings("deprecation")
	public void freshInv() {
		player1.updateInventory();
		player2.updateInventory();
	}

	public Player getLauncher() {
		return this.player1;
	}

	public Player getTarget() {
		return this.player2;
	}

	public Inventory getTradeInventory() {
		return this.inv;
	}

	public void returnItems() {
		if (inv == null)
			return;
		int i = 0;
		for (i = 0; i < 18; i++) {
			ItemStack item = inv.getItem(i);
			if (item != null)
				player1.getInventory().addItem(inv.getItem(i));
		}
		for (i = 27; i < 45; i++) {
			ItemStack item = inv.getItem(i);
			if (item != null)
				player2.getInventory().addItem(inv.getItem(i));
		}
		player1.sendMessage(TString.Prefix("PigRPG", 3) + "交易终止,返还物品");
		player2.sendMessage(TString.Prefix("PigRPG", 3) + "交易终止,返还物品");
		inv.clear();
	}

	public void startTrade() {
		this.inv = PlayerTradeChest.getInventory();
		tradelist2.put(this.inv, this);
		player1.openInventory(inv);
		player2.openInventory(inv);
	}

	private void startTradeQue(Player p1, Player p2) {
		p2.sendMessage(TString.Prefix("PigRPG", 5) + p1.getName()
				+ TString.Color(3) + "请求与你交易");
		new TMessage(TString.Prefix("PigRPG", 3) + "输入/pr trade ")
				.then(ChatColor.GREEN.toString() + ChatColor.UNDERLINE
						+ "accept")
				.tooltip("同意\n/pr trade accept")
				.suggest("/pr trade accept")
				.then(ChatColor.RED + "|")
				.then(ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "deny")
				.tooltip("拒绝\n/pr trade deny").suggest("/pr trade deny")
				.send(p2);
		p2.sendMessage(TString.Color(5) + "请求将在5秒后自动取消");
		p1.sendMessage(TString.Prefix("PigRPG", 3) + "你的交易请求已发送");
		p1.sendMessage(TString.Color(5) + "请求将在5秒后自动取消");
		tradelist.put(p1, this);
		tradelist.put(p2, this);
	}

	public void stopTrade() {
		tradelist.remove(this.player1);
		tradelist.remove(this.player2);
		if (inv == null)
			return;
		inv.clear();
		player1.closeInventory();
		player2.closeInventory();
	}
}
