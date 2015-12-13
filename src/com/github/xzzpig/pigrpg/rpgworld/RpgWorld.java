package com.github.xzzpig.pigrpg.rpgworld;
import com.github.xzzpig.BukkitTools.*;
import java.util.*;

public class RpgWorld
{
	public static List<String> rpgworldlist = TConfig.getConfigFile("PigRPG","rpgworld.yml").getStringList("rpgworld.enableworld");
	public static int part = TConfig.getConfigFile("PigRPG","rpgworld.yml").getInt("rpgworld.levelpart",64);
	static{
		if(rpgworldlist != null){
			rpgworldlist = new ArrayList<String>();
			rpgworldlist.add("world");
			TConfig.saveConfig("PigRPG","rpgworld.yml","rpgworld.enableworld",rpgworldlist);
			TConfig.saveConfig("PigRPG","rpgworld.yml","rpgworld.levelpart",part);
		}
	}
}
