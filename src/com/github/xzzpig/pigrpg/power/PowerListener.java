package com.github.xzzpig.pigrpg.power;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.github.xzzpig.BukkitTools.TPremission;
import com.github.xzzpig.pigrpg.State;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.equip.EquipType;
import com.github.xzzpig.pigrpg.equip.Equipment;
import com.github.xzzpig.pigrpg.equip.PowerLore;
import com.github.xzzpig.pigrpg.power.type.PT_BeDamage;
import com.github.xzzpig.pigrpg.power.type.PT_Damage;
import com.github.xzzpig.pigrpg.power.type.PT_Equip;
import com.github.xzzpig.pigrpg.power.type.PT_Killed;
import com.github.xzzpig.pigrpg.power.type.PT_Limit;
import com.github.xzzpig.pigrpg.power.type.PT_RightClick;

public class PowerListener implements Listener {
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof LivingEntity))
			return;
		if (!(event.getEntity() instanceof LivingEntity))
			return;
		LivingEntity damager = (LivingEntity) event.getDamager();
		LivingEntity target = (LivingEntity) event.getEntity();
		State dstate = State.getFrom(damager), tstate = State.getFrom(target);
		for (Power p : dstate.getPowers()) {
			if (!(p instanceof PT_Damage))
				continue;
			((PT_Damage) p).rebulidDamage(event);
			p.run();
		}
		int origindamage = dstate.getPhysicDamage();
		if (event.getDamager() instanceof Player) {
			User user = User.getUser((Player) event.getDamager());
			Equipment equip = user.getHandEquip();
			if (equip.getEquiptype() == EquipType.Core)
				equip = user.getEquip(EquipType.Core);
			else if (EquipType.Weapon.getInherit().hasChild(
					TPremission.valueOf(equip.getEquiptype().toString()))) {
				if (EquipType
						.getFrom(
								equip.getEquiptype().toString()
										.replaceAll("核心", ""))
						.getInherit()
						.hasChild(
								TPremission.valueOf(equip.getEquiptype()
										.toString()))) {
					equip = user.getEquip(EquipType.Core);
				} else {
					user.sendPluginMessage("&4由于武器类型不符，你无法使用该武器");
					return;
				}
			}
			pls: for (PowerLore pl : equip.getPowerLores()) {
				if (!(pl.isRunTime(PowerRunTime.Damage)))
					continue pls;
				ps: for (Power p : pl.powers) {
					if (p instanceof PT_Limit)
						if (!((PT_Limit) p).can()) {
							user.sendPluginMessage(((PT_Limit) p).cantMessage());
							break ps;
						}
					if (!(p instanceof PT_Damage))
						continue ps;
					((PT_Damage) p).rebulidDamage(event);
					p.run();
				}
			}

		}
		if (event.getEntity() instanceof Player) {
			User user = User.getUser((Player) event.getEntity());
			Equipment equip = user.getHandEquip();
			if (equip.getEquiptype() == EquipType.Core)
				equip = user.getEquip(EquipType.Core);
			else if (EquipType.Weapon.getInherit().hasChild(
					TPremission.valueOf(equip.getEquiptype().toString()))) {
				if (EquipType
						.getFrom(
								equip.getEquiptype().toString()
										.replaceAll("核心", ""))
						.getInherit()
						.hasChild(
								TPremission.valueOf(equip.getEquiptype()
										.toString()))) {
					equip = user.getEquip(EquipType.Core);
				} else {
					user.sendPluginMessage("&4由于武器类型不符，你无法使用该武器");
					return;
				}
			}
			pls: for (PowerLore pl : equip.getPowerLores()) {
				if (!(pl.isRunTime(PowerRunTime.BeDamage)))
					continue pls;
				ps: for (Power p : pl.powers) {
					if (p instanceof PT_Limit)
						if (!((PT_Limit) p).can()) {
							user.sendPluginMessage(((PT_Limit) p).cantMessage());
							break ps;
						}
					if (!(p instanceof PT_BeDamage))
						continue ps;
					((PT_BeDamage) p).rebulidBeDamage(event);
					p.run();
				}
			}
		}
		event.setDamage(event.getDamage() + dstate.getPhysicDamage()
				- tstate.getPhysicDefence());
		dstate.setPhysicDamage(origindamage);
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR
				&& event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		User user = User.getUser(event.getPlayer());
		State state = user.getState();
		int origindamage = state.getPhysicDamage();
		Equipment equip = user.getHandEquip();
		if (event.getMaterial().isBlock()
				&& equip.getEquiptype() != EquipType.Default)
			event.setCancelled(true);
		if (equip.getEquiptype() == EquipType.Core)
			equip = user.getEquip(EquipType.Core);
		else if (EquipType.Weapon.getInherit().hasChild(
				TPremission.valueOf(equip.getEquiptype().toString()))) {
			if (EquipType
					.getFrom(
							equip.getEquiptype().toString()
									.replaceAll("核心", ""))
					.getInherit()
					.hasChild(
							TPremission
									.valueOf(equip.getEquiptype().toString()))) {
				equip = user.getEquip(EquipType.Core);
			} else {
				user.sendPluginMessage("&4由于武器类型不符，你无法使用该武器");
				return;
			}
		}
		pls: for (PowerLore pl : equip.getPowerLores()) {
			if (!pl.isRunTime(PowerRunTime.RightClick))
				continue pls;
			ps: for (Power p : pl.powers) {
				if (p instanceof PT_Limit)
					if (!((PT_Limit) p).can()) {
						user.sendPluginMessage(((PT_Limit) p).cantMessage());
						break ps;
					}
				if (!(p instanceof PT_RightClick))
					continue;
				((PT_RightClick) p).rebuildRC(event);
				p.run();
			}
		}
		state.setPhysicDamage(origindamage);
	}

	@EventHandler
	public void onArrowHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow))
			return;
		if (Power_Arrow.arrows.contains(event.getEntity()))
			event.getEntity().remove();
	}

	@EventHandler
	public void onCloseInv(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		User user = User.getUser(player);
		if (event.getInventory() != player.getInventory())
			return;
		for (ItemStack item : player.getInventory().getArmorContents()) {
			if (item == null)
				continue;
			Equipment equip = new Equipment(item, player);
			for (PotionEffectType p : user.getState().potions)
				user.getPlayer().removePotionEffect(p);
			user.getState().potions.clear();
			user.getState().setHp(20).setMagicDamage(0).setMagicDefine(0)
					.setMp(0).setPhysicDamage(1).setPhysicDefence(0);
			pls: for (PowerLore pl : equip.getPowerLores()) {
				if (!pl.isRunTime(PowerRunTime.CloseEC))
					continue pls;
				ps: for (Power p : pl.powers) {
					if (p instanceof PT_Limit)
						if (!((PT_Limit) p).can()) {
							user.sendPluginMessage(((PT_Limit) p).cantMessage());
							break ps;
						}
					if (!(p instanceof PT_Equip))
						continue;
					((PT_Equip) p).rebuildEquip(event);
					p.run();
				}
			}
		}
		for (EquipType et : EquipType.values()) {
			Equipment equip = user.getEquip(et);
			pls: for (PowerLore pl : equip.getPowerLores()) {
				if (!pl.isRunTime(PowerRunTime.CloseEC))
					continue pls;
				ps: for (Power p : pl.powers) {
					if (p instanceof PT_Limit)
						if (!((PT_Limit) p).can()) {
							user.sendPluginMessage(((PT_Limit) p).cantMessage());
							break ps;
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
	public void onKill(EntityDeathEvent event) {
		State state = State.getFrom(event.getEntity());
		for (Power p : state.getPowers()) {
			if (!(p instanceof PT_Killed))
				continue;
			((PT_Killed) p).rebulidKilled(event);
			p.run();
		}
		if (event.getEntity() instanceof Player) {
			User user = User.getUser((Player) event.getEntity());
			Equipment equip = user.getHandEquip();
			if (equip.getEquiptype() == EquipType.Core)
				equip = user.getEquip(EquipType.Core);
			else if (EquipType.Weapon.getInherit().hasChild(
					TPremission.valueOf(equip.getEquiptype().toString()))) {
				if (EquipType
						.getFrom(
								equip.getEquiptype().toString()
										.replaceAll("核心", ""))
						.getInherit()
						.hasChild(
								TPremission.valueOf(equip.getEquiptype()
										.toString()))) {
					equip = user.getEquip(EquipType.Core);
				} else {
					user.sendPluginMessage("&4由于武器类型不符，你无法使用该武器");
					return;
				}
			}
			pls: for (PowerLore pl : equip.getPowerLores()) {
				if (!(pl.isRunTime(PowerRunTime.Killed)))
					continue pls;
				ps: for (Power p : pl.powers) {
					if (p instanceof PT_Limit)
						if (!((PT_Limit) p).can()) {
							user.sendPluginMessage(((PT_Limit) p).cantMessage());
							break ps;
						}
					if (!(p instanceof PT_Killed))
						continue ps;
					((PT_Killed) p).rebulidKilled(event);
					p.run();
				}
			}
			for (EquipType et : EquipType.values()) {
				equip = user.getEquip(et);
				pls: for (PowerLore pl : equip.getPowerLores()) {
					if (!pl.isRunTime(PowerRunTime.Killed))
						continue pls;
					ps: for (Power p : pl.powers) {
						if (p instanceof PT_Limit)
							if (!((PT_Limit) p).can()) {
								user.sendPluginMessage(((PT_Limit) p)
										.cantMessage());
								break ps;
							}
						if (!(p instanceof PT_Killed))
							continue;
						((PT_Killed) p).rebulidKilled(event);
						p.run();
					}
				}
			}
		}
	}
}
