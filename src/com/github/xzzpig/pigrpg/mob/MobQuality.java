package com.github.xzzpig.pigrpg.mob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.pigapi.bukkit.TConfig;
import com.github.xzzpig.pigapi.bukkit.TPremission;
import com.github.xzzpig.pigapi.bukkit.TString;

/*
 * public enum MobQuality {
 * Boss("Boss",ChatColor.MAGIC),Gost("通灵",ChatColor.RED)
 * ,Damond("钻石",ChatColor.BLUE
 * ),Gold("黄金",ChatColor.GOLD),Cream("精英",ChatColor.GREEN
 * ),Common("普通",ChatColor.WHITE);
 * 
 * 
 * public static MobQuality valueOf(int id){ if(id >= values().length) return
 * Common; return values()[id]; }
 * 
 * String name; ChatColor c; int powerNumber;
 * 
 * MobQuality(String name,ChatColor c){ this.name = name; this.c = c;
 * this.powerNumber =
 * TConfig.getConfigFile("PigRPG","mob.yml").getInt("mobquality."
 * +name+".powernumber",1); }
 * 
 * public String getName(){ return this.name; } public ChatColor getChatColor(){
 * return c; } public void setPowerNumber(int powerNumber) { this.powerNumber =
 * powerNumber; } public int getPowerNumber() { return powerNumber; } }
 */
class EquipType {
	private static HashMap<String, EquipType> typelist = new HashMap<String, EquipType>();

	public static final EquipType Default = new EquipType("无", false, 280);
	public static final EquipType Weapon = new EquipType("武器", false, 268);
	public static final EquipType Core = new EquipType("核心", 268);
	public static final EquipType Head = new EquipType("头盔", 298);
	public static final EquipType Chest = new EquipType("护甲", 299);
	public static final EquipType Leg = new EquipType("战靴", 300);
	public static final EquipType Hand = new EquipType("首饰", 40);
	public static final EquipType Neck = new EquipType("项链", 111);
	public static final EquipType Consume = new EquipType("消耗品", false, 260);

	public static EquipType getFrom(String typename) {
		if (typelist.containsKey(typename))
			return typelist.get(typename);
		return null;
	}
	public static boolean hasType(String typename) {
		return typelist.containsKey(typename);
	}
	public static void load() {
		FileConfiguration config = TConfig.getConfigFile("PigRPG",
				"equiptype.yml");
		try {
			for (String typename : TConfig.getConfigPath("PigRPG",
					"equiptype.yml", "type")) {
				boolean show = config.getBoolean("type." + typename + ".show",
						false);
				if (hasType(typename))
					continue;
				String parent = "无";
				if (!show)
					parent = config.getString("type." + typename + ".parent",
							"无");
				int typeid = config.getInt("type." + typename + ".typeid", 1);
				new EquipType(typename, show, typeid, parent).getInherit();
				TString.Print(TString.Prefix("PigRPG", 3) + "自定义装备类型 "
						+ typename + "	已加载");
			}
		} catch (Exception e) {
		}
		EquipType.resetParents();
		EquipType.saveAll();
	}
	private static void resetParents() {
		for (EquipType et : EquipType.values()) {
			if (et.parent.equalsIgnoreCase("无"))
				continue;
			TPremission tparent = TPremission.valueOf(et.parent);
			if (tparent == null) {
				TString.Print(TString.Prefix("PigRPG", 3) + "没有父类型" + et.parent
						+ "," + et + "的父类型更改为 无");
				et.parent = "无";
				continue;
			}
			tparent.addChild(TPremission.valueOf(et.toString()));
		}
	}
	public static void saveAll() {
		for (EquipType et : EquipType.values())
			et.save();
	}

	public static EquipType[] values() {
		return typelist.values().toArray(new EquipType[0]);
	}

	private String typename;

	private boolean show = true;

	private TPremission Inherit;

	private int type = 1;

	private String parent = "无";

	EquipType(String typename, boolean show, int typeid) {
		build(typename, typeid);
		this.show = show;
	}

	EquipType(String typename, boolean show, int typeid, String parent) {
		build(typename, typeid);
		this.show = show;
		this.parent = parent;
	}

