package com.github.xzzpig.pigrpg.friend;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class FriendEvent implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Friend.loadFriend(event.getPlayer().getName());
	}
}
