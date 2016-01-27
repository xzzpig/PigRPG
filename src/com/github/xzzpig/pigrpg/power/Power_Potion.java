package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import java.util.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.potion.*;

public class Power_Potion extends Power implements PT_Damge,PT_RightClick,PT_Equip,PT_BeDamage
{
	private static Random rand = new Random();

	PotionEffectType potion;
	int time,distance,level,chance;
	String target,type;

	LivingEntity entity;

	@Override
	public String getPowerName(){
		return "Potion";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		potion = PotionEffectType.getByName(pl.getReplaced(path.getString("potion",PotionEffectType.POISON.toString())));
		time = Integer.valueOf(pl.getReplaced(path.getString("time","0")));
		level = Integer.valueOf(pl.getReplaced(path.getString("level","1")));
		chance = Integer.valueOf(pl.getReplaced(path.getString("chance","100")));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance","10")));
		target = pl.getReplaced(path.getString("target","self"));
		type = pl.getReplaced(path.getString("type","add"));
		return this;
	}

	@Override
	public void run(){
		if(entity==null)
			return;
		if(type.equalsIgnoreCase("add")){
			if(rand.nextInt(100)<=chance)
				new PotionEffect(potion,time,level).apply(entity);}
		else
			entity.removePotionEffect(potion);
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(target.equalsIgnoreCase("self")){
			if(event.getDamager() instanceof LivingEntity)
				entity = (LivingEntity) event.getDamager();}
		else if(event.getEntity() instanceof LivingEntity)
			entity = (LivingEntity) event.getEntity();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		if(target.equalsIgnoreCase("self"))
			entity = event.getPlayer();
		else
			entity = TEntity.getTarget(event.getPlayer(),distance);
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event){
		if(target.equalsIgnoreCase("self"))
			entity = event.getPlayer();
		else
			entity = TEntity.getTarget(event.getPlayer(),distance);
		if(type.equalsIgnoreCase("add"))
			State.getFrom(entity).potions.add(potion);
	}
	
	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event){
		if(target.equalsIgnoreCase("target")){
			if(event.getDamager() instanceof LivingEntity)
				entity = (LivingEntity) event.getDamager();}
		else if(event.getEntity() instanceof LivingEntity)
			entity = (LivingEntity) event.getEntity();
	}
}