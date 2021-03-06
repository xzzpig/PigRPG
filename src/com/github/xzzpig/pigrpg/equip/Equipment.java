package com.github.xzzpig.pigrpg.equip;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.xzzpig.BukkitTools.TArgsSolver;
import com.github.xzzpig.BukkitTools.TData;
import com.github.xzzpig.BukkitTools.TPremission;
import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.pigrpg.power.Power;
import com.github.xzzpig.pigrpg.power.type.PT_Lore;

public class Equipment extends ItemStack
{
	private EquipType etype = EquipType.Default;;
	private EquipQuality equality = EquipQuality.Common;
	private ItemMeta im;
	public List<Power> powers = new ArrayList<Power>();


	@SuppressWarnings("deprecation")
	public Equipment(int id){
		super(id);
		if(id==0)
			super.setTypeId(1);
		this.loadEnums();
	}
	@SuppressWarnings("deprecation")
	public Equipment(ItemStack is){
		super(is);
		if(this.getType()==Material.AIR)
			super.setTypeId(1);
		loadEnums();
	}

	public static boolean isEquipItem(ItemStack is){
		if(is instanceof Equipment)
			return true;
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
		if(lore==null)
			return false;
		if(EquipType.hasType(lore.get(0).replaceAll(" ","")))
			return true;
		return false;
	}

	public Equipment setDisplayName(String name){
		return this.setDisplayName(name,this.equality);
	}
	public Equipment setDisplayName(String name,EquipQuality eq){
		this.getItemMeta().setDisplayName(eq+name);
		this.saveItemMeta();
		return this;
	}

	public Equipment setEquipQuality(EquipQuality equality){
		if(equality==null)
			equality = EquipQuality.Common;
		this.setDisplayName(this.getItemMeta().getDisplayName().replaceFirst(this.equality.toString(),""),equality);
		this.equality = equality;
		return this;
	}
	public EquipQuality getEquipQuality(){
		return equality;
	}

	public Equipment setEquiptype(EquipType etype){
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
			if(lore==null)
				lore = new ArrayList<String>();
			lore.add(0,"        "+etype.toString());
			im.setLore(lore);
			this.setItemMeta(im);
		}
		if(EquipType.Consume.getInherit().hasChild(TPremission.valueOf(etype.toString()))){
			powers.remove(Power.Consume);
			powers.add(Power.Consume);
		}
		return this;
	}
	public EquipType getEquiptype(){
		return etype;
	}
	public Equipment saveItemMeta(){
		super.setItemMeta(this.im);
		return this;
	}


	@Override
	public ItemMeta getItemMeta(){
		this.im = super.getItemMeta();
		return this.im;
	}

	public String getLoreData(String key){
		return new TArgsSolver(getItemMeta().getLore().toArray(new String[0])).get(key);
	}
	public boolean hasLoreType(){
		ItemMeta im = this.getItemMeta();
		List<String> lore = im.getLore();
		if(lore==null)
			return false;
		if(EquipType.hasType(lore.get(0).replaceAll(" ","")))
			return true;
		return false;
	}

	public Power[] getPowers(){
		return powers.toArray(new Power[0]);
	}

	public Equipment loadEnums(){
		ItemMeta im = this.getItemMeta();
		List<String> lore = im.getLore();
		if(lore==null){
			lore = new ArrayList<String>();
		}
		if(this.hasLoreType())
			this.setEquiptype(EquipType.getFrom(lore.get(0).replaceAll(" ","")));
		else
			this.setEquiptype(EquipType.Default);
		if(this.getItemMeta().getDisplayName()==null)
			this.setDisplayName(this.getType().toString());
		else{
			String dis = this.getItemMeta().getDisplayName();
			EquipQuality eq = EquipQuality.fromColor(dis);
			if(eq!=null){
				this.setEquipQuality(eq);
			}
		}
		loadPowers();
		reBuildLore();
		return this;
	}
	public Equipment loadPowers(){
		powers.clear();
		if(EquipType.Consume.getInherit().hasChild(TPremission.valueOf(etype.toString()))){
			powers.remove(Power.Consume);
			powers.add(Power.Consume);
		}
		TArgsSolver map = new TArgsSolver(this.getItemMeta().getLore().toArray(new String[0]));
		for(Power p:Power.values()){
			if(!(p instanceof PT_Lore))
				continue;
			if(map.get(p.getPowerName())!=null)
				powers.add(p.clone(new TData().setString("item",this.toString())));
		}
		return this;
	}

	public Equipment reBuildLore(){
		List<String> lore = this.getItemMeta().getLore();
		List<String> plore = new ArrayList<String>();
		List<String> clore = new ArrayList<String>();
		lore.remove(0);
		for(String l:lore){
			if(l.startsWith("-"))
				plore.add(l);
			else if(l.startsWith("§5=")||l.startsWith("§2="))
				continue;
			else
				clore.add(l);
		}
		lore.clear();
		lore.add("        "+etype.toString());
		for(String s:clore)
			lore.add(s);
		String p = "";
		for(int i = 0;i<lore.get(0).length();i++)
			p = p+"=";
		lore.add(TString.Color(2)+p);
		for(Power po : powers)
			if(po instanceof PT_Lore)
				lore.add(TString.Color(5)+'='+((PT_Lore)po).getLore(this));
		lore.add(TString.Color(2)+p);
		for(String ps : plore)
			lore.add(ps);
		this.getItemMeta().setLore(lore);
		this.saveItemMeta();
		return this;
	}
}
