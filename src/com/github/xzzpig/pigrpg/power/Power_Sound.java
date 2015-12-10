package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Power_Sound extends Power implements PT_Damge,PT_Lore
{
	private boolean clone = false;
	@SuppressWarnings("unused")
	private TData data;

	protected Power_Sound(){
		powers.add(this);
	}
	private Power_Sound(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-Sound:[文本.音效]";
	}

	@Override
	public String getLore(Equipment equip){
		String ssound = equip.getLoreData("Sound");
		return "攻击时播放音效"+ssound;
	}


	@Override
	public String getPowerName(){
		return "Sound";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Sound(data);
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
		String ssound = equip.getLoreData("Sound");
		Sound sound  = Sound.valueOf(ssound);
		if(sound == null){
			user.sendPluginMessage("不存在该音效");
			return;
		}
		player.playSound(player.getLocation(),sound,10,10);
	}

	@Override
	public void run(){
		// TODO: Implement this method
	}

	
	
}
