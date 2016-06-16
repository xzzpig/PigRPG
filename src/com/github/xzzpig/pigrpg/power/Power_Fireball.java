package com.github.xzzpig.pigrpg.power;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Fireball extends Power implements PT_Damage, PT_RightClick {
	Random random = new Random();

	int amount = 1, range = 100;

	Player launcher;

	@Override
	public String getPowerName() {
		return "Fireball";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		amount = Integer.valueOf(pl.getReplaced(path.getString("amount", "1")));
		range = Integer.valueOf(pl.getReplaced(path.getString("range", "100")));
		return this;
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		launcher = event.getPlayer();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player)
			launcher = (Player) event.getDamager();
	}

	@Override
	public void run() {
		if (launcher == null)
			return;
		int r = random.nextInt(100);
		if (r > range)
			return;
		for (int i = 0; i < amount; i++) {
			launcher.launchProjectile(Fireball.class);
			launcher.playSound(launcher.getLocation(), Sound.GHAST_FIREBALL,
					1.0f, 1.0f);
		}
	}
}
