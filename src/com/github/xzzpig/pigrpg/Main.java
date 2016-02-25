package com.github.xzzpig.pigrpg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;
import com.github.xzzpig.BukkitTools.TMessage;
import com.github.xzzpig.BukkitTools.TUpdate;
import com.github.xzzpig.pigrpg.chat.ChatListener;
import com.github.xzzpig.pigrpg.commands.Commands;
import com.github.xzzpig.pigrpg.commands.Help;
import com.github.xzzpig.pigrpg.equip.EquipListener;
import com.github.xzzpig.pigrpg.equip.EquipType;
import com.github.xzzpig.pigrpg.exlist.RCChestListener;
import com.github.xzzpig.pigrpg.friend.FriendEvent;
import com.github.xzzpig.pigrpg.mob.MobListener;
import com.github.xzzpig.pigrpg.power.Power;
import com.github.xzzpig.pigrpg.power.PowerListener;
import com.github.xzzpig.pigrpg.rpg.RPGListener;
import com.github.xzzpig.pigrpg.rpgworld.RpgWorldListener;
import com.github.xzzpig.pigrpg.sale.Sale;
import com.github.xzzpig.pigrpg.sale.SaleListener;
import com.github.xzzpig.pigrpg.teleport.TelListener;
import com.github.xzzpig.pigrpg.teleport.Warp;
import com.github.xzzpig.pigrpg.trade.PlayerTradeListener;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		getLogger().info(getName() + getDescription().getVersion() + "插件已被加载");
		saveDefaultConfig();
		Vars.configs = this.getConfig();
		Vars.hasEss = setupEss();
		Voids.loadBanWords();
		try {
			TMessage.class.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		final Plugin plugin = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (TUpdate.hasUpdate(plugin, "UpDating"))
						getLogger().info(
								"已检测到该插件有所更新,更新内容:"
										+ TUpdate.getNewestMessgae(plugin,
												"UpDating").split("||")[1]);
				} catch (Exception e) {
					getLogger().info("更新检测失败");
				}
			}
		}).start();
		try {
			Warp.loadAll();
		} catch (Exception e) {
			getLogger().info(" Warp读取失败,原因可能是暂无 Warp");
		}
		EquipType.load();
		Sale.loadItems();
		Power.values();
		if (Vars.RCSystem)
			getServer().getPluginManager().registerEvents(
					new RCChestListener(), this);
		if (Vars.FriendSystem)
			getServer().getPluginManager().registerEvents(new FriendEvent(),
					this);
		if (Vars.TradeSystem)
			getServer().getPluginManager().registerEvents(
					new PlayerTradeListener(), this);
		if (Vars.ChatSystem)
			getServer().getPluginManager().registerEvents(new ChatListener(),
					this);
		if (Vars.TeleportSystem)
			getServer().getPluginManager().registerEvents(new TelListener(),
					this);
		if (Vars.SaleSystem)
			getServer().getPluginManager().registerEvents(new SaleListener(),
					this);
		if (Vars.EquipSystem)
			getServer().getPluginManager().registerEvents(new EquipListener(),
					this);
		if (Vars.PowerSystem)
			getServer().getPluginManager().registerEvents(new PowerListener(),
					this);
		if (Vars.RpgWorldSystem)
			getServer().getPluginManager().registerEvents(
					new RpgWorldListener(), this);
		if (Vars.RpgMobSystem)
			getServer().getPluginManager().registerEvents(new MobListener(),
					this);
		getServer().getPluginManager().registerEvents(new RPGListener(), this);
	}

	// 插件停用函数
	@Override
	public void onDisable() {
		getLogger().info(getName() + "插件已被停用 ");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		return Commands.command(sender, cmd, label, args);
	}

	private boolean setupEss() {
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("Essentials")) {
			Vars.ess = (Essentials) Bukkit.getPluginManager().getPlugin(
					"Essentials");
		}
		return (Vars.ess != null);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		//Debuger.print(command.getName()+"|"+alias+"|"+Arrays.toString(args));
		List<String> tab = new ArrayList<String>();
		String cmd = command.getName();
		for(String arg:args){
			cmd = cmd +" " + arg;
		}
		if(cmd.endsWith(" "))
			cmd = cmd.substring(0,cmd.length()-1);
		for(CommandHelp help:CommandHelp.valueOf(Help.PIGRPG,cmd).getSubCommandHelps()){
			tab.add(help.toStrings()[help.toStrings().length-1]);
		}
		List<String> tab2 = new ArrayList<String>();
		for(String str:tab){
			if(str.contains(args[args.length-1])){
				tab2.add(str);
			}
		}
		if(!tab2.isEmpty())
			tab = tab2;
		for(String str:tab)
			CommandHelp.valueOf(Help.PIGRPG,cmd).getSubCommandHelp(str).getHelpMessage().send((Player)sender);
		if(tab.isEmpty())
			tab.add(CommandHelp.valueOf(Help.PIGRPG,cmd).getVar());
		return tab;
	}
}
