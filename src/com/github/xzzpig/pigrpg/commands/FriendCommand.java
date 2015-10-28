package com.github.xzzpig.pigrpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.BukkitTools.TEntity;
import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.chests.FriendListChest;
import com.github.xzzpig.pigrpg.friend.Friend;

public class FriendCommand {
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(getarg(args, 1).equalsIgnoreCase("help")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr friend list   -打开好友列表");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr friend del    -处理好友删除请求");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr friend accept -接受好友请求");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr friend deny   -拒绝好友请求");
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("list")){
			FriendListChest.getInventory((Player) sender);
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("accept")){
			if(Friend.friendque.containsKey(sender.getName()))
				Friend.addFriend(sender.getName(), Friend.friendque.get(sender.getName()));
			else
				sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有好友请求");
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("deny")){
			if(Friend.friendque.containsKey(sender.getName())){
				String friend = Friend.friendque.get(sender.getName());
				Friend.friendque.remove(sender.getName());
				sender.sendMessage(TString.Prefix("PigRPG",4)+"你拒绝了"+friend+"的好友请求");
				TEntity.toPlayer(friend).sendMessage(TString.Prefix("PigRPG",3)+friend+"拒绝了你的好友请求");	
			}
			else
				sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有好友请求");
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("del")){
			if(getarg(args, 2).equalsIgnoreCase("accept")){
				if(Friend.friendque.containsKey(sender.getName()))
					Friend.delFriend(sender.getName(), Friend.friendque.get(sender.getName()));
				else
					sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有删除好友请求");
				return true;
			}
			else if(getarg(args, 2).equalsIgnoreCase("deny")){
				if(Friend.friendque.containsKey(sender.getName())){
					String friend = Friend.friendque.get(sender.getName());
					Friend.friendque.remove(sender.getName());
					sender.sendMessage(TString.Prefix("PigRPG",4)+"你拒绝了"+friend+"的删除好友请求");
					TEntity.toPlayer(friend).sendMessage(TString.Prefix("PigRPG",3)+friend+"拒绝了你的删除好友请求");	
				}
				else
					sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有删除好友请求");
				return true;
			}
		}
		sender.sendMessage(TString.Prefix("PigRPG",4)+"输入/pr friend del help 获取帮助");
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
