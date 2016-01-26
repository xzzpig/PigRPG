package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class Power_Sound extends Power implements PT_Damge,PT_RightClick
{
	Sound sound;
	String target;
	int distance;

	LivingEntity entity;

	@Override
	public String getPowerName(){
		return "Sound";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		sound = Sound.valueOf(pl.getReplaced(path.getString("sound")));
		target = pl.getReplaced(path.getString("target","self"));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance","10")));
		return this;
	}

	@Override
	public void run(){
		entity.getLocation().getWorld().playSound(entity.getLocation(),sound,10,10);
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(target.equalsIgnoreCase("point")){
			if(event.getEntity() instanceof LivingEntity)
				entity = (LivingEntity)event.getEntity();}
		else if(event.getDamager() instanceof LivingEntity)
			entity = (LivingEntity)event.getDamager();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		if(target.equalsIgnoreCase("point"))
			entity = TEntity.getTarget(event.getPlayer(),distance);
		else
			entity = event.getPlayer();
	}

}
