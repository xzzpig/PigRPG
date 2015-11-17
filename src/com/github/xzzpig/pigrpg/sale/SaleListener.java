package com.github.xzzpig.pigrpg.sale;

import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.chests.*;
import com.github.xzzpig.pigrpg.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;
import java.util.*;

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
	
	@EventHandler
	public void onBuyClick(InventoryClickEvent event){
		if(!event.getInventory().getTitle().contains("拍卖行"))
			return;
		event.setCancelled(true);
		if(!event.isLeftClick())
			return;
		User user = User.getUser((Player)event.getWhoClicked());
		Inventory inv = event.getInventory();
		int page = 1;
		int price = 1;
		String seller = new TArgsSolver(event.getCurrentItem().getItemMeta().getLore().toArray(new String[0])).get("卖家");
		try
		{
			page = Integer.valueOf(TString.sub(inv.getName(), "(第", "页)"));
			price = Integer.valueOf(new TArgsSolver(event.getCurrentItem().getItemMeta().getLore().toArray(new String[0])).get("价格"));
		}
		catch (NumberFormatException e)
		{
			event.getWhoClicked().closeInventory();
			user.sendPluginMessage("&4页数或价格错误");
			return;
		}
		if(!user.getEcoAPI().pay(seller,(double)price)){
			user.sendPluginMessage("&4你没有足够的钱");
			return;
		}
		user.getPlayer().getInventory().addItem(event.getCurrentItem());
		ItemStack is = event.getCurrentItem();
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
		lore.remove(lore.size()-1);
		lore.remove(lore.size()-1);
		Sale.removeItem(event.getCurrentItem());
		user.getPlayer().openInventory(SaleChest.getInventory(page));
		user.getPlayer().updateInventory();
		user.sendPluginMessage("&3已购买了物品");
	}
}
