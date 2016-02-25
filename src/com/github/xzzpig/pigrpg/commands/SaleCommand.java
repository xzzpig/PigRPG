package com.github.xzzpig.pigrpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.xzzpig.BukkitTools.TArgsSolver;
import com.github.xzzpig.BukkitTools.TMessage;
import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.CommandHelp;
import com.github.xzzpig.pigrpg.chests.SaleChest;
import com.github.xzzpig.pigrpg.sale.Sale;

public class SaleCommand {
	public static boolean command(CommandSender sender, Command cmd,
			String label, String[] args) {
		Player player = (Player) sender;
		if (getarg(args, 1).equalsIgnoreCase("help")) {
			for (CommandHelp ch : CommandHelp.valueOf(Help.PIGRPG,
					"pigrpg sale").getSubCommandHelps())
				ch.getHelpMessage().send((Player) sender);
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("list")) {
			player.openInventory(SaleChest.getInventory(1));
			return true;
		} else if (getarg(args, 1).equalsIgnoreCase("sell")) {
			ItemStack item = player.getItemInHand();
			int price, n;
			boolean max = false;
			TArgsSolver sargs = new TArgsSolver(args);
			try {
				price = Integer.valueOf(sargs.get("p"));
			} catch (NumberFormatException e) {
				price = 1;
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "注意:价格未添，默认为1");
			}
			try {
				n = Integer.valueOf(sargs.get("n"));
			} catch (NumberFormatException e) {
				n = item.getAmount();
				max = true;
			}
			if (n > item.getAmount()) {
				sender.sendMessage(TString.Prefix("PigRPG", 4) + "错误:你没有这么多物品");
				return true;
			}
			ItemMeta im = item.getItemMeta();
			List<String> lore = im.getLore();
			if (lore == null)
				lore = new ArrayList<String>();
			lore.add("-价格:" + price);
			lore.add("-卖家:" + player.getName());
			im.setLore(lore);
			ItemStack sellitem = item.clone();
			sellitem.setItemMeta(im);
			sellitem.setAmount(n);
			Sale.addItem(sellitem);
			if (max)
				player.setItemInHand(null);
			else {
				item.setAmount(item.getAmount() - n);
				player.setItemInHand(item);
			}
			sender.sendMessage(TString.Prefix("PigRPG", 3) + "拍卖成功");
			return true;
		}
		new TMessage(
				TString.Prefix("PigRPG", 4) + "输入/pr sale help")
				.tooltip(
						CommandHelp.valueOf(Help.PIGRPG, "pigrpg sale")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
				.suggest("/pr sale help").tooltip("").send((Player) sender);
		return true;
	}

	public static String getarg(String[] args, int num) {
		if (args.length <= num) {
			return "";
		}
		return args[num];
	}
}
