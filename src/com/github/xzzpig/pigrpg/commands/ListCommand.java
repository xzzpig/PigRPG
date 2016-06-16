package com.github.xzzpig.pigrpg.commands;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.Premissions;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.equip.EquipQuality;
import com.github.xzzpig.pigrpg.equip.EquipType;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.mob.CustomMob;
import com.github.xzzpig.pigrpg.power.PowerRunTime;

public class ListCommand {
	public static boolean command(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (!User.getUser((Player) sender).hasPremission(
				Premissions.pigrpg_command_list)) {
			sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
			return true;
		}
		Player player = (Player) sender;
		User user = User.getUser(player);
		if (getarg(args, 1).equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
					"pigrpg list").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send((Player) sender);
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("lore")) {
			user.sendPluginMessage("&3特殊Lore列表:");
			for (PowerLore p : PowerLore.powerlores) {
				new TMessage("" + ChatColor.GREEN + ChatColor.UNDERLINE
						+ p.name)
						.tooltip(
								ChatColor.YELLOW + "用法:" + p.getUsage()
										+ "\n点击匹配")
						.suggest(
								"/pr equip addlore "
										+ p.getUsage().replace(' ', '_'))
						.send(player);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("runtime")) {
			user.sendPluginMessage("&3Lore触发时间参数:");
			for (PowerRunTime p : PowerRunTime.values()) {
				new TMessage("" + ChatColor.GREEN + ChatColor.UNDERLINE
						+ p.toString()).send(player);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("quality")) {
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "装备品质列表:");
			for (EquipQuality quality : EquipQuality.values()) {
				new TMessage(quality + quality.getName())
						.tooltip(ChatColor.GREEN + quality.name())
						.suggest("/pr equip setquality " + quality.getName())
						.send((Player) sender);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("type")) {
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "装备类型列表:");
			for (EquipType type : EquipType.values()) {
				new TMessage(ChatColor.BLUE + type.toString())
						.tooltip(ChatColor.GREEN + "在装备栏中显示:" + type.isShow())
						.suggest("/pr equip settype " + type)
						.send((Player) sender);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("effect")) {
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "粒子效果列表:");
			for (Effect effect : Effect.values()) {
				new TMessage(ChatColor.BLUE + effect.toString())
						.tooltip(
								ChatColor.GREEN + "点击可匹配命令:"
										+ "/pr equip addlore -Effect:" + effect)
						.suggest("/pr equip addlore -Effect:" + effect)
						.send((Player) sender);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("sound")) {
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "音效列表:");
			for (Sound sound : Sound.values()) {
				new TMessage(ChatColor.BLUE + sound.toString())
						.tooltip(
								ChatColor.GREEN + "点击可匹配命令:"
										+ "/pr equip addlore -Sound:" + sound)
						.suggest("/pr equip addlore -Sound:" + sound)
						.send((Player) sender);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("potion")) {
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "药水效果列表:");
			for (PotionEffectType potion : PotionEffectType.values()) {
				new TMessage(ChatColor.BLUE + potion.toString()).tooltip(
						ChatColor.RED + "该lore过于复杂,无法匹配").send((Player) sender);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("entitytype")) {
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "生物类型列表:");
			for (EntityType entitytype : EntityType.values()) {
				new TMessage(ChatColor.BLUE + entitytype.toString() + "("
						+ CustomMob.mobname.get(entitytype) + ")").tooltip(
						ChatColor.GREEN + "可用于配置mob.yml-moblist|mobname").send(
						(Player) sender);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("color")) {
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "颜色列表:");
			for (ChatColor color : ChatColor.values()) {
				new TMessage(color + color.name() + ":" + color.getChar())
						.send((Player) sender);
			}
			return true;
		}

		new TMessage(TString.Prefix("PigRPG", 4) + "输入/pr list help")
				.tooltip(
						TCommandHelp.valueOf(Help.PIGRPG, "pigrpg list")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
				.suggest("/pr list help").tooltip("").send((Player) sender);
		return false;
	}

	public static String getarg(String[] args, int num) {
		if (args.length <= num) {
			return "";
		}
		return args[num];
	}
}
