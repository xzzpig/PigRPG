package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.equip.*;
import com.github.xzzpig.pigrpg.power.type.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;

public class Power_Prefix extends Power implements PT_Equip
{
	String prefix;
	
	User user;
	@Override
	public String getPowerName(){
		return "Prefix";
	}

	@Override
	public Power reBuild(ConfigurationSection path,PowerLore pl){
		if(path == null){
			prefix = null;
			return this;
		}
		prefix = pl.getReplaced(path.getString("prefix"));
		return this;
	}

	@Override
	public void run(){
		if(user==null||prefix==null)
			return;
		user.setPrefix(prefix);
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event){
		user = User.getUser((Player)event.getPlayer());
		if(prefix == null)
			prefix = User.getUser((Player)event.getPlayer()).getEquip(EquipType.Prefix).getItemMeta().getDisplayName();
	}
	
}
