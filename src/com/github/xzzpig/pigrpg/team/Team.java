package com.github.xzzpig.pigrpg.team;
import java.util.*;
import com.github.xzzpig.pigrpg.*;
import org.bukkit.*;

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
	public Team(User leader){
		teams.add(this);
		this.leader = leader;
	}
	
	public User getLeader(){
		return leader;
	}
	public Team setLeader(User member){
		leader = member;
		users.add(member);
		return this;
	}
	
	public Team broadMessage(String message,boolean ispl){
		if(ispl)
			for(User user:users)
				user.sendPluginMessage(message);
		else
			for(User user:users)
				user.getPlayer().sendMessage(message);
		return this;
	}
	
	public Team addMember(User member){
		users.add(member);
		broadMessage(Color.BLUE+ member.getPlayer().getName()+"&3加入了队伍",true);
		return this;
	}
	public User[] getMembers(){
		return users.toArray(new User[0]);
	}
	public boolean hasMember(User member){
		return users.contains(member);
	}
	public Team removeMember(User member){
		users.remove(member);
		broadMessage(Color.BLUE+ member.getPlayer().getName()+"&3离开了队伍",true);
		return this;
	}
	
	public void finish(){
		broadMessage("&3队伍将解散",true);
		teams.remove(this);
	}
}