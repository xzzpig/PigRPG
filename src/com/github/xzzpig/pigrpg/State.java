package com.github.xzzpig.pigrpg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import com.github.xzzpig.pigrpg.power.*;

public class State
{
	private static HashMap<LivingEntity,State> statelist = new HashMap<LivingEntity,State>();
	private static List<State> ids = new ArrayList<State>();
	private static int maxid = -1;
	
	private User user;
	private LivingEntity entity;
	private int id = maxid+1,hp=20,mp,pda,mda,pde,mde;
	private List<Power> powers = new ArrayList<Power>();
	
	public State(LivingEntity entity){
		statelist.put(entity,this);
		maxid = maxid + 1;
		ids.add(id,this);
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
		if(!statelist.containsKey(entity))
			return new State(entity);
		return statelist.get(entity);
	}
	public static State getFrom(int id){
		if(id > maxid)
			return null;
		return ids.get(id);
	}
	public static boolean hasState(LivingEntity entity){
		return statelist.containsKey(entity);
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
		statelist.remove(entity);
		ids.remove(id);
	}
}
