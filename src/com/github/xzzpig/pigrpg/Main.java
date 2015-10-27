package com.github.xzzpig.pigrpg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.xzzpig.pigrpg.commands.Commands;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
	getLogger().info(getName()+"插件已被加载");
	saveDefaultConfig();
	Vars.configs = this.getConfig();
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
