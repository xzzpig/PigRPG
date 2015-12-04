package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;

import java.util.*;

import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Power_Knockup extends Power implements PT_Damge,PT_Lore
{
	private static Random rand = new Random();

	private boolean clone = false;
	@SuppressWarnings("unused")
	private TData data;

	protected Power_Knockup(){
		powers.add(this);
	}
	private Power_Knockup(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-Knockup:[整数.力度]|[整数.几率]";
	}

	@Override
	public String getLore(Equipment equip){
		String[] args = equip.getLoreData("Knockup").split("|");
		int power = 0,chance = 0;
		try{
			power = Integer.valueOf(args[0]);
			chance = Integer.valueOf(args[1]);
		}
		catch(Exception e){}
		return chance+"%几率在攻击时击飞对方(Lv"+power+")";
	}


	@Override
	public String getPowerName(){
		return "Knockup";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Knockup(data);
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
		String[] args = equip.getLoreData("Knockup").split("|");
		int power = 0,chance = 0;
		try{
			power = Integer.valueOf(args[0]);
			chance = Integer.valueOf(args[1]);
		}
		catch(Exception e){
			user.sendPluginMessage("&错误:力度或几率不是整数");
			return;
		}
		run(player,(LivingEntity)event.getEntity(),power,chance);
	}

	public void run(Player player, LivingEntity e,int power,int chance) {
        if(chance>100)
			chance = 100;
		if(rand.nextInt(100)<=chance)
            e.setVelocity(player.getLocation().getDirection().setY(power));
    }
}
