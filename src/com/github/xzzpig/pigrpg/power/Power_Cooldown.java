package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.pigrpg.power.type.*;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import org.bukkit.*;

public class Power_Cooldown extends Power implements PT_RightClick,PT_Limit,PT_Lore
{
	private boolean clone = false;
	private TData data;
	private long finaltime = System.currentTimeMillis();
	
	protected Power_Cooldown(){
		powers.add(this);
	}
	private Power_Cooldown(TData data){
		clone = true;
		this.data = data;
	}
	@Override
	public String getPowerName(){
		return "Cooldown";
	}

	@Override
	public String getLore(Equipment equip){
		String scooldown = equip.getLoreData("Cooldown");
		long cooldown = 0;
		try{
			cooldown = Long.valueOf("0"+scooldown);
		}catch(NumberFormatException e){}
		return "使用冷却时间:"+cooldown+"毫秒";
	}


	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Cooldown(data);
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
		String scooldown = equip.getLoreData("Cooldown");
		long cooldown = 0;
		try{
			cooldown = Long.valueOf("0"+scooldown);
		}
		catch(NumberFormatException e){
			user.sendPluginMessage("&4错误:冷却时间不是整数");
			return;
		}
		run(cooldown);
		for(Power p: equip.powers){
			if(p instanceof Power_Cooldown)
				equip.powers.remove(this);
		}
		equip.powers.add(this);
	}
	
	//time:时间 单位:毫秒
	public void run(long time){
		this.finaltime = System.currentTimeMillis() + time;
	}
	
	@Override
	public boolean can(){
		boolean can = System.currentTimeMillis() >= this.finaltime;
		return can;
	}

	@Override
	public String getUsage(){
		return "-Cooldown:[整数.毫秒]";
	}

	@Override
	public String cantMessage(){
		return (ChatColor.RED + "未冷却,剩余时间:"+(this.finaltime - System.currentTimeMillis())+"毫秒");
	}

	
	
}
