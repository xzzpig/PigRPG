package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

public class Power_Command extends Power implements PT_RightClick,PT_Damge,PT_Equip,PT_BeDamage
{
	String command,temppremission;
	boolean op;

	Player player;

	@Override
	public String getPowerName(){
		return "Command";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		command = pl.getReplaced(path.getString("command","help"));
		op = Boolean.valueOf(pl.getReplaced(path.getString("op","false")));
		temppremission = pl.getReplaced(path.getString("temppremission",""));
		return this;
	}

	@Override
	public void run(){
		if(player==null)
			return;
		boolean originop = player.isOp();
		if(op)
			player.setOp(true);
		player.addAttachment(Bukkit.getPluginManager().getPlugin("PigRPG"),temppremission,true,10);
		player.chat("/"+command.replaceAll("</player/>",player.getName()));
		player.setOp(originop);
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player)
			player = (Player)event.getDamager();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event){
		player = event.getPlayer();
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event){
		player = (Player)event.getPlayer();
	}

	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player)
			player = (Player)event.getDamager();
	}
	
}
