package com.github.xzzpig.pigrpg.power;

public enum PowerRunTime
{
	Never,RightClick,Damage,CloseEC;
	
	private PowerRunTime() {}
	
	public static PowerRunTime form(String name){
		for(PowerRunTime pr:PowerRunTime.values())
			if(pr.toString().equalsIgnoreCase(name))
				return pr;
		return Never;
	}
}
