package com.github.xzzpig.pigrpg.chests;

import com.github.xzzpig.BukkitTools.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import com.github.xzzpig.pigrpg.teleport.*;

public class WarpChest
{
	public static Inventory getInventory()
	{
		Inventory inv =Bukkit.createInventory(null,(Warp.getWarps().length/9+1)*9 , TString.Color(5)+"Warp列表");
		for(Warp warp : Warp.getWarps()){
			inv.addItem(ItemForChest.warpInfo(warp));
		}
		return inv;
	}
}
