package com.github.xzzpig.pigrpg;
import org.bukkit.*;
import org.bukkit.entity.*;

public class Debuger
{
	public static boolean isdebug = true;
	@SuppressWarnings("deprecation")
	public static void prints(String s)
	{
		if(!isdebug)
			return;
		System.out.println(s);
		for(Player p:Bukkit.getOnlinePlayers())
			if(p.isOp())
				System.out.println(s);
	}
}
