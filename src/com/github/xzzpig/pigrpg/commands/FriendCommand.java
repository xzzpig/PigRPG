package com.github.xzzpig.pigrpg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.Premissions;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chests.FriendListChest;
import com.github.xzzpig.pigrpg.friend.Friend;

public class FriendCommand {
	public static boolean command(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (!User.getUser((Player) sender).hasPremission(
				Premissions.pigrpg_command_friend_default)) {
			sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
			return true;
		}
		if (getarg(args, 1).equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
					"pigrpg friend").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send((Player) sender);
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("list")) {
			((Player) sender).openInventory(FriendListChest
					.getInventory((Player) sender));
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("accept")) {
			if (Friend.friendque.containsKey(sender.getName()))
				Friend.addFriend(sender.getName(),
						Friend.friendque.get(sender.getName()));
			else
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有好友请求");
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("deny")) {
			if (Friend.friendque.containsKey(sender.getName())) {
				String friend = Friend.friendque.get(sender.getName());
				Friend.friendque.remove(sender.getName());
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你拒绝了"
						+ friend + "的好友请求");
				TEntity.toPlayer(friend).sendMessage(
						TString.Prefix("PigRPG", 3) + friend + "拒绝了你的好友请求");
			} else
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有好友请求");
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("del")) {
			if (getarg(args, 2).equalsIgnoreCase("accept")) {
				if (Friend.friendque.containsKey(sender.getName()))
					Friend.delFriend(sender.getName(),
							Friend.friendque.get(sender.getName()));
				else
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "你没有删除好友请求");
				return true;
			} else if (getarg(args, 2).equalsIgnoreCase("deny")) {
				if (Friend.friendque.containsKey(sender.getName())) {
					String friend = Friend.friendque.get(sender.getName());
					Friend.friendque.remove(sender.getName());
					sender.sendMessage(TString.Prefix("PigRPG", 4) + "你拒绝了"
							+ friend + "的删除好友请求");
					TEntity.toPlayer(friend).sendMessage(
							TString.Prefix("PigRPG", 3) + friend
									+ "拒绝了你的删除好友请求");
				} else
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "你没有删除好友请求");
				return true;
			} else if (getarg(args, 2).equalsIgnoreCase("help")) {
				for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
						"pigrpg friend del").getSubCommandHelps())
					ch.getHelpMessage("PigRPG").send((Player) sender);
				return true;
			}
			new TMessage(TString.Prefix("PigRPG", 4) + "输入/pr friend del help")
					.tooltip(
							TCommandHelp.valueOf(Help.PIGRPG,
									"pigrpg friend del").getDescribe())
					.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
					.suggest("/pr friend del help").tooltip("")
					.send((Player) sender);
			return false;
		}
		new TMessage(TString.Prefix("PigRPG", 4) + "输入/pr friend help")
				.tooltip(
						TCommandHelp.valueOf(Help.PIGRPG, "pigrpg friend")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
				.suggest("/pr friend help").tooltip("").send((Player) sender);
		return false;
	}

	public static String getarg(String[] args, int num) {
		if (args.length <= num) {
			return "";
		}
		return args[num];
	}
}
