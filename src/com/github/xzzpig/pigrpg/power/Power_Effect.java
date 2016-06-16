package com.github.xzzpig.pigrpg.power;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_BeDamage;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Effect extends Power implements PT_RightClick, PT_Damage,
		PT_Equip, PT_BeDamage {
	public static void playEffect(Location loc, Effect effect, int i) {
		loc.getWorld().playEffect(loc, effect, i);
	}

	Effect effect;

	Location loc;

	@Override
	public String getPowerName() {
		return "Effect";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		effect = Effect.valueOf(pl.getReplaced(path.getString("effect")));
		return this;
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event) {
		loc = (Location) event.getPlayer();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		loc = event.getPlayer().getLocation();
	}

	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event) {
		loc = event.getEntity().getLocation();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		loc = event.getDamager().getLocation();
	}

	@Override
	public void run() {
		playEffect(loc, effect, 0);
	}
}
