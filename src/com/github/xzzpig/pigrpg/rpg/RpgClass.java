package com.github.xzzpig.pigrpg.rpg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.BukkitTools.TConfig;
import com.github.xzzpig.BukkitTools.TData;

public class RpgClass {
	private static HashMap<String, RpgClass> classlist = new HashMap<String, RpgClass>();

	private static final FileConfiguration classconfig = TConfig.getConfigFile(
			"PigRPG", "class.yml");

	private HashMap<Integer, TData> levelinfos = new HashMap<Integer, TData>();

	private static void load() {
		try {
			for (String classname : classconfig
					.getConfigurationSection("class").getKeys(false)) {
				new RpgClass(classconfig.getConfigurationSection("class."
						+ classname));
			}
		} catch (Exception e) {

		}
	}

	private static void save() {
		Iterator<Map.Entry<String, RpgClass>> ir = classlist.entrySet()
				.iterator();
		while (ir.hasNext()) {
			Map.Entry<String, RpgClass> e = ir.next();
			save(e.getValue());
		}
	}

	private static void save(RpgClass rpgclass) {
		classconfig.set("class." + rpgclass.getName() + ".displayname",
				rpgclass.displayname);
		classconfig.set("class." + rpgclass.getName() + ".maxlevel",
				rpgclass.maxlevel);
		classconfig.set("class." + rpgclass.getName() + ".preclass",
				rpgclass.preclass);
		Iterator<Entry<Integer, TData>> ir = rpgclass.levelinfos.entrySet()
				.iterator();
		while (ir.hasNext()) {
			Entry<Integer, TData> next = ir.next();
			classconfig.set("class." + rpgclass.getName() + ".levelinfo."
					+ next.getKey() + ".HP", next.getValue().getString("HP"));
			classconfig.set("class." + rpgclass.getName() + ".levelinfo."
					+ next.getKey() + ".MP", next.getValue().getString("MP"));
			classconfig.set("class." + rpgclass.getName() + ".levelinfo."
					+ next.getKey() + ".PDamage",
					next.getValue().getString("PDamage"));
			classconfig.set("class." + rpgclass.getName() + ".levelinfo."
					+ next.getKey() + ".MDamage",
					next.getValue().getString("MDamage"));
			classconfig.set("class." + rpgclass.getName() + ".levelinfo."
					+ next.getKey() + ".PDefence",
					next.getValue().getString("PDefence"));
			classconfig.set("class." + rpgclass.getName() + ".levelinfo."
					+ next.getKey() + ".MDefence",
					next.getValue().getString("MDefence"));
		}
		TConfig.saveConfig("PigRPG", classconfig, "class.yml");
	}

	public static RpgClass valueOf(String rpgclass) {
		if (classlist.containsKey(rpgclass))
			return classlist.get(rpgclass);
		return defaultclass;
	}

	public static RpgClass defaultclass;
	static {
		defaultclass = new RpgClass();
		defaultclass.name = "default";
		defaultclass.displayname = "无";
		defaultclass.preclass = "default";
		defaultclass.maxlevel = -1;
		TData data = new TData();
		data.setString("HP", "20+</rpglevel/>");
		data.setString("MP", "0");
		data.setString("PDamage", "</rpglevel/>");
		data.setString("MDamage", "0");
		data.setString("PDefence", "0");
		data.setString("Mdefence", "0");
		defaultclass.levelinfos.put(1, data);
		try {
			load();
		} finally {
			save();
		}
		if (classlist.get("default") != null)
			defaultclass = classlist.get("default");
	}

	private String name, displayname, preclass;
	private int maxlevel;

	private RpgClass() {
		classlist.put(this.name, this);
	}

	public RpgClass(ConfigurationSection path) {
		this.name = path.getName();
		this.displayname = path.getString("displayname", this.name);
		this.maxlevel = path.getInt("maxlevel", -1);
		this.preclass = path.getString("preclass", "defalut");
		String[] levelinfo = path.getConfigurationSection("levelinfo")
				.getKeys(false).toArray(new String[0]);
		for (String slevel : levelinfo) {
			TData data = new TData();
			int level;
			try {
				level = Integer.valueOf(slevel);
			} catch (Exception e) {
				level = 1;
			}
			levelinfos.put(level, data);
			ConfigurationSection levelpath = path.getConfigurationSection(
					"levelinfo").getConfigurationSection(slevel);
			data.setString("HP", levelpath.getString("HP", "20"));
			data.setString("MP", levelpath.getString("MP", "0"));
			data.setString("PDamage", levelpath.getString("PDamage", "1"));
			data.setString("MDamage", levelpath.getString("MDamage", "0"));
			data.setString("PDefence", levelpath.getString("PDefence", "0"));
			data.setString("MDefence", levelpath.getString("MDefence", "0"));
		}
		classlist.put(this.name, this);
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayname;
	}

	public int getMaxLevel() {
		return maxlevel;
	}

	public RpgClass getPreClass() {
		return classlist.get(preclass);
	}

	public TData getLevelInfo(int level) {
		while (!levelinfos.containsKey(level)) {
			level--;
			if (level < 1)
				return defaultclass.levelinfos.get(1);
		}
		return levelinfos.get(level);
	}
}
