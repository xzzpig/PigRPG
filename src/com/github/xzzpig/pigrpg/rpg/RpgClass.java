package com.github.xzzpig.pigrpg.rpg;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.BukkitTools.TConfig;
import java.util.Iterator;
import java.util.Map;

public class RpgClass {
	private static HashMap<String, RpgClass> classlist = new HashMap<String, RpgClass>();

	private static final FileConfiguration classconfig = TConfig.getConfigFile(
			"PigRPG", "class.yml");
	
	private static void load(){
		for (String classname : classconfig.getConfigurationSection("class")
				.getKeys(false)) {
			new RpgClass(classconfig.getConfigurationSection("class."
					+ classname));
		}
	}
	private static void save(){
		Iterator<Map.Entry<String, RpgClass>> ir = classlist.entrySet().iterator();
		while(ir.hasNext()){
			Map.Entry<String, RpgClass> e = ir.next();
			save(e.getValue());
		}
	}
	private static void save(RpgClass rpgclass){
		classconfig.set("class."+rpgclass.getName()+".displayname", rpgclass.displayname);
		classconfig.set("class."+rpgclass.getName()+".maxlevel", rpgclass.maxlevel);
		classconfig.set("class."+rpgclass.getName()+".preclass", rpgclass.preclass);
		TConfig.saveConfig("PigRPG", classconfig, "class.yml");
	}
	
	public static  RpgClass valueOf(String rpgclass){
		if(classlist.containsKey(rpgclass))
			return classlist.get(rpgclass);
		return defaultclass;
	}
	
	public static RpgClass defaultclass;
	static{
		defaultclass = new RpgClass();
		defaultclass.name = "default";
		defaultclass.displayname = "无";
		defaultclass.preclass = "default";
		defaultclass.maxlevel = -1;
		
		try {
		load();
		} catch (Exception e) {
			save();
		}
		save();
	}
	
	private String name,displayname,preclass;
	private int maxlevel;
	
	private RpgClass(){
	}
	
	public RpgClass(ConfigurationSection path) {
		this.name = path.getName();
		this.displayname = path.getString("displayname", this.name);
		this.maxlevel = path.getInt("maxlevel",-1);
		this.preclass = path.getString("preclass","defalut");
		
		classlist.put(this.name, this);
	}
	
	public String getName(){
		return name;
	}

	public String getDisplayName(){
		return displayname;
	}
	
	public int getMaxLevel(){
		return maxlevel;
	}
	
	public RpgClass getPreClass(){
		return classlist.get(preclass);
	}
}
