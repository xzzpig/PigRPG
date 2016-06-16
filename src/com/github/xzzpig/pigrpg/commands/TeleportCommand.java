package com.github.xzzpig.pigrpg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chests.WarpChest;
import com.github.xzzpig.pigrpg.teleport.Warp;

public class TeleportCommand {
	public static boolean command(CommandSender sender, Command cmd,
			String label, String[] args) {
		Player player = (Player) sender;
		if (getarg(args, 1).equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
					"pigrpg tel").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send((Player) sender);
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("list")) {
			player.openInventory(WarpChest.getInventory(User.getUser(player)));
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("setwarp")) {
			if (getarg(args, 2).equalsIgnoreCase("")) {
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

	public static String getarg(String[] args, int num) {
		if (args.length <= num) {
			return "";
		}
		return args[num];
	}
}
