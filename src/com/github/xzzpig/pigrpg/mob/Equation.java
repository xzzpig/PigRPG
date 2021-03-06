package com.github.xzzpig.pigrpg.mob;
import com.github.xzzpig.BukkitTools.*;
import org.bukkit.configuration.file.*;

public class Equation
{
	public static String Hp = TConfig.getConfigFile("PigRPG","mob.yml").getString("calculate.hp","$level*$level+20");
	public static String Damage = TConfig.getConfigFile("PigRPG","mob.yml").getString("calculate.pdamage","$level");
	public static String Defence = TConfig.getConfigFile("PigRPG","mob.yml").getString("calculate.pdefence","$level*2");
	static{
		FileConfiguration config = TConfig.getConfigFile("PigRPG","mob.yml");
		config.set("calculate.hp",Hp);
		config.set("calculate.pdamage",Damage);
		config.set("calculate.pdefence",Defence);
		TConfig.saveConfig("PigRPG",config,"mob.yml");
	}
}
