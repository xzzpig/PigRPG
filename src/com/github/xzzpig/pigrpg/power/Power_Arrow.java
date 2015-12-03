package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Power_Arrow extends Power implements PT_RightClick,PT_Lore
{
	private boolean clone = false;
	private TData data;

	protected Power_Arrow(){
		powers.add(this);
	}
	private Power_Arrow(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-Arrow:";
	}

	@Override
	public String getLore(Equipment equip){
		return "发射箭矢";
	}


	@Override
	public String getPowerName(){
		return "Arrow";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Arrow(data);
	}

	//user:User
	@Override
	public void runRC(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		user.getPlayer().launchProjectile(Arrow.class);
		user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
	}
}
