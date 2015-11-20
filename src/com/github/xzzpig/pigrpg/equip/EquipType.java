package com.github.xzzpig.pigrpg.equip;
import java.util.HashMap;

import com.github.xzzpig.BukkitTools.TPremission;

public class EquipType{
	public static final EquipType Default = new EquipType("无",false);
	public static final EquipType Weapon = new EquipType("武器",false);
	public static final EquipType Core = new EquipType("核心");
	public static final EquipType Head = new EquipType("头盔");
	public static final EquipType Chest = new EquipType("护甲");
	public static final EquipType Leg = new EquipType("战靴");
	public static final EquipType Hand = new EquipType("首饰");
	public static final EquipType Neck = new EquipType("项链");
	public static final EquipType Consume = new EquipType("消耗品");
	
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
	
	public boolean isShow() {
		return show;
	}
	public EquipType setShow(boolean show) {
		this.show = show;
		return this;
	}
	
	@Override
	public String toString()
	{
		return this.typename;
	}
	
}
