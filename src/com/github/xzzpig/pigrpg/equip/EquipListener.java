package com.github.xzzpig.pigrpg.equip;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import com.github.xzzpig.pigrpg.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class EquipListener implements Listener
{
	@EventHandler
	public void onCloseInv(InventoryCloseEvent event){
		if(!event.getInventory().getTitle().contains("装备栏"))
			return;
		User user = User.getUser((Player)event.getPlayer());
		Inventory inv = event.getInventory();
		for(ItemStack is:inv.getContents()){
			if(is.getItemMeta().getLore()==null)
				continue;
			if(is instanceof Equipment)
				user.setEquip((Equipment)is);
			else
				user.setEquip(new Equipment(is));
		}
	}

	@EventHandler
	public void onClickBanItem(InventoryClickEvent event){
		Inventory inv = event.getInventory();
		int iitem = event.getRawSlot();
		if(!event.getInventory().getTitle().contains("物品栏"))
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
		Player clicker = (Player) event.getWhoClicked();
		if(!event.getInventory().getTitle().contains("装备栏"))
			return;
		if(event.getAction()!=InventoryAction.PLACE_ALL)
			return;
		if(!event.isLeftClick())
			return;
		int iitem = event.getRawSlot();
		if(iitem>=inv.getSize())
			return;
		int line = iitem/9;
		if((line%2)==0)
			return;
		ItemStack is = event.getCurrentItem();
		if(is==null||is.getType()==Material.AIR)
			return;
		EquipType targettype = ((Equipment)inv.getItem(iitem-9)).getEquiptype();
		Equipment equip;
		if(is instanceof Equipment)
			equip = (Equipment)is;
		else
			equip = new Equipment(is);
		if(equip.getEquiptype()==targettype)
			return;
		if(targettype.getInherit().getAllChildren().contains(equip.getEquiptype()))
			return;
		event.setCancelled(true);
	}
}
