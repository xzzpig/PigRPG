package com.github.xzzpig.pigrpg.mob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.github.xzzpig.BukkitTools.TConfig;
import com.github.xzzpig.pigrpg.rpgworld.RpgChunk;
import com.github.xzzpig.pigrpg.rpgworld.RpgWorld;

@SuppressWarnings("deprecation")
public class MobListener implements Listener {
	private static Random randm = new Random();
	private static FileConfiguration mobconfig = TConfig.getConfigFile("PigRPG", "mob.yml");
	private static List<String> smoblist = mobconfig.getStringList("moblist");
	private static List<EntityType> moblist = new ArrayList<EntityType>();
	static{
		if(smoblist == null||smoblist.size() == 0){
			for(EntityType et:EntityType.values()){
				smoblist.add(et.getName());
			}
			moblist.addAll(Arrays.asList(EntityType.values()));
			mobconfig.set("moblist",smoblist);
			TConfig.saveConfig("PigRPG",mobconfig,"mob.yml");
		}
		else{
			for(String type:smoblist){
				moblist.add(EntityType.valueOf(type));
			}
		}
	}
	

	@EventHandler
	public void onChangeEntityToRpg(CreatureSpawnEvent event) {
		if (!RpgWorld.rpgworldlist.contains(event.getLocation().getWorld()
				.getName()))
			return;
		LivingEntity entity = event.getEntity();
		if (entity instanceof Player)
			return;
		if(!moblist.contains(entity.getType()))
			return;
		RpgChunk rc = new RpgChunk(entity.getLocation().getChunk());
		int level = rc.getBasicLevel() + randm.nextInt(10) - 5;
		if(level<1)
			level = 1;
		CustomMob.getRangeMob(entity, level);
	}
}
