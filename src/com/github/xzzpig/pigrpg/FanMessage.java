package com.github.xzzpig.pigrpg;
import com.github.xzzpig.BukkitTools.*;
import com.gmail.filoghost.holographicdisplays.nms.interfaces.*;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class FanMessage
{
	@SuppressWarnings("deprecation")
	public static FancyMessage getBy(ItemStack is){
		FancyMessage fm = Vars.nms.newFancyMessage(ChatColor.GOLD+"  物品信息");
		ItemMeta im = is.getItemMeta();
		String tip = ChatColor.RESET+"  物品名称:"+im.getDisplayName()+"\n"+
				   ChatColor.GRAY +"  类型:"+is.getType()+"("+is.getTypeId()+")"+"\n"+
				   ChatColor.GREEN+"  数量:"+is.getAmount()+"\n"+
				   ChatColor.YELLOW+"  LORE:";
		if(im.getLore() != null)
			for(String lore:im.getLore())
				tip = tip + "\n    " + ChatColor.RESET+lore;
		else
			tip = tip + ChatColor.RED +"无";
		fm.tooltip(tip);
		return fm;
	}
	
	public static FancyMessage commandhelp(String command,String describe){
		String help = TString.Prefix("PigRPG",3)+"/" +command + " -" + describe;
		FancyMessage fm = Vars.nms.newFancyMessage(help);
		fm.suggest("/"+command);
		return fm;
	}
	
	public static FancyMessage helpweb(){
		String help = "https://github.com/xzzpig/PigRPG/wiki/HelpIndx";
		FancyMessage fm = Vars.nms.newFancyMessage("点击打开帮助网页");
		fm.tooltip(ChatColor.UNDERLINE + help).link(help);
		return fm;
	}
	
}
