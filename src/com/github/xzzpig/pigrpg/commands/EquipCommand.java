package com.github.xzzpig.pigrpg.commands;
import org.bukkit.command.*;
import com.github.xzzpig.pigrpg.*;
import org.bukkit.entity.*;
import com.github.xzzpig.pigrpg.chests.*;
import com.github.xzzpig.pigrpg.equip.*;

public class EquipCommand
{
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player)sender;
		if(getarg(args, 1).equalsIgnoreCase("help")){
			FanMessage.commandhelp("pr equip open","  打开装备栏").send(player);
			FanMessage.commandhelp("pr equip change","将手中物品变成装备").send(player);
			return true;
		}
		if(getarg(args, 1).equalsIgnoreCase("open")){
			player.openInventory(EquipChest.getInventory(User.getUser(player)));
			return true;
		}
		if(getarg(args, 1).equalsIgnoreCase("change")){
			player.setItemInHand(new Equipment(player.getItemInHand()));
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
