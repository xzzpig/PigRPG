package com.github.xzzpig.BukkitTools;

public class TArgsSolver
{
	private TData data = new TData();
	public TArgsSolver(String arg){
		String[] args = arg.split(" ");
		for(String set:args){
			if(set.startsWith("-")){
				String key = TString.sub(set,"-",":");
				String value = set.replaceAll("-"+key+":","");
				data.setString(key,value);
			}
		}
	}
	public TArgsSolver(String[] args){
		for(String set:args){
			if(set.startsWith("-")){
				String key = TString.sub(set,"-",":");
				String value = set.replaceAll("-"+key+":","");
				data.setString(key,value);
			}
		}
	}
	public String get(String key){
		return data.getString(key);
	}
}
