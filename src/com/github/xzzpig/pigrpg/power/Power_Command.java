package com.github.xzzpig.pigrpg.power;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_BeDamage;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class Power_Command extends Power implements PT_RightClick, PT_Damage,
		PT_Equip, PT_BeDamage {
	String command, temppremission;
	boolean op;

	Player player;

	@Override
	public String getPowerName() {
		return "Command";
	}

	@Override
	public Power reBuild(ConfigurationSection path, PowerLore pl) {
		command = pl.getReplaced(path.getString("command", "help"));
		op = Boolean.valueOf(pl.getReplaced(path.getString("op", "false")));
		temppremission = pl.getReplaced(path.getString("temppremission", ""));
		return this;
	}

	@Override
	public void rebuildEquip(InventoryCloseEvent event) {
		player = (Player) event.getPlayer();
	}

	@Override
	public void rebuildRC(PlayerInteractEvent event) {
		player = event.getPlayer();
	}

	@Override
	public void rebulidBeDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player)
			player = (Player) event.getDamager();
	}

	@Override
	public void rebulidDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player)
			player = (Player) event.getDamager();
	}

	@Override
	public void run() {
		if (player == null)
			return;
		boolean originop = player.isOp();
		if (op)
			player.setOp(true);
		player.addAttachment(Bukkit.getPluginManager().getPlugin("PigRPG"),
				temppremission, true, 10);
		player.chat("/" + command.replaceAll("</player/>", player.getName()));
		player.setOp(originop);
	}

}
