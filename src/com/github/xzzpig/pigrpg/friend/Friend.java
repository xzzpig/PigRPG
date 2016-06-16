package com.github.xzzpig.pigrpg.friend;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;

public class Friend {
	public static void addFriend(String player, String friend) {
		Friend.getFriends(player).add(friend);
		Friend.getFriends(friend).add(player);
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
		User.getUser(friend).getData().set("friend.que",player.getName());
//		friendque.put(friend.getName(), player.getName());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				User.getUser(friend).getData().remove("friend.que");
			}
		}).start();
	}

	public static void delFriend(String player, String friend) {
		Friend.getFriends(player).remove(friend);
		Friend.getFriends(friend).remove(player);
		
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
		User.getUser(pfriend).getData().set("friend.del.que",player.getName());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				User.getUser(pfriend).getData().remove("friend.del.que");
			}
		}).start();
	}

	@SuppressWarnings("deprecation")
	public static List<String> getFriends(String player) {
		List<String> list =User.getUser(Bukkit.getPlayer(player)).getData().getList("friend.list");
		User.getUser(Bukkit.getPlayer(player)).getData().set("friend.list",list);
		return list;
	}

	public static boolean hasFriend(String player, String friend) {
		if (getFriends(player).contains(friend))
			return true;
		else
			return false;
	}
}
