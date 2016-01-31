package com.github.xzzpig.pigrpg.team;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import org.bukkit.*;

public class TeamQue
{
	public User launcher,target;
	int type;
	
	public TeamQue(User launcher,User target){
		String message;
		if(launcher.hasTeam()){
			if(target.hasTeam())
				if(launcher.getTeam()==target.getTeam())
					message = (Color.BLUE+"对方已是你队友");
				else
					message = (Color.GRAY+"对方已有队伍，不可重复邀请");
			else{
				if(launcher.getTeam().getLeader()==launcher)
					message = (Color.GREEN+"点击邀请对方加入队伍");
				else
					message = (Color.GRAY+"你不是队长,无法邀请 对方加入队伍");}
		}else{
			if(target.hasTeam())
				message = (Color.YELLOW+"申请加入对方队伍");
			else
				message = (Color.GREEN+"创建并邀请对方加入队伍");
		}
		
		if(message.contains("邀请对方加入队伍")){
			launcher.sendPluginMessage(Color.GREEN+"邀请已发送");
			target.sendPluginMessage(Color.BLUE+launcher.getPlayer().getName()+Color.GREEN+"邀请你加入他的队伍");
			Vars.nms.newFancyMessage(TString.Prefix("PigRPG",3)+"输入/pr list ")
				.then(ChatColor.GREEN.toString()+ChatColor.UNDERLINE+"accept")
				.tooltip("同意\n/pr team accept")
				.suggest("/pr team accept")
				.then(ChatColor.RED+"|")
				.then(ChatColor.GREEN.toString()+ChatColor.UNDERLINE+"deny")
				.tooltip("拒绝\n/pr team deny")
				.suggest("/pr team deny")
				.send(target.getPlayer());
			type = 1;
		}
		else if(message.equalsIgnoreCase("申请加入对方队伍")){
			target = target.getTeam().getLeader();
			launcher.sendPluginMessage(Color.GREEN+"申请已发送");
			target.sendPluginMessage(Color.BLUE+launcher.getPlayer().getName()+Color.GREEN+"申请加入你的队伍");
			Vars.nms.newFancyMessage(TString.Prefix("PigRPG",3)+"输入/pr list ")
				.then(ChatColor.GREEN.toString()+ChatColor.UNDERLINE+"accept")
				.tooltip("同意\n/pr team accept")
				.suggest("/pr team accept")
				.then(ChatColor.RED+"|")
				.then(ChatColor.GREEN.toString()+ChatColor.UNDERLINE+"deny")
				.tooltip("拒绝\n/pr team deny")
				.suggest("/pr team deny")
				.send(target.getPlayer());
			type = 2;
		}
		else{
			launcher.sendPluginMessage(Color.RED+"对方已有队伍或你不是队长");
			return;
		}
		this.launcher = launcher;
		this.target = target;
		target.getDatas().setObject("teamque",this);
		
	}
	
	
	public void finish(){
		Team team;
		if(type == 1){
			team = launcher.getTeam();
			if(team == null)
				team = new Team(launcher);
			team.addMember(target);
		}
		else if(type == 2){
			target.getTeam().addMember(launcher);
		}
	}
}