package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_BeDamage;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Hungery extends Power implements PT_RightClick, PT_Damage,PT_BeDamage {
	int amount;
	String type;
	
	Player player;
	
	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof LivingEntity)
			player = (Player) event.getEntity();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player)
			player = (Player) event.getDamager();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		player = event.getPlayer();
	}

	@Override
	public String getPowerName() {
		return "Hungery";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		amount = Integer.valueOf(pl.getReplaced(path.getString("amount")));
		type = pl.getReplaced(path.getString("type","add"));
		return this;
	}
	
	@Override
	public void run() {
		if(player == null)
			return;
		switch (type) {
		case "add":
			amount = amount+player.getFoodLevel();
			if(amount > 20)
				amount = 20;
			player.setFoodLevel(amount);
			break;
		case "set":
			if(amount < 20)
				amount = 20;
			player.setFoodLevel(amount);
			break;
		case "reduce":
			amount = player.getFoodLevel() - amount;
			if(amount<0)
				amount = 0;
			else if(amount>20)
				amount = 20;
			player.setFoodLevel(amount);
			break;
		}
	}

}
