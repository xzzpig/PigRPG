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

public class PigCommand {

	public static boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String arg0 = "help";
		try {
			arg0 = args[0];
		} catch (Exception e) {
		}
		if(arg0.equalsIgnoreCase("help")){
			for(TCommandHelp sub:Help.PIGRPG.getSubCommandHelps()){
				sub.getHelpMessage(Vars.PluginName).send(sender);
			}
			return true;
		}
		else if (arg0.equalsIgnoreCase("friend")) {
			return PigCommand_Friend.onCommand(sender, command, label, args);
		}
		else if (arg0.equalsIgnoreCase("trade")) {
			return PigCommand_PlayerTrade.onCommand(sender, command, label, args);
		}
		else if (arg0.equalsIgnoreCase("test")) {
			System.err.println("\n"+User.getUser((Player) sender).getData().getPrintString());
		}
		new TMessage(TString.Prefix("PigRPG", 4) + "输入/pr help")
				.tooltip(
						TCommandHelp.valueOf(Help.PIGRPG, "pigrpg")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
				.suggest("/pr help").tooltip("").send((Player) sender);
		return true;
	}

}
