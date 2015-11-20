package com.github.xzzpig.pigrpg;
import com.gmail.filoghost.holographicdisplays.nms.interfaces.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class FanMessage
{
	public static FancyMessage getBy(ItemStack is){
		FancyMessage fm = Vars.nms.newFancyMessage(ChatColor.GOLD+"物品信息:");
		ItemMeta im = is.getItemMeta();
		fm.then(ChatColor.RESET+"  物品名称:"+im.getDisplayName());
		fm.then(ChatColor.GRAY +"  类型:"+is.getType()+"("+is.getTypeId()+")");
		fm.then(ChatColor.GREEN+"  数量:"+is.getAmount());
		fm.then(ChatColor.YELLOW+"  LORE:");
		if(im.getLore() != null)
			for(String lore:im.getLore())
				fm.then(ChatColor.RESET+lore);
		else
			fm.then(ChatColor.RED+"无");
		return fm;
	}
}
