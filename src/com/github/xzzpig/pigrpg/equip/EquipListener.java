package com.github.xzzpig.pigrpg.equip;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.github.xzzpig.BukkitTools.TData;
import com.github.xzzpig.pigrpg.StringMatcher;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.chests.EquipChest;
import com.github.xzzpig.pigrpg.power.Power;
import com.github.xzzpig.pigrpg.power.PowerRunTime;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;
import com.github.xzzpig.pigrpg.power.type.PT_Limit;

public class EquipListener implements Listener {
	@EventHandler
	public void onCloseInv(InventoryCloseEvent event) {
		if (!event.getInventory().getTitle().contains("装备栏"))
			return;
		User user = User.getUser((Player) event.getPlayer());
		user.sendPluginMessage("你的属性已加载");
		Inventory inv = event.getInventory();
		for (ItemStack is : inv.getContents()) {
			if (is == null)
				continue;
			if (is instanceof Equipment)
				user.setEquip((Equipment) is);
			else
				user.setEquip(new Equipment(is, user.getPlayer()));
		}
		for (PotionEffectType p : user.getState().potions) {
			user.getPlayer().removePotionEffect(p);
		}
		user.getState().potions.clear();
		user.getState().getPowers().clear();
		TData levelinfo = user.getRpgClass().getLevelInfo(user.getLevel());
		int HP = 20, MP = 0, PDamage = 1, MDamage = 0, PDefence = 0, MDefence = 0;
		try {
			HP = Integer.valueOf(StringMatcher.buildStr(
					levelinfo.getString("HP"), user.getPlayer(), true));
		} catch (Exception e) {
			System.out.println("职业" + user.getRpgClass().getDisplayName()
					+ user.getLevel() + "的HP计算公式错误");
		}
		try {
			MP = Integer.valueOf(StringMatcher.buildStr(
					levelinfo.getString("MP"), user.getPlayer(), true));
		} catch (Exception e) {
			System.out.println("职业" + user.getRpgClass().getDisplayName()
					+ user.getLevel() + "的MP计算公式错误");
		}
		try {
			PDamage = Integer.valueOf(StringMatcher.buildStr(
					levelinfo.getString("PDamage"), user.getPlayer(), true));
		} catch (Exception e) {
			System.out.println("职业" + user.getRpgClass().getDisplayName()
					+ user.getLevel() + "的PDamage计算公式错误");
		}
		try {
			MDamage = Integer.valueOf(StringMatcher.buildStr(
					levelinfo.getString("MDamage"), user.getPlayer(), true));
		} catch (Exception e) {
			System.out.println("职业" + user.getRpgClass().getDisplayName()
					+ user.getLevel() + "的MDamage计算公式错误");
		}
		try {
			PDefence = Integer.valueOf(StringMatcher.buildStr(
					levelinfo.getString("PDefence"), user.getPlayer(), true));
		} catch (Exception e) {
			System.out.println("职业" + user.getRpgClass().getDisplayName()
					+ user.getLevel() + "的PDefence计算公式错误");
		}
		try {
			MDefence = Integer.valueOf(StringMatcher.buildStr(
					levelinfo.getString("MDefence"), user.getPlayer(), true));
		} catch (Exception e) {
			System.out.println("职业" + user.getRpgClass().getDisplayName()
					+ user.getLevel() + "的MDefence计算公式错误");
		}
		user.getState().setHp(HP).setMp(MP).setPhysicDamage(PDamage)
				.setMagicDamage(MDamage).setPhysicDefence(PDefence)
				.setMagicDefine(MDefence);
		for (ItemStack item : user.getPlayer().getInventory()
				.getArmorContents()) {
			if (item == null)
				continue;
			Equipment equip = new Equipment(item, user.getPlayer());
			for (PowerLore pl : equip.getPowerLores()) {
				user.getState().addPowers(pl.powers);
				if (!pl.isRunTime(PowerRunTime.CloseEC))
					continue;
				for (Power p : pl.powers) {
					if (p instanceof PT_Limit)
						if (!((PT_Limit) p).can()) {
							user.sendPluginMessage(((PT_Limit) p).cantMessage());
							break;
						}
					if (!(p instanceof PT_Equip))
						continue;
					((PT_Equip) p).rebuildEquip(event);
					p.run();
				}
			}
		}
		for (EquipType et : EquipType.values()) {
			if (!et.isShow())
				return;
			Equipment equip = user.getEquip(et);
			for (PowerLore pl : equip.getPowerLores()) {
				user.getState().addPowers(pl.powers);
				if (!pl.isRunTime(PowerRunTime.CloseEC))
					continue;
				for (Power p : pl.powers) {
					if (p instanceof PT_Limit)
						if (!((PT_Limit) p).can()) {
							user.sendPluginMessage(((PT_Limit) p).cantMessage());
							break;
						}
					if (!(p instanceof PT_Equip))
						continue;
					((PT_Equip) p).rebuildEquip(event);
					p.run();
				}
			}
		}
	}

	@EventHandler
	public void onClickBanItem(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		int iitem = event.getRawSlot();
		if (!event.getInventory().getTitle().contains("装备栏"))
			return;
		if (iitem >= inv.getSize())
			return;
		int line = iitem / 9;
		if ((line % 2) == 0)
			event.setCancelled(true);
	}

	@EventHandler
	public void onPutItem(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		if (!event.getInventory().getTitle().contains("装备栏"))
			return;
		if (event.getAction() == InventoryAction.PICKUP_ONE
				|| event.getAction() == InventoryAction.PICKUP_ALL
				|| event.getAction() == InventoryAction.PICKUP_SOME)
			return;
		int iitem = event.getRawSlot();
		if (iitem >= inv.getSize())
			return;
		int line = iitem / 9;
		if ((line % 2) == 0)
			return;
		ItemStack is = event.getCursor();
		EquipType targettype = new Equipment(inv.getItem(iitem - 9),
				(Player) event.getWhoClicked()).getEquiptype();
		Equipment equip;
		if (is instanceof Equipment)
			equip = (Equipment) is;
		else
			equip = new Equipment(is, (Player) event.getWhoClicked());
		if (equip.getEquiptype() != targettype
				&& (!targettype.getInherit().getAllChildren()
						.contains(equip.getEquiptype()))) {
			event.setCancelled(true);
			User.getUser((Player) event.getWhoClicked()).sendPluginMessage(
					"&4请将装备放入对应的装备栏");
			return;
		}
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR
				&& event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		ItemStack item = event.getItem();
		if (item == null)
			return;
		User user = User.getUser(event.getPlayer());
		Equipment equip = user.getHandEquip();
		EquipType et = equip.getEquiptype();
		if (!et.isShow())
			return;
		Equipment equiped = user.getEquip(et);
		if (equiped.getItemMeta().getDisplayName().contains("STONE"))
			equiped = null;
		user.setEquip(equip);
		user.getPlayer().setItemInHand(equiped);
		user.getPlayer().openInventory(EquipChest.getInventory(user));
		user.getPlayer().closeInventory();
		user.sendPluginMessage("&2装备 &f" + equip.getItemMeta().getDisplayName()
				+ " &2已装备");
	}

}
