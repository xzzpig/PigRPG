package com.github.xzzpig.pigrpg.equip;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

import com.github.xzzpig.pigrpg.*;

import org.bukkit.entity.*;
import org.bukkit.*;

import com.github.xzzpig.pigrpg.power.*;
import com.github.xzzpig.pigrpg.power.type.*;
import com.github.xzzpig.BukkitTools.*;

public class EquipListener implements Listener
{
	@EventHandler
	public void onCloseInv(InventoryCloseEvent event){
		if(!event.getInventory().getTitle().contains("装备栏"))
			return;
		User user = User.getUser((Player)event.getPlayer());
		Inventory inv = event.getInventory();
		for(ItemStack is:inv.getContents()){
			if(is.getItemMeta()==null)
				continue;
			if(is instanceof Equipment)
				user.setEquip((Equipment)is);
			else
				user.setEquip(new Equipment(is));
		}
		for(Power p:Power.values()){
			if(!(p instanceof PT_Equip))
				return;
			((PT_Equip)p.clone(new TData().setObject("user",user))).runEquip();
		}
	}

	@EventHandler
	public void onClickBanItem(InventoryClickEvent event){
		Inventory inv = event.getInventory();
		int iitem = event.getRawSlot();
		if(!event.getInventory().getTitle().contains("装备栏"))
			return;
		if(iitem>=inv.getSize())
			return;
		int line = iitem/9;
		if((line%2)==0)
			event.setCancelled(true);
	}

	@EventHandler
	public void onPutItem(InventoryClickEvent event){
		Inventory inv = event.getInventory();
		if(!event.getInventory().getTitle().contains("装备栏"))
			return;
		if(event.getAction()==InventoryAction.PICKUP_ONE||event.getAction()==InventoryAction.PICKUP_ALL||event.getAction()==InventoryAction.PICKUP_SOME)
			return;
		int iitem = event.getRawSlot();
		if(iitem>=inv.getSize())
			return;
		int line = iitem/9;
		if((line%2)==0)
			return;
		ItemStack is = event.getCursor();
		if(is==null||is.getType()==Material.AIR){
			Debuger.print("isair");return;}
		EquipType targettype = new Equipment(inv.getItem(iitem-9)).getEquiptype(); 
		Equipment equip;
		if(is instanceof Equipment)
			equip = (Equipment)is;
		else
			equip = new Equipment(is);
		if(equip.getEquiptype()!=targettype&&(!targettype.getInherit().getAllChildren().contains(equip.getEquiptype()))){
			event.setCancelled(true);
			User.getUser((Player)event.getWhoClicked()).sendPluginMessage("&4请将装备放入对应的装备栏");
			return;
		}
	}
}
