package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.util.*;
import org.bukkit.entity.*;

public class Power_Teleport extends Power implements PT_RightClick,PT_Lore
{
	private boolean clone = false;
	private TData data;

	protected Power_Teleport(){
		powers.add(this);
	}
	private Power_Teleport(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-Teleport:[整数.距离]";
	}

	@Override
	public String getLore(Equipment equip){
		return "传送";
	}


	@Override
	public String getPowerName(){
		return "Teleport";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Teleport(data);
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
		String sdistance = equip.getLoreData("Teleport");
		int distance;
		try{
			distance = Integer.valueOf(0+sdistance);
		}catch(Exception ex){
			user.sendPluginMessage("&4错误:距离不是整数");
			return;
		}
		Player player = user.getPlayer();
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

	@Override
	public void run(){
		// TODO: Implement this method
	}

	
	
}
