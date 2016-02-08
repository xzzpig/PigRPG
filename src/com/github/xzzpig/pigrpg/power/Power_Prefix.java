package com.github.xzzpig.pigrpg.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.equip.EquipType;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;

public class Power_Prefix extends Power implements PT_Equip {
	String prefix;
	boolean isCustom;

	User user;

	@Override
	public String getPowerName() {
		return "Prefix";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		if (path == null) {
			prefix = null;
			return this;
		}
		prefix = pl.getReplaced(path.getString("prefix"));
		isCustom = true;
		return this;
	}

	@Override
	public void run() {
		if (user == null || prefix == null)
			return;
		user.setPrefix(prefix);
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event) {
		user = User.getUser((Player) event.getPlayer());
		if (!isCustom) {
			prefix = User.getUser((Player) event.getPlayer())
					.getEquip(EquipType.Prefix).getItemMeta().getDisplayName();
		}
	}

}
