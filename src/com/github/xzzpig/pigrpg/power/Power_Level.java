package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.pigrpg.power.type.*;
import com.github.xzzpig.pigrpg.equip.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import com.github.xzzpig.BukkitTools.*;

public class Power_Level extends Power implements PT_Limit
{
	String type;
	int level;
	
	Player player;
	
	@Override
	public String getPowerName(){
		return "Level";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		player = pl.getEquip().getOwner();
		pl.data.setString("level",player.getLevel()+"");
		level = (int) TCalculate.getResult(pl.getReplaced(path.getString("level")));
		type = pl.getReplaced(path.getString("type","less"));
		return this;
	}

	@Override
	public void run(){
	}

	@Override
	public boolean can(){
		if(type.equalsIgnoreCase("less"))
			return player.getLevel() < level;
		else if(type.equalsIgnoreCase("more"))
			return player.getLevel() > level;
		return player.getLevel() == level;
	}

	@Override
	public String cantMessage(){
		return "等级不足,无法使用";
	}
	
}