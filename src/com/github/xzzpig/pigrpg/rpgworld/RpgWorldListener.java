package com.github.xzzpig.pigrpg.rpgworld;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.xzzpig.pigrpg.User;

public class RpgWorldListener implements Listener {
	@EventHandler
	public void onChunkLoad(PlayerMoveEvent event) {
		RpgChunk rc = new RpgChunk(event.getTo().getChunk());
		if (RpgWorld.rpgworldlist.contains(event.getTo().getChunk()
				.getBlock(1, 1, 1).getWorld().getName()) == false)
			return;
		if (rc.isChanged())
			return;
		rc.change();
//		User user = User.getUser(event.getPlayer());
//		user.sendBroadMessage(
//				ChatColor.GREEN + "欢迎来到" + ChatColor.BLUE + rc.getData("name")
//						+ RpgChunk.chbiome.get(rc.getBiome()), 3);
//		user.sendPluginMessage(ChatColor.GREEN + "欢迎来到" + ChatColor.BLUE
//				+ rc.getData("name") + RpgChunk.chbiome.get(rc.getBiome()));
	}

	@EventHandler
	public void onIntoNewArea(PlayerMoveEvent event) {
		RpgChunk to = new RpgChunk(event.getTo().getChunk());
		if (RpgWorld.rpgworldlist
				.contains(event.getFrom().getWorld().getName()) == false
				|| to.isChanged() == false
				|| to.getData("name") == null
				|| to.getData("name").equalsIgnoreCase(
						new RpgChunk(event.getFrom().getChunk())
								.getData("name")))
			return;
		User user = User.getUser(event.getPlayer());
		user.sendBroadMessage(
				ChatColor.GREEN + "欢迎来到" + ChatColor.BLUE + to.getData("name")
						+ RpgChunk.chbiome.get(to.getBiome()), 3);;
		user.sendPluginMessage(ChatColor.GREEN + "欢迎来到^^" + ChatColor.BLUE
				+ to.getData("name") + RpgChunk.chbiome.get(to.getBiome())+"^&3Lv"+to.getBasicLevel()+"^");
	}
}
