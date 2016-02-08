package com.github.xzzpig.pigrpg.score;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.BukkitTools.TConfig;

public class CustomScore {
	public static final FileConfiguration config = TConfig.getConfigFile(
			"PigRPG", "score.yml");
	public static HashMap<String, Object> scores = new HashMap<String, Object>();
	static {
		ConfigurationSection sec = config.getConfigurationSection("scores");
		if (sec != null)
			scores.putAll(sec.getValues(false));
	}
}