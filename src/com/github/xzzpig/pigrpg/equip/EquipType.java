package com.github.xzzpig.pigrpg.equip;
import java.util.*;
import com.github.xzzpig.BukkitTools.*;
import java.lang.annotation.*;

public enum EquipType{
	Default("无",false),
	Weapon("武器",false),
	Core("核心"),
	Head("头盔"),
	Chest("护甲"),
	Leg("战靴"),
	Hand("首饰"),
	Neck("项链"),
	Consume("消耗品");
	
	private static HashMap<String,EquipType> typelist = new HashMap<String,EquipType>();
	
	private String typename;
	private boolean show = true;
	private TPremission Inherit;
	
	public static EquipType getFrom(String typename){
		if(typelist.containsKey(typename))
			return typelist.get(typename);
		return null;
	}
	
	public static boolean hasType(String typename){
		return typelist.containsKey(typename);
	}
	
	EquipType(String typename){
		this.typename = typename;
		typelist.put(typename,this);
		this.Inherit = new TPremission(typename,null);
	}
	EquipType(String typename,boolean show){
		this.typename = typename;
		this.show = show;
		typelist.put(typename,this);
		this.Inherit = new TPremission(typename,null);
	}
	
	public TPremission getInherit()
	{
		return Inherit;
	}
	
	@Override
	public String toString()
	{
		return this.typename;
	}
	
}