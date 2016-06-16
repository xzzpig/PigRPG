package com.github.xzzpig.pigrpg.power;

import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Knockup extends Power implements PT_Damage, PT_RightClick {
	private static Random rand = new Random();

	int power, chance, distance;

	LivingEntity launcher, target;

	@Override
	public String getPowerName() {
		return "Knockup";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		power = Integer.valueOf(pl.getReplaced(path.getString("power", "0")));
		chance = Integer
				.valueOf(pl.getReplaced(path.getString("chance", "100")));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance",
				"10")));
		return this;
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		launcher = event.getPlayer();
		target = TEntity.getTarget(launcher, distance);
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity)
			launcher = (LivingEntity) event.getDamager();
		if (event.getEntity() instanceof LivingEntity)
			target = (LivingEntity) event.getEntity();
	}

	@Override
	public void run() {
		if (launcher == null || target == null)
			return;
		if (chance > 100)
			chance = 100;
		if (rand.nextInt(100) <= chance)
			target.setVelocity(launcher.getLocation().getDirection()
					.setY(power));
	}
}
