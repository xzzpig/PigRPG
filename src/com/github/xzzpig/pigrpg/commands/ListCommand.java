package com.github.xzzpig.pigrpg.commands;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.PowerRunTime;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;

public class ListCommand
{
	public static boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!User.getUser((Player)sender).hasPremission(Premissions.pigrpg_command_list)){
			sender.sendMessage(TString.Prefix("PigRPG",4)+"你没有权限执行该命令");
			return true;
		}
		Player player = (Player) sender;
		User user = User.getUser(player);
		if(getarg(args, 1).equalsIgnoreCase("help")){
			for(CommandHelp ch:CommandHelp.valueOf(Help.PIGRPG,"pigrpg list").getSubCommandHelps())
				ch.getHelpMessage().send((Player)sender);
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("lore")){
			user.sendPluginMessage("&3特殊Lore列表:");
			for(PowerLore p:PowerLore.powerlores){
				Vars.nms.newFancyMessage(""+ChatColor.GREEN + ChatColor.UNDERLINE + p.name).
					tooltip(ChatColor.YELLOW+"用法:"+p.getUsage()+"\n点击匹配").
					suggest("/pr equip addlore "+p.getUsage().replace(' ', '_')).
					send(player);
			}
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("runtime")){
			user.sendPluginMessage("&3Lore触发时间参数:");
			for(PowerRunTime p:PowerRunTime.values()){
				Vars.nms.newFancyMessage(""+ChatColor.GREEN + ChatColor.UNDERLINE + p.toString()).
					send(player);
			}
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("quality")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"装备品质列表:");
			for(EquipQuality quality:EquipQuality.values()){
				Vars.nms.newFancyMessage(quality+quality.getName())
					.tooltip(ChatColor.GREEN + quality.name())
					.suggest("/pr equip setquality "+quality.getName())
					.send((Player)sender);
			}
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("type")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"装备类型列表:");
			for(EquipType type:EquipType.values()){
				Vars.nms.newFancyMessage(ChatColor.BLUE + type.toString())
					.tooltip(ChatColor.GREEN + "在装备栏中显示:" + type.isShow())
					.suggest("/pr equip settype "+type)
					.send((Player)sender);
			}
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("effect")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"粒子效果列表:");
			for(Effect effect:Effect.values()){
				Vars.nms.newFancyMessage(ChatColor.BLUE + effect.toString())
					.tooltip(ChatColor.GREEN + "点击可匹配命令:"+"/pr equip addlore -Effect:"+effect)
					.suggest("/pr equip addlore -Effect:"+effect)
					.send((Player)sender);
			}
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("sound")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"音效列表:");
			for(Sound sound:Sound.values()){
				Vars.nms.newFancyMessage(ChatColor.BLUE + sound.toString())
					.tooltip(ChatColor.GREEN + "点击可匹配命令:"+"/pr equip addlore -Sound:"+sound)
					.suggest("/pr equip addlore -Sound:"+sound)
					.send((Player)sender);
			}
			return true;
		}
		else if(getarg(args, 1).equalsIgnoreCase("potion")){
			sender.sendMessage(TString.Prefix("PigRPG",3)+"药水效果列表:");
			for(PotionEffectType potion:PotionEffectType.values()){
				Vars.nms.newFancyMessage(ChatColor.BLUE + potion.toString())
					.tooltip(ChatColor.RED + "该lore过于复杂,无法匹配")
					.send((Player)sender);
			}
			return true;
		}
		Vars.nms.newFancyMessage(TString.Prefix("PigRPG",4)+"输入/pr list help")
			.tooltip(CommandHelp.valueOf(Help.PIGRPG,"pigrpg list").getDescribe())
			.then(ChatColor.BLUE+""+ChatColor.UNDERLINE+"获取帮助")
			.suggest("/pr list help")
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
