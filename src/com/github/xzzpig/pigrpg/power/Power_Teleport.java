package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.util.*;

public class Power_Teleport extends Power implements PT_Damge,PT_RightClick
{
	String target,type;
	int distance;

	LivingEntity entity;
	Location loc;

	@Override
	public String getPowerName(){
		return "Teleport";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		target = pl.getReplaced(path.getString("target","self"));
		type = pl.getReplaced(path.getString("type","jump"));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance","10")));
		if(type.equalsIgnoreCase("loc")){
			String world = pl.getReplaced(path.getString("world"));
			double x = Double.valueOf(pl.getReplaced(path.getString("x")));
			double y = Double.valueOf(pl.getReplaced(path.getString("y")));
			double z = Double.valueOf(pl.getReplaced(path.getString("z")));
			loc = new Location(Bukkit.getWorld(world),x,y,z);
		}
		return this;
	}

	@Override
	public void run(){
		if(loc == null){
		LivingEntity player = entity;
		World world = player.getWorld();
		Location start = player.getLocation();
		start.setY(start.getY() + 1.6);
		Block lastSafe = world.getBlockAt(start);
		BlockIterator bi = new BlockIterator(player, distance);
		while (bi.hasNext()) {
			Block block = bi.next();
			if (!block.getType().isSolid() || (block.getType() == Material.AIR)) {
				lastSafe = block;
			} else {
				break;
			}
		}
		Location newLoc = lastSafe.getLocation();
		newLoc.setPitch(start.getPitch());
		newLoc.setYaw(start.getYaw());
		player.teleport(newLoc);
		Power_Effect.playEffect(newLoc, Effect.ENDER_SIGNAL, 0);
		world.playSound(newLoc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.3f);
		}
		else{
			entity.teleport(loc);
			Power_Effect.playEffect(loc, Effect.ENDER_SIGNAL, 0);
			loc.getWorld().playSound(loc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.3f);
		}
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(target.equalsIgnoreCase("self")){
			if(event.getDamager() instanceof LivingEntity)
				entity = (LivingEntity) event.getDamager();
			if(type.equalsIgnoreCase("entity"))
				loc = event.getEntity().getLocation();
		}
		else if(event.getEntity() instanceof LivingEntity){
			entity = (LivingEntity) event.getEntity();
			loc = event.getDamager().getLocation();
		}
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		if(target.equalsIgnoreCase("self")){
			entity = event.getPlayer();
			loc = TEntity.getTarget(event.getPlayer(),distance).getLocation();
		}
		else{
			entity = TEntity.getTarget(event.getPlayer(),distance);
			loc = event.getPlayer().getLocation();
		}
	}

}
