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

public class Power_Message extends Power implements PT_Damge,PT_RightClick,PT_Equip,PT_Killed
{
	String message,target;
	int distance;
	
	Player player;
	PowerLore pl;
	
	@Override
	public String getPowerName(){
		return "Message";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		this.pl = pl;
		message = pl.getReplaced(path.getString("message"));
		target = pl.getReplaced(path.getString("target","self"));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance","10")));
		return this;
	}

	@Override
	public void run(){
		if(player == null)
			return;
		message = Power_Condition.buildStr(message,player,false);
		User.getUser(player).sendPluginMessage(message);
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(target.equalsIgnoreCase("point")){
			if(event.getEntity() instanceof Player)
				player = (Player)event.getEntity();}
		else if(event.getDamager() instanceof Player)
			player = (Player)event.getDamager();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		if(target.equalsIgnoreCase("point")){
			LivingEntity entity = TEntity.getTarget(event.getPlayer(),distance);
			if(entity instanceof Player)
				player = (Player)entity;
		}else
			player = event.getPlayer();
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event){
		if(target.equalsIgnoreCase("point")){
			LivingEntity entity = TEntity.getTarget(event.getPlayer(),distance);
			if(entity instanceof Player)
				player = (Player)entity;
		}else
			player = (Player)event.getPlayer();
	}
	
	@Override
	public void rebulidKilled(EntityDeathEvent event){
		if(target.equalsIgnoreCase("point")){
			player = event.getEntity().getKiller();
		}else
		if(event.getEntity() instanceof Player)
			player = (Player)event.getEntity();
	}
}
