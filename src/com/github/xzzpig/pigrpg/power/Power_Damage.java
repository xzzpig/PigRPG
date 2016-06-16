package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigapi.TCalculate;
import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigrpg.State;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_BeDamage;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Damage extends Power implements PT_Damage, PT_RightClick,
		PT_Equip, PT_BeDamage {
	String type, sdamage;
	int range, damage, distance, times;

	LivingEntity entity, target;

	@Override
	public String getPowerName() {
		return "Damage";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		type = pl.getReplaced(path.getString("type"));
		sdamage = pl.getReplaced(path.getString("damage", "0"));
		range = (int) TCalculate.getResult(pl.getReplaced(path.getString(
				"range", "0")));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance",
				"10")));
		times = Integer.valueOf(pl.getReplaced(path.getString("times", "1")));
		return this;
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event) {
		entity = event.getPlayer();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		entity = event.getPlayer();
	}

	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof LivingEntity))
			return;
		target = (LivingEntity) event.getDamager();
		if (event.getEntity() instanceof LivingEntity)
			entity = (LivingEntity) event.getEntity();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof LivingEntity))
			return;
		entity = (LivingEntity) event.getDamager();
		if (event.getEntity() instanceof LivingEntity)
			target = (LivingEntity) event.getEntity();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if (entity == null)
			return;
		damage = (int) TCalculate.getResult(sdamage.replaceAll("</damage/>",
				State.getFrom(entity).getPhysicDamage() + ""));
		State state = State.getFrom(entity);
		if (type.equalsIgnoreCase("set"))
			state.setPhysicDamage(damage);
		else if (type.equalsIgnoreCase("add"))
			state.setPhysicDamage(damage + state.getPhysicDamage());
		else if (type.equalsIgnoreCase("max")) {
			if (state.getPhysicDamage() > damage) {
				state.setPhysicDamage(damage);
			}
		} else {
			target = TEntity.getTarget(entity, distance);
			for (int i = 0; i < times; i++)
				target.damage(damage);
		}

	}
}
