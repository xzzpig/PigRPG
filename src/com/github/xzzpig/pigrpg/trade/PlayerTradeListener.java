package com.github.xzzpig.pigrpg.trade;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.chests.ItemForChest;

public class PlayerTradeListener implements Listener
{
	@EventHandler
	public void onPutItem(InventoryClickEvent event)
	{
		if(!event.getInventory().getTitle().contains("玩家交易界面"))
			return;
		if(!event.getClick().isLeftClick()){
			event.setCancelled(true);
			return;
		}
		Inventory inv = event.getInventory();
		PlayerTrade trade = PlayerTrade.getTrade(inv);
		if(trade == null){
			for(HumanEntity player:inv.getViewers()){
				((Player)player).sendMessage(TString.Prefix("PigRPG",4)+"未知交易错误");
				player.closeInventory();
			}
			event.setCancelled(true);
			return;
		}
		Player clicker = (Player) event.getWhoClicked();
		int type = 0;
		int item = event.getRawSlot();
		if(clicker == trade.getLauncher()){
			if(item > 17&&item < 45)
				event.setCancelled(true);
			type = 1;
		}
		else if(clicker == trade.getTarget()){
			if(item < 27&&item>-1)
				event.setCancelled(true);
			type = 2;
		}
		if(type == 0){
			for(HumanEntity player:inv.getViewers()){
				((Player)player).sendMessage(TString.Prefix("PigRPG",4)+"未知交易错误");
				player.closeInventory();
			}
			event.setCancelled(true);
			return;
		}
		if(type == 1&&item == 18&&inv.getItem(item).getItemMeta().getDisplayName().contains("确认交易")){
			trade.changeatTradeState(1,true);
			inv.clear(18);
			inv.setItem(18,ItemForChest.customItem(TString.Color(3)+"↑更改交易↑",76,null));
		}
		if(type == 1&&item == 18&&inv.getItem(item).getItemMeta().getDisplayName().contains("更改交易")){
			trade.changeatTradeState(1,false);
			inv.clear(18);
			inv.setItem(18,ItemForChest.customItem(TString.Color(3)+"↑确认交易↑",50,null));
		}
		if(type == 2&&item == 26&&inv.getItem(item).getItemMeta().getDisplayName().contains("确认交易")){
			trade.changeatTradeState(2,true);
			inv.clear(26);
			inv.setItem(26,ItemForChest.customItem(TString.Color(3)+"↓更改交易↓",76,null));
		}
		if(type == 2&&item == 26&&inv.getItem(item).getItemMeta().getDisplayName().contains("更改交易")){
			trade.changeatTradeState(2,false);
			inv.clear(26);
			inv.setItem(26,ItemForChest.customItem(TString.Color(3)+"↓确认交易↓",50,null));
		}
		trade.freshInv();
	}
	
	@EventHandler
	public void onCloseInv(InventoryCloseEvent event)
	{
		if(!event.getInventory().getTitle().contains("玩家交易界面"))
			return;
		Inventory inv = event.getInventory();
		final PlayerTrade trade = PlayerTrade.getTrade(inv);
		if(trade == null)
			return;
		new Thread(new Runnable(){
				@Override
				public void run()
				{
					trade.returnItems();
				}
			}).start();
	}
}
