package com.github.xzzpig.pigrpg.power;

import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.github.xzzpig.pigrpg.*;

public class PowerListener implements Listener
{
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if(!(event.getDamager() instanceof LivingEntity))
			return;
		if(!(event.getEntity() instanceof LivingEntity))
			return;
		
		LivingEntity damager = (LivingEntity) event.getDamager();
		LivingEntity target = (LivingEntity) event.getEntity();
		int damage = event.getDamage();
		
		damage = damage + State.getFrom(damager).getPhysicDamage() - State.getFrom(target).getPhysicDefence();
		if(damage < 0)
			damage = 0;
		event.setDamage(damage);
	}
}
