package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;

public class Power_SetPhysicDefence extends Power implements PT_Equip,PT_Lore
{
	private boolean clone = false;
	private TData data;

	protected Power_SetPhysicDefence(){
		powers.add(this);
	}
	private Power_SetPhysicDefence(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getUsage(){
		return "-pdefence:[整数.物防]";
	}

	@Override
	public String getLore(Equipment equip){
		int defence = 0;
			try{
				defence = defence+Integer.valueOf(0+(equip.getLoreData("pdefence")));
			}catch(Exception e){}
		return null;
	}

	@Override
	public String getPowerName(){
		return "SetPhysicDefence";
	}

	@Override
	public String[] getAnotherName(){
		return new String[]{"pdefence"};
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_SetPhysicDefence(data);
	}

	//user:User
	@Override
	public void runEquip(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		int defence = 0;
		for(EquipType ep:EquipType.values()){
			if(!ep.isShow())
				continue;
			try{
				defence = defence+Integer.valueOf(0+(user.getEquip(ep).getLoreData("pdefence")));
			}
			catch(Exception e){
				continue;
			}
		}
		if(user.getState().getPhysicDefence() != defence)
		user.sendPluginMessage("&2你的物理防御力已更改为"+defence);
		user.getState().setPhysicDefence(defence);
	}
}
