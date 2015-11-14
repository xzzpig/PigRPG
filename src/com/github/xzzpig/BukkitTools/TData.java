package com.github.xzzpig.BukkitTools;
import java.util.*;

public class TData
{
	private HashMap<String,String> strs = new HashMap<String,String>();
	private HashMap<String,Integer> ints = new HashMap<String,Integer>();
	private HashMap<String,Boolean> boos = new HashMap<String,Boolean>();
	
	public TData(){}
	
	public String getString(String key){
		if(!this.strs.containsKey(key))
			return null;
		return this.strs.get(key);
	}
	public void setString(String key,String value){
		strs.put(key,value);
	}
	public HashMap<String,String> getStrings(){
		return this.strs;
	}
	
	public int getInt(String key){
		if(!this.ints.containsKey(key))
			return 0;
		return this.ints.get(key);
	}
	public void setInt(String key,int value){
		ints.put(key,value);
	}
	public HashMap<String,Integer> getInts(){
		return this.ints;
	}
	
	public boolean getBoolan(String key){
		if(!this.boos.containsKey(key))
			return false;
		return this.boos.get(key);
	}
	public void setBoolean(String key,boolean value){
		boos.put(key,value);
	}
	public HashMap<String,Boolean> getBooleans(){
		return this.boos;
	}
}
