package com.github.xzzpig.pigrpg.equip;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.power.Power;
import com.github.xzzpig.pigrpg.power.PowerRunTime;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;
import com.github.xzzpig.pigrpg.power.type.PT_Limit;

public class EquipListener implements Listener
{
	@EventHandler
	public void onCloseInv(InventoryCloseEvent event){
		if(!event.getInventory().getTitle().contains("装备栏"))
			return;
		User user = User.getUser((Player)event.getPlayer());
		Inventory inv = event.getInventory();
		for(ItemStack is:inv.getContents()){
			if(is == null)
				continue;
			if(is instanceof Equipment)
				user.setEquip((Equipment)is);
			else
				user.setEquip(new Equipment(is));
		}
		for(EquipType et:EquipType.values()){
			Equipment equip = user.getEquip(et);
			pls:for(PowerLore pl:equip.getPowerLores()){
				if(!pl.isRunTime(PowerRunTime.CloseEC))
					continue pls;
				ps:for(Power p:pl.powers){
					if(p instanceof PT_Limit)
						if(!((PT_Limit) p).can()){
							user.sendPluginMessage(((PT_Limit) p).cantMessage());
							break ps;
						}
					if(!(p instanceof PT_Equip))
						continue;
					((PT_Equip)p).rebuildEquip(event);
					p.run();
				}
			}
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
