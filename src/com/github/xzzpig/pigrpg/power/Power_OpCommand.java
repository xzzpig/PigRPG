package com.github.xzzpig.pigrpg.power;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.entity.*;

public class Power_OpCommand extends Power implements PT_RightClick
{
	private boolean clone = false;
	private TData data;

	protected Power_OpCommand(){
		powers.add(this);
	}
	private Power_OpCommand(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getPowerName(){
		return "OpCommand";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_OpCommand(data);
	}

	//user:User
	@Override
	public void runRC(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		Equipment equip = user.getHandEquip();
		String commands = equip.getLoreData("Command");
		for(String command:commands.split("|"))
			run(user.getPlayer(),command);
		
	}
	public void run(Player player, String command){
		boolean op = false;
		if(player.isOp())
			op = true;
		else
			player.setOp(true);
		player.chat("/"+ StringMatcher.solve(command,User.getUser(player)));
		if(!op)
			player.setOp(false);
	}
}
