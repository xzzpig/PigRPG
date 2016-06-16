package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import com.github.xzzpig.pigapi.TCalculate;
import com.github.xzzpig.pigrpg.StringMatcher;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Limit;

public class Power_Condition extends Power implements PT_Limit {
	public static String buildStr(String str, LivingEntity entity, boolean isInt) {
		return StringMatcher.buildStr(str, entity, isInt);
	}

	String left, right, type, data, message;

	LivingEntity entity;

	@Override
	public boolean can() {
		String sl = buildStr(left, entity, data.equalsIgnoreCase("int"));
		String sr = buildStr(right, entity, data.equalsIgnoreCase("int"));
		if (data.equalsIgnoreCase("int")) {
			if (type.equalsIgnoreCase("equal"))
				return TCalculate.getResult(sl) == TCalculate.getResult(sr);
			else if (type.equalsIgnoreCase("unequal"))
				return TCalculate.getResult(sl) != TCalculate.getResult(sr);
			else if (type.equalsIgnoreCase("less"))
				return TCalculate.getResult(sl) < TCalculate.getResult(sr);
			else if (type.equalsIgnoreCase("more"))
				return TCalculate.getResult(sl) > TCalculate.getResult(sr);
		} else {
			if (type.equalsIgnoreCase("equal"))
				return sl.equalsIgnoreCase(sr);
			else
				return !sl.equalsIgnoreCase(sr);
		}
		return false;
	}

	@Override
	public String cantMessage() {
		return buildStr(message, entity, false);
	}

	@Override
	public String getPowerName() {
		return "Condition";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		left = pl.getReplaced(path.getString("left"));
		right = pl.getReplaced(path.getString("right"));
		type = pl.getReplaced(path.getString("type", "equal"));
		data = pl.getReplaced(path.getString("data", "str"));
		message = pl.getReplaced(path.getString("message", "条件不符"));
		entity = pl.getEquip().getOwner();
		return this;
	}

	@Override
	public void run() {
		// TODO: Implement this method
	}
}
