package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.pigrpg.power.type.*;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;

public class Power_SetHealth extends Power implements PT_Equip
{
	private boolean clone = false;
	private TData data;
	
	protected Power_SetHealth(){
		powers.add(this);
	}
	private Power_SetHealth(TData data){
		clone = true;
		this.data = data;
	}
	
	@Override
	public String getPowerName(){
		return "SetHealth";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_SetHealth(data);
	}
	
	//user:User
	@Override
	public void runEquip(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		int health = 20;
		for(EquipType ep:EquipType.values()){
			if(!ep.isShow())
				continue;
			try{
				health = health+Integer.valueOf(0+(user.getEquip(ep).getLoreData("health")));
			}
			catch(Exception e){
				continue;
			}
		}
		user.getState().setHp(health);
		user.sendPluginMessage("&2你的血量已更改为"+health);
	}
}
