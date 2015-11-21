package com.github.xzzpig.pigrpg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import com.github.xzzpig.pigrpg.power.*;
import org.bukkit.metadata.*;
import org.bukkit.*;

public class State
{
	private User user;
	private LivingEntity entity;
	private int hp=20,mp,pda,mda,pde,mde;
	private List<Power> powers = new ArrayList<Power>();
	
	public State(LivingEntity entity){
		FixedMetadataValue fd = new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("PigRPG"),this);
		entity.setMetadata("state",fd);
		this.entity = entity;
		if(entity instanceof Player){
			this.user = User.getUser((Player)entity);
		}
	}
	
	public User getUser()
	{
		return user;
	}
	
	public LivingEntity getEntity()
	{
		return entity;
	}
	
	public static State getFrom(LivingEntity entity){
		for(MetadataValue mv : entity.getMetadata("state")){
			if(mv.value() instanceof State)
				return (State)mv.value();
		}
		return null;
	}
	public static boolean hasState(LivingEntity entity){
		return getFrom(entity) != null;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}
	public int getHp()
	{
		return hp;
	}
	
	public void setMp(int mp)
	{
		this.mp = mp;
	}
	public int getMp()
	{
		return mp;
	}
	public void setPhysicDamage(int pda)
	{
		this.pda = pda;
	}
	public int getPhysicDamage()
	{
		return pda;
	}

	public void setMagicDamage(int mda)
	{
		this.mda = mda;
	}
	public int getMagicDamage()
	{
		return mda;
	}

	public void setPhysicDefine(int pde)
	{
		this.pde = pde;
	}
	public int getPhysicDefine()
	{
		return pde;
	}

	public void setMagicDefine(int mde)
	{
		this.mde = mde;
	}
	public int getMagicDefine()
	{
		return mde;
	}
	
	public void remove(){
		entity.removeMetadata("state",Bukkit.getPluginManager().getPlugin("PigPG"));
	}
}
