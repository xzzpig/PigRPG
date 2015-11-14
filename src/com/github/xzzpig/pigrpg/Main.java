package com.github.xzzpig.pigrpg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;
import com.github.xzzpig.pigrpg.chat.ChatListener;
import com.github.xzzpig.pigrpg.commands.Commands;
import com.github.xzzpig.pigrpg.exlist.RCChestListener;
import com.github.xzzpig.pigrpg.friend.FriendEvent;
import com.github.xzzpig.pigrpg.teleport.TelListener;
import com.github.xzzpig.pigrpg.teleport.Warp;
import com.github.xzzpig.pigrpg.trade.PlayerTradeListener;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		getLogger().info(getName()+"插件已被加载");
		saveDefaultConfig();
		Vars.configs = this.getConfig();
		Vars.hasEss = setupEss();
		Voids.loadBanWords();
		try {
			Warp.loadAll();
		} catch (Exception e) {
			getLogger().info(" Warp读取失败,原因可能是暂无 Warp");
		}
		getServer().getPluginManager().registerEvents(new RCChestListener(), this);
		getServer().getPluginManager().registerEvents(new FriendEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerTradeListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new TelListener(), this);
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
	private boolean setupEss()
    {
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("Essentials")){
			Vars.ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
		}
        return (Vars.ess != null);
    }
}
