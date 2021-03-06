package com.github.xzzpig.pigrpg.equip;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.xzzpig.BukkitTools.TConfig;
import com.github.xzzpig.BukkitTools.TPremission;
import com.github.xzzpig.BukkitTools.TString;

public class EquipType
{
	private static HashMap<String,EquipType> typelist = new HashMap<String,EquipType>();
	
	public static final EquipType Default = new EquipType("无",false,280);
	public static final EquipType Weapon = new EquipType("武器",false,268);
	public static final EquipType Core = new EquipType("核心",268);
	public static final EquipType Head = new EquipType("头盔",298);
	public static final EquipType Chest = new EquipType("护甲",299);
	public static final EquipType Leg = new EquipType("战靴",300);
	public static final EquipType Hand = new EquipType("首饰",40);
	public static final EquipType Neck = new EquipType("项链",111);
	public static final EquipType Consume = new EquipType("消耗品",false,260);

	
	private String typename;
	private boolean show = true;
	private TPremission Inherit;
	private int type = 1;
	private String parent = "无";

	EquipType(String typename,int typeid){
		build(typename,typeid);
	}
	EquipType(String typename,boolean show,int typeid){
		build(typename,typeid);
		this.show = show;
	}
	EquipType(String typename,boolean show,int typeid,String parent){
		build(typename,typeid);
		this.show = show;
		this.parent = parent;
	}

	public static EquipType getFrom(String typename){
		if(typelist.containsKey(typename))
			return typelist.get(typename);
		return null;
	}

	public static boolean hasType(String typename){
		return typelist.containsKey(typename);
	}
	
	public static void load(){
		FileConfiguration config = TConfig.getConfigFile("PigRPG","equiptype.yml");
		try {
			for(String typename : TConfig.getConfigPath("PigRPG","equiptype.yml","type")){
				boolean show = config.getBoolean("type."+typename+".show",false);
				if(hasType(typename))
					continue;
				String parent = "无";
				if(!show)
					parent = config.getString("type."+typename+".parent","无");
				int typeid = config.getInt("type."+typename+".typeid",1);
				new EquipType(typename,show,typeid,parent).getInherit();
				TString.Print(TString.Prefix("PigRPG",3)+"自定义装备类型 "+ typename + "	已加载");
			}
		} catch (Exception e) {
		}
		EquipType.resetParents();
		EquipType.saveAll();
	}
	
	public static void saveAll(){
		for(EquipType et : EquipType.values())
			et.save();
	}

	public static EquipType[] values(){
		return typelist.values().toArray(new EquipType[0]);
	}

	private void build(String typename,int typeid){
		this.typename = typename;
		this.type = typeid;
		typelist.put(typename,this);
		this.Inherit = new TPremission(typename,null);
	}
	
	private static void resetParents(){
		for(EquipType et : EquipType.values()){
			if(et.parent.equalsIgnoreCase("无"))
				continue;
			TPremission tparent = TPremission.valueOf(et.parent);
			if(tparent == null){
				TString.Print(TString.Prefix("PigRPG",3)+"没有父类型"+et.parent+","+et+"的父类型更改为 无");
				et.parent = "无";
				continue;
			}
			tparent.addChild(TPremission.valueOf(et.toString()));
		}
	}
	
	public EquipType getFinalParent(){
		EquipType type = this;
		while(!type.parent.equalsIgnoreCase("无")){
			type = EquipType.getFrom(type.parent);
		}
		return type;
	}

	public TPremission getInherit(){
		return Inherit;
	}

	public boolean isShow(){
		return show;
	}
	public EquipType setShow(boolean show){
		this.show = show;
		return this;
	}

	public int getItemTypeId(){
		return type;
	}

	public void save(){
		FileConfiguration config = TConfig.getConfigFile("PigRPG","equiptype.yml");
		config.set("type."+typename+".show",show);
		config.set("type."+typename+".parent",parent);
		config.set("type."+typename+".typeid",type);
		TConfig.saveConfig("PigRPG", config, "equiptype.yml");
	}

	@Override
	public String toString(){
		return this.typename;
	}


}
