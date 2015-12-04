package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;

import java.util.*;

import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Power_Lightning extends Power implements PT_Damge,PT_Lore
{
	private static Random rand = new Random();

	private boolean clone = false;
	@SuppressWarnings("unused")
	private TData data;

	protected Power_Lightning(){
		powers.add(this);
	}
	private Power_Lightning(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-Lightning:[整数.几率]";
	}

	@Override
	public String getLore(Equipment equip){
		String schance = equip.getLoreData("Knockup");
		int chance = 0;
		try{
			chance = Integer.valueOf(0+schance);
		}
		catch(Exception e){}
		return chance+"%几率在攻击时击发射闪电";
	}


	@Override
	public String getPowerName(){
		return "Lightning";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Lightning(data);
	}

	@Override
	public void runDamage(EntityDamageByEntityEvent event){
		if(!this.isCloned())
			return;
		if(!(event.getDamager() instanceof Player))
			return;
		if(!(event.getEntity() instanceof LivingEntity))
			return;
		Player player = (Player)event.getDamager();
		User user = User.getUser(player);
		Equipment equip = user.getHandEquip();
		String schance = equip.getLoreData("Lightning");
		int chance = 0;
		try{
			chance = Integer.valueOf(0+schance);
		}
		catch(Exception e){
			user.sendPluginMessage("&错误:力度或几率不是整数");
			return;
		}
		run((LivingEntity)event.getEntity(),chance);
	}

	public void run(LivingEntity e,int chance) {
        if(chance>100)
			chance = 100;
		if(rand.nextInt(100)<=chance)
           e.getWorld().strikeLightning(e.getLocation());
    }
}
