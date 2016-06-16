package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Limit;

public class Power_Premission extends Power implements PT_Limit {
	String premission;

	Player player;

	@Override
	public boolean can() {
		return player.hasPermission(premission);
	}

	@Override
	public String cantMessage() {
		return "权限不足,无法使用";
	}

	@Override
	public String getPowerName() {
		return "Premission";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		player = pl.getEquip().getOwner();
		premission = pl.getReplaced(path.getString("pression"));
		return this;
	}

	@Override
	public void run() {
	}

}
