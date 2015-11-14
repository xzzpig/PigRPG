package com.github.xzzpig.pigrpg.commands;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.chests.*;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import com.github.xzzpig.pigrpg.*;

import org.bukkit.*;

import com.github.xzzpig.pigrpg.chat.*;

public class ChatCommand
{
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{

		if(!sender.hasPermission("pigrpg.command.chat.default")){
			sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有权限执行该命令");
			return true;
		}
		if(getarg(args, 1).equalsIgnoreCase("help")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat setaccept   -打开设置接受聊天频道列表");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat change      -打开选择聊天频道列表");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat self        -进入私聊频道");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat ban [关键字] -屏蔽含关键字聊天");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat mute [玩家] <true|false> -设置玩家禁言");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr chat muteall <true|false> -设置群体玩家禁言");
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
		else if(getarg(args, 1).equalsIgnoreCase("mute")){
			String  splayer = getarg(args,2),
					sboolean = getarg(args,3);
			if(splayer.equalsIgnoreCase("")){
				sender.sendMessage(TString.Prefix("PigRPG",4)+"玩家不可为空");
				return true;
			}
			@SuppressWarnings("deprecation")
			User user = User.getUser(Bukkit.getPlayer(splayer));
			
			if(sboolean.equalsIgnoreCase("")){
				user.getChatManager().mute();
				user.sendPluginMessage("&4你被禁言");
			}
			else if(sboolean.equalsIgnoreCase("true")){
				user.getChatManager().mute(true);
				user.sendPluginMessage("&4你被禁言");
			}
			else if(sboolean.equalsIgnoreCase("false")){
				user.getChatManager().mute(false);
				user.sendPluginMessage("&4你已解除禁言");
			}
			else{
				sender.sendMessage(TString.Prefix("PigRPG",4)+"请设置true 或 false");
				return true;
			}
			sender.sendMessage(TString.Prefix("PigRPG",3)+"已改变玩家"+user.getPlayer().getName()+"禁言之状态");
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("muteall")){
			String sboolean = getarg(args,2);

			if(sboolean.equalsIgnoreCase(""))
				Chat.setMuteAll(true);
			else if(sboolean.equalsIgnoreCase("true"))
				Chat.setMuteAll(true);
			else if(sboolean.equalsIgnoreCase("false"))
				Chat.setMuteAll(false);
			else{
				sender.sendMessage(TString.Prefix("PigRPG",4)+"请设置true 或 false");
				return true;
			}
			sender.sendMessage(TString.Prefix("PigRPG",3)+"已改变全体禁言之状态");
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
