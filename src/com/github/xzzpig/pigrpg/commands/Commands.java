package com.github.xzzpig.pigrpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Commands {
	public static boolean command(CommandSender sender,Command cmd,String label,String[] args)  {
		if(label.equalsIgnoreCase("PigRPG")||label.equalsIgnoreCase("pr")){
			if(getarg(args, 0).equalsIgnoreCase("friend")){
				return FriendCommand.command(sender, cmd, label, args);
			}
		}
		return false;
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
