package com.github.xzzpig.pigrpg.equip;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import com.github.xzzpig.pigrpg.*;
import org.bukkit.entity.*;

public class EquipListener implements Listener
{
	@EventHandler
	public void onCloseInv(InventoryCloseEvent event)
	{
		if(!event.getInventory().getTitle().contains("装备栏"))
			return;
		User user = User.getUser((Player)event.getPlayer());
		Inventory inv = event.getInventory();
		for(ItemStack is:inv.getContents()){
			if(is.getItemMeta().getLore() == null)
				continue;
			if(is instanceof Equipment)
				user.setEquip((Equipment)is);
			else
				user.setEquip(new Equipment(is));
		}
	}
	
	@EventHandler
	public void onClickBanItem(InventoryClickEvent event)
	{
		Inventory inv = event.getInventory();
		Player clicker = (Player) event.getWhoClicked();
		int iitem = event.getRawSlot();
		if(!event.getInventory().getTitle().contains("物品栏"))
			return;
		if(iitem >= inv.getSize())
			return;
		int line = iitem/9;
		if((line%2) == 0)
			event.setCancelled(true);
	}
}
