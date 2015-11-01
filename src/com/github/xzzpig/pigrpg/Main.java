package com.github.xzzpig.pigrpg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.xzzpig.pigrpg.commands.Commands;
import com.github.xzzpig.pigrpg.exlist.RCChestListener;
import com.github.xzzpig.pigrpg.friend.FriendEvent;
import com.github.xzzpig.pigrpg.trade.PlayerTradeListener;
import com.github.xzzpig.pigrpg.chat.*;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
	getLogger().info(getName()+"插件已被加载");
	saveDefaultConfig();
	Vars.configs = this.getConfig();
	getServer().getPluginManager().registerEvents(new RCChestListener(), this);
	getServer().getPluginManager().registerEvents(new FriendEvent(), this);
	getServer().getPluginManager().registerEvents(new PlayerTradeListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
	}
	
	//插件停用函数
	@Override
	public void onDisable() {
	getLogger().info(getName()+"插件已被停用 ");
	}
	
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args)  {
		return Commands.command(sender, cmd, label, args);
	}
}
