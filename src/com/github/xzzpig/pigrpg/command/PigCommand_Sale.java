package com.github.xzzpig.pigrpg.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.xzzpig.pigapi.bukkit.TArgsSolver;
import com.github.xzzpig.pigapi.bukkit.TCommandHelp;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.Vars;
import com.github.xzzpig.pigrpg.chest.SaleChest;
import com.github.xzzpig.pigrpg.sale.Sale;

public class PigCommand_Sale {
	public static String getarg(String[] args, int num) {
		if (args.length <= num) {
			return "";
		}
		return args[num];
	}

	public static boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		String arg1 = "help";
		try {
			arg1 = args[1];
		} catch (Exception e) {
		}
		if (arg1.equalsIgnoreCase("help")) {
			for (TCommandHelp ch : TCommandHelp.valueOf(Help.PIGRPG,
					"pigrpg sale").getSubCommandHelps())
				ch.getHelpMessage("PigRPG").send(sender);
			return true;
		}
		if (!(Vars.enables.containsKey("Sale") && Vars.enables.get("Sale"))) {// 好友系统未启用
			sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
					+ "拍卖系统为启用");
			return true;
		}
		if (!(sender instanceof Player)) {// 非玩家返回
			sender.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
					+ "控制台无法使用该命令");
			return true;
		}
		Player player = (Player) sender;
		if (arg1.equalsIgnoreCase("list")) {
			if (!player.hasPermission("pigrpg.command.sale.list")) {
				player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
						+ "你没有权限使用该命令");
				return true;
			}
			player.openInventory(SaleChest.getInventory(1));
			return true;
		} else if (arg1.equalsIgnoreCase("sell")) {
			if (!player.hasPermission("pigrpg.command.sale.sell")) {
				player.sendMessage(ChatColor.GOLD + "[PigRPG]" + ChatColor.RED
						+ "你没有权限使用该命令");
				return true;
			}
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
		new TMessage(TString.Prefix("PigRPG", 4) + "输入/pr sale help")
				.tooltip(
						TCommandHelp.valueOf(Help.PIGRPG, "pigrpg sale")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "获取帮助")
				.suggest("/pr sale help").tooltip("").send(sender);
		return true;
	}
}
