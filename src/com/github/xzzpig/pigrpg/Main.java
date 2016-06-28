package com.github.xzzpig.pigrpg;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;
import com.github.xzzpig.pigapi.PigData;
import com.github.xzzpig.pigapi.bukkit.TConfig;
import com.github.xzzpig.pigrpg.command.Help;
import com.github.xzzpig.pigrpg.command.PigCommand;
import com.github.xzzpig.pigrpg.friend.FriendListener;
import com.github.xzzpig.pigrpg.rclist.RCListListener;
import com.github.xzzpig.pigrpg.sale.SaleListener;
import com.github.xzzpig.pigrpg.score.CustomScore;
import com.github.xzzpig.pigrpg.teleport.TelListener;
import com.github.xzzpig.pigrpg.teleport.Warp;
import com.github.xzzpig.pigrpg.trade.PlayerTradeListener;

public class Main extends JavaPlugin {

	public static Main self;

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return PigCommand.onCommand(sender, command, label, args);
	}

	// 插件停用函数
	@Override
	public void onDisable() {
		for (User user : User.users) {
			user.saveData();// 保存User
		}
		getLogger().info(getName() + "插件已被停用");
	}

	@Override
	public void onEnable() {
		self = this;
		getLogger().info(getName() + getDescription().getVersion() + "插件已被加载");
		saveDefaultConfig();// 初始化
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("Essentials")) {// 加载ESS
			Vars.ess = (Essentials) Bukkit.getPluginManager().getPlugin(
					"Essentials");
		}
		Vars.config = TConfig.getConfigFile("PigRPG", "config.yml");// 加载配置
		Vars.debuger = Vars.config.getBoolean("pigrpg.debuger", false);
		Vars.pdata = new PigData();
		Vars.playerinfotype = Vars.config.getString("pigrpg.playerinfo.type");
		Vars.playerinfo = Vars.config.getStringList("pigrpg.playerinfo.custom");
		Vars.enables = new HashMap<String, Boolean>();
		for (String key : Vars.config.getConfigurationSection("pigrpg.enable")
				.getKeys(false))
			Vars.enables.put(key,
					Vars.config.getBoolean("pigrpg.enable." + key, true));// 读取所有系统开关
		this.getServer().getPluginManager()
				.registerEvents(BaseListener.self, this);
		if (Vars.enables.containsKey("RCList") && Vars.enables.get("RCList")) {// 加载右键菜单系统
			Vars.pdata.set("config.rclist.cancel",
					Vars.config.getBoolean("pigrpg.rclist.cancel", true));
			this.getServer().getPluginManager()
					.registerEvents(RCListListener.self, this);
		}
		if (Vars.enables.containsKey("Friend") && Vars.enables.get("Friend")) {// 加载好友系统
			this.getServer().getPluginManager()
					.registerEvents(FriendListener.self, this);
		}
		if (Vars.enables.containsKey("Trade") && Vars.enables.get("Trade")) {// 加载交易系统
			this.getServer().getPluginManager()
					.registerEvents(PlayerTradeListener.self, this);
		}
		if (Vars.enables.containsKey("Sale") && Vars.enables.get("Sale")) {// 加载拍卖系统
			this.getServer().getPluginManager()
					.registerEvents(SaleListener.self, this);
		}
		if (Vars.enables.containsKey("Teleport")
				&& Vars.enables.get("Teleport")) {// 加载传送系统
			Vars.pdata
					.set("tel.warp.enbale", Vars.config.getBoolean(
							"pigrpg.teleport.warp.enable", true));
			Vars.pdata.set("tel.friend.enbale", Vars.config.getBoolean(
					"pigrpg.teleport.friend.enable", true));
			Vars.pdata.set("tel.friend.overworld", Vars.config.getBoolean(
					"pigrpg.teleport.friend.overworld", true));
			Vars.pdata.set("tel.friend.allworld", Vars.config.getBoolean(
					"pigrpg.teleport.friend.allworld", true));
			Vars.pdata.set("tel.friend.enableworld", Vars.config
					.getStringList("pigrpg.teleport.friend.enableworld"));
			Vars.pdata.set("tel.cooldown",
					Vars.config.getStringList("pigrpg.cooldown"));
			try {
				Warp.loadAll();
			} catch (Exception e) {
				getLogger().warning("Warp读取错误,可能由无Warp造成");
			}
			this.getServer().getPluginManager()
					.registerEvents(TelListener.self, this);
		}
		if (Vars.enables.containsKey("Score")
				&& Vars.enables.get("Score")) {// 加载传送系统
			CustomScore.AUTO_FRESH = Vars.config.getBoolean("pigrpg.score.autofresh",true);
			CustomScore.FRESH_TIME = Vars.config.getInt("pigrpg.score.freshtime",1000);
			CustomScore.HEAD = Vars.config.getString("pigrpg.score.head","§6§l[PigRPG]");
			CustomScore.scores = Vars.config.getStringList("pigrpg.score.scores");
			if(CustomScore.AUTO_FRESH)
				CustomScore.freshthread.start();
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		return Help.PIGRPG.getTabComplete(getName(), sender, command, alias,
				args);
	}
}
