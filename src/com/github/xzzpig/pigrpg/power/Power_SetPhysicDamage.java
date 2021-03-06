package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;

public class Power_SetPhysicDamage extends Power implements PT_Equip,PT_Lore
{
	private boolean clone = false;
	private TData data;
	
	protected Power_SetPhysicDamage(){
		powers.add(this);
	}
	private Power_SetPhysicDamage(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-pdamage:[整数.物攻]";
	}

	@Override
	public String getLore(Equipment equip){
		int damage = 0;
			try{
				damage = damage+Integer.valueOf(0+(equip.getLoreData("pdamage")));
			}
			catch(Exception e){}
		return "物理攻击力+"+damage;
	}


	
	@Override
	public String getPowerName(){
		return "SetPhysicDamage";
	}

	@Override
	public String[] getAnotherName(){
		return new String[]{"pdamage"};
	}


	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_SetPhysicDamage(data);
	}
	
	//user:User
	@Override
	public void runEquip(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		int damage = 0;
		for(EquipType ep:EquipType.values()){
			if(!ep.isShow())
				continue;
			try{
				damage = damage+Integer.valueOf(0+(user.getEquip(ep).getLoreData("pdamage")));
			}
			catch(Exception e){
				continue;
			}
		}
		if(user.getState().getPhysicDamage() != damage)
			user.sendPluginMessage("&2你的物理攻击力已更改为"+damage);
		user.getState().setPhysicDamage(damage);
	}

	@Override
	public void run(){
		// TODO: Implement this method
	}

	
	
}
