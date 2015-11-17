package com.github.xzzpig.pigrpg.commands;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.trade.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class PlayerTradeCommand
{
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!User.getUser((Player)sender).hasPremission(Premissions.pigrpg_trade_default)){
			sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有权限执行该命令");
			return true;
		}
		Player player = (Player) sender;
		if(getarg(args, 1).equalsIgnoreCase("help")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr trade accept -接受交易请求");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr trade deny   -拒绝交易请求");
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("accept")){
			PlayerTrade trade = PlayerTrade.getTrade(player);
			if(trade != null)
				trade.startTrade();
			else
				sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有交易请求");
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("deny")){
			PlayerTrade trade = PlayerTrade.getTrade(player);
			if (trade != null){
				Player launcher = trade.getLauncher();
				trade.stopTrade();
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你拒绝了" + launcher.getName() + "的交易请求");
				launcher.sendMessage(TString.Prefix("PigRPG",3)+player.getName()+"拒绝了你的交易请求");	
			}
			else
				sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有交易请求");
			return true;
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
