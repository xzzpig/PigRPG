package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class Power_Consume extends Power implements PT_Damge,PT_RightClick
{
	Equipment equip;
	
	@Override
	public String getPowerName(){
		return "Consume";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		return this;
	}

	@Override
	public void run(){
		int count = equip.getAmount() - 1;
        if (count == 0) {
            equip.setAmount(0);
			equip.powerlores.clear();
        } else {
            equip.setAmount(count);
        }
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		equip = User.getUser(event.getPlayer()).getHandEquip();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player)
			equip = User.getUser((Player)event.getDamager()).getHandEquip();
	}
	
}
