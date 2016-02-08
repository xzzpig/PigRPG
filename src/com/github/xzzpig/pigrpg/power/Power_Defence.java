package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.BukkitTools.TCalculate;
import com.github.xzzpig.BukkitTools.TEntity;
import com.github.xzzpig.pigrpg.State;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_BeDamage;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Defence extends Power implements PT_BeDamage, PT_RightClick {
	String sdefence, type, target;
	int distance, time;

	LivingEntity entity;
	int defence;

	@Override
	public String getPowerName() {
		return "Defence";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		sdefence = pl.getReplaced(path.getString("defence"));
		type = pl.getReplaced(path.getString("type"));
		target = pl.getReplaced(path.getString("target", "self"));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance",
				"10")));
		time = Integer.valueOf(pl.getReplaced(path.getString("time")));
		return this;
	}

	@Override
	public void run() {
		final State state = State.getFrom(entity);
		defence = (int) TCalculate.getResult(sdefence.replaceAll("</defence/>",
				state.getPhysicDefence() + ""));
		if (type.equalsIgnoreCase("set")) {
			if (time > 0) {
				if (!state.data.getBoolan("firstd")) {
					state.data
							.setInt("origindefence", state.getPhysicDefence());
					state.data.setBoolean("firstd", true);
				}
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						state.setPhysicDefence(state.data
								.getInt("origindefence"));
						state.data.setBoolean("firstd", false);
					}
				}).start();
			}
			state.setPhysicDefence(defence);
		} else {
			state.setPhysicDefence(state.getPhysicDefence() + defence);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					state.setPhysicDefence(state.getPhysicDefence() - defence);

				}
			}).start();
		}
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		if (target.equalsIgnoreCase("self"))
			entity = event.getPlayer();
		else
			entity = TEntity.getTarget(entity, distance);
	}

	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event) {
		if (target.equalsIgnoreCase("self")) {
			if (event.getEntity() instanceof LivingEntity)
				entity = (LivingEntity) event.getEntity();
		} else if (event.getDamager() instanceof LivingEntity)
			entity = (LivingEntity) event.getDamager();
	}
}
