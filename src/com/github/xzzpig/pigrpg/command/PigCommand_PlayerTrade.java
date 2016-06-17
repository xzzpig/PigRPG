package com.github.xzzpig.pigrpg.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.Vars;
import com.github.xzzpig.pigrpg.trade.PlayerTrade;

public class PigCommand_PlayerTrade {
	public static boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		String arg1 = "help";
		try {
			arg1 = args[1];
		} catch (Exception e) {
		}
		if (arg1.equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
					"pigrpg trade").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send(sender);
			return true;
		}
		if (!(Vars.enables.containsKey("Trade") && Vars.enables.get("Trade"))) {// 好友系统未启用
			sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
					+ "交易系统为启用");
			return true;
		}
		if (!(sender instanceof Player)) {// 非玩家返回
			sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
					+ "控制台无法使用该命令");
			return true;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("pigrpg.command.trade")) {
			player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
					+ "你没有权限使用该命令");
			return true;
		}
		if (getarg(args, 1).equalsIgnoreCase("accept")) {
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
