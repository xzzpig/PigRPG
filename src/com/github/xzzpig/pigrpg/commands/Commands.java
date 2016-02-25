package com.github.xzzpig.pigrpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.xzzpig.BukkitTools.TMessage;
import com.github.xzzpig.BukkitTools.TPlayer;
import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.CommandHelp;
import com.github.xzzpig.pigrpg.FanMessage;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.Vars;
import com.github.xzzpig.pigrpg.equip.PowerLore;

public class Commands {
	@SuppressWarnings("deprecation")
	public static boolean command(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (label.equalsIgnoreCase("PigRPG") || label.equalsIgnoreCase("pr")) {
			if (getarg(args, 0).equalsIgnoreCase("debug")) {
				User user = User.getUser((Player) sender);
				user.getPlayer().teleport(
						user.getPlayer().getLocation().getChunk()
								.getBlock(0, 0, 0).getLocation());
				return true;
			}
			if (getarg(args, 0).equalsIgnoreCase("help")) {
				if (sender instanceof Player) {
					for (CommandHelp ch : CommandHelp.valueOf(Help.PIGRPG,
							"pigrpg").getSubCommandHelps())
						ch.getHelpMessage().send((Player) sender);
					FanMessage.helpweb().send((Player) sender);
					new TMessage(
							ChatColor.GREEN + "" + ChatColor.UNDERLINE
									+ "点我显示所有命令").tooltip("")
							.command("/pr helpall").send((Player) sender);
					return true;
				}
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "该命令只能由玩家使用");
				return true;
			} else if (getarg(args, 0).equalsIgnoreCase("helpall")) {
				if (sender instanceof Player)
					for (CommandHelp ch : CommandHelp.valueOf(Help.PIGRPG,
							"pigrpg").getAllSubs())
						ch.getHelpMessage().send((Player) sender);
				else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
					return true;
				}
			} else if (getarg(args, 0).equalsIgnoreCase("reload")) {
				Plugin pigrpg = Bukkit.getPluginManager().getPlugin("PigRPG");
				Bukkit.getPluginManager().disablePlugin(pigrpg);
				Bukkit.getPluginManager().enablePlugin(pigrpg);
				PowerLore.loadpower();
				sender.sendMessage("PigRPG重载完成");
				return true;
			} else if (getarg(args, 0).equalsIgnoreCase("friend")) {
				if (!Vars.FriendSystem) {
					sender.sendMessage(TString.Prefix("PigRPG", 4) + "该系统未开启");
					return false;
				}
				if (sender instanceof Player)
					return FriendCommand.command(sender, cmd, label, args);
				else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
					return true;
				}
			} else if (getarg(args, 0).equalsIgnoreCase("trade")) {
				if (!Vars.TradeSystem) {
					sender.sendMessage(TString.Prefix("PigRPG", 4) + "该系统未开启");
					return false;
				}
				if (sender instanceof Player)
					return PlayerTradeCommand.command(sender, cmd, label, args);
				else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
					return true;
				}
			} else if (getarg(args, 0).equalsIgnoreCase("chat")) {
				if (!Vars.ChatSystem) {
					sender.sendMessage(TString.Prefix("PigRPG", 4) + "该系统未开启");
					return false;
				}
				if (sender instanceof Player)
					return ChatCommand.command(sender, cmd, label, args);
				else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
					return true;
				}

			} else if (getarg(args, 0).equalsIgnoreCase("tel")) {
				if (!Vars.TeleportSystem) {
					sender.sendMessage(TString.Prefix("PigRPG", 4) + "该系统未开启");
					return false;
				}
				if (sender instanceof Player)
					return TeleportCommand.command(sender, cmd, label, args);
				else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
					return true;
				}

			} else if (getarg(args, 0).equalsIgnoreCase("sale")) {
				if (!Vars.SaleSystem) {
					sender.sendMessage(TString.Prefix("PigRPG", 4) + "该系统未开启");
					return false;
				}
				if (sender instanceof Player)
					return SaleCommand.command(sender, cmd, label, args);
				else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
					return true;
				}
			} else if (getarg(args, 0).equalsIgnoreCase("equip")) {
				if (!Vars.EquipSystem) {
					sender.sendMessage(TString.Prefix("PigRPG", 4) + "该系统未开启");
					return false;
				}
				if (sender instanceof Player)
					return EquipCommand.command(sender, cmd, label, args);
				else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
					return true;
				}
			} else if (getarg(args, 0).equalsIgnoreCase("list")) {
				if (sender instanceof Player)
					return ListCommand.command(sender, cmd, label, args);
				else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
					return true;
				}
			} else if (getarg(args, 0).equalsIgnoreCase("team")) {
				if (!Vars.TeamSystem) {
					sender.sendMessage(TString.Prefix("PigRPG", 4) + "该系统未开启");
					return false;
				}
				if (sender instanceof Player)
					return TeamCommand.command(sender, cmd, label, args);
				else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
					return true;
				}
			} else if (getarg(args, 0).equalsIgnoreCase("showhand")) {
				if (sender instanceof Player) {
					String target = getarg(args, 1);
					Player player = (Player) sender;
					if (player.getItemInHand() == null
							|| player.getItemInHand().getType() == Material.AIR) {
						User.getUser(player).sendPluginMessage("&4无法展示 空气");
						return true;
					}
					TMessage fm = FanMessage.getBy(player.getItemInHand())
							.then(ChatColor.BLUE + "由玩家" + player.getName()
									+ "展示");
					if (target.equalsIgnoreCase("")) {
						for (Player p : TPlayer.getAllPlayers()) {
							User.getUser(p).sendPluginMessage(
									"玩家" + player.getName() + "展示物品:");
							fm.send(p);
						}
					} else {
						User.getUser(Bukkit.getPlayer(target))
								.sendPluginMessage(
										"玩家" + player.getName() + "展示物品:");
						fm.send(Bukkit.getPlayer(target));
					}
				} else {
					sender.sendMessage(TString.Prefix("PigRPG", 4)
							+ "该命令只能由玩家使用");
				}
				return true;
			}
		}
		new TMessage(TString.Prefix("PigRPG", 4) + "输入/pr help")
				.tooltip(
						CommandHelp.valueOf(Help.PIGRPG, "pigrpg")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
				.suggest("/pr help").tooltip("").send((Player) sender);
		return true;
	}

	public static String getarg(String[] args, int num) {
		if (args.length <= num) {
			return "";
		}
		return args[num];
	}
}
