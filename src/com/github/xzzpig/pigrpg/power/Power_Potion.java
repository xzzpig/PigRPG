package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.potion.*;

public class Power_Potion extends Power implements PT_Lore,PT_Equip,PT_Damge,PT_RightClick
{
	private static Random rand = new Random();
	
	private boolean clone = false;
	private TData data;

	protected Power_Potion(){
		powers.add(this);
	}
	private Power_Potion(TData data){
		clone = true;
		this.data = data;
	}

	@Override
	public String getPowerName(){
		return "Potion";
	}

	@Override
	public boolean isCloned(){
		return clone;
	}

	@Override
	public Power clone(TData data){
		return new Power_Potion(data);
	}

	@Override
	public void runDamage(EntityDamageByEntityEvent event){
		if(!this.isCloned())
			return;
		if(!(event.getDamager() instanceof Player))
			return;
		if(!(event.getEntity() instanceof LivingEntity))
			return;
		Player player = (Player)event.getDamager();
		User user = User.getUser(player);
		Equipment equip = user.getHandEquip();
		String[] args = equip.getLoreData("Potion").replaceAll("|","~").split("~");
		try{
			if(!args[2].equalsIgnoreCase("Target"))
				return;
			String 
				spotion = args[0],
				slevel = args[1],
				stime = args[3],
				schance = args[4];
			PotionEffectType poitiont = PotionEffectType.getByName(spotion);
			int 
				level = Integer.valueOf(slevel),
				time = Integer.valueOf(stime),
				chance = Integer.valueOf(schance);
			if(chance>100)
			chance = 100;
		if(rand.nextInt(100)<=chance)
				new PotionEffect(poitiont, time, level).apply((LivingEntity)event.getEntity());
		}catch(Exception ex){user.sendPluginMessage("&4药水效果生效失败");return;}
	}

	@Override
	public void runRC(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		Equipment equip = user.getHandEquip();
		String[] args = equip.getLoreData("Potion").replaceAll("|","~").split("~");
		try{
			if(!args[2].equalsIgnoreCase("Self"))
				return;
			String 
				spotion = args[0],
				slevel = args[1],
				stime = args[3];
			PotionEffectType poitiont = PotionEffectType.getByName(spotion);
			int 
				level = Integer.valueOf(slevel),
				time = Integer.valueOf(stime);
			new PotionEffect(poitiont, time, level).apply(user.getPlayer());
			user.sendPluginMessage("&2药水效果"+spotion+"已生效");
		}catch(Exception ex){user.sendPluginMessage("&4药水效果生效失败");return;}
		
	}

	@Override
	public String getUsage(){
		return "-Potion:[文本.效果]|[整数.等级]|[Self|Target|Equip]|<Tick.持续时间(Equip为无穷)>|<整数.几率(Target生效)>";
	}

	@Override
	public String getLore(Equipment equip){
		String[] args = equip.getLoreData("Potion").replaceAll("|","~").split("~");
		try{
			if(args[2].equalsIgnoreCase("Self")){
				String 
				spotion = args[0],
				slevel = args[1],
				stime = args[3];
				return "右键给自己添加药水效果"+spotion+"(Lv"+slevel+")"+stime+"秒";
			}
			else if(args[2].equalsIgnoreCase("Target")){
				String 
					spotion = args[0],
					slevel = args[1],
					stime = args[3],
					schance = args[4];
				return schance+"%攻击时给对方添加药水效果"+spotion+"(Lv"+slevel+")"+stime+"秒";
			}
			else if(args[2].equalsIgnoreCase("Equip")){
				String 
					spotion = args[0],
					slevel = args[1];
				return "装备获得药水效果"+spotion+"(Lv"+slevel+")";
			}
		}catch(Exception ex){}
		return "";
	}

	@Override
	public void runEquip(){
		if(!this.isCloned())
			return;
		if(!(data.getObject("user") instanceof User))
			return;
		User user = (User)data.getObject("user");
		for(PotionEffectType p:user.getState().potions)
			user.getPlayer().removePotionEffect(p);
		user.getState().potions.clear();
		for(EquipType ep:EquipType.values()){
			if(!ep.isShow())
				continue;
			try{
				add(user,user.getEquip(ep));
			}
			catch(Exception e){
				continue;
			}
		}
	}
	private void add(User user,Equipment equip){
		if(equip.getLoreData("Potion") == null)
			return;
		String[] args = equip.getLoreData("Potion").replaceAll("|","~").split("~");
		try{
			if(!args[2].equalsIgnoreCase("Equip"))
				return;
			String 
				spotion = args[0],
				slevel = args[1];
			PotionEffectType poitiont = PotionEffectType.getByName(spotion);
			int level = Integer.valueOf(slevel);
			new PotionEffect(poitiont, Integer.MAX_VALUE, level).apply(user.getPlayer());
			user.getState().potions.add(poitiont);
			user.sendPluginMessage("&2药水效果"+spotion+"已生效");
		}catch(Exception ex){user.sendPluginMessage("&4药水效果生效失败");return;}
		
	}
}
