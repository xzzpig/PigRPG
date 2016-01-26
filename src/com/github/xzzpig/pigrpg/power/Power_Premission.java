package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;

public class Power_Premission extends Power implements PT_Limit
{
	String premission;

	Player player;

	@Override
	public String getPowerName(){
		return "Premission";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		player = pl.getEquip().getOwner();
		premission = pl.getReplaced(path.getString("pression"));
		return this;
	}

	@Override
	public void run(){
	}

	@Override
	public boolean can(){
		return player.hasPermission(premission);
	}

	@Override
	public String cantMessage(){
		return "权限不足,无法使用";
	}

}
