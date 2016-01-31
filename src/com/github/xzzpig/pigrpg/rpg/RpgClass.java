package com.github.xzzpig.pigrpg.rpg;
import java.util.*;
import com.github.xzzpig.BukkitTools.*;
import org.bukkit.configuration.*;

public class RpgClass
{	
	private static HashMap<String,RpgClass> classlist = new HashMap<String,RpgClass>();
	
	private String name;
	private TPremission Inherit;
	private String parent = "无";

	public static RpgClass getFrom(String name){
		if(classlist.containsKey(name))
			return classlist.get(name);
		return null;
	}

	public static boolean hasType(String name){
		return classlist.containsKey(name);
	}

	

}
