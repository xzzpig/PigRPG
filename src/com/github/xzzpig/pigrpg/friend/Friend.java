package com.github.xzzpig.pigrpg.friend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.xzzpig.BukkitTools.TConfig;

public class Friend
{
	public static HashMap<String,List<String>> friendlist = new HashMap<String,List<String>>();
	
	public static List<String> getFriends(String player)
	{
		if(!friendlist.containsKey(player))
		{
			ArrayList<String> list = new ArrayList<String>();
			friendlist.put(player,list);
			return list;
		}
		return friendlist.get(player);
	}
	public static void addFriend(String player,String friend)
	{
		friendlist.get(player).add(friend);
	}
	public static boolean hasFriend(String player,String friend)
	{
		if(getFriends(player).contains(friend))
			return true;
		else
			return false;
	}
	public static void save(String player)
	{
		TConfig.saveConfig("PigRPG","friend.yml",player,getFriends(player));
	}
	public static void save()
	{
		Iterator<Map.Entry<String, List<String>>> ir = friendlist.entrySet().iterator();
		while(ir.hasNext())
		{
			Map.Entry<String, List<String>> var = ir.next();
			TConfig.saveConfig("PigRPG","friend.yml",var.getKey(),var.getValue());
		}
	}
	public static void loadFriend(String player)
	{
		friendlist.put(player,TConfig.getConfigFile("PigRPG","friend.yml").getStringList(player));
	}
}
