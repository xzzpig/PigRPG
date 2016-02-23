package com.github.xzzpig.pigrpg.mob;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.github.xzzpig.BukkitTools.TCalculate;
import com.github.xzzpig.BukkitTools.TConfig;
import com.github.xzzpig.pigrpg.State;
import com.github.xzzpig.pigrpg.StringMatcher;

public class CustomMob {
	private static final Random random = new Random();
	private static FileConfiguration mobconfig = TConfig.getConfigFile(
			"PigRPG", "mob.yml");
	private static final String displayname = TConfig.getConfigFile("PigRPG",
			"mob.yml").getString("custom_displayname",
			"[</quality/>&r]</mobname/>-Lv</level/>");
	public static final HashMap<EntityType, String> mobname = new HashMap<EntityType, String>();
	static {
		TConfig.saveConfig("PigRPG", "mob.yml", "custom_displayname",
				displayname);
		for (EntityType et : EntityType.values()) {
			mobname.put(et, mobconfig.getString("mobname." + et, et + ""));
			mobconfig.set("mobname." + et, mobname.get(et));
		}
		TConfig.saveConfig("PigRPG", mobconfig, "mob.yml");
	}

	@SuppressWarnings({ "deprecation" })
	public static LivingEntity getRangeMob(LivingEntity entity, int level) {
		if (entity instanceof Player)
			return entity;
		State.getFrom(entity).remove();
		State state = new State(entity);
		state.setLevel(level);
		CustomMob mob = new CustomMob(entity);
		mob.setMobQuality(MobQuality.valueOf(random.nextInt(MobQuality.values().length)));
		state.setHp((int) TCalculate.getResult(
				Equation.Hp.replaceAll("</level/>", level + ""), 20));
		entity.setHealth(state.getHp());
		state.setPhysicDamage((int) TCalculate.getResult(
				Equation.Damage.replaceAll("</level/>", level + ""), 1));
		state.setPhysicDefence((int) TCalculate.getResult(
				Equation.Defence.replaceAll("</level/>", level + ""), 0));
		entity.setCustomName(StringMatcher.buildStr(displayname, entity, false)
				.replaceAll("</mobname/>", mobname.get(entity.getType())));
		/*
		 * Power power = null; Power[] powers = Power.values(); for(int i = 0;i
		 * < mob.getMobQuality().getPowerNumber();i++){ while(power instanceof
		 * PT_Entity){ power = powers[random.nextInt(powers.length)]; }
		 * //state.addPowers(power.clone(null)); }
		 */
		return entity;
	}

	private LivingEntity entity;

	public CustomMob(LivingEntity entity) {
		this.entity = entity;
	}

	public CustomMob setMobQuality(MobQuality q) {
		State.getFrom(entity).data.setObject("Quality", q);
		return this;
	}

	public MobQuality getMobQuality() {
		return (MobQuality) State.getFrom(entity).data.getObject("Quality");
	}
}
