package com.github.xzzpig.pigrpg.power;
import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Damge;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;
import java.util.*;

public class Power_Arrow extends Power implements PT_Damge,PT_RightClick
{
	public static List<Arrow> arrows = new ArrayList<Arrow>();
	
	Random random = new Random();
	
	int amount = 1,range = 100;
	
	Player launcher;
	
	@Override
	public String getPowerName(){
		return "Arrow";
	}
	
	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		amount = Integer.valueOf(pl.getReplaced(path.getString("amount","1")));
		range = Integer.valueOf(pl.getReplaced(path.getString("range","100")));
		return this;
	}

	@Override
	public void run(){
		for(Arrow arr:arrows)
			arr.remove();
		arrows.clear();
		if(launcher == null)
			return;
		int r = random.nextInt(100);
		if(r > range)
			return;
		for(int i = 0;i < amount;i++){
			Arrow arrow = launcher.launchProjectile(Arrow.class);
			arrows.add(arrow);
			launcher.playSound(launcher.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
		}
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player)
			launcher = (Player)event.getDamager();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		launcher = event.getPlayer();
	}
}
