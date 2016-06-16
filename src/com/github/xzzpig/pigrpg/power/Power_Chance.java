package com.github.xzzpig.pigrpg.power;

import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;

import com.github.xzzpig.pigapi.TCalculate;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Limit;

public class Power_Chance extends Power implements PT_Limit {
	private static Random rand = new Random();

	String message;
	int chance;

	@Override
	public boolean can() {
		if (rand.nextInt(100) <= chance)
			return true;
		return false;
	}

	@Override
	public String cantMessage() {
		return message;
	}

	@Override
	public String getPowerName() {
		return "Chance";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		message = pl.getReplaced(path.getString("message"));
		chance = (int) TCalculate.getResult(pl.getReplaced(path.getString(
				"chance", "100")));
		return this;
	}

	@Override
	public void run() {
	}

}
