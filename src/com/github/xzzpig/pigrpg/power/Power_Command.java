package com.github.xzzpig.pigrpg.power;
import org.bukkit.entity.Player;

import com.github.xzzpig.BukkitTools.TData;
import com.github.xzzpig.pigrpg.StringMatcher;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.equip.Equipment;
import com.github.xzzpig.pigrpg.power.type.PT_Lore;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Command extends Power implements PT_RightClick,PT_Lore
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
	public String getUsage(){
		return "-Command:[文本.命令]…";
	}

	@Override
	public String getLore(Equipment equip){
		String commands = equip.getLoreData("Command");
		return "执行命令:"+commands;
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
		for(String command:commands.replaceAll("|","~").split("~"))
			run(user.getPlayer(),command);

	}
	public void run(Player player,String command){
		player.chat("/"+StringMatcher.solve(command,User.getUser(player)));
	}
	
	//*user:User commands:String command:String
	@Override
	public void run(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		String commands = data.getString("commands");
		if(commands!=null)
			for(String command:commands.replaceAll("|","~").split("~"))
				run(user.getPlayer(),command);
		String command = data.getString("command");
		if(command!=null)
			run(user.getPlayer(),command);
	}



}
