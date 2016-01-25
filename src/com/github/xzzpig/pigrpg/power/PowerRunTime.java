package com.github.xzzpig.pigrpg.power;

import java.util.ArrayList;
import java.util.List;

public enum PowerRunTime
{
	Never,RightClick,Damage,CloseEC,BeDamage;
	
	private PowerRunTime() {}
	
	public static PowerRunTime form(String name){
		for(PowerRunTime pr:PowerRunTime.values())
			if(pr.toString().equalsIgnoreCase(name))
				return pr;
		return Never;
	}

	public static PowerRunTime[] form(List<String> runtimes) {
		List<PowerRunTime> prs = new ArrayList<PowerRunTime>();
		for(String s:runtimes)
			prs.add(form(s));
		return prs.toArray(new PowerRunTime[0]);
	}
}
