package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;
import com.github.xzzpig.pigrpg.power.type.PT_Killed;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Message extends Power implements PT_Damage, PT_RightClick,
		PT_Equip, PT_Killed {
	String message, target;
	int distance;

	Player player;
	PowerLore pl;

	@Override
	public String getPowerName() {
		return "Message";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		this.pl = pl;
		message = pl.getReplaced(path.getString("message"));
		target = pl.getReplaced(path.getString("target", "self"));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance",
				"10")));
		return this;
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event) {
		if (target.equalsIgnoreCase("point")) {
			LivingEntity entity = TEntity
					.getTarget(event.getPlayer(), distance);
			if (entity instanceof Player)
				player = (Player) entity;
		} else
			player = (Player) event.getPlayer();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		if (target.equalsIgnoreCase("point")) {
			LivingEntity entity = TEntity
					.getTarget(event.getPlayer(), distance);
			if (entity instanceof Player)
				player = (Player) entity;
		} else
			player = event.getPlayer();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		if (target.equalsIgnoreCase("point")) {
			if (event.getEntity() instanceof Player)
				player = (Player) event.getEntity();
		} else if (event.getDamager() instanceof Player)
			player = (Player) event.getDamager();
	}

	@Override
	public void rebulidKilled(EntityDeathEvent event) {
		if (target.equalsIgnoreCase("point")) {
			player = event.getEntity().getKiller();
		} else if (event.getEntity() instanceof Player)
			player = (Player) event.getEntity();
	}

	@Override
	public void run() {
		if (player == null)
			return;
		message = Power_Condition.buildStr(message, player, false);
		User.getUser(player).sendPluginMessage(message);
	}
}
