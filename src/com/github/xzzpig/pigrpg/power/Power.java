package com.github.xzzpig.pigrpg.power;
import com.github.xzzpig.BukkitTools.*;

public abstract class Power
{
	protected Power(){};
	
	public abstract String getPowerName();
	
	public abstract boolean isCloned();
	
	public abstract Power clone(TData data);
}
