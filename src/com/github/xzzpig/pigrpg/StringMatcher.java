package com.github.xzzpig.pigrpg;
import com.github.xzzpig.BukkitTools.*;

public class StringMatcher
{
	public static String solve(String ps){
		return ps.replaceAll("&",TString.s);
	}
	@SuppressWarnings("deprecation")
	public static String solve(String arg,User user){
		arg = solve(arg);
		arg = arg.replaceAll("<Player>",user.getPlayer().getName()).
			replaceAll("<World>",user.getPlayer().getLocation().getWorld().getName()).
			replaceAll("<Item>",user.getPlayer().getItemInHand().getItemMeta().getDisplayName()+"("+user.getPlayer().getItemInHand().getType()+user.getPlayer().getItemInHand().getTypeId()+")").
			replaceAll("<Loc>","("+user.getPlayer().getLocation().getBlockX()+","+user.getPlayer().getLocation().getBlockY()+","+user.getPlayer().getLocation().getBlockY()+")");
		return arg;
	}
}
