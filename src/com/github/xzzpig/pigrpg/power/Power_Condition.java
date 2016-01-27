package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.pigrpg.power.type.*;

import org.bukkit.configuration.*;

import com.github.xzzpig.pigrpg.equip.*;

import org.bukkit.entity.*;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;

public class Power_Condition extends Power implements PT_Limit
{
	String left,right,type,data,message;
	
	LivingEntity entity;
	
	@Override
	public String getPowerName(){
		return "Condition";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		left = pl.getReplaced(path.getString("left"));
		right = pl.getReplaced(path.getString("right"));
		type = pl.getReplaced(path.getString("type","equal"));
		data = pl.getReplaced(path.getString("data","str"));
		message = pl.getReplaced(path.getString("message","条件不符"));
		entity = pl.getEquip().getOwner();
		return this;
	}

	@Override
	public void run(){
		// TODO: Implement this method
	}

	@Override
	public boolean can(){
		String sl = buildStr(left,entity,data.equalsIgnoreCase("int"));
		String sr = buildStr(right,entity,data.equalsIgnoreCase("int"));
		if(data.equalsIgnoreCase("int")){
			if(type.equalsIgnoreCase("equal"))
				return TCalculate.getResult(sl) == TCalculate.getResult(sr);
			else if(type.equalsIgnoreCase("unequal"))
				return TCalculate.getResult(sl) != TCalculate.getResult(sr);
			else if(type.equalsIgnoreCase("less"))
				return TCalculate.getResult(sl) < TCalculate.getResult(sr);
			else if(type.equalsIgnoreCase("more"))
				return TCalculate.getResult(sl) > TCalculate.getResult(sr);
		}
		else{
			if(type.equalsIgnoreCase("equal"))
				return sl.equalsIgnoreCase(sr);
			else
				return !sl.equalsIgnoreCase(sr);
		}
		return false;
	}

	@Override
	public String cantMessage(){
		return buildStr(message,entity,false);
	}
	
	@SuppressWarnings("deprecation")
	public static String buildStr(String str,LivingEntity entity,boolean isInt){
		State state = State.getFrom(entity);
		String re = str.
			replaceAll("</world/>",entity.getWorld().getName()).
			replaceAll("</loc/>",entity.getLocation().toString()).
			replaceAll("</x/>",entity.getLocation().getBlockX()+"").
			replaceAll("</y/>",entity.getLocation().getBlockY()+"").
			replaceAll("</z/>",entity.getLocation().getBlockZ()+"").
			replaceAll("</maxhealth/>",state.getHp()+"").
			replaceAll("</currenthealth/>",""+(int)((Damageable)entity).getHealth()).
			replaceAll("</name/>",entity.getCustomName()).
			replaceAll("</type/>",entity.getType().toString());
		if(entity instanceof Player){
			Player player = (Player) entity;
			User user = User.getUser(player);
			re = re.
				replaceAll("</fly/>",player.getAllowFlight()+"").
				replaceAll("</exp/>",player.getExpToLevel()+"").
				replaceAll("</handid/>",player.getItemInHand().getTypeId()+"").
				replaceAll("</level/>",player.getLevel()+"").
				replaceAll("</gamemode/>",player.getGameMode().name()).
				replaceAll("</cahtchannel/>",user.getChatchannel().getName()).
				replaceAll("</money/>",""+(int)user.getEcoAPI().getMoney()).
				replaceAll("</op/>",player.isOp()+"").
				replaceAll("</sneak/>",""+player.isSneaking());
		}
		if(isInt)
			re = ((int)TCalculate.getResult(re))+"";
		return re;
	}
}
