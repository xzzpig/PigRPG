package com.github.xzzpig.pigrpg.mob;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.github.xzzpig.pigrpg.*;
import java.util.*;
import com.github.xzzpig.BukkitTools.*;

public class CustomMob
{
	private static final Random random = new Random();
	
	public static LivingEntity getRangeMob(LivingEntity entity,int level){
		if(entity instanceof Player)
			return entity;
		State state = new State(entity);
		CustomMob mob = new CustomMob(entity);
		mob.setMobQuality(MobQuality.valueOf(random.nextInt(MobQuality.values().length)));
		state.setHp((int)TCalculate.getResult(Equation.Hp.replaceAll("$level",level+"")));
		state.setPhysicDamage((int)TCalculate.getResult(Equation.Damage.replaceAll("$level",level+"")));
		state.setPhysicDefence((int)TCalculate.getResult(Equation.Defence.replaceAll("$level",level+"")));
		return entity;
	}
	
	private LivingEntity entity;
	
	private CustomMob(LivingEntity entity){
		this.entity = entity;
	}
	
	public CustomMob setMobQuality(MobQuality q){
		State.getFrom(entity).data.setObject("Quality",q);
		return this;
	}
	public MobQuality getMobQuality(){
		return (MobQuality)State.getFrom(entity).data.getObject("Quality");
	}
}
