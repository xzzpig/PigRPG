package com.github.xzzpig.pigrpg.power;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import com.github.xzzpig.pigrpg.equip.Equipment;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Limit;

public class Power_Cooldown extends Power implements PT_Limit {
	private static HashMap<String, Long> times = new HashMap<String, Long>();

	long time;

	private long finaltime = System.currentTimeMillis();
	Equipment equip;

	@Override
	public String getPowerName() {
		return "Cooldown";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		time = Long.valueOf(pl.getReplaced(path.getString("time", "0")));
		equip = pl.getEquip();
		return this;
	}

	@Override
	public void run() {
		this.finaltime = System.currentTimeMillis() + time;
		times.put(equip.toString(), this.finaltime);
	}

	@Override
	public boolean can() {
		this.finaltime = getTime(equip + "");
		boolean can = System.currentTimeMillis() >= this.finaltime;
		if (can)
			run();
		return can;
	}

	@Override
	public String cantMessage() {
		return (ChatColor.RED + "未冷却,剩余时间:"
				+ (this.finaltime - System.currentTimeMillis()) + "毫秒");
	}

	private static long getTime(String item) {
		if (!times.containsKey(item)) {
			return System.currentTimeMillis();
		}
		return times.get(item);
	}
}
