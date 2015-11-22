package com.github.xzzpig.pigrpg.commands;

import com.github.xzzpig.BukkitTools.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import com.github.xzzpig.pigrpg.teleport.*;
import com.github.xzzpig.pigrpg.chests.*;
import com.github.xzzpig.pigrpg.*;

public class TeleportCommand
{
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player) sender;
		if(getarg(args, 1).equalsIgnoreCase("help")){
			for(CommandHelp ch:CommandHelp.valueOf(Help.PIGRPG,"pigrpg tel").getSubCommandHelps())
				ch.getHelpMessage().send((Player)sender);
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("list")){
			player.openInventory(WarpChest.getInventory());
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("setwarp")){
			if(getarg(args,2).equalsIgnoreCase("")){
				sender.sendMessage(TString.Prefix("PigRPG",4)+"用法错误，输入/pr tel help 获取帮助");
				return true;
			}
			new Warp(getarg(args,2),player.getLocation()).save();
		}
		sender.sendMessage(TString.Prefix("PigRPG",4)+"输入/pr tel help 获取帮助");
		return true;
	}

	public static String getarg(String[] args,int num)
	{
		if(args.length<=num)
		{
			return "";
		}
		return args[num];
	}
}
