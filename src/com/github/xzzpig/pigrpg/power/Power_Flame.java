package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;

import java.util.*;

import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Power_Flame extends Power implements PT_Damge,PT_Lore
{
	private static Random rand = new Random();
	
	private boolean clone = false;
	@SuppressWarnings("unused")
	private TData data;

	protected Power_Flame(){
		powers.add(this);
	}
	private Power_Flame(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-Flame:[整数.毫秒]|[整数.几率]";
	}

	@Override
	public String getLore(Equipment equip){
		String[] args = equip.getLoreData("Flame").replaceAll("|","~").split("~");
		int time = 0,chance = 0;
		try{
			time = Integer.valueOf(args[0]);
			chance = Integer.valueOf(args[1]);
		}
		catch(Exception e){}
		return chance+"%几率在攻击时点燃对方"+time+"毫秒";
	}


	@Override
	public String getPowerName(){
		return "Flame";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Flame(data);
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
		String[] args = equip.getLoreData("Flame").split("|");
		int time = 0,chance = 0;
		try{
			time = Integer.valueOf(args[0]);
			chance = Integer.valueOf(args[1]);
		}
		catch(Exception e){
			user.sendPluginMessage("&错误:时间或几率不是整数");
			return;
		}
		run((LivingEntity)event.getEntity(),time,chance);
	}


	public void run(LivingEntity entity,int time,int chance){
		if(chance>100)
			chance = 100;
		if(rand.nextInt(100)<=chance)
			entity.setFireTicks(time);
	}
}
