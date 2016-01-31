package com.github.xzzpig.pigrpg.team;
import java.util.*;
import com.github.xzzpig.pigrpg.*;

public class Team
{
	public static List<Team> teams = new ArrayList<Team>();

	public static Team getFrom(User user){
		for(Team t:teams)
			if(t.hasMember(user))
				return t;
		return null;
	}

	private List<User> users = new ArrayList<User>();
	private User leader;
	
	public Team(){
		teams.add(this);
	}
	
	public User getLeader(){
		return leader;
	}
	public Team setLeader(User member){
		leader = member;
		users.add(member);
		return this;
	}
	
	public Team addMember(User member){
		users.add(member);
		return this;
	}
	public boolean hasMember(User member){
		return users.contains(member);
	}
	public Team removeMember(User member){
		users.remove(member);
		return this;
	}
}
