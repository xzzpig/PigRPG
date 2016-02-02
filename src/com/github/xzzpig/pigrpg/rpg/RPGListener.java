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
	private static final String message = TConfig.getConfigFile("PigRPG","skillconfig.yml").getString("level.get_exp_message","你获得了</exp/>点exp");
	private static final boolean teamshare = TConfig.getConfigFile("PigRPG","skillconfig.yml").getBoolean("level.enable_team_share_exp",true);
	private static final String shareexp = TConfig.getConfigFile("PigRPG","skillconfig.yml").getString("level.team_share_exp","</exp/>/10+1");
	private static final int sharedistance = TConfig.getConfigFile("PigRPG","skillconfig.yml").getInt("level.team_share_max_distance",30);
	
	private static HashMap<EntityType,Integer> exps = new HashMap<EntityType,Integer>();
	static{
		TConfig.saveConfig("PigRPG","skillconfig.yml","level.get_exp_message",message);
		TConfig.saveConfig("PigRPG","skillconfig.yml","level.exptype",exptype);
		TConfig.saveConfig("PigRPG","skillconfig.yml","level.enable_team_share_exp",teamshare);
		TConfig.saveConfig("PigRPG","skillconfig.yml","level.team_share_exp",shareexp);
		TConfig.saveConfig("PigRPG","skillconfig.yml","level.team_share_distance",sharedistance);
		
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
		user.sendPluginMessage(message.replaceAll("</exp/>",""+exp));
		if(teamshare&&(user.getTeam() != null)){
			int sexp = (int) TCalculate.getResult(shareexp.replaceAll("</exp/>",""+exp));
			for(User member:user.getTeam().getMembers()){
				if(member == user)
					continue;
				if(member.getPlayer().getWorld()!=user.getPlayer().getWorld())
					continue;
				if(member.getPlayer().getLocation().distance(user.getPlayer().getLocation())>sharedistance)
					continue;
				member.addExp(sexp);
				member.sendPluginMessage("&3你从"+user.getPlayer().getName()+"分享到了"+sexp+"点exp");
			}
		}
	}
}
