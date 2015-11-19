package com.github.xzzpig.pigrpg.equip;
import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class Equipment extends ItemStack
{
	private EquipType etype;
	
	public Equipment(int id)
	{
		super(id);
		if(id == 0)
			super.setTypeId(1);
		this.loadEnums();
	}
	public Equipment(ItemStack is)
	{
		super(is);
		if(this.getType() == Material.AIR)
			super.setTypeId(1);
		loadEnums();
	}
	
	public Equipment setEquiptype(EquipType etype)
	{
		this.etype = etype;
		if(this.hasLoreType()){
			ItemMeta im = this.getItemMeta();
			List<String> lore = im.getLore();
			lore.set(0,"        "+etype.toString());
			im.setLore(lore);
			this.setItemMeta(im);
		}
		else{
			ItemMeta im = this.getItemMeta();
			List<String> lore = im.getLore();
			lore.add(0,"        "+etype.toString());
			im.setLore(lore);
			this.setItemMeta(im);
		}
		return this;
	}
	public EquipType getEquiptype()
	{
		return etype;
	}
	
	public boolean hasLoreType(){
		ItemMeta im = this.getItemMeta();
		List<String> lore = im.getLore();
		if(lore == null)
			return false;
		if(EquipType.hasType(lore.get(0).replaceAll(" ","")))
			return true;
		return false;
	}
	
	public void loadEnums(){
		ItemMeta im = this.getItemMeta();
		List<String> lore = im.getLore();
		if(lore == null){
			return;
		}
		if(this.hasLoreType())
			this.setEquiptype(EquipType.getFrom(lore.get(0).replaceAll(" ","")));
		else
			this.setEquiptype(EquipType.Default);
	}
}
