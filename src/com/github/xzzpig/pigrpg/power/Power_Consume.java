package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.pigrpg.power.type.*;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;

public class Power_Consume extends Power implements PT_RightClick
{
	private boolean clone = false;
	private TData data;

	protected Power_Consume(){
		powers.add(this);
	}
	private Power_Consume(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getPowerName(){
		return "Consume";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Consume(data);
	}
	
	//user:User
	@Override
	public void runRC(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		user.getPlayer().setItemInHand(null);
	}
	
}
