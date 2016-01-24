package com.github.xzzpig.pigrpg.power;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.BukkitTools.TPremission;
import com.github.xzzpig.pigrpg.State;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.equip.EquipType;
import com.github.xzzpig.pigrpg.equip.Equipment;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Damge;
import com.github.xzzpig.pigrpg.power.type.PT_Limit;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;

public class PowerListener implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		if(!(event.getDamager() instanceof LivingEntity))
			return;
		if(!(event.getEntity() instanceof LivingEntity))
			return;
		LivingEntity damager = (LivingEntity) event.getDamager();
		LivingEntity target = (LivingEntity) event.getEntity();
		State dstate = State.getFrom(damager),tstate = State.getFrom(target) ;
		int origindamage = dstate.getPhysicDamage();
		if(event.getDamager() instanceof Player){
			User user = User.getUser((Player)event.getDamager());
			Equipment equip = user.getHandEquip();
			if(equip.getEquiptype()==EquipType.Core)
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
			pls:for(PowerLore pl : equip.getPowerLores()){
				if(!pl.isRunTime(PowerRunTime.Damage))
					continue pls;
				ps:for(Power p:pl.powers){
					if(p instanceof PT_Limit)
						if(!((PT_Limit) p).can()){
							user.sendPluginMessage(((PT_Limit) p).cantMessage());
							break ps;
						}
					if(!(p instanceof PT_Damge))
						continue;
					((PT_Damge)p).rebulidDamage(event);
					p.run();
				}
			}
			event.setDamage(event.getDamage()+dstate.getPhysicDamage());
			dstate.setPhysicDamage(origindamage);
		}
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		if(event.getAction()!=Action.RIGHT_CLICK_AIR&&event.getAction()!=Action.RIGHT_CLICK_BLOCK)
			return;
		User user = User.getUser(event.getPlayer());
		State state =user.getState() ;
		int origindamage = state.getPhysicDamage();
		Equipment equip = user.getHandEquip();
		if(event.getMaterial().isBlock()&&equip.getEquiptype() != EquipType.Default)
			event.setCancelled(true);
		if(equip.getEquiptype()==EquipType.Core)
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
		pls:for(PowerLore pl : equip.getPowerLores()){
			if(!pl.isRunTime(PowerRunTime.RightClick))
				continue pls;
			ps:for(Power p:pl.powers){
				if(p instanceof PT_Limit)
					if(!((PT_Limit) p).can()){
						user.sendPluginMessage(((PT_Limit) p).cantMessage());
						break ps;
					}
				if(!(p instanceof PT_RightClick))
					continue;
				((PT_RightClick)p).rebuildRC(event);
				p.run();
			}
		}
		state.setPhysicDamage(origindamage);
	}
	
	@EventHandler
	public void onPickUpItem(PlayerPickupItemEvent event){
		if(!(event.getItem() instanceof Arrow))
			return;
		if(Power_Arrow.arrows.contains(event.getItem()))
			event.setCancelled(true);
	}
}
