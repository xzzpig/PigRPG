package com.github.xzzpig.pigrpg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.Premissions;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.trade.PlayerTrade;

public class PlayerTradeCommand {
	public static boolean command(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (!User.getUser((Player) sender).hasPremission(
				Premissions.pigrpg_trade_default)) {
			sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
			return true;
		}
		Player player = (Player) sender;
		if (getarg(args, 1).equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
					"pigrpg trade").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send((Player) sender);
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("accept")) {
			PlayerTrade trade = PlayerTrade.getTrade(player);
			if (trade != null)
				trade.startTrade();
			else
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有交易请求");
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("deny")) {
			PlayerTrade trade = PlayerTrade.getTrade(player);
			if (trade != null) {
				Player launcher = trade.getLauncher();
				trade.stopTrade();
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你拒绝了"
						+ launcher.getName() + "的交易请求");
				launcher.sendMessage(TString.Prefix("PigRPG", 3)
						+ player.getName() + "拒绝了你的交易请求");
			} else
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有交易请求");
			return true;
		}
		new TMessage(TString.Prefix("PigRPG", 4) + "输入/pr trade help")
				.tooltip(
						TCommandHelp.valueOf(Help.PIGRPG, "pigrpg trade")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
				.suggest("/pr trade help").tooltip("").send((Player) sender);
		return true;
	}

	public static String getarg(String[] args, int num) {
		if (args.length <= num) {
			return "";
		}
		return args[num];
	}
}
