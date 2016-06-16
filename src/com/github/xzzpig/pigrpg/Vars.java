package com.github.xzzpig.pigrpg;

import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.pigapi.PigData;

public class Vars {
	public static final String PluginName = "PigRPG";

	public static PigData pdata;

	public static FileConfiguration config;

	public static HashMap<String, Boolean> enables;

	public static String playerinfotype;

	public static List<String> playerinfo;

	public static boolean debuger;
}