	EquipType(String typename, int typeid) {
		build(typename, typeid);
	}

	private void build(String typename, int typeid) {
		this.typename = typename;
		this.type = typeid;
		typelist.put(typename, this);
		this.Inherit = new TPremission(typename, null);
	}

	public EquipType getFinalParent() {
		EquipType type = this;
		while (!type.parent.equalsIgnoreCase("无")) {
			type = EquipType.getFrom(type.parent);
		}
		return type;
	}

	public TPremission getInherit() {
		return Inherit;
	}

	public int getItemTypeId() {
		return type;
	}

	public boolean isShow() {
		return show;
	}

	public void save() {
		FileConfiguration config = TConfig.getConfigFile("PigRPG",
				"equiptype.yml");
		config.set("type." + typename + ".show", show);
		config.set("type." + typename + ".parent", parent);
		config.set("type." + typename + ".typeid", type);
		TConfig.saveConfig("PigRPG", config, "equiptype.yml");
	}

	public EquipType setShow(boolean show) {
		this.show = show;
		return this;
	}

	@Override
	public String toString() {
		return this.typename;
	}

}

public class MobQuality {
	public static List<MobQuality> qualitys = new ArrayList<MobQuality>();
	public static List<String> ids = new ArrayList<String>();

	public static final MobQuality Boss = new MobQuality("BOSS", "BOSS",
			ChatColor.LIGHT_PURPLE, 5), Ghost = new MobQuality("Ghost", "通灵",
			ChatColor.RED, 4), Damond = new MobQuality("Damond", "钻石",
			ChatColor.BLUE, 3), Gold = new MobQuality("Gold", "黄金",
			ChatColor.GOLD, 2), Cream = new MobQuality("Cream", "精英",
			ChatColor.GREEN, 1), Common = new MobQuality("Common", "普通",
			ChatColor.WHITE, 0);

	static {
		try {
			for (String id : TConfig.getConfigPath("PigRPG", "mob.yml",
					"mobquality")) {
				if (ids.contains(id))
					continue;
				new MobQuality(id);
			}
		} catch (Exception e) {
		}
	}

	public static MobQuality valueOf(int i) {
		return qualitys.get(i);
	}

	public static MobQuality[] values() {
		return qualitys.toArray(new MobQuality[0]);
	}

	String id, name;
	ChatColor c;
	int powerNumber;

	public MobQuality(String quality) {
		this.id = quality;
		this.name = TConfig.getConfigFile("PigRPG", "mob.yml").getString(
				"mobquality." + quality + ".displayname", "普通");
		this.c = ChatColor.getByChar(TConfig.getConfigFile("PigRPG", "mob.yml")
				.getString("mobquality." + quality + ".color",
						ChatColor.GREEN.getChar() + ""));
		this.powerNumber = TConfig.getConfigFile("PigRPG", "mob.yml").getInt(
				"mobquality." + quality + ".powernumber", 1);
		build();
	}

	public MobQuality(String quality, String name, ChatColor c, int num) {
		this.id = quality;
		this.name = TConfig.getConfigFile("PigRPG", "mob.yml").getString(
				"mobquality." + quality + ".displayname", name);
		this.c = ChatColor
				.getByChar(TConfig.getConfigFile("PigRPG", "mob.yml")
						.getString("mobquality." + quality + ".color",
								c.getChar() + ""));
		this.powerNumber = TConfig.getConfigFile("PigRPG", "mob.yml").getInt(
				"mobquality." + quality + ".powernumber", num);
		build();
	}

	private void build() {
		ids.add(id);
		qualitys.add(this);
		save();
	}

	public ChatColor getChatColor() {
		return c;
	}

	public String getName() {
		return this.name;
	}

	public int getPowerNumber() {
		return powerNumber;
	}

	public void save() {
		TConfig.saveConfig("PigRPG", "mob.yml", "mobquality." + id + ".name",
				name);
		TConfig.saveConfig("PigRPG", "mob.yml", "mobquality." + id + ".color",
				c.getChar());
		TConfig.saveConfig("PigRPG", "mob.yml", "mobquality." + id
				+ ".powernumber", powerNumber);
	}

	@Override
	public String toString() {
		return c + name;
	}
}
