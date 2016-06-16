package com.github.xzzpig.pigrpg;

import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.xzzpig.pigapi.PigData;
import com.github.xzzpig.pigapi.bukkit.TConfig;
import com.github.xzzpig.pigrpg.command.Help;
import com.github.xzzpig.pigrpg.command.PigCommand;
import com.github.xzzpig.pigrpg.friend.FriendListener;
import com.github.xzzpig.pigrpg.rclist.RCListListener;

public class Main extends JavaPlugin {

	public static Main self;

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
		Vars.config = TConfig.getConfigFile("PigRPG", "config.yml");// 加载配置
		Vars.debuger = Vars.config.getBoolean("pigrpg.debuger",false);
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
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return PigCommand.onCommand(sender, command, label, args);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		return Help.PIGRPG.getTabComplete(getName(), sender, command, alias, args);
	}
}
