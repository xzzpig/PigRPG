package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;

import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.*;

public class Power_Effect extends Power implements PT_Damge,PT_Lore
{
	private boolean clone = false;
	@SuppressWarnings("unused")
	private TData data;

	protected Power_Effect(){
		powers.add(this);
	}
	private Power_Effect(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-Effect:[文本.粒子效果]";
	}

	@Override
	public String getLore(Equipment equip){
		String seffect = equip.getLoreData("Sound");
		return "攻击时产生粒子效果"+seffect;
	}


	@Override
	public String getPowerName(){
		return "Effect";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Effect(data);
	}

	@SuppressWarnings("deprecation")
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
		String seffect = equip.getLoreData("Effect");
		Effect effect = org.bukkit.Effect.valueOf(seffect);
		if(effect == null){
			user.sendPluginMessage("不存在该粒子效果");
			return;
		}
		player.playEffect(player.getLocation(),effect,0);
	}
}
