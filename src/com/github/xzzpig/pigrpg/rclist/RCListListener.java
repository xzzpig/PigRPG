package com.github.xzzpig.pigrpg.rclist;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.github.xzzpig.pigrpg.Vars;
import com.github.xzzpig.pigrpg.chest.RCChest;

public class RCListListener implements Listener {
	public static final RCListListener self = new RCListListener();

	@EventHandler
	public void onClickPlayer(PlayerInteractEntityEvent event) {// 右键玩家打开右键菜单
		if (!(event.getRightClicked() instanceof Player))// 非玩家返回
			return;
		Player player = event.getPlayer(), target = (Player) event
				.getRightClicked();
		if (!player.isSneaking())// 玩家未潜行
			return;
		player.openInventory(RCChest.getInventory(target, player));
		if(Vars.pdata.getBoolean("config.relist.canel"))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onChestClick(InventoryClickEvent event){
		if (event.getInventory().getTitle().contains("的右键菜单"))
			event.setCancelled(true);
		else
			return;
	}
}
