package com.github.xzzpig.pigrpg.commands;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.team.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class TeamCommand extends Commands
{
	public static boolean command(CommandSender sender,Command cmd,String label,String[] args){
		User user = User.getUser((Player) sender);
		if(getarg(args,1).equalsIgnoreCase("help")){
			for(CommandHelp ch:CommandHelp.valueOf(Help.PIGRPG,"pigrpg team").getSubCommandHelps())
				ch.getHelpMessage().send((Player)sender);
			return true;
		}
		else if(getarg(args,1).equalsIgnoreCase("accept")){
			TeamQue tq = (TeamQue) user.getDatas().getObject("teamque");
			if(tq==null){
				user.sendPluginMessage("&4你没有组队请求");
				return true;
			}
			tq.finish();
			user.getDatas().setObject("teamque",null);
			return true;
		}
		else if(getarg(args,1).equalsIgnoreCase("deny")){
			TeamQue tq = (TeamQue) user.getDatas().getObject("teamque");
			if(tq==null){
				user.sendPluginMessage("&4你没有组队请求");
				return true;
			}
			user.sendPluginMessage("&3你拒绝了组队请求");
			tq.launcher.sendPluginMessage(
				Color.BLUE+user.getPlayer().getName()+"&3拒绝了你的组队请求");
			user.getDatas().setObject("teamque",null);
			return true;
		}
		else if(getarg(args,1).equalsIgnoreCase("list")){
			Team team = user.getTeam();
			if(team == null){
				user.sendPluginMessage("&4你不在队伍中");
				return true;
			}
			user.sendPluginMessage("&5队伍成员列表:");
			for(User member:team.getMembers()){
				String s = "&3-";
				if(team.getLeader() == member)
					s = s+"[队长]";
				s = s+member.getPlayer().getName();
				user.getPlayer().sendMessage(s.replaceAll("&",TString.s));
			}
			return true;
		}
		Vars.nms.newFancyMessage(TString.Prefix("PigRPG",4)+"输入/pr team help")
			.tooltip(CommandHelp.valueOf(Help.PIGRPG,"pigrpg team").getDescribe())
			.then(ChatColor.BLUE+""+ChatColor.UNDERLINE+"获取帮助")
			.suggest("/pr team help")
			.tooltip("")
			.send((Player)sender);
		return true;
	}
}