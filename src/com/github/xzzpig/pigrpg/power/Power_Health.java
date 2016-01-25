package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import com.github.xzzpig.pigrpg.*;

public class Power_Health extends Power implements PT_Damge,PT_RightClick,PT_Equip
{
	String target,type,type2,samount;
	int distance;

	int amount;
	LivingEntity entity;

	@Override
	public String getPowerName(){
		return "Health";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		target = pl.getReplaced(path.getString("target","self"));
		type = pl.getReplaced(path.getString("type"));
		samount = pl.getReplaced(path.getString("amount"));
		type2 = pl.getReplaced(path.getString("type2"));
		return this;
	}

	@Override
	public void run(){
		if(entity==null)
			return;
		State state = State.getFrom(entity);
		samount = samount.replaceAll("</health/>",((int)entity.getHealth())+"").replaceAll("</maxhealth/>",((int)entity.getMaxHealth())+"").replaceAll("</damage/>",state.getPhysicDamage()+"");
		amount = (int)TCalculate.getResult(samount);
		if(type.equalsIgnoreCase("max")&&type2.equalsIgnoreCase("set"))
			state.setHp(amount);
		else if(type.equalsIgnoreCase("current")&&type2.equalsIgnoreCase("set")){
			if(amount>state.getHp())
				amount = state.getHp();
			entity.setHealth(amount);
		}
		else if(type.equalsIgnoreCase("max")&&type2.equalsIgnoreCase("add"))
			state.setHp(state.getHp()+amount);
		else if(type.equalsIgnoreCase("current")&&type2.equalsIgnoreCase("add")){
			amount = (int) entity.getHealth()+amount;
			if(amount>state.getHp())
				amount = state.getHp();
			entity.setHealth(amount);
		}
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(target.equalsIgnoreCase("point")){
			if(event.getEntity() instanceof LivingEntity)
				entity = (LivingEntity)event.getEntity();}
		else if(event.getDamager() instanceof LivingEntity)
			entity = (LivingEntity)event.getDamager();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		if(target.equalsIgnoreCase("point"))
			entity = TEntity.getTarget(event.getPlayer(),distance);
		else
			entity = event.getPlayer();
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event){
		if(target.equalsIgnoreCase("point"))
			entity = TEntity.getTarget(event.getPlayer(),distance);
		else
			entity = event.getPlayer();
	}

}
