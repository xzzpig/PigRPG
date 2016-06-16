package com.github.xzzpig.pigrpg.power;

import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_BeDamage;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Flame extends Power implements PT_Damage, PT_RightClick,
		PT_Equip, PT_BeDamage {
	private static Random rand = new Random();

	String target;
	int time, chance, distance;

	LivingEntity entity;

	@Override
	public String getPowerName() {
		return "Flame";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		target = pl.getReplaced(path.getString("target", "point"));
		time = Integer.valueOf(pl.getReplaced(path.getString("time", "0")));
		chance = Integer
				.valueOf(pl.getReplaced(path.getString("chance", "100")));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance",
				"10")));
		return this;
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event) {
		if (target.equalsIgnoreCase("point"))
			entity = TEntity.getTarget(event.getPlayer(), distance);
		else
			entity = event.getPlayer();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		if (target.equalsIgnoreCase("point"))
			entity = TEntity.getTarget(event.getPlayer(), distance);
		else
			entity = event.getPlayer();
	}

	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event) {
		if (target.equalsIgnoreCase("self")) {
			if (event.getEntity() instanceof LivingEntity)
				entity = (LivingEntity) event.getEntity();
		} else if (event.getDamager() instanceof LivingEntity)
			entity = (LivingEntity) event.getDamager();
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
		if (entity == null)
			return;
		if (chance > 100)
			chance = 100;
		if (rand.nextInt(100) <= chance)
			entity.setFireTicks(time);
	}

}
