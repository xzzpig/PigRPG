package com.github.xzzpig.pigrpg.power;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.BukkitTools.TEntity;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Lightning extends Power implements PT_Damage, PT_RightClick {
	private static Random rand = new Random();

	String target;
	int chance, distance;

	Location loc;

	@Override
	public String getPowerName() {
		return "Lightning";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		target = pl.getReplaced(path.getString("target", "point"));
		chance = Integer
				.valueOf(pl.getReplaced(path.getString("chance", "100")));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance",
				"10")));
		return this;
	}

	@Override
	public void run() {
		if (loc == null)
			return;
		if (rand.nextInt(100) <= chance)
			loc.getWorld().strikeLightning(loc);
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		if (target.equalsIgnoreCase("point"))
			loc = event.getEntity().getLocation();
		else
			loc = event.getDamager().getLocation();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		if (target.equalsIgnoreCase("point"))
			loc = TEntity.getTarget(event.getPlayer(), distance).getLocation();
		else
			loc = event.getPlayer().getLocation();
	}
}
