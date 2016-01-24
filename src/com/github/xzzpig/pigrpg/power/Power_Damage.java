package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.pigrpg.power.type.*;
import com.github.xzzpig.pigrpg.equip.*;
import org.bukkit.event.entity.*;
import org.bukkit.configuration.*;
import org.bukkit.event.player.*;
import org.bukkit.event.inventory.*;
import org.bukkit.entity.*;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;

public class Power_Damage extends Power implements PT_Damge,PT_RightClick,PT_Equip
{
	String type,sdamage;
	int range,damage,distance,times;

	LivingEntity entity,target;

	@Override
	public String getPowerName(){
		return "Damage";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		type = pl.getReplaced(path.getString("type"));
		sdamage = pl.getReplaced(path.getString("damage","0"));
		range = Integer.valueOf(pl.getReplaced(path.getString("range","0")));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance","10")));
		times = Integer.valueOf(pl.getReplaced(path.getString("times","1")));
		return this;
	}

	@Override
	public void run(){
		if(entity==null)
			return;
		damage = (int)TCalculate.getResult(sdamage.replaceAll("</damage/>",State.getFrom(entity).getPhysicDamage()+""));
		State state = State.getFrom(entity);
		if(type.equalsIgnoreCase("set"))
			state.setPhysicDamage(damage);
		else if(type.equalsIgnoreCase("add"))
			state.setPhysicDamage(damage+state.getPhysicDamage());
		else{
			if(target==null)
				target = TEntity.getTarget(entity,distance);
			for(int i = 0;i<times;i++)
				target.damage(damage,entity);
		}

	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(!(event.getDamager() instanceof LivingEntity))
			return;
		entity = (LivingEntity) event.getDamager();
		if(event.getEntity() instanceof LivingEntity)
			target = (LivingEntity) event.getEntity();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		entity = event.getPlayer();
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event){
		entity = event.getPlayer();
	}

}
