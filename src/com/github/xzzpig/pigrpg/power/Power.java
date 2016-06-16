package com.github.xzzpig.pigrpg.power;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.github.xzzpig.pigrpg.equip.PowerLore;

public abstract class Power {
	private static List<Class<?>> powerclasss = new ArrayList<Class<?>>();

	static {
		regrestPowerClass(new Class<?>[] { Power_Arrow.class,
				Power_Chance.class, Power_Command.class, Power_Condition.class,
				Power_Consume.class, Power_Cooldown.class, Power_Damage.class,
				Power_Defence.class, Power_Effect.class, Power_Fireball.class,
				Power_Flame.class, Power_Health.class, Power_Hungery.class,
				Power_Knockup.class, Power_Level.class, Power_Lightning.class,
				Power_Message.class, Power_Money.class, Power_Potion.class,
				Power_Prefix.class, Power_Premission.class, Power_Rumble.class,
				Power_Sound.class, Power_Teleport.class, Power_Test.class });
	}

	public static void regrestPowerClass(Class<?> powerclass) {
		powerclasss.add(powerclass);
	}

	public static void regrestPowerClass(Class<?>[] powerclass) {
		powerclasss.addAll(Arrays.asList(powerclass));
	}

	public static Power valueOf(String name) {
		for (Power p : values()) {
			if (p.getPowerName().equalsIgnoreCase(name))
				return p;
		}
		return null;
	}

	public static Power[] values() {
		List<Power> powers = new ArrayList<Power>();
		for (Class<?> c : powerclasss) {
			Power p = null;
			try {
				Object instance = c.newInstance();
				if (!(instance instanceof Power))
					continue;
				p = (Power) c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (p == null)
				continue;
			powers.add(p);
		}
		return powers.toArray(new Power[0]);
	};

	private PowerRunTime[] runtime;

	public Power() {
	};

	public String[] getAnotherName() {
		return new String[] {};
	}

	public abstract String getPowerName();

	public boolean isRunTime(PowerRunTime rt) {
		for (PowerRunTime prt : runtime)
			if (rt == prt)
				return true;
		return false;
	}

	public abstract Power reBuild(ConfigurationSection path, PowerLore pl);

	public abstract void run();

	public Power setRunTimes(PowerRunTime[] rt) {
		this.runtime = rt;
		return this;
	}
}
