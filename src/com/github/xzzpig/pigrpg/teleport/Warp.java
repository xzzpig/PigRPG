package com.github.xzzpig.pigrpg.teleport;
import org.bukkit.*;

import java.util.*;

import com.github.xzzpig.BukkitTools.*;

public class Warp
{
	private static List<Warp> warps = new ArrayList<Warp>();
	public final static Warp Null = new Warp("Null",new Location(Bukkit.getWorlds().get(0),0,0,0));
	
	String name;
	Location loc;
	
	public Warp(String name,Location loc){
		this.name = name;
		this.loc = loc;
		if(name.equalsIgnoreCase("Null"))
			return;
		warps.add(this);
	}
	
	public static Warp getWarp(String name){
		for(Warp warp:warps){
			if(warp.getName().equalsIgnoreCase(name))
				return warp;
		}
		return Null;
	}
	public static Warp[] getWarps(){
		return warps.toArray(new Warp[0]);
	}
	
	public static void load(String name){
		String world = TConfig.getConfigFile("PigRPG", "warp.yml").getString("warp." + name + ".world");
		List<Integer> xzy = TConfig.getConfigFile("PigRPG", "warp.yml").getIntegerList("warp." + name + ".xyz");
		new Warp(name,new Location(Bukkit.getWorld(world),xzy.get(0),xzy.get(1),xzy.get(2)));
		TString.Print(TString.Prefix("PigRPG",3)+"warp "+ name + "("+world +"," + xzy.get(0)+","+xzy.get(1)+"," + xzy.get(2) + ")	已加载");
	}
	public static void loadAll(){
		for(String name:TConfig.getConfigPath("PigRPG","warp.yml","warp")){
			load(name);
		}
	}

	public Location getLocation(){
		return this.loc;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void save(){
		TConfig.saveConfig("PigRPG","warp.yml","warp."+this.getName()+".world",loc.getWorld().getName());
		int[] xyz = {loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()};
		List<Integer> xyz2 = new ArrayList<Integer>();
		for(int i:xyz){
			xyz2.add(i);
		}
		TConfig.saveConfig("PigRPG","warp.yml","warp."+this.getName()+".xyz",xyz2);
		TString.Print(TString.Prefix("PigRPG",3)+"warp "+ name +"	已保存");
	}
}
