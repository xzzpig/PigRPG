package com.github.xzzpig.pigrpg.friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.bukkit.TConfig;
import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;

public class Friend {
	public static HashMap<String, List<String>> friendlist = new HashMap<String, List<String>>();
	public static HashMap<String, String> friendque = new HashMap<String, String>();

	public static void addFriend(String player, String friend) {
		Friend.getFriends(player).add(friend);
		Friend.getFriends(friend).add(player);
		Friend.save(player);
		Friend.save(friend);
		TEntity.toPlayer(player).sendMessage(
				TString.Prefix("PigRPG", 3) + "你成功添加好友" + friend);
		TEntity.toPlayer(friend).sendMessage(
				TString.Prefix("PigRPG", 3) + "你成功添加好友" + player);
	}

	public static void addFriendQue(Player player, final Player friend) {
		friend.sendMessage(TString.Prefix("PigRPG", 5) + player.getName()
				+ TString.Color(3) + "请求加你好友");
		new TMessage(TString.Prefix("PigRPG", 3) + "输入/pr friend ")
				.then(ChatColor.GREEN.toString() + ChatColor.UNDERLINE
						+ "accept")
				.tooltip("同意\n/pr friend accept")
				.suggest("/pr friend accept")
				.then(ChatColor.RED + "|")
				.then(ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "deny")
				.tooltip("拒绝\n/pr friend deny").suggest("/pr friend deny")
				.send(friend);
		friend.sendMessage(TString.Color(5) + "请求将在5秒后自动取消");
		player.sendMessage(TString.Prefix("PigRPG", 3) + "你的好友请求已发送");
		player.sendMessage(TString.Color(5) + "请求将在5秒后自动取消");
		friendque.put(friend.getName(), player.getName());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				friendque.remove(friend);
			}
		}).start();
	}

	public static void delFriend(String player, String friend) {
		Friend.getFriends(player).remove(friend);
		Friend.getFriends(friend).remove(player);
		Friend.save(player);
		Friend.save(friend);
		TEntity.toPlayer(player).sendMessage(
				TString.Prefix("PigRPG", 3) + "你成功删除好友" + friend);
		TEntity.toPlayer(friend).sendMessage(
				TString.Prefix("PigRPG", 3) + "你成功删除好友" + player);
	}

	@SuppressWarnings("deprecation")
	public static void delFriendQue(Player player, String friend) {
		if (!Bukkit.getOfflinePlayer(friend).isOnline()) {
			player.sendMessage(TString.Color(4) + friend + "未在线，无法发送删除好友请求");
			return;
		}
		final Player pfriend = TEntity.toPlayer(friend);
		pfriend.sendMessage(TString.Prefix("PigRPG", 5) + player.getName()
				+ TString.Color(3) + "请求与你断绝好友关系");
		new TMessage(TString.Prefix("PigRPG", 3) + "输入/pr friend del ")
				.then(ChatColor.GREEN.toString() + ChatColor.UNDERLINE
						+ "accept")
				.tooltip("同意\n/pr friend del accept")
				.suggest("/pr friend del accept")
				.then(ChatColor.RED + "|")
				.then(ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "deny")
				.tooltip("拒绝\n/pr friend del deny")
				.suggest("/pr friend del deny").send(pfriend);
		pfriend.sendMessage(TString.Color(5) + "请求将在5秒后自动取消");
		player.sendMessage(TString.Prefix("PigRPG", 3) + "你的删除好友请求已发送");
		player.sendMessage(TString.Color(5) + "请求将在5秒后自动取消");
		friendque.put(friend, player.getName());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				friendque.remove(pfriend.getName());
			}
		}).start();
	}

	public static List<String> getFriends(String player) {
		if (!friendlist.containsKey(player)) {
			ArrayList<String> list = new ArrayList<String>();
			friendlist.put(player, list);
			return list;
		}
		return friendlist.get(player);
	}

	public static boolean hasFriend(String player, String friend) {
		if (getFriends(player).contains(friend))
			return true;
		else
			return false;
	}

	public static void loadFriend(String player) {
		friendlist.put(player, TConfig.getConfigFile("PigRPG", "friend.yml")
				.getStringList(player));
		TString.Print(TString.Prefix("PigRPG") + player + "的好友列表已加载");
	}

	public static void save() {
		Iterator<Map.Entry<String, List<String>>> ir = friendlist.entrySet()
				.iterator();
		while (ir.hasNext()) {
			Map.Entry<String, List<String>> var = ir.next();
			TConfig.saveConfig("PigRPG", "friend.yml", var.getKey(),
					var.getValue());
		}
	}

	public static void save(String player) {
		TConfig.saveConfig("PigRPG", "friend.yml", player, getFriends(player));
	}
}
