package com.github.xzzpig.pigrpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.Premissions;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chests.EquipChest;
import com.github.xzzpig.pigrpg.equip.EquipQuality;
import com.github.xzzpig.pigrpg.equip.EquipType;
import com.github.xzzpig.pigrpg.equip.Equipment;
import com.github.xzzpig.pigrpg.equip.PowerLore;

public class EquipCommand {
	@SuppressWarnings("deprecation")
	public static boolean command(CommandSender sender, Command cmd,
			String label, String[] args) {
		Player player = (Player) sender;
		User user = User.getUser(player);
		if (getarg(args, 1).equalsIgnoreCase("debug")) {
			return true;
		}
		if (getarg(args, 1).equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
					"pigrpg equip").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send((Player) sender);
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("list")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_list)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			user.sendPluginMessage("&3特殊Lore列表:");
			for (PowerLore p : PowerLore.powerlores) {
				new TMessage("" + ChatColor.GREEN + ChatColor.UNDERLINE
						+ p.name)
						.tooltip(
								ChatColor.YELLOW + "用法:" + p.getUsage()
										+ "\n点击匹配")
						.suggest("/pr equip addlore " + p.getUsage())
						.send(player);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("open")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_open)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			player.openInventory(EquipChest.getInventory(User.getUser(player)));
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("change")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_change)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			player.setItemInHand(new Equipment(player.getItemInHand(), player));
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("setdisplayname")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_setdisplayname)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			Equipment equip;
			if (player.getItemInHand() == null
					|| player.getItemInHand().getType() == Material.AIR) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "错误:手中物品不可为空");
				return true;
			}
			if (player.getItemInHand() instanceof Equipment)
				equip = (Equipment) player.getItemInHand();
			else
				equip = new Equipment(player.getItemInHand(), player);
			String name = getarg(args, 2);
			if (name.equalsIgnoreCase("")) {
				name = equip.getItemMeta().getDisplayName();
				sender.sendMessage(TString.Prefix("PigRPG", 4)
						+ "错误:装备名称为，已默认设为 " + name);
			}
			equip.setDisplayName(name.replaceAll("&", TString.s));
			player.setItemInHand(equip);
			user.getPlayer().sendMessage(
					TString.Prefix("PigRPG", 2) + "手中装备名称已设置为 " + name);
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("setid")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_setid)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			Equipment equip;
			if (player.getItemInHand() == null
					|| player.getItemInHand().getType() == Material.AIR) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "错误:手中物品不可为空");
				return true;
			}
			if (player.getItemInHand() instanceof Equipment)
				equip = (Equipment) player.getItemInHand();
			else
				equip = new Equipment(player.getItemInHand(), player);
			String sid = getarg(args, 2);
			if (sid.equalsIgnoreCase("")) {
				sid = equip.getTypeId() + "";
				sender.sendMessage(TString.Prefix("PigRPG", 4)
						+ "错误:id为空，已默认设为 " + sid);
			}
			int id = 1;
			try {
				id = Integer.valueOf(sid);
			} catch (NumberFormatException e) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "错误:Id不是整数");
				return true;
			}
			equip.setTypeId(id);
			player.setItemInHand(equip);
			user.getPlayer().sendMessage(
					TString.Prefix("PigRPG", 2) + "手中装备id已设置为 "
							+ equip.getType() + id);
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("qualitylist")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_qualitylist)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "装备品质列表:");
			for (EquipQuality quality : EquipQuality.values()) {
				new TMessage(quality + quality.getName())
						.tooltip(ChatColor.GREEN + quality.name())
						.suggest("/pr equip setquality " + quality.getName())
						.send((Player) sender);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("setquality")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_setquality)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			Equipment equip;
			if (player.getItemInHand() == null
					|| player.getItemInHand().getType() == Material.AIR) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "错误:手中物品不可为空");
				return true;
			}
			if (player.getItemInHand() instanceof Equipment)
				equip = (Equipment) player.getItemInHand();
			else
				equip = new Equipment(player.getItemInHand(), player);
			String squality = getarg(args, 2);
			EquipQuality quality = EquipQuality.fromName(squality);
			if (quality == null)
				quality = EquipQuality.fromColor(squality);
			if (quality == null)
				quality = EquipQuality.valueOf(squality);
			if (quality == null) {
				sender.sendMessage(TString.Prefix("PigRPG", 4)
						+ "错误:未知的装备品质，已默认设为 普通");
				new TMessage(TString.Prefix("PigRPG", 4)
						+ "输入/pr equip qualitylist")
						.tooltip(
								TCommandHelp.valueOf(Help.PIGRPG,
										"pigrpg equip qualitylist")
										.getDescribe())
						.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE
								+ "获取装备品质列表").suggest("/pr equip qualitylist")
						.tooltip("").send((Player) sender);
				quality = EquipQuality.Common;
			}
			equip.setEquipQuality(quality);
			player.setItemInHand(equip);
			sender.sendMessage(TString.Prefix("PigRPG", 2) + "手中装备品质已设置为 "
					+ quality.getName());
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("typelist")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_typelist)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "装备类型列表:");
			for (EquipType type : EquipType.values()) {
				new TMessage(ChatColor.BLUE + type.toString())
						.tooltip(ChatColor.GREEN + "在装备栏中显示:" + type.isShow())
						.suggest("/pr equip settype " + type)
						.send((Player) sender);
			}
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("settype")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_settype)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			Equipment equip;
			if (player.getItemInHand() == null
					|| player.getItemInHand().getType() == Material.AIR) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "错误:手中物品不可为空");
				return true;
			}
			if (player.getItemInHand() instanceof Equipment)
				equip = (Equipment) player.getItemInHand();
			else
				equip = new Equipment(player.getItemInHand(), player);
			String stype = getarg(args, 2);
			EquipType type = EquipType.getFrom(stype);
			if (type == null) {
				sender.sendMessage(TString.Prefix("PigRPG", 4)
						+ "错误:未知的装备类型，已默认设为 无");
				new TMessage(TString.Prefix("PigRPG", 4)
						+ "输入/pr equip typelist")
						.tooltip(
								TCommandHelp.valueOf(Help.PIGRPG,
										"pigrpg equip typelist").getDescribe())
						.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE
								+ "获取装备类型列表").suggest("/pr equip typelist")
						.tooltip("").send((Player) sender);
				type = EquipType.Default;
			}
			equip.setEquiptype(type).reBuildLore();
			player.setItemInHand(equip);
			sender.sendMessage(TString.Prefix("PigRPG", 2) + "手中装备类型已设置为 "
					+ type);
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("addlore")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_addlore)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			if (getarg(args, 2).equalsIgnoreCase("")) {
				new TMessage(TString.Prefix("PigRPG", 4)
						+ "用法错误,输入/pr equip help")
						.tooltip(
								TCommandHelp.valueOf(Help.PIGRPG,
										"pigrpg equip").getDescribe())
						.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE
								+ "获取帮助").suggest("/pr equip help").tooltip("")
						.send((Player) sender);
				return true;
			}
			ItemStack is = player.getItemInHand();
			if (is == null) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "错误:手中物品不可为空");
				return true;
			}
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			if (lore == null)
				lore = new ArrayList<String>();
			int line = -1;
			if (!getarg(args, 3).equalsIgnoreCase("")) {
				try {
					line = Integer.valueOf(args[3]);
				} catch (NumberFormatException e) {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "错误:行数不是整数");
					return false;
				}
			}
			if (line == -1) {
				lore.add(args[2].replaceAll("_", " ").replaceAll("&", "§"));
			} else {
				if (lore.size() <= 0) {
					sender.sendMessage((TString.Prefix("PigRPG", 4) + "lore行数应大于0"));
					return true;
				}
				lore.add(line, args[2].replaceAll("_", " "));
			}
			im.setLore(lore);
			is.setItemMeta(im);
			player.setItemInHand(new Equipment(is, player));
			sender.sendMessage((TString.Prefix("PigRPG", 4) + "lore已修改"));
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("dellore")) {
			if (!User.getUser((Player) sender).hasPremission(
					Premissions.pigrpg_command_equip_dellore)) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限执行该命令");
				return true;
			}
			if (getarg(args, 2).equalsIgnoreCase("")) {
				new TMessage(TString.Prefix("PigRPG", 4)
						+ "用法错误,输入/pr equip help")
						.tooltip(
								TCommandHelp.valueOf(Help.PIGRPG,
										"pigrpg equip").getDescribe())
						.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE
								+ "获取帮助").suggest("/pr equip help").tooltip("")
						.send((Player) sender);
				return true;
			}
			int line = 0;
			if (!getarg(args, 2).equalsIgnoreCase("")) {
				try {
					line = Integer.valueOf(args[2]);
				} catch (NumberFormatException e) {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "错误:行数不是整数");
					return true;
				}
			}
			ItemStack is = player.getItemInHand();
			if (is == null) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "错误:手中物品不可为空");
				return true;
			}
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			if (lore == null)
				lore = new ArrayList<String>();
			if (lore.size() < line) {
				sender.sendMessage((TString.Prefix("PigRPG", 4) + "lore行数过大"));
				return true;
			}
			if (lore.size() <= 0) {
				sender.sendMessage((TString.Prefix("PigRPG", 4) + "lore行数应大于0"));
				return true;
			}
			lore.remove(line);
			im.setLore(lore);
			is.setItemMeta(im);
			player.setItemInHand(new Equipment(is, player));
			sender.sendMessage((TString.Prefix("PigRPG", 4) + "lore已删除"));
			return true;
		}
		new TMessage(TString.Prefix("PigRPG", 4) + "输入/pr equip help")
				.tooltip(
						TCommandHelp.valueOf(Help.PIGRPG, "pigrpg equip")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
				.suggest("/pr equip help").tooltip("").send((Player) sender);
		return true;
	}

	public static String getarg(String[] args, int num) {
		if (args.length <= num) {
			return "";
		}
		return args[num];
	}
}
