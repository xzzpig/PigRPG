package com.github.xzzpig.pigrpg.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.Vars;

public class PigCommand_Score {
	public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String arg1 = "help";
		try {
			arg1 = args[1];
		} catch (Exception e) {
		}
		if (arg1.equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG, "pigrpg score").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send(sender);
			return true;
		}
		if (!(Vars.enables.containsKey("Score") && Vars.enables.get("Score"))) {
			sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED + "计分板系统为启用");
			return true;
		}
		if (!(sender instanceof Player)) {// 非玩家返回
			sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED + "控制台无法使用该命令");
			return true;
		}
		Player player = (Player) sender;
		if (arg1.equalsIgnoreCase("show")) {
			boolean open;
			try {
				open = args[2].equalsIgnoreCase("true");
			} catch (Exception e) {
				sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED + "[true|false]不可省略");
				return true;
			}
			String target = player.getName();
			try {
				target = args[3];
			} catch (Exception e) {
			}
			if(target.equalsIgnoreCase(player.getName())){
				if(player.hasPermission("pigrpg.command.score.show.self")){
					User.getUser(player).setScoreShow(open);
					sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.GREEN + "你的计分板显示已设置为"+open);
					return true;
				}
				else {
					sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.GREEN +target+ "的计分板显示已设置为"+open);
					return true;
				}
			}
			else {
				if(player.hasPermission("pigrpg.command.score.show.other")){
					User.getUser(target).setScoreShow(open);
					return true;
				}
				else {
					sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED + "你没有权限使用该命令");
					return true;
				}
			}
		}
		return true;
	}
}
