package com.github.xzzpig.pigrpg.equip;
import org.bukkit.inventory.*;

public class Equipment extends ItemStack
{
	private EquipType etype;
	
	public Equipment(int id)
	{
		super(id);
	}
	
	public Equipment setEquiptype(EquipType etype)
	{
		this.etype = etype;
		return this;
	}
	public EquipType getEquiptype()
	{
		return etype;
	}
	
	
}
