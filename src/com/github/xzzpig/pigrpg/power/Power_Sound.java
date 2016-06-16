package com.github.xzzpig.pigrpg.power;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Sound extends Power implements PT_Damage, PT_RightClick {
	Sound sound;
	String target;
	int distance;

	LivingEntity entity;

	@Override
	public String getPowerName() {
		return "Sound";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		sound = Sound.valueOf(pl.getReplaced(path.getString("sound")));
		target = pl.getReplaced(path.getString("target", "self"));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance",
				"10")));
		return this;
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		if (target.equalsIgnoreCase("point"))
			entity = TEntity.getTarget(event.getPlayer(), distance);
		else
			entity = event.getPlayer();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		if (target.equalsIgnoreCase("point")) {
			if (event.getEntity() instanceof LivingEntity)
				entity = (LivingEntity) event.getEntity();
		} else if (event.getDamager() instanceof LivingEntity)
			entity = (LivingEntity) event.getDamager();
	}

	@Override
	public void run() {
		entity.getLocation().getWorld()
				.playSound(entity.getLocation(), sound, 10, 10);
	}

}
