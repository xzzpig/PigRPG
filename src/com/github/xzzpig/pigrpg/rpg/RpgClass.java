package com.github.xzzpig.pigrpg.rpg;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.BukkitTools.TConfig;

public class RpgClass {
	private static HashMap<String, RpgClass> classlist = new HashMap<String, RpgClass>();

	private static final FileConfiguration classconfig = TConfig.getConfigFile(
			"PigRPG", "class.yml");

	static {
		for (String classname : classconfig.getConfigurationSection("class")
				.getKeys(false)) {
			new RpgClass(classconfig.getConfigurationSection("class."
					+ classname));
		}
	}

	@SuppressWarnings("unused")
	private String name, displayname;

	public RpgClass(ConfigurationSection path) {
		this.name = path.getName();
		this.displayname = path.getString("displayname", this.name);

		classlist.put(this.name, this);
	}
}
