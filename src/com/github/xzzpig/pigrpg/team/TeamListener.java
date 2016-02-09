package com.github.xzzpig.pigrpg.team;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.xzzpig.pigrpg.User;

public class TeamListener implements Listener {

	@EventHandler
	public void onPlayerQuite(PlayerQuitEvent event) {
		User user = User.getUser(event.getPlayer());
		if (user.hasTeam())
			user.getTeam().removeMember(user);
	}
}
