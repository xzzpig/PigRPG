package com.github.xzzpig.pigrpg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.earth2me.essentials.Essentials;
import com.github.xzzpig.BukkitTools.TConfig;
import com.gmail.filoghost.holographicdisplays.nms.interfaces.NMSManager;

public class Vars {
	public static FileConfiguration configs;

	public static List<String> banWords = new ArrayList<String>();

	public static boolean hasEco;

	public static Essentials ess;

	public static boolean hasEss;

	public static NMSManager nms;

	public static FileConfiguration switchconfig = TConfig.getConfigFile(
			"PigRPG", "switch.yml");

	public static final boolean RCSystem = Vars.switchconfig.getBoolean(
			"RightClick", true);
	public static final boolean FriendSystem = Vars.switchconfig.getBoolean(
			"Friend", true);
	public static final boolean TradeSystem = Vars.switchconfig.getBoolean(
			"Trade", true);
	public static final boolean TeleportSystem = Vars.switchconfig.getBoolean(
			"Teleport", true);
	public static final boolean ChatSystem = Vars.switchconfig.getBoolean(
			"Chat", true);
	public static final boolean TeamSystem = Vars.switchconfig.getBoolean(
			"Team", true);
	public static final boolean SaleSystem = Vars.switchconfig.getBoolean(
			"Sale", true);
	public static final boolean EquipSystem = Vars.switchconfig.getBoolean(
			"Equip", true);
	public static final boolean PowerSystem = Vars.switchconfig.getBoolean(
			"Power", true);
	public static final boolean ScoreSystem = Vars.switchconfig.getBoolean(
			"Score", true);
	public static final boolean RpgWorldSystem = Vars.switchconfig.getBoolean(
			"RpgWorld", true);
	public static final boolean RpgMobSystem = Vars.switchconfig.getBoolean(
			"CustomMob", true);
	static {
		TConfig.saveConfig("PigRPG", "switch.yml", "RightClick", RCSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "Friend", FriendSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "Trade", TradeSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "Teleport", TeleportSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "Chat", ChatSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "Team", TeamSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "Sale", SaleSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "Equip", EquipSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "Power", PowerSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "Score", ScoreSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "RpgWorld", RpgWorldSystem);
		TConfig.saveConfig("PigRPG", "switch.yml", "CustomMob", RpgMobSystem);
	}

}
