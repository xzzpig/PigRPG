package com.github.xzzpig.pigrpg.equip;

import org.bukkit.configuration.*;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.pigrpg.Debuger;
import com.github.xzzpig.pigrpg.power.*;

import java.util.*;
import java.util.Map.Entry;

import com.github.xzzpig.BukkitTools.*;

public class PowerLore
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
	public PowerRunTime runtime;
	private List<EquipType> needequip = new ArrayList<EquipType>();
	public List<Power> powers = new ArrayList<Power>();
	public TData data = new TData();
	private ConfigurationSection path;
	
	private PowerLore() {}
	public PowerLore(ConfigurationSection path){
		this.path = path;
		matchkey = path.getString("matchkey");
		form = path.getString("form");
		show = path.getString("show");
		runtime = PowerRunTime.form(path.getString("runtime"));
		if(runtime == null)
			runtime = PowerRunTime.Never;
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
		return pl;
	}
	
	public String getKey(){
		return matchkey;
	}
	
	public PowerLore loadPowers(){
		Set<String> powernames = path.getConfigurationSection("power").getKeys(false);
		Debuger.print(path.getName());
		for(String powername:powernames){
			Debuger.print(powername);
			try {
				powers.add(((Power)Class.forName("com.github.xzzpig.pigrpg.power.Power_"+powername).newInstance()).reBuild(path.getConfigurationSection("power."+powername),this));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("[PigRPG]错误:加载Power错误(未知的Power名称)");
			}
			for(Power p:this.powers){
				Debuger.print(p.getPowerName());
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
			Debuger.print(key+":"+value);
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
		Debuger.print(show);
		return this.getReplaced(show);
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
