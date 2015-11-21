package com.github.xzzpig.pigrpg.equip;
import java.util.*;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class Equipment extends ItemStack
{
	private EquipType etype;
	private EquipQuality equality = EquipQuality.Common;
	private ItemMeta im;
	
	@SuppressWarnings("deprecation")
	public Equipment(int id)
	{
		super(id);
		if(id == 0)
			super.setTypeId(1);
		this.loadEnums();
	}
	@SuppressWarnings("deprecation")
	public Equipment(ItemStack is)
	{
		super(is);
		if(this.getType() == Material.AIR)
			super.setTypeId(1);
		loadEnums();
	}
	
	public static boolean isEquipItem(ItemStack is){
		if(is instanceof Equipment)
			return true;
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
		if(lore == null)
			return false;
		if(EquipType.hasType(lore.get(0).replaceAll(" ","")))
			return true;
		return false;
	}
	
	public Equipment setDisplayName(String name)
	{
		return this.setDisplayName(name,this.equality);
	}
	public Equipment setDisplayName(String name,EquipQuality eq)
	{
		this.getItemMeta().setDisplayName(eq+name);
		this.saveItemMeta();
		return this;
	}
	
	public Equipment setEquipQuality(EquipQuality equality)
	{
		if(equality == null)
			equality = EquipQuality.Common;
		this.setDisplayName(this.getItemMeta().getDisplayName().replaceFirst(this.equality.toString(),""),equality);
		this.equality = equality;
		return this;
	}
	public EquipQuality getEquipQuality()
	{
		return equality;
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
	public Equipment saveItemMeta()
	{
		super.setItemMeta(this.im);
		return this;
	}
	
	
	@Override
	public ItemMeta getItemMeta()
	{
		this.im = super.getItemMeta();
		return this.im;
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
		if(this.getItemMeta().getDisplayName() == null)
			this.setDisplayName(this.getType().toString());
		else{
			String dis = this.getItemMeta().getDisplayName();
			EquipQuality eq = EquipQuality.fromColor(dis);
			if(eq != null){
				this.setEquipQuality(eq);
			}
		}
			
	}
}
