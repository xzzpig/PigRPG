package com.github.xzzpig.pigrpg.sale;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.xzzpig.BukkitTools.TArgsSolver;
import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chests.SaleChest;
import com.github.xzzpig.pigrpg.*;

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
	
	@SuppressWarnings("deprecation")
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
		if(event.getCurrentItem().getType() == Material.AIR||event.getRawSlot()>51)
			return;
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
		if(user.getPlayer().getInventory().firstEmpty() == -1){
			user.sendPluginMessage("&4你背包没有足够空间");
			user.getPlayer().closeInventory();
			return;
		}
		if(!user.getEcoAPI().pay(seller,(double)price)){
			user.sendPluginMessage("&4你没有足够的钱");
			return;
		}
		Sale.removeItem(event.getCurrentItem());
		ItemStack is = event.getCurrentItem().clone();
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
		lore.remove(lore.size()-1);
		lore.remove(lore.size()-1);
		im.setLore(lore);
		is.setItemMeta(im);
		user.getPlayer().getInventory().addItem(is);
		user.getPlayer().openInventory(SaleChest.getInventory(page));
		user.getPlayer().updateInventory();
		user.sendPluginMessage("&3已购买了物品:");
		FanMessage.getBy(is).send(user.getPlayer());
	}
}
