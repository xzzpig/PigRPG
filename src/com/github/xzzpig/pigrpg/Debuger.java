package com.github.xzzpig.pigrpg;
import org.bukkit.*;
import org.bukkit.entity.*;

public class Debuger
{
	public static long time;
	
	public static boolean isdebug = true;
	@SuppressWarnings("deprecation")
	public static void print(String s)
	{
		if(isdebug == false)return;
		System.out.println("\n****************\n"+s+"\n****************");
		for(Player p: Bukkit.getServer().getOnlinePlayers())
		{
			if(p.isOp())
				p.sendMessage(s);
		}
	}
	
	public static void timeStart(){
		time = System.nanoTime();
	}
	public static void timeStop(String s){
		Debuger.print(s+(System.nanoTime()-time));
	}
}
