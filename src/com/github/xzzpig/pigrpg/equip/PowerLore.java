package com.github.xzzpig.pigrpg.equip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.pigapi.TData;
import com.github.xzzpig.pigapi.bukkit.TConfig;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.power.Power;
import com.github.xzzpig.pigrpg.power.PowerRunTime;

public class PowerLore implements Comparable<PowerLore> {
	public static List<PowerLore> powerlores = new ArrayList<PowerLore>();

	static {
		loadpower();
	}

	public static final PowerLore consume = new PowerLore();

	static {
		consume.name = "consume";
		consume.matchkey = "用于装备类型 消耗品";
		consume.form = consume.matchkey;
		consume.show = "&2右键使用并消耗";
		consume.runtime = new PowerRunTime[] { PowerRunTime.RightClick };
		consume.needequip.addAll(Arrays.asList(EquipType.values()));
		Power pconsume = Power.valueOf("Consume").reBuild(null, consume);
		consume.powers.add(pconsume);
		powerlores.add(consume);
	}
	public static final PowerLore prefix = new PowerLore();
	static {
		prefix.name = "prefix";
		prefix.matchkey = "用于装备类型 称号";
		prefix.form = prefix.matchkey;
		prefix.show = "&2装备使用称号";
		prefix.runtime = new PowerRunTime[] { PowerRunTime.CloseEC };
		prefix.needequip.addAll(Arrays.asList(EquipType.values()));
		Power pprefix = Power.valueOf("Prefix").reBuild(null, prefix);
		prefix.powers.add(pprefix);
		powerlores.add(prefix);
	}
	public static PowerLore getFromName(String name) {
		for (PowerLore pl : powerlores)
			if (pl.name.equalsIgnoreCase(name))
				return pl;
		return null;
	}

	public static void loadpower() {
		FileConfiguration config = TConfig.getConfigFile("PigRPG",
				"customlore.yml");
		try {
			for (String lorename : TConfig.getConfigPath("PigRPG",
					"customlore.yml", "customlore")) {
				PowerLore pl = new PowerLore(
						config.getConfigurationSection("customlore." + lorename));
				powerlores.remove(getFromName(pl.name));
				powerlores.add(pl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String name;
	private String matchkey, form, show;
	private PowerRunTime[] runtime;
	private List<EquipType> needequip = new ArrayList<EquipType>();
	public List<Power> powers = new ArrayList<Power>();
	public TData data = new TData();
	private ConfigurationSection path;
	private Equipment equip;
	private int level;

	private PowerLore() {
	}

	public PowerLore(ConfigurationSection path) {
		this.path = path;
		this.name = path.getName();
		matchkey = path.getString("matchkey");
		form = path.getString("form");
		show = path.getString("show");
		level = path.getInt("level", 0);
		runtime = PowerRunTime.form(path.getStringList("runtime"));
		if (runtime == null)
			runtime = new PowerRunTime[] { PowerRunTime.Never };
		for (String type : path.getStringList("needequip"))
			needequip.add(EquipType.getFrom(type));
	}

	@Override
	public PowerLore clone() {
		PowerLore pl = new PowerLore();
		pl.name = this.name;
		pl.matchkey = this.matchkey;
		pl.form = this.form;
		pl.show = this.show;
		pl.runtime = this.runtime;
		pl.needequip = new ArrayList<EquipType>();
		pl.needequip.addAll(this.needequip);
		pl.data = this.data.clone();
		pl.path = this.path;
		pl.equip = this.equip;
		return pl;
	}

	@Override
	public int compareTo(PowerLore p1) {
		if (p1 == null)
			return 1;
		return level - p1.level;
	}

	public Equipment getEquip() {
		return equip;
	}

	public String getKey() {
		return matchkey;
	}

	public String getLore() {
		return this.getReplaced(show);
	}

	public String getReplaced(String old) {
		for (Entry<String, String> set : data.getStrings().entrySet())
			old = old.replaceAll("</" + set.getKey() + "/>", set.getValue());
		return old.replaceAll("&", TString.s);

	}

	public String getUsage() {
		return this.form;
	}

	public boolean isRunTime(PowerRunTime rt) {
		for (PowerRunTime prt : runtime)
			if (rt == prt)
				return true;
		return false;
	}

	public PowerLore loadPowers() {
		Set<String> powernames = path.getConfigurationSection("power").getKeys(
				false);
		for (String powername : powernames) {
			String solved = powername.split("_")[0];
			Power p = (Power.valueOf(solved).reBuild(
					path.getConfigurationSection("power." + powername), this));
			if (p == null) {
				System.out.println("[PigRPG]错误:加载Power错误(未知的Power名称)" + solved);
				continue;
			}
			powers.add((Power.valueOf(solved).reBuild(
					path.getConfigurationSection("power." + powername), this)
					.setRunTimes(runtime)));
		}
		return this;
	}

	public PowerLore loadVars(String lore) {
		String form = this.form;
		String match = lore;
		while (true) {
			int start = form.indexOf("</");
			int end = form.indexOf("/>", start);
			if (start == -1)
				break;
			String key = form.substring(start + 2, end);
			int nextstart = form.indexOf("</", end);
			if (nextstart == -1)
				nextstart = form.length();
			String mid = form.substring(end + 2, nextstart);
			String value;
			if (mid.equalsIgnoreCase(""))
				value = match.substring(start);
			else {
				value = match.substring(start, match.indexOf(mid, start));
			}
			data.setString(key, value);
			form = form.substring(end + 2);
			match = match.substring(start + value.length());
		}
		return this;
	}

	public PowerLore setEquip(Equipment equip) {
		this.equip = equip;
		return this;
	}

}
/*
 * 漏了
 * 
 * lores: ksyd: form: 快速移动 </距离/> </冷却/> </伤害/> show:快速向前移动</距离/>格 runtime: -
 * rightclick needequip: - 核心 - 鞋子 power: teleport: distance: </距离/>
 * cooldown:</冷却/> damage: amount:</伤害/> target:target
 */
