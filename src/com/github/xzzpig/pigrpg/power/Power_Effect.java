package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.configuration.*;
import com.github.xzzpig.pigrpg.equip.*;
import org.bukkit.event.player.*;
import org.bukkit.*;

public class Power_Effect extends Power implements PT_RightClick,PT_Damge,PT_Equip
{
	Effect effect;
	
	Location loc;
	
	@Override
	public String getPowerName(){
		return "Effect";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		effect = Effect.valueOf(pl.getReplaced(path.getString("effect")));
		return this;
	}

	@Override
	public void run(){
		playEffect(loc,effect,0);
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		loc = event.getPlayer().getLocation();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		loc = event.getDamager().getLocation();
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event){
		loc = (Location) event.getPlayer();
	}
	
	public static void playEffect(Location loc,Effect effect,int i){
		loc.getWorld().playEffect(loc,effect,i);
	}
}
