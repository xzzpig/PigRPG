package com.github.xzzpig.pigrpg.mob;
import org.bukkit.*;

public enum MobQuality
{
	Boss("Boss",ChatColor.MAGIC),Gost("通灵",ChatColor.RED),Damond("钻石",ChatColor.BLUE),Gold("黄金",ChatColor.GOLD),Cream("精英",ChatColor.GREEN),Common("普通",ChatColor.WHITE);
	
	public static MobQuality valueOf(int id){
		if(id >= values().length)
			return Common;
		return values()[id];
	}
	
	String name;
	ChatColor c;
	
	MobQuality(String name,ChatColor c){
		this.name = name;
		this.c = c;
	}
	
	public String getName(){
		return this.name;
	}
	public ChatColor getChatColor(){
		return c;
	}
}
