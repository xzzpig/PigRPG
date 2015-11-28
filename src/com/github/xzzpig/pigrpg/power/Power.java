package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;
import java.util.*;

public abstract class Power{
	protected static List<Power> powers = new ArrayList<Power>();

	public static final Power SetHealth = new Power_SetHealth();
	public static final Power SetPhysicDamage = new Power_SetPhysicDamage();
	public static final Power SetPhysicDefence = new Power_SetPhysicDefence();
	public static final Power Consume = new Power_Consume();
	public static final Power Command = new Power_Command();
	public static final Power OpCommand = new Power_OpCommand();
	
	public static Power valueOf(String powername){
		for(Power p:powers)
			if(p.getPowerName().equalsIgnoreCase(powername))
				return p;
		return null;
	}
	public static Power[] values(){
		return powers.toArray(new Power[0]);
	}

	protected Power(){};

	public abstract String getPowerName();

	public abstract boolean isCloned();

	public abstract Power clone(TData data);
}
