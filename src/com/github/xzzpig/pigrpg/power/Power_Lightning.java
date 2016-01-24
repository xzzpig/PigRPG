package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.configuration.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class Power_Lightning extends Power implements PT_Damge,PT_RightClick
{
	private static Random rand = new Random();
	
	String target;
	int chance,distance;
	
	Location loc;
	
	@Override
	public String getPowerName(){
		return "Lightning";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		target = pl.getReplaced(path.getString("target","point"));
		chance = Integer.valueOf(pl.getReplaced(path.getString("chance","100")));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance","10")));
		return this;
	}

	@Override
	public void run(){
		if(loc == null)
			return;
		if(rand.nextInt(100)<=chance)
			loc.getWorld().strikeLightning(loc);
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(target.equalsIgnoreCase("point"))
			loc = event.getEntity().getLocation();
		else
			loc = event.getDamager().getLocation();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		if(target.equalsIgnoreCase("point"))
			loc = TEntity.getTarget(event.getPlayer(),distance).getLocation();
		else
			loc = event.getPlayer().getLocation();
	}
}
