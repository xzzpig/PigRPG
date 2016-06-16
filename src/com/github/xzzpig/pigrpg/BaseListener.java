package com.github.xzzpig.pigrpg;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class BaseListener implements Listener{
	public static final BaseListener self = new BaseListener();
	
	@EventHandler
	public void onPlayerLogOut(PlayerQuitEvent event){
		User.getUser(event.getPlayer()).saveData();
		User.removeOffline();
	}
}
