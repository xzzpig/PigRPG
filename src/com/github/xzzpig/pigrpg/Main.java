package com.github.xzzpig.pigrpg;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TUpdate;
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
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		return Commands.command(sender, cmd, label, args);
	}

	// 插件停用函数
	@Override
	public void onDisable() {
		getLogger().info(getName() + "插件已被停用 ");
	}

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

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		return Help.PIGRPG.getTabComplete(getName(), sender, command, alias,
				args);
	}

	private boolean setupEss() {
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("Essentials")) {
			Vars.ess = (Essentials) Bukkit.getPluginManager().getPlugin(
					"Essentials");
		}
		return (Vars.ess != null);
	}
}
