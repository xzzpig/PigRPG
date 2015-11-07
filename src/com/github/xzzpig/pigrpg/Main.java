package com.github.xzzpig.pigrpg;

import com.github.xzzpig.pigrpg.chat.*;
import com.github.xzzpig.pigrpg.commands.*;
import com.github.xzzpig.pigrpg.exlist.*;
import com.github.xzzpig.pigrpg.friend.*;
import com.github.xzzpig.pigrpg.teleport.*;
import com.github.xzzpig.pigrpg.trade.*;
import net.milkbowl.vault.economy.*;
import org.bukkit.command.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		getLogger().info(getName()+"插件已被加载");
		saveDefaultConfig();
		Vars.configs = this.getConfig();
		Warp.loadAll();
		Vars.hasEco = setupEconomy();
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
	
	private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            Vars.economy = economyProvider.getProvider();
        }

        return (Vars.economy != null);
    }
}
