package com.github.xzzpig.pigrpg.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.Vars;
import com.github.xzzpig.pigrpg.chest.FriendListChest;
import com.github.xzzpig.pigrpg.friend.Friend;

public class PigCommand_Friend {

	public static boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String arg1 = "help";
		try {
			arg1 = args[1];
		} catch (Exception e) {
		}
		if (arg1.equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
					"pigrpg friend").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send(sender);
			return true;
		}
		if (!(Vars.enables.containsKey("Friend") && Vars.enables.get("Friend"))) {// 好友系统未启用
			sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
					+ "好友系统为启用");
			return true;
		}
		if (!(sender instanceof Player)) {// 非玩家返回
			sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
					+ "控制台无法使用该命令");
			return true;
		}
		Player player = (Player) sender;
		User user = User.getUser(player);
		if (arg1.equalsIgnoreCase("list")) {
			if (!player.hasPermission("pigrpg.command.friend.list")) {
				player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
						+ "你没有权限使用该命令");
				return true;
			}
			player.openInventory(FriendListChest.getInventory(user));
			return true;
		} else if (arg1.equalsIgnoreCase("accept")) {
			if (!player.hasPermission("pigrpg.command.friend.other")) {
				player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
						+ "你没有权限使用该命令");
				return true;
			}
			if (user.getData().contianKey("friend.que")) {
				Friend.addFriend(sender.getName(),
						user.getData().getString("friend.que"));
				User.saveAllData();
			} else
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有好友请求");
			return true;
		} else if (arg1.equalsIgnoreCase("deny")) {
			if (!player.hasPermission("pigrpg.command.friend.other")) {
				player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
						+ "你没有权限使用该命令");
				return true;
			}
			if (user.getData().contianKey("friend.que")) {
				String friend = user.getData().getString("friend.que");
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你拒绝了"
						+ friend + "的好友请求");
				TEntity.toPlayer(friend).sendMessage(
						TString.Prefix("PigRPG", 3) + friend + "拒绝了你的好友请求");
			} else
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有好友请求");
			return true;
		} else if (arg1.equalsIgnoreCase("del")) {
			if (!player.hasPermission("pigrpg.command.friend.other")) {
				player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
						+ "你没有权限使用该命令");
				return true;
			}
			String arg2 = "help";
			try {
				arg2 = args[2];
			} catch (Exception e) {
			}
			if (arg2.equalsIgnoreCase("accept")) {
				if (user.getData().contianKey("friend.del.que")){					
					Friend.delFriend(sender.getName(),
							user.getData().getString("friend.del.que"));
					User.saveAllData();
				}
				else
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "你没有删除好友请求");
				return true;
			} else if (arg2.equalsIgnoreCase("deny")) {
				if (user.getData().contianKey("friend.que")) {
					String friend = user.getData().getString("friend.del.que");
					sender.sendMessage(TString.Prefix("PigRPG", 4) + "你拒绝了"
							+ friend + "的删除好友请求");
					TEntity.toPlayer(friend).sendMessage(
							TString.Prefix("PigRPG", 3) + friend
									+ "拒绝了你的删除好友请求");
				} else
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "你没有删除好友请求");
				return true;
			} else if (arg2.equalsIgnoreCase("help")) {
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
				.suggest("/pr friend help").tooltip("").send(sender);
		return true;
	}
}
