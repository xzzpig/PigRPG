package com.github.xzzpig.pigrpg;

import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.xzzpig.BukkitTools.TString;
import com.gmail.filoghost.holographicdisplays.nms.interfaces.FancyMessage;

public class FanMessage {
	@SuppressWarnings("deprecation")
	public static FancyMessage getBy(ItemStack is) {
		FancyMessage fm = Vars.nms.newFancyMessage(ChatColor.GOLD + "  "
				+ is.getItemMeta().getDisplayName());
		ItemMeta im = is.getItemMeta();
		String tip = ChatColor.RESET + "  物品名称:" + im.getDisplayName() + "\n"
				+ ChatColor.GRAY + "  类型:" + is.getType() + "("
				+ is.getTypeId() + ")" + "\n" + ChatColor.GREEN + "  数量:"
				+ is.getAmount() + "\n" + ChatColor.YELLOW + "  LORE:";
		if (im.getLore() != null)
			for (String lore : im.getLore())
				tip = tip + "\n    " + ChatColor.RESET + lore;
		else
			tip = tip + ChatColor.RED + "无";
		tip = tip + "\n" + ChatColor.YELLOW + "  附魔:";
		Iterator<Entry<Enchantment, Integer>> ir = is.getEnchantments()
				.entrySet().iterator();
		while (ir.hasNext()) {
			Entry<Enchantment, Integer> e = ir.next();
			tip = tip + "\n    " + ChatColor.BLUE + e.getKey().getName() + ":"
					+ e.getValue();
		}
		if (is.getEnchantments().isEmpty())
			tip = tip + ChatColor.RED + "无";
		fm.tooltip(tip);
		return fm;
	}

	public static FancyMessage getBy(String str, boolean highlight) {
		return getBy(Vars.nms.newFancyMessage(""), str, highlight);
	}

	public static FancyMessage getBy(FancyMessage fm, String str,
			boolean highlight) {
		str = str.replaceAll("&", TString.s).replace('^', '醃');
		String[] strs = str.split("醃");
		if (strs.length == 1) {
			return fm.then(str);
		}
		boolean istip = false;
		int i = 1;
		for (String s : strs) {
			if (s.equalsIgnoreCase("")) {
				// continue;
			}
			if (s.contains("#s")) {
				fm.suggest(s.split("#s")[1]);
				s = s.replaceAll("#s" + s.split("#s")[1], "");
			}
			if (s.contains("#c")) {
				fm.command(s.split("#c")[1]);
				s = s.replaceAll("#c" + s.split("#c")[1], "");
			}
			if (!istip) {
				if (highlight && i != strs.length
						&& (!strs[i].equalsIgnoreCase("")))
					s = "§l§n" + rePlaceColor(s) + ChatColor.RESET;
				fm.then(s);
				istip = true;
				i++;
			} else {
				fm.tooltip(s);
				istip = false;
				i++;
			}
		}
		return fm;
	}

	public static FancyMessage commandhelp(String command, String describe) {
		String help = TString.Prefix("PigRPG", 3) + "/" + command + " -"
				+ describe;
		FancyMessage fm = Vars.nms.newFancyMessage(help);
		fm.suggest("/" + command);
		return fm;
	}

	public static FancyMessage helpweb() {
		String help = "https://github.com/xzzpig/PigRPG/wiki/HelpIndx";
		FancyMessage fm = Vars.nms.newFancyMessage("点击打开帮助网页");
		fm.tooltip(ChatColor.UNDERLINE + help).link(help);
		return fm;
	}

	public static String rePlaceColor(String str) {
		for (int i = 0; i < 10; i++) {
			str = str.replaceAll("§" + i, "§" + i + "§l§n");
		}
		return str;
	}
}
