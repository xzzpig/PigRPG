package com.github.xzzpig.pigrpg.mob;

import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.github.xzzpig.pigrpg.rpgworld.RpgChunk;
import com.github.xzzpig.pigrpg.rpgworld.RpgWorld;

public class MobListener implements Listener {
	private static Random randm = new Random();

	@EventHandler
	public void onChangeEntityToRpg(CreatureSpawnEvent event) {
		if (!RpgWorld.rpgworldlist.contains(event.getLocation().getWorld()
				.getName()))
			return;
		LivingEntity entity = event.getEntity();
		if (entity instanceof Player)
			return;
		RpgChunk rc = new RpgChunk(entity.getLocation().getChunk());
		int level = rc.getBasicLevel() + randm.nextInt(10) - 5;
		if(level<1)
			level = 1;
		CustomMob.getRangeMob(entity, level);
		// State state = State.getFrom(entity);
		// Debuger.print("hp:"+state.getHp());
		// Debuger.print("damage:"+state.getPhysicDamage());
		// Debuger.print("def:"+state.getPhysicDefence());
	}
}
