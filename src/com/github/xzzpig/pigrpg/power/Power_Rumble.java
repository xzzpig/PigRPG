package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;

import java.util.*;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.Vector;

public class Power_Rumble extends Power implements PT_RightClick,PT_Lore
{
	private boolean clone = false;
	private TData data;

	protected Power_Rumble(){
		powers.add(this);
	}
	private Power_Rumble(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-Rumble:[整数.力度]|[整数.距离]";
	}

	@Override
	public String getLore(Equipment equip){
		return "发射火焰弹";
	}


	@Override
	public String getPowerName(){
		return "Rumble";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Rumble(data);
	}

	//user:User
	@Override
	public void runRC(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		Equipment equip = user.getHandEquip();
		String[] args = equip.getLoreData("Rumble").replaceAll("|","~").split("~");
		int power = 0,distance = 0;
		try{
			power = Integer.valueOf(args[0]);
			distance = Integer.valueOf(args[1]);
		}catch(Exception ex){
			user.sendPluginMessage("&4错误:力度或距离不是整数");
			return;
		}
		final int spower = power,sdistance = distance;
		final Player player = user.getPlayer();
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
						temp.getWorld().playEffect(temp,Effect.STEP_SOUND,block.getTypeId());
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
					location.getWorld().createExplosion(location.getX(),location.getY(),location.getZ(),spower,false,false);
					near = getNearbyEntities(location,2.5);
					for(Entity e : near){
						if(e!=player)
							e.setVelocity(new Vector(random.nextGaussian()/4d,1d+random.nextDouble()*(double) spower,random.nextGaussian()/4d));
					}
					cancel();
					return;
				}
				location.add(direction);
				if(count>=sdistance){
					cancel();
				}
				count++;
			}
		};
		task.runTaskTimer(Bukkit.getPluginManager().getPlugin("PigRPG"),0,3);
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
