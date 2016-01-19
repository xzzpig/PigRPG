package com.github.xzzpig.pigrpg.power;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.github.xzzpig.BukkitTools.TClass;
import com.github.xzzpig.pigrpg.equip.PowerLore;

public abstract class Power
{	
	public Power(){};

	public abstract String getPowerName();

	public String[] getAnotherName(){return new String[]{};};
	
	public abstract Power reBuild(ConfigurationSection path,PowerLore pl);
	
	public abstract void run();

	public static Power[] values() {
		List<Power> powers = new ArrayList<Power>();
		for(Class<?> c:TClass.getClass("com.github.xzzpig.pigrpg.power")){
			if(!c.getName().contains("Power_"))
				continue;
			Power p = null;
			try {
				p = (Power) c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if(p == null)
				continue;
			powers.add(p);
		}
		return powers.toArray(new Power[0]);
	}
	
}
