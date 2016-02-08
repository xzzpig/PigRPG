package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.BukkitTools.TCalculate;
import com.github.xzzpig.BukkitTools.TEntity;
import com.github.xzzpig.pigrpg.State;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_BeDamage;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Health extends Power implements PT_Damage, PT_RightClick,
		PT_Equip, PT_BeDamage {
	String target, type, type2, samount;
	int distance;

	int amount;
	LivingEntity entity;

	@Override
	public String getPowerName() {
		return "Health";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		target = pl.getReplaced(path.getString("target", "self"));
		type = pl.getReplaced(path.getString("type"));
		samount = pl.getReplaced(path.getString("amount"));
		type2 = pl.getReplaced(path.getString("type2"));
		return this;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if (entity == null)
			return;
		State state = State.getFrom(entity);
		samount = samount
				.replaceAll("</health/>", (TEntity.getHealth(entity)) + "")
				.replaceAll("</maxhealth/>", (TEntity.getHealth(entity)) + "")
				.replaceAll("</damage/>", state.getPhysicDamage() + "");
		amount = (int) TCalculate.getResult(samount);
		if (type.equalsIgnoreCase("max") && type2.equalsIgnoreCase("set"))
			state.setHp(amount);
		else if (type.equalsIgnoreCase("current")
				&& type2.equalsIgnoreCase("set")) {
			if (amount > state.getHp())
				amount = state.getHp();
			entity.setHealth(amount);
		} else if (type.equalsIgnoreCase("max")
				&& type2.equalsIgnoreCase("add"))
			state.setHp(state.getHp() + amount);
		else if (type.equalsIgnoreCase("current")
				&& type2.equalsIgnoreCase("add")) {
			amount = TEntity.getHealth(entity) + amount;
			if (amount > state.getHp())
				amount = state.getHp();
			entity.setHealth(amount);
		}
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
	public void rebulidBeDamage(EntityDamageByEntityEvent event) {
		if (target.equalsIgnoreCase("self")) {
			if (event.getEntity() instanceof LivingEntity)
				entity = (LivingEntity) event.getEntity();
		} else if (event.getDamager() instanceof LivingEntity)
			entity = (LivingEntity) event.getDamager();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		if (target.equalsIgnoreCase("point"))
			entity = TEntity.getTarget(event.getPlayer(), distance);
		else
			entity = event.getPlayer();
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event) {
		if (target.equalsIgnoreCase("point"))
			entity = TEntity.getTarget(event.getPlayer(), distance);
		else
			entity = event.getPlayer();
	}

}
