package com.github.xzzpig.pigrpg.power;

import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;

import com.github.xzzpig.pigrpg.*;

import org.bukkit.event.player.*;

import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import com.github.xzzpig.BukkitTools.*;

public class PowerListener implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if(!(event.getDamager() instanceof LivingEntity))
			return;
		if(!(event.getEntity() instanceof LivingEntity))
			return;
		Debuger.print("onDamage");
		LivingEntity damager = (LivingEntity) event.getDamager();
		LivingEntity target = (LivingEntity) event.getEntity();
		int damage = (int) event.getDamage();
		
		damage = damage + State.getFrom(damager).getPhysicDamage() - State.getFrom(target).getPhysicDefence();
		if(damage < 0)
			damage = 0;
		event.setDamage(damage);
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event)
	{
		Debuger.print("onRightClick()");
		if(event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		User user = User.getUser(event.getPlayer());
		Equipment equip = user.getHandEquip();
		if(equip.getEquiptype() == EquipType.Core)
			equip = user.getEquip(EquipType.Core);
		else if(EquipType.Weapon.getInherit().hasChild(TPremission.valueOf(equip.getEquiptype().toString()))){
			 if(EquipType.getFrom(equip.getEquiptype().toString().replaceAll("核心","")).getInherit().hasChild(TPremission.valueOf(equip.getEquiptype().toString()))){
				 equip = user.getEquip(EquipType.Core);
			 }
			 else{
				user.sendPluginMessage("&4由于武器类型不符，你无法使用该武器");
				return;
			 }
		}
			
		for(Power p : equip.getPowers()){
			if(!(p instanceof PT_RightClick))
				continue;
			((PT_RightClick)p.clone(new TData().setObject("user",User.getUser(event.getPlayer())))).runRC();
		}
	}
}
