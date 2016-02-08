package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.BukkitTools.TCalculate;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_BeDamage;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_Killed;
import com.github.xzzpig.pigrpg.power.type.PT_Limit;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Money extends Power implements PT_Damage, PT_RightClick,
		PT_Killed, PT_Limit, PT_BeDamage {
	String type, samount, target, message;

	Player entity;
	int amount;
	User user;

	@Override
	public String getPowerName() {
		return "Money";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		samount = pl.getReplaced(path.getString("amount"));
		type = pl.getReplaced(path.getString("type", "has"));
		target = pl.getReplaced(path.getString("target", "self"));
		message = pl.getReplaced(path.getString("message"));
		return this;
	}

	@Override
	public void run() {
		if (entity == null)
			return;
		user = User.getUser(entity);
		amount = (int) TCalculate.getResult(samount.replaceAll("</money/>", ""
				+ (int) user.getEcoAPI().getMoney()));
		switch (type) {
		case "add":
			user.getEcoAPI().setMoney(amount + user.getEcoAPI().getMoney());
			break;
		case "take":
		case "reduce":
			if (user.getEcoAPI().getMoney() < amount)
				user.getEcoAPI().setMoney(0);
			else
				user.getEcoAPI().setMoney(user.getEcoAPI().getMoney() - amount);
			break;
		}
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		if (target.equalsIgnoreCase("other")) {
			if (event.getEntity() instanceof Player)
				entity = (Player) event.getEntity();
		} else if (event.getDamager() instanceof Player)
			entity = (Player) event.getDamager();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		entity = event.getPlayer();
	}

	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event) {
		if (target.equalsIgnoreCase("self")) {
			if (event.getEntity() instanceof Player)
				entity = (Player) event.getEntity();
		} else if (event.getDamager() instanceof LivingEntity)
			entity = (Player) event.getDamager();
	}

	@Override
	public void rebulidKilled(EntityDeathEvent event) {
		if (target.equalsIgnoreCase("self")) {
			if (event.getEntity() instanceof LivingEntity)
				entity = (Player) event.getEntity();
		} else if (event.getEntity().getKiller() instanceof LivingEntity)
			entity = event.getEntity().getKiller();
	}

	@Override
	public boolean can() {
		if (entity == null)
			return false;
		user = User.getUser(entity);
		amount = (int) TCalculate.getResult(samount.replaceAll("</money/>", ""
				+ (int) user.getEcoAPI().getMoney()));
		if (type.equalsIgnoreCase("take") || type.equalsIgnoreCase("has"))
			return user.getEcoAPI().hasMoney(amount);
		return true;
	}

	@Override
	public String cantMessage() {
		return message;
	}

}
