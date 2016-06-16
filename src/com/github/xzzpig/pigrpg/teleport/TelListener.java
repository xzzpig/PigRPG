package com.github.xzzpig.pigrpg.teleport;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;

public class TelListener implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onWarpInvClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().contains("Warp列表"))
			event.setCancelled(true);
		else
			return;
		if (event.getCurrentItem().getTypeId() != 0) {
			String swarp = event.getCurrentItem().getItemMeta()
					.getDisplayName().replaceAll(TString.Color(3), "");
			Player player = (Player) event.getWhoClicked();
			Warp warp = Warp.getWarp(swarp);
			if (warp == Warp.Null)
				return;
			User.getUser(player).teleport(warp);
		}
	}
}
