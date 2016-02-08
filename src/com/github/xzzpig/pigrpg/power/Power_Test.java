package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Test extends Power implements PT_RightClick {
	String var;

	@Override
	public void rebuildRC(PlayerInteractEvent event) {

	}

	@Override
	public String getPowerName() {
		return "Test";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		var = pl.getReplaced(path.getString("var"));
		return this;
	}

	@Override
	public void run() {
		System.out.println(var);
	}

}
