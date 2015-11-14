package com.github.xzzpig.pigrpg;
import com.github.xzzpig.BukkitTools.*;

public class Voids
{
	public static void loadBanWords(){
		Vars.banWords = TConfig.getConfigFile("PigRPG","chat.yml").getStringList("chat.banwords");
	}
	public static void saveBanWords(){
		TConfig.saveConfig("PigRPG","chat.yml","chat.banwords",Vars.banWords);
	}
}
