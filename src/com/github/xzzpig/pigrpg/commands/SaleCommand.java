package com.github.xzzpig.pigrpg.commands;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.chests.*;
import com.github.xzzpig.pigrpg.teleport.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.inventory.meta.*;
import com.github.xzzpig.pigrpg.sale.*;

public class SaleCommand
{
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player) sender;
		if(getarg(args, 1).equalsIgnoreCase("help")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr sale list -打开拍卖行");
			sender.sendMessage(TString.Prefix("PigRPG",3)+"/pr sale sell <?> -出售手上物品到拍卖行");
			sender.sendMessage("<？>列表(可多个，空格隔开):");
			sender.sendMessage("    -p:[整数_价格](默认1)");
			sender.sendMessage("    -n:[整数_数量](默认 全部)");
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("list")){
			player.openInventory(SaleChest.getInventory(1));
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("sell")){
			ItemStack item = player.getItemInHand();
			int price,n;
			TArgsSolver sargs = new TArgsSolver(args);
			try
			{
				price = Integer.valueOf(sargs.get("p"));
			}
			catch (NumberFormatException e)
			{
				price = 1;
				sender.sendMessage(TString.Prefix("PigRPG",4)+"注意:价格未添，默认为1");
			}
			try
			{
				n = Integer.valueOf(sargs.get("n"));
			}
			catch (NumberFormatException e)
			{
				n = item.getAmount();
			}
			ItemMeta im = item.getItemMeta();
			List<String> lore = im.getLore();
			if(lore == null)
				lore = new ArrayList<String>();
			lore.add("-价格:"+price);
			im.setLore(lore);
			item.setItemMeta(im);
			Sale.addItem(item);
			return true;
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
