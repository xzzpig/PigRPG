package com.github.xzzpig.pigrpg.chests;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.chests.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import com.github.xzzpig.pigrpg.sale.*;

public class SaleChest
{
	public static Inventory getInventory(int page)
	{
		if(page <1)
			page = 1;
		Inventory inv =Bukkit.createInventory(null,54 , TString.Color(5)+"拍卖行(第"+page+"页)");
		for(int i = 0;(i<Sale.items.size()&&i<52);i++){
			inv.addItem(Sale.items.get(i+(page-1)*52));
		}
		inv.setItem(52,ItemForChest.customItem("上一页",1,null));
		inv.setItem(53,ItemForChest.customItem("下一页",1,null));
		return inv;
	}
}
