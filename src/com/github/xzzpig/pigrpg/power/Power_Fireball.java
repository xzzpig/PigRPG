package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import com.github.xzzpig.pigrpg.equip.*;

public class Power_Fireball extends Power implements PT_RightClick,PT_Lore
{
	private boolean clone = false;
	private TData data;

	protected Power_Fireball(){
		powers.add(this);
	}
	private Power_Fireball(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-Fireball:";
	}

	@Override
	public String getLore(Equipment equip){
		return "发射火焰弹";
	}


	@Override
	public String getPowerName(){
		return "Fireball";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Fireball(data);
	}

	//user:User
	@Override
	public void runRC(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		Player player = user.getPlayer();
		player.playSound(player.getLocation(),Sound.GHAST_FIREBALL,1.0f,1.0f);
		player.launchProjectile(SmallFireball.class);
	}
	
	//entity:Livingentity
	@Override
	public void run(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("entity") instanceof LivingEntity))
			return;
		LivingEntity entity = (LivingEntity)data.getObject("entity");
		entity.getWorld().playSound(entity.getLocation(),Sound.GHAST_FIREBALL,1.0f,1.0f);
		entity.launchProjectile(SmallFireball.class);
	}
}
