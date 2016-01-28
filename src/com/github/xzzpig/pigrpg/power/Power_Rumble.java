package com.github.xzzpig.pigrpg.power;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Rumble extends Power implements PT_Damage,PT_RightClick
{
	int power,distance,chance;
	
	LivingEntity entity;
	
	@Override
	public String getPowerName(){
		return "Rumble";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		power = Integer.valueOf(pl.getReplaced(path.getString("power")));
		distance = Integer.valueOf(pl.getReplaced(path.getString("distance","10")));
		chance = Integer.valueOf(pl.getReplaced(path.getString("chance","100")));
		
		return this;
	}

	@Override
	public void run(){
		if(entity == null)
			return;
		
		final LivingEntity player = entity;
		final Location location = player.getLocation().add(0,-0.2,0);
		final Vector direction = player.getLocation().getDirection();
		direction.setY(0);
		direction.normalize();
		
		BukkitRunnable task = new BukkitRunnable() {

			private int count = 0;

			@SuppressWarnings("deprecation")
			public void run(){
				Location above = location.clone().add(0,1,0);
				if(above.getBlock().getType().isSolid()||!location.getBlock().getType().isSolid()){
					cancel();
					return;
				}

				Location temp = location.clone();
				for(int x = -2; x<=2; x++){
					for(int z = -2; z<=2; z++){
						temp.setX(x+location.getBlockX());
						temp.setZ(z+location.getBlockZ());
						Block block = temp.getBlock();
						Power_Effect.playEffect(temp,Effect.STEP_SOUND,block.getTypeId());
					}
				}
				Entity[] near = getNearbyEntities(location,1.5);
				boolean hit = false;
				Random random = new Random();
				for(Entity e : near){
					if(e!=player){
						hit = true;
						break;
					}
				}
				if(hit){
					location.getWorld().createExplosion(location.getX(),location.getY(),location.getZ(),power,false,false);
					near = getNearbyEntities(location,2.5);
					for(Entity e : near){
						if(e!=player)
							e.setVelocity(new Vector(random.nextGaussian()/4d,1d+random.nextDouble()*(double) power,random.nextGaussian()/4d));
					}
					cancel();
					return;
				}
				location.add(direction);
				if(count>=distance){
					cancel();
				}
				count++;
			}
		};
		task.runTaskTimer(Bukkit.getPluginManager().getPlugin("PigRPG"),0,3);
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof LivingEntity)
			entity = (LivingEntity) event.getDamager();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		entity = event.getPlayer();
	}
	
	public static Entity[] getNearbyEntities(Location l, double radius) {
        int iRadius = (int) radius;
        int chunkRadius = iRadius < 16 ? 1 : (iRadius - (iRadius % 16)) / 16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
                for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock())
                        radiusEntities.add(e);
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }
	
}
