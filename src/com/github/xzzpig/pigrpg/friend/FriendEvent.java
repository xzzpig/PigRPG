package com.github.xzzpig.pigrpg.friend;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class FriendEvent implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Friend.loadFriend(event.getPlayer().getName());
		event.setJoinMessage(event.getJoinMessage()+"\n你的好友列表已加载");
	}
}
