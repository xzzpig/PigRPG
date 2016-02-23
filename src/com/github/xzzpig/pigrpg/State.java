package com.github.xzzpig.pigrpg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;

import com.github.xzzpig.BukkitTools.TData;
import com.github.xzzpig.pigrpg.power.Power;

public class State {
	private User user;
	private LivingEntity entity;
	private int hp = 20, mp, pda, mda, pde, mde, level = 1;
	private List<Power> powers = new ArrayList<Power>();
	public List<PotionEffectType> potions = new ArrayList<PotionEffectType>();
	public TData data = new TData();

	public State(LivingEntity entity) {
		FixedMetadataValue fd = new FixedMetadataValue(Bukkit
				.getPluginManager().getPlugin("PigRPG"), this);
		entity.setMetadata("state", fd);
		this.entity = entity;
		if (entity instanceof Player) {
			this.user = User.getUser((Player) entity);
		}
	}

	public User getUser() {
		return user;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public static State getFrom(LivingEntity entity) {
		if (entity instanceof Player)
			return User.getUser((Player) entity).getState();
		for (MetadataValue mv : entity.getMetadata("state")) {
			if (mv.value() instanceof State)
				return (State) mv.value();
		}
		return new State(entity);
	}

	public static boolean hasState(LivingEntity entity) {
		return getFrom(entity) != null;
	}

	@SuppressWarnings("deprecation")
	public State setHp(int hp) {
		this.hp = hp;
		entity.setMaxHealth(hp);
		return this;
	}

	public int getHp() {
		return hp;
	}

	public State setMp(int mp) {
		this.mp = mp;
		return this;
	}

	public int getMp() {
		return mp;
	}

	public State setLevel(int level) {
		this.level = level;
		if (user != null) {
			user.getPlayer().setLevel(level);
		}
		return this;
	}

	public int getLevel() {
		if (user != null) {
			return user.getPlayer().getLevel();
		}
		return level;
	}

	public State setPhysicDamage(int pda) {
		this.pda = pda;
		return this;
	}

	public int getPhysicDamage() {
		return pda;
	}

	public State setMagicDamage(int mda) {
		this.mda = mda;
		return this;
	}

	public int getMagicDamage() {
		return mda;
	}

	public State setPhysicDefence(int pde) {
		this.pde = pde;
		return this;
	}

	public int getPhysicDefence() {
		return pde;
	}

	public State addPower(Power power) {
		this.powers.add(power);
		return this;
	}

	public State addPowers(List<Power> powers) {
		this.powers.addAll(powers);
		return this;
	}

	public State delPower(Power power) {
		this.powers.remove(power);
		return this;
	}

	public List<Power> getPowers() {
		return powers;
	}

	public State setPowers(List<Power> powers) {
		this.powers = powers;
		return this;
	}

	public State setMagicDefine(int mde) {
		this.mde = mde;
		return this;
	}

	public int getMagicDefine() {
		return mde;
	}

	public void remove() {
		entity.removeMetadata("state",
				Bukkit.getPluginManager().getPlugin("PigRPG"));
	}
}
