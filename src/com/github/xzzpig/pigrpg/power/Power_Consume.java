package com.github.xzzpig.pigrpg.power;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.github.xzzpig.pigrpg.power.type.*;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;

public class Power_Consume extends Power implements PT_RightClick,PT_Lore
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
	public String getUsage(){
		return "-Consume:";
	}

	@Override
	public String getLore(Equipment equip){
		return "右键消耗";
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
		ItemStack item = user.getPlayer().getItemInHand();
		int count = item.getAmount() - 1;
        if (count == 0) {
            item.setAmount(0);
            user.getPlayer().setItemInHand(null);
        } else {
            item.setAmount(count);
        }
	}
	
}
