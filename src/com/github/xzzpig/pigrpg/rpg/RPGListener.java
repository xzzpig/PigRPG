package com.github.xzzpig.pigrpg.rpg;

import java.util.HashMap;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.xzzpig.BukkitTools.TCalculate;
import com.github.xzzpig.BukkitTools.TConfig;
import com.github.xzzpig.BukkitTools.TPlayer;
import com.github.xzzpig.pigrpg.User;

public class RPGListener implements Listener {
	private static final String leveltype = TConfig.getConfigFile("PigRPG",
			"skillconfig.yml").getString("level.leveltype", "normal");
	private static final String level = TConfig.getConfigFile("PigRPG",
			"skillconfig.yml").getString("level.level", "</exp/>/100");
	private static final String exptype = TConfig.getConfigFile("PigRPG",
			"skillconfig.yml").getString("level.exptype", "normal");
	private static final String message = TConfig.getConfigFile("PigRPG",
			"skillconfig.yml").getString("level.get_exp_message",
			"你获得了</exp/>点exp");
	private static final boolean teamshare = TConfig.getConfigFile("PigRPG",
			"skillconfig.yml").getBoolean("level.enable_team_share_exp", true);
	private static final String shareexp = TConfig.getConfigFile("PigRPG",
			"skillconfig.yml")
			.getString("level.team_share_exp", "</exp/>/10+1");
	private static final int sharedistance = TConfig.getConfigFile("PigRPG",
			"skillconfig.yml").getInt("level.team_share_max_distance", 30);

	private static HashMap<EntityType, Integer> exps = new HashMap<EntityType, Integer>();
	static {
		TConfig.saveConfig("PigRPG", "skillconfig.yml", "level.leveltype",
				leveltype);
		if (!leveltype.equalsIgnoreCase("normal"))
			TConfig.saveConfig("PigRPG", "skillconfig.yml", "level.level",
					level);
		TConfig.saveConfig("PigRPG", "skillconfig.yml",
				"level.get_exp_message", message);
		TConfig.saveConfig("PigRPG", "skillconfig.yml", "level.exptype",
				exptype);
		TConfig.saveConfig("PigRPG", "skillconfig.yml",
				"level.enable_team_share_exp", teamshare);
		TConfig.saveConfig("PigRPG", "skillconfig.yml", "level.team_share_exp",
				shareexp);
		TConfig.saveConfig("PigRPG", "skillconfig.yml",
				"level.team_share_distance", sharedistance);

		if (!exptype.equalsIgnoreCase("normal"))
			for (EntityType et : EntityType.values()) {
				int exp = TConfig.getConfigFile("PigRPG", "skillconfig.yml")
						.getInt("level.exp." + et, -1);
				exps.put(et, exp);
				TConfig.saveConfig("PigRPG", "skillconfig.yml", "level.exp."
						+ et, exp);
			}
	}

	@EventHandler
	public void onKillEntity(EntityDeathEvent event) {
		Player player = event.getEntity().getKiller();
		if (player == null) {
			return;
		}
		User user = User.getUser(player);
		int exp = event.getDroppedExp();
		if (!exptype.equalsIgnoreCase("normal"))
			exp = exps.get(event.getEntityType());
		if (exp == -1)
			exp = event.getDroppedExp();
		user.addExp(exp);
		user.sendPluginMessage(message.replaceAll("</exp/>", "" + exp));
		user.buildScore();
		if (teamshare && (user.getTeam() != null)) {
			int sexp = (int) TCalculate.getResult(shareexp.replaceAll(
					"</exp/>", "" + exp));
			for (User member : user.getTeam().getMembers()) {
				if (member == user)
					continue;
				if (member.getPlayer().getWorld() != user.getPlayer()
						.getWorld())
					continue;
				if (member.getPlayer().getLocation()
						.distance(user.getPlayer().getLocation()) > sharedistance)
					continue;
				member.addExp(sexp);
				member.sendPluginMessage("&3你从" + user.getPlayer().getName()
						+ "分享到了" + sexp + "点exp");
				member.buildScore();
			}
		}
	}

	public static int getLevel(int exp) {
		if (leveltype.equalsIgnoreCase("normal"))
			return TPlayer.ExpToLevel(exp);
		return (int) TCalculate.getResult(RPGListener.level.replaceAll(
				"</exp/>", exp + ""));
	}
}
