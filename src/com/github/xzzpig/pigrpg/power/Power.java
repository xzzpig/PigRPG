package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import java.util.*;

public abstract class Power
{
	protected static List<Power> powers = new ArrayList<Power>();

	public static final Power SetHealth = new Power_SetHealth();
	public static final Power SetPhysicDamage = new Power_SetPhysicDamage();
	public static final Power SetPhysicDefence = new Power_SetPhysicDefence();
	public static final Power Consume = new Power_Consume();
	public static final Power Command = new Power_Command();
	public static final Power OpCommand = new Power_OpCommand();
	public static final Power Cooldown = new Power_Cooldown();
	public static final Power Arrow = new Power_Arrow();
	public static final Power Fireball = new Power_Fireball();
	public static final Power Flame = new Power_Flame();
	public static final Power Knockup = new Power_Knockup();
	public static final Power Lightning = new Power_Lightning();
	public static final Power Potion = new Power_Potion();
	public static final Power Rumble = new Power_Rumble();
	public static final Power Teleport = new Power_Teleport();
	public static final Power Effect_ = new Power_Effect();
	public static final Power Sound_ = new Power_Sound();
	
	public static Power valueOf(String powername){
		for(Power p:powers)
			if(p.getPowerName().equalsIgnoreCase(powername))
				return p;
			else for(String an:p.getAnotherName())
					if(an.equalsIgnoreCase(powername))
						return p;
		return null;
	}
	public static Power[] values(){
		return powers.toArray(new Power[0]);
	}

	protected Power(){};

	public abstract String getPowerName();

	public String[] getAnotherName(){return new String[]{};};

	public abstract boolean isCloned();

	public abstract Power clone(TData data);
	
	public abstract void run();
}
