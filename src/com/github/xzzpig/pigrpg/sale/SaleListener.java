package com.github.xzzpig.pigrpg.sale;

import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.chests.*;

public class SaleListener implements Listener
{	
	@EventHandler
	public void onChangePage(InventoryClickEvent event){
		if(!event.getInventory().getTitle().contains("拍卖行"))
			return;
		event.setCancelled(true);
		if(!event.isLeftClick())
			return;
		Inventory inv = event.getInventory();
		int iitem = event.getRawSlot();
		int page = 1;
		try
		{
			page = Integer.valueOf(TString.sub(inv.getName(), "(第", "页)"));
		}
		catch (NumberFormatException e)
		{
			event.getWhoClicked().closeInventory();
			return;
		}
		switch (iitem){
			case 52:
				event.getWhoClicked().openInventory(SaleChest.getInventory(page-1));
				break;
			case 53:
				event.getWhoClicked().openInventory(SaleChest.getInventory(page+1));
				break;
		}
	}
}
