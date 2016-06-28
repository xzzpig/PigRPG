package com.github.xzzpig.pigrpg.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.Vars;
import com.github.xzzpig.pigrpg.chest.WarpChest;
import com.github.xzzpig.pigrpg.teleport.Warp;

public class PigCommand_Teleport {
	public static String getarg(String[] args, int num) {
		if (args.length <= num) {
			return "";
		}
		return args[num];
	}

	public static boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		Player player = (Player) sender;
		String arg1 = "help";
		try {
			arg1 = args[1];
		} catch (Exception e) {
		}
		if (arg1.equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
					"pigrpg tel").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send((Player) sender);
			return true;
		} else if (arg1.equalsIgnoreCase("list")) {
			if (!player.hasPermission("pigrpg.command.teleport.list")) {
				player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
						+ "你没有权限使用该命令");
				return true;
			}
			if (!Vars.pdata.getBoolean("tel.warp.enbale")) {
				player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
						+ "传送系统的Warp未启用");
				return true;
			}
			player.openInventory(WarpChest.getInventory(User.getUser(player)));
			return true;
		} else if (arg1.equalsIgnoreCase("setwarp")) {
			String arg2 = "";
			try {
				arg2 = args[2];
			} catch (Exception e) {
			}
			if (arg2.equalsIgnoreCase("")) {
				new TMessage(TString.Prefix("PigRPG", 4)
						+ "用法错误，输入/pr tel help")
						.tooltip(
								TCommandHelp.valueOf(Help.PIGRPG,
										"pigrpg tel setwarp").getDescribe())
						.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE
								+ "获取帮助").suggest("/pr tel help").tooltip("")
						.send((Player) sender);
				return true;
			}
			if (!player.hasPermission("pigrpg.command.teleport.setwarp")) {
				player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
						+ "你没有权限使用该命令");
				return true;
			}
			new Warp(getarg(args, 2), player.getLocation()).save();
		}
		new TMessage(TString.Prefix("PigRPG", 4) + "输入/pr tel help")
				.tooltip(
						TCommandHelp.valueOf(Help.PIGRPG, "pigrpg tel")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
				.suggest("/pr tel help").tooltip("").send((Player) sender);
		return true;
	}
}
