package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.power.type.*;
import com.github.xzzpig.pigrpg.equip.*;
import org.bukkit.entity.*;

public class Power_Command extends Power implements PT_RightClick
{
	private boolean clone = false;
	private TData data;

	protected Power_Command(){
		powers.add(this);
	}
	private Power_Command(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getPowerName(){
		return "Command";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Command(data);
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
		player.chat("/"+ StringMatcher.solve(command,User.getUser(player)));
	}
}
