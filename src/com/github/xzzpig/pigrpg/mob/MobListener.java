package com.github.xzzpig.pigrpg.mob;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import com.github.xzzpig.pigrpg.rpgworld.*;
import org.bukkit.entity.*;
import java.util.*;

public class MobListener implements Listener
{
	private static Random randm = new Random();
	
	@EventHandler
	public void onChangeEntityToRpg(CreatureSpawnEvent event){
		if(!RpgWorld.rpgworldlist.contains(event.getLocation().getWorld().getName()))
			return;
		LivingEntity entity = event.getEntity();
		RpgChunk rc = new RpgChunk(entity.getLocation().getChunk());
		int levet = rc.getBasicLevel() + randm.nextInt(10) -5;
		CustomMob.getRangeMob(entity,levet);
	}
}
