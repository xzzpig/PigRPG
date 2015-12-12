package com.github.xzzpig.pigrpg.rpgworld;
import org.bukkit.event.*;
import org.bukkit.event.world.*;
import org.bukkit.event.player.*;
import com.github.xzzpig.pigrpg.*;
import org.bukkit.*;

public class RpgWorldListener implements Listener
{
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event){
		
		RpgChunk rc = new RpgChunk(event.getChunk());
		if(RpgWorld.rpgworldlist.contains(event.getChunk().getBlock(1,1,1).getWorld().getName())==false)
			return;
		rc.change();
	}

	@EventHandler
	public void onIntoNewArea(PlayerMoveEvent event){
		RpgChunk to =new RpgChunk(event.getTo().getChunk());
		if(RpgWorld.rpgworldlist.contains(event.getFrom().getWorld().getName())==false||to.isChanged()==false||to.getData("name").equalsIgnoreCase(new RpgChunk(event.getFrom().getChunk()).getData("name")))
			return;
		User user = User.getUser(event.getPlayer());
		user.sendBroadMessage(ChatColor.GREEN+"欢迎来到"+ChatColor.BLUE+to.getData("name"),3);
	}
}
