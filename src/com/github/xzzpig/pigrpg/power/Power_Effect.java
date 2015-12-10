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
	
	public static void playEffect(Location loc,Effect effect,int i){
		loc.getWorld().playEffect(loc,effect,i);
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
		Power_Effect.playEffect(player.getLocation(),effect,0);
	}

	//*effect:String *location:Location/String type:int/String
	@Override
	public void run(){
		if(!this.isCloned())
			return;
		String seffect = data.getString("effect");
		if(seffect ==null)
			return;
		Effect effect = org.bukkit.Effect.valueOf(seffect);
		if(effect == null)
			return;
		Location loc;
		if((data.getObject("location") instanceof Location))
			loc = (Location)data.getObject("location");
		else
			loc = Voids.toLocation(data.getString("location"));
		if(loc == null)
			return;
		int type = data.getInt("type");
		try{type = Integer.valueOf(data.getString("type"));}
		catch(Exception e){}
		Power_Effect.playEffect(loc,effect,type);
		
	}

	
	
}
