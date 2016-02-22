package com.github.xzzpig.pigrpg.mob;

import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.github.xzzpig.BukkitTools.TCalculate;
import com.github.xzzpig.pigrpg.State;

public class CustomMob {
	private static final Random random = new Random();

	@SuppressWarnings({ "deprecation" })
	public static LivingEntity getRangeMob(LivingEntity entity, int level) {
		if (entity instanceof Player)
			return entity;
		State.getFrom(entity).remove();
		State state = new State(entity);
		CustomMob mob = new CustomMob(entity);
		mob.setMobQuality(MobQuality.valueOf(random.nextInt(MobQuality.values().length)));
		state.setHp((int) TCalculate.getResult(
				Equation.Hp.replaceAll("</level/>", level + ""), 20));
		entity.setHealth(state.getHp());
		state.setPhysicDamage((int) TCalculate.getResult(
				Equation.Damage.replaceAll("</level/>", level + ""), 1));
		state.setPhysicDefence((int) TCalculate.getResult(
				Equation.Defence.replaceAll("</level/>", level + ""), 0));
		entity.setCustomName(entity.getCustomName()+"-Lv"+level);
		/*
		 * Power power = null; Power[] powers = Power.values(); for(int i = 0;i
		 * < mob.getMobQuality().getPowerNumber();i++){ while(power instanceof
		 * PT_Entity){ power = powers[random.nextInt(powers.length)]; }
		 * //state.addPowers(power.clone(null)); }
		 */
		return entity;
	}

	private LivingEntity entity;

	private CustomMob(LivingEntity entity) {
		this.entity = entity;
	}

	public CustomMob setMobQuality(MobQuality q) {
		State.getFrom(entity).data.setObject("Quality", q);
		entity.setCustomName(q.getChatColor() + "[" + q.getName() + "]"
				+ entity.getType());
		return this;
	}

	public MobQuality getMobQuality() {
		return (MobQuality) State.getFrom(entity).data.getObject("Quality");
	}
}
