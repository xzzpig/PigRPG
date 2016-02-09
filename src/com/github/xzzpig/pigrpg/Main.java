package com.github.xzzpig.pigrpg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;
import com.github.xzzpig.BukkitTools.TUpdate;
import com.github.xzzpig.pigrpg.chat.ChatListener;
import com.github.xzzpig.pigrpg.commands.Commands;
import com.github.xzzpig.pigrpg.equip.EquipListener;
import com.github.xzzpig.pigrpg.equip.EquipType;
import com.github.xzzpig.pigrpg.exlist.RCChestListener;
import com.github.xzzpig.pigrpg.friend.FriendEvent;
import com.github.xzzpig.pigrpg.power.Power;
import com.github.xzzpig.pigrpg.power.PowerListener;
import com.github.xzzpig.pigrpg.rpg.RPGListener;
import com.github.xzzpig.pigrpg.rpgworld.RpgWorldListener;
import com.github.xzzpig.pigrpg.sale.Sale;
import com.github.xzzpig.pigrpg.sale.SaleListener;
import com.github.xzzpig.pigrpg.teleport.TelListener;
import com.github.xzzpig.pigrpg.teleport.Warp;
import com.github.xzzpig.pigrpg.trade.PlayerTradeListener;
import com.gmail.filoghost.holographicdisplays.nms.interfaces.NMSManager;
import com.gmail.filoghost.holographicdisplays.util.VersionUtils;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		getLogger().info(getName() + getDescription().getVersion() + "插件已被加载");
		saveDefaultConfig();
		Vars.configs = this.getConfig();
		Vars.hasEss = setupEss();
		Voids.loadBanWords();
		loadNms();
		final Plugin plugin = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (TUpdate.hasUpdate(plugin, "Aide"))
						getLogger().info(
								"已检测到该插件有所更新,更新内容:"
										+ TUpdate.getNewestMessgae(plugin,
												"Aide").split("||")[1]);
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
		// getServer().getPluginManager().registerEvents(new
		// MobListener(),this);
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

	public void loadNms() {
		String version = VersionUtils.getBukkitVersion();

		if (version == null) {
			// Caused by MCPC+ / Cauldron renaming packages, extract the version
			// from Bukkit.getVersion().
			version = VersionUtils.getMinecraftVersion();

			if ("1.6.4".equals(version)) {
				version = "v1_6_R3";
			} else if ("1.7.2".equals(version)) {
				version = "v1_7_R1";
			} else if ("1.7.5".equals(version)) {
				version = "v1_7_R2";
			} else if ("1.7.8".equals(version)) {
				version = "v1_7_R3";
			} else if ("1.7.10".equals(version)) {
				version = "v1_7_R4";
			} else if ("1.8".equals(version)) {
				version = "v1_8_R1";
			} else if ("1.8.3".equals(version)) {
				version = "v1_8_R2";
			} else {
				// Cannot definitely get the version. This will cause the plugin
				// to disable itself.
				version = null;
			}
		}
		NMSManager nmsManager;
		// It's simple, we don't need reflection.
		if ("v1_6_R3".equals(version)) {
			nmsManager = new com.gmail.filoghost.holographicdisplays.nms.v1_6_R3.NmsManagerImpl();
		} else if ("v1_7_R1".equals(version)) {
			nmsManager = new com.gmail.filoghost.holographicdisplays.nms.v1_7_R1.NmsManagerImpl();
		} else if ("v1_7_R2".equals(version)) {
			nmsManager = new com.gmail.filoghost.holographicdisplays.nms.v1_7_R2.NmsManagerImpl();
		} else if ("v1_7_R3".equals(version)) {
			nmsManager = new com.gmail.filoghost.holographicdisplays.nms.v1_7_R3.NmsManagerImpl();
		} else if ("v1_7_R4".equals(version)) {
			nmsManager = new com.gmail.filoghost.holographicdisplays.nms.v1_7_R4.NmsManagerImpl();
		} else if ("v1_8_R1".equals(version)) {
			nmsManager = new com.gmail.filoghost.holographicdisplays.nms.v1_8_R1.NmsManagerImpl();
		} else if ("v1_8_R2".equals(version)) {
			nmsManager = new com.gmail.filoghost.holographicdisplays.nms.v1_8_R2.NmsManagerImpl();
		} else if ("v1_8_R3".equals(version)) {
			nmsManager = new com.gmail.filoghost.holographicdisplays.nms.v1_8_R3.NmsManagerImpl();
		} else {
			System.out
					.println("******************************************************\n"
							+ "     This version of PIGRPG can\n"
							+ "     only work on these server versions:\n"
							+ "     from 1.6.4 to 1.8.8.\n"
							+ "     The plugin will be disabled.\n"
							+ "******************************************************");
			return;
		}

		try {
			if (VersionUtils.isMCPCOrCauldron()) {
				getLogger().info("Trying to enable Cauldron/MCPC+ support...");
			}

			nmsManager.setup();

			if (VersionUtils.isMCPCOrCauldron()) {
				getLogger().info(
						"Successfully added support for Cauldron/MCPC+!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Vars.nms = nmsManager;
	}
}
