package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import java.util.*;
import org.bukkit.configuration.*;

public class Power_Chance extends Power implements PT_Limit
{
	private static Random rand = new Random();
	
	String message;
	int chance;
	
	@Override
	public String getPowerName(){
		return "Chance";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		message = pl.getReplaced(path.getString("message"));
		chance = (int)TCalculate.getResult(pl.getReplaced(path.getString("chance","100")));
		return this;
	}

	@Override
	public void run(){}

	@Override
	public boolean can(){
		if(rand.nextInt(100) <= chance)
			return true;
		return false;
	}

	@Override
	public String cantMessage(){
		return message;
	}
	
}
