package com.github.xzzpig.pigrpg.equip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.BukkitTools.TConfig;
import com.github.xzzpig.BukkitTools.TData;
import com.github.xzzpig.pigrpg.power.Power;
import com.github.xzzpig.pigrpg.power.PowerRunTime;

public class PowerLore implements Comparable<PowerLore>
{
	public static List<PowerLore> powerlores = new ArrayList<PowerLore>();

	static{
		FileConfiguration config = TConfig.getConfigFile("PigRPG", "customlore.yml");
		try {
			for(String lorename:TConfig.getConfigPath("PigRPG", "customlore.yml","customlore")){
				PowerLore pl = new PowerLore(config.getConfigurationSection("customlore."+lorename));
				pl.name = lorename;
				powerlores.add(pl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String name;
	private String matchkey,form,show;
	private PowerRunTime[] runtime;
	private List<EquipType> needequip = new ArrayList<EquipType>();
	public List<Power> powers = new ArrayList<Power>();
	public TData data = new TData();
	private ConfigurationSection path;
	private Equipment equip;
	private int level;
	
	private PowerLore() {}
	public PowerLore(ConfigurationSection path){
		this.path = path;
		matchkey = path.getString("matchkey");
		form = path.getString("form");
		show = path.getString("show");
		level = path.getInt("level",0);
		runtime = PowerRunTime.form(path.getStringList("runtime"));
		if(runtime == null)
			runtime = new PowerRunTime[]{PowerRunTime.Never};
		for(String type:path.getStringList("needequip"))
			needequip.add(EquipType.getFrom(type));
	}
	
	@Override
	public PowerLore clone(){
		PowerLore pl = new PowerLore();
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
	public int compareTo(PowerLore p1){
		if(p1 == null)
			return 1;
		return level - ((PowerLore)p1).level;
	}
	
	public String getKey(){
		return matchkey;
	}
	
	public PowerLore setEquip(Equipment equip){
		this.equip = equip;
		return this;
	}
	public Equipment getEquip(){
		return equip;
	}
	public PowerLore loadPowers(){
		Set<String> powernames = path.getConfigurationSection("power").getKeys(false);
		for(String powername:powernames){
			String solved = powername.split("_")[0];
			try {
				powers.add(((Power)Class.forName("com.github.xzzpig.pigrpg.power.Power_"+solved).newInstance()).reBuild(path.getConfigurationSection("power."+powername),this));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("[PigRPG]错误:加载Power错误(未知的Power名称)");
			}
		}
		return this;
	}
	
	public PowerLore loadVars(String lore){
		String form = this.form;
		String match = lore;
		while(true){
			int start = form.indexOf("</");
			int end = form.indexOf("/>",start);
			if(start == -1)
				break;
			String key = form.substring(start+2,end);
			int nextstart = form.indexOf("</",end);
			if(nextstart == -1)
				nextstart = form.length();
			String mid = form.substring(end+2,nextstart);
			String value;
			if(mid.equalsIgnoreCase(""))
				value = match.substring(start);
			else{
				value = match.substring(start,match.indexOf(mid,start));
			}
			data.setString(key, value);
			form = form.substring(end+2);
			match = match.substring(start+value.length());
		}
		return this;
	}
	public String getUsage() {
		return this.form;
	}
	
	public String getReplaced(String old){
		for(Entry<String, String> set:data.getStrings().entrySet())
			old = old.replaceAll("</"+set.getKey()+"/>",set.getValue());
		return old;
		
	}
	public String getLore() {
		return this.getReplaced(show);
	}
	
	public boolean isRunTime(PowerRunTime rt){
		for(PowerRunTime prt:runtime)
			if(rt == prt)
				return true;
		return false;
	}
	
}
/*
漏了

lores: 
  ksyd: 
    form: 快速移动  </距离/>  </冷却/> </伤害/>
    show:快速向前移动</距离/>格
    runtime:
      - rightclick
    needequip: 
      - 核心
      - 鞋子
    power: 
      teleport: 
        distance: </距离/>
      cooldown:</冷却/>
      damage: 
        amount:</伤害/>
        target:target
*/
