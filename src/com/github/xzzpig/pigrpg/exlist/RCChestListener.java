package com.github.xzzpig.pigrpg.exlist;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;

import com.github.xzzpig.pigrpg.chests.*;

public class RCChestListener implements Listener
{
	@EventHandler
	public void onInteraction(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		if(!player.isSneaking())
			return;
		if(event.getRightClicked().getType() != EntityType.PLAYER)
			return;
		Player target = (Player)event.getRightClicked();
		player.openInventory(RCChest.getInventory(target));
		event.setCancelled(true);
	}
	@EventHandler
	public void onBanClick(InventoryClickEvent event)
	{
		if(event.getInventory().getTitle().contains("的右键菜单"))
			event.setCancelled(true);
	}
}
