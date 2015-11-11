package com.github.xzzpig.pigrpg.commands;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.chests.*;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import com.github.xzzpig.pigrpg.*;

public class ChatCommand
{
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!sender.hasPermission("pigrpg.command.chat.default")){
			sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有权限执行该命令");
			return true;
		}
		if(getarg(args, 1).equalsIgnoreCase("help")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat setaccept -打开设置接受聊天频道列表");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat change    -打开选择聊天频道列表");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat self      -进入私聊频道");
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("setaccept")){
			((Player)sender).openInventory(ChatChannelChest.getAcceptInventory(User.getUser((Player) sender)));
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("change")){
			((Player)sender).openInventory(ChatChannelChest.getChooseInventory(User.getUser((Player) sender)));
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("self")){
			User.getUser((Player)sender).setSelfChat();
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("ban")){
			String ban = getarg(args,2);
			if(ban.equalsIgnoreCase("")){
				sender.sendMessage(TString.Prefix("PigRPG",4)+"禁止内容不可为空");
				return true;
			}
			Vars.banWords.add(ban);
			Voids.saveBanWords();
			sender.sendMessage(TString.Prefix("PigRPG",2)+"已屏蔽关键字"+ban);
			return true;
		}
		sender.sendMessage(TString.Prefix("PigRPG",4)+"输入/pr chat help 获取帮助");
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
