package com.github.xzzpig.pigrpg.rpg;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.BukkitTools.*;
import java.util.*;

public class RPGListener implements Listener
{
	private static final String exptype = TConfig.getConfigFile("PigRPG","skillconfig.yml").getString("level.exptype","normal");
	private static HashMap<EntityType,Integer> exps = new HashMap<EntityType,Integer>();
	static{
		TConfig.saveConfig("PigRPG","skillconfig.yml","level.exptype",exptype);
		if(!exptype.equalsIgnoreCase("normal"))
			for(EntityType et:EntityType.values()){
				int exp = TConfig.getConfigFile("PigRPG","skillconfig.yml").getInt("level.exp."+et,1);
				exps.put(et,exp);
				TConfig.saveConfig("PigRPG","skillconfig.yml","level.exp."+et,exp);
			}
	}
	
	@EventHandler
	public void onKillEntity(EntityDeathEvent event){
		Player player = event.getEntity().getKiller();
		if(player == null)
			return;
		User user = User.getUser(player);
		int exp = event.getDroppedExp();
		if(!exptype.equalsIgnoreCase("normal"))
			exp = exps.get(event.getEntityType());
		user.addExp(exp);
	}
}
