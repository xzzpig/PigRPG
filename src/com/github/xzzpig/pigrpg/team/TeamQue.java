package com.github.xzzpig.pigrpg.team;
import com.github.xzzpig.pigrpg.*;
import org.bukkit.*;

public class TeamQue
{
	User launchet,target;
	int type;
	
	public TeamQue(User launcher,User target){
		String message;
		this.launchet = launcher;
		this.target = target;
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
			launcher.sendPluginMessage("邀请已发送");
			target.sendPluginMessage(Color.BLUE+launcher.getPlayer().getName()+Color.GREEN+"邀请你加入他的队伍");
			type = 1;
		}
		else if(message.equalsIgnoreCase("申请加入对方队伍")){
			launcher.sendPluginMessage("邀请已发送");
			target.sendPluginMessage(Color.BLUE+launcher.getPlayer().getName()+Color.GREEN+"邀请你加入他的队伍");
			type = 2;
		}
	}
}
