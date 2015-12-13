package com.github.xzzpig.pigrpg.commands;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.chests.*;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import com.github.xzzpig.pigrpg.*;

import org.bukkit.*;

import com.github.xzzpig.pigrpg.chat.*;
import me.confuser.barapi.*;

public class ChatCommand
{
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!User.getUser((Player)sender).hasPremission(Premissions.pigrpg_command_chat_default)){
			sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有权限执行该命令");
			return true;
		}
		if(getarg(args, 1).equalsIgnoreCase("help")){
			for(CommandHelp ch:CommandHelp.valueOf(Help.PIGRPG,"pigrpg chat").getSubCommandHelps())
				ch.getHelpMessage().send((Player)sender);
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
				CommandHelp.valueOf(Help.PIGRPG,"pigrpg chat mute").getHelpMessage().send((Player)sender);
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
				Vars.nms.newFancyMessage(TString.Prefix("PigRPG",4)+"请设置")
					.then(ChatColor.GREEN.toString()+ChatColor.UNDERLINE+"true")
					.tooltip("/pr chat mute "+splayer+" true")
					.suggest("/pr chat mute "+splayer+" true")
					.then(ChatColor.RED+" 或 ")
					.then(ChatColor.GREEN.toString()+ChatColor.UNDERLINE+"false")
					.tooltip("/pr chat mute "+splayer+" false")
					.suggest("/pr chat mute "+splayer+" false")
					.send((Player)sender);
				return true;
			}
			sender.sendMessage(TString.Prefix("PigRPG",3)+"已改变玩家"+user.getPlayer().getName()+"禁言的状态");
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
				Vars.nms.newFancyMessage(TString.Prefix("PigRPG",4)+"请设置")
					.then(ChatColor.GREEN.toString()+ChatColor.UNDERLINE+"true")
					.tooltip("/pr chat muteall true")
					.suggest("/pr chat muteall true")
					.then(ChatColor.RED+" 或 ")
					.then(ChatColor.GREEN.toString()+ChatColor.UNDERLINE+"false")
					.tooltip("/pr chat muteall false")
					.suggest("/pr chat muteall false")
					.send((Player)sender);
				return true;
			}
			sender.sendMessage(TString.Prefix("PigRPG",3)+"已改变全体禁言之状态");
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("broad")){
			String message = getarg(args,2);
			BarAPI.setMessage("[广播]"+message.replaceAll("_"," ").replaceAll("&",TString.s),5);
			return true;
		}
		Vars.nms.newFancyMessage(TString.Prefix("PigRPG",4)+"输入/pr chat help")
			.tooltip(CommandHelp.valueOf(Help.PIGRPG,"pigrpg chat").getDescribe())
			.then(ChatColor.BLUE+""+ChatColor.UNDERLINE+"获取帮助")
			.suggest("/pr chat help")
			.tooltip("")
			.send((Player)sender);
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
