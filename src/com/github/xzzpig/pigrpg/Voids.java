package com.github.xzzpig.pigrpg;
import com.github.xzzpig.BukkitTools.*;
import org.bukkit.*;

public class Voids
{
	public static void loadBanWords(){
		Vars.banWords = TConfig.getConfigFile("PigRPG","chat.yml").getStringList("chat.banwords");
	}
	public static void saveBanWords(){
		TConfig.saveConfig("PigRPG","chat.yml","chat.banwords",Vars.banWords);
	}
	
	public static Location toLocation(String sloc){
		Location loc;
		if(sloc == null)
			return null;
		String[] locarg = sloc.split(",");
		try{
			World world = Bukkit.getWorld(locarg[0]);
			int x = Integer.valueOf(locarg[1]);
			int y = Integer.valueOf(locarg[2]);
			int z = Integer.valueOf(locarg[3]);
			loc = new Location(world,x,y,z);
		}catch(Exception e){return null;}
		return loc;
	}
}
