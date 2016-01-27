package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class Power_Consume extends Power implements PT_Damge,PT_RightClick,PT_BeDamage
{
	Equipment equip;
	User user;
	
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
		boolean hand = false;
        if(equip == user.getHandEquip())
        	hand = true;
		int count = equip.getAmount() - 1;
        if (count == 0) {
            equip.setAmount(0);
        } else {
            equip.setAmount(count);
        }
        if(hand)
        	user.getPlayer().setItemInHand(equip);
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		user = User.getUser(event.getPlayer());
		equip = User.getUser(event.getPlayer()).getHandEquip();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player){
			user = User.getUser((Player)event.getDamager());
			equip = User.getUser((Player)event.getDamager()).getHandEquip();
		}
	}

	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event){
		if(event.getEntity() instanceof Player){
			user = User.getUser((Player)event.getEntity());
			equip = User.getUser((Player)event.getEntity()).getHandEquip();
		}
	}
	
}
