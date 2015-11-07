package com.github.xzzpig.pigrpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.BukkitTools.TString;

public class Commands {
	public static boolean command(CommandSender sender,Command cmd,String label,String[] args)  {
		if(label.equalsIgnoreCase("PigRPG")||label.equalsIgnoreCase("pr")){
			if(getarg(args, 0).equalsIgnoreCase("help")){
				sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr friend -获取 好友系统 的帮助");
				sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr trade  -获取 交易系统 的帮助");
				sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat   -获取 聊天系统 的帮助");
				sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr tel    -获取 传送系统 的帮助");
				return true;
			}
			else if(getarg(args, 0).equalsIgnoreCase("friend")){
				if(sender instanceof Player)
					return FriendCommand.command(sender, cmd, label, args);
				else{
					sender.sendMessage(TString.Prefix("PigRPG",4)+"该命令只能由玩家使用");
					return true;
				}
					
			}
			else if(getarg(args, 0).equalsIgnoreCase("trade")){
				if(sender instanceof Player)
					return  PlayerTradeCommand.command(sender, cmd, label, args);
				else{
					sender.sendMessage(TString.Prefix("PigRPG",4)+"该命令只能由玩家使用");
					return true;
				}
			}
			else if(getarg(args, 0).equalsIgnoreCase("chat")){
				if(sender instanceof Player)
					return  ChatCommand.command(sender, cmd, label, args);
				else{
					sender.sendMessage(TString.Prefix("PigRPG",4)+"该命令只能由玩家使用");
					return true;
				}

			}
			else if(getarg(args, 0).equalsIgnoreCase("tel")){
				if(sender instanceof Player)
					return TeleportCommand.command(sender, cmd, label, args);
				else{
					sender.sendMessage(TString.Prefix("PigRPG",4)+"该命令只能由玩家使用");
					return true;
				}

			}
		}
		sender.sendMessage(TString.Prefix("PigRPG",4)+"输入/pr help 获取帮助");
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
