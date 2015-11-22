package com.github.xzzpig.pigrpg.commands;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.chests.*;
import com.github.xzzpig.pigrpg.equip.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class EquipCommand
{
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player)sender;
		if(getarg(args, 1).equalsIgnoreCase("help")){
			for(CommandHelp ch:CommandHelp.valueOf(Help.PIGRPG,"pigrpg equip").getSubCommandHelps())
				ch.getHelpMessage().send((Player)sender);
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
		Vars.nms.newFancyMessage(TString.Prefix("PigRPG",4)+"输入/pr equip help")
			.tooltip(CommandHelp.valueOf(Help.PIGRPG,"pigrpg equip").getDescribe())
			.then(ChatColor.BLUE+""+ChatColor.UNDERLINE+"获取帮助")
			.suggest("/pr equip help")
			.tooltip("")
			.send((Player)sender);
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
