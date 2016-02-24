package com.github.xzzpig.pigrpg;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.earth2me.essentials.craftbukkit.SetExpFix;
import com.github.xzzpig.BukkitTools.TCalculate;
import com.github.xzzpig.BukkitTools.TEntity;
import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.mob.CustomMob;
import com.github.xzzpig.pigrpg.rpgworld.RpgChunk;

public class StringMatcher {
	public static String solve(String ps) {
		return ps.replaceAll("&", TString.s);
	}

	@SuppressWarnings("deprecation")
	public static String buildStr(String str, LivingEntity entity, boolean isInt) {
		State state = State.getFrom(entity);
		String re = str
				.replaceAll("</world/>", entity.getWorld().getName())
				.replaceAll("</loc/>", entity.getLocation().toString())
				.replaceAll("</x/>", entity.getLocation().getBlockX() + "")
				.replaceAll("</y/>", entity.getLocation().getBlockY() + "")
				.replaceAll("</z/>", entity.getLocation().getBlockZ() + "")
				.replaceAll("</maxhealth/>", state.getHp() + "")
				.replaceAll("</currenthealth/>", "" + TEntity.getHealth(entity))
				.replaceAll("</name/>", entity.getCustomName())
				.replaceAll("</type/>", entity.getType().toString())
				.replaceAll("</online/>", Bukkit.getOnlinePlayers().length + "")
				.replaceAll("</worldpvp/>", entity.getWorld().getPVP() + "")
				.replaceAll(
						"</areaname/>",
						new RpgChunk(entity.getLocation().getChunk())
								.getData("name")
								+ RpgChunk.chbiome.get(new RpgChunk(entity
										.getLocation().getChunk()).getBiome()))
				.replaceAll(
						"</arealevel/>",
						new RpgChunk(entity.getLocation().getChunk())
								.getBasicLevel() + "")
				.replaceAll("</level/>", state.getLevel() + "")
				.replaceAll("</quality/>",
						new CustomMob(entity).getMobQuality() + "")
				.replaceAll("</pdamage/>", state.getPhysicDamage() + "")
				.replaceAll("</mdamage/>", state.getMagicDamage() + "")
				.replaceAll("</pdefence/>", state.getPhysicDefence() + "")
				.replaceAll("</mdefence/>", state.getMagicDefine() + "")
				.replaceAll("</mp/>", state.getMp() + "")
				.replaceAll("</lastdamage/>",state.getLastDamage()+"");
		if (entity instanceof Player) {
			Player player = (Player) entity;
			User user = User.getUser(player);
			re = re.replaceAll("</fly/>", player.getAllowFlight() + "")
					.replaceAll("</exp/>",
							SetExpFix.getTotalExperience(player) + "")
					.replaceAll("</handid/>",
							player.getItemInHand().getTypeId() + "")
					.replaceAll("</gamemode/>", player.getGameMode().name())
					.replaceAll("</chatchannel/>",
							user.getChatchannel().getName())
					.replaceAll("</money/>",
							"" + (int) user.getEcoAPI().getMoney())
					.replaceAll("</op/>", player.isOp() + "")
					.replaceAll("</sneak/>", "" + player.isSneaking())
					.replaceAll("</rpgexp/>", user.getExp() + "")
					.replaceAll("</rpglevel/>", user.getLevel() + "")
					.replaceAll("</hunger/>",
							user.getPlayer().getFoodLevel() + "")
					.replaceAll("</rpgclass/>", user.getRpgClass().getName())
					.replaceAll("</rpgclassname/>",
							user.getRpgClass().getDisplayName());
		}
		if (isInt)
			re = ((int) TCalculate.getResult(re)) + "";
		re = solve(re);
		return re;
	}
}
