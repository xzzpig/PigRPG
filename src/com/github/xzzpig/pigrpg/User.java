package com.github.xzzpig.pigrpg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.xzzpig.pigapi.Debuger;
import com.github.xzzpig.pigapi.PigData;
import com.github.xzzpig.pigapi.bukkit.TEntity;
import com.github.xzzpig.pigapi.bukkit.TMessage;
import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigapi.bukkit.TStringMatcher;

public class User {
	public static final List<User> users = new ArrayList<User>();
	private static final File dataDir = new File(Main.self.getDataFolder(),"userdata");
	
	static{
		Debuger.setIsDebug(User.class,Vars.debuger);
		dataDir.mkdirs();
		new Thread(new Runnable() {
			@Override
			public void run() {
				removeOffline();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static User getUser(Player player) {
		for (User user : users) {
			if (user.player == player)
				return user;
		}
		return new User(player);
	}
	
	public static User getUser(String name) {
		for (User user : users) {
			if (user.name == name)
				return user;
		}
		return new User(TEntity.toPlayer(name));
	}

	public static void removeOffline(){
		List<User> removelist = new ArrayList<User>();
		for (User user : users) {
			if(!user.player.isOnline()){
				Debuger.print(user.player.getName()+"已移除");				
				removelist.add(user);//移除User
			}
		}
		users.removeAll(removelist);
	}

	public static void saveAllData(){
		for (User user : users) {
			user.saveData();
		}
	}
	
	private Player player;
	private File dataFile;
	private PigData data = new PigData();
	private String name;
	private Eco eco;

	private User(Player player) {
		if(player==null)
			throw new NullPointerException("Player不可为null");
		users.add(this);
		this.player = player;
		this.name = player.getName();
		dataFile = new File(dataDir,player.getName()+".pigdata");
		this.eco = new Eco(this);
		try {
			dataFile.createNewFile();
			data = new PigData(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getInfos() {
		List<String> info = new ArrayList<String>();
		if (Vars.playerinfotype.equalsIgnoreCase("custom")) {// 自定义INFO
			for (String sinfo : Vars.playerinfo) {
				info.add(TStringMatcher.buildStr(sinfo, player, false));
			}
		} else {
			info.add(TString.Color(2) + "昵名:" + player.getDisplayName());
			info.add(TString.Color(2)
					+ "HP:"
					+ TStringMatcher.buildStr(
							"</currenthealth/>/</maxhealth/>", player, false));
			// info.add(TString.Color(2)
			// + "攻击:"
			// + TStringMatcher.buildStr(
			// "</pdamage/>(物)|</mdamage/>(魔)|</lastdamage/>(最近)",
			// player, false));
			info.add(TString.Color(2) + "位置:" + player.getWorld().getName()
					+ "," + player.getLocation().getBlockX() + ","
					+ player.getLocation().getBlockY() + ","
					+ player.getLocation().getBlockZ());
			info.add(TString.Color(2) + "游戏等级:"
					+ (player.getLevel() + player.getExp()));
			info.add(TString.Color(2) + "游戏模式:"
					+ TStringMatcher.buildStr("</gamemode/>", player, false));
			// info.add(TString.Color(2)
			// + "区域:"
			// + TStringMatcher.buildStr("</areaname/>(Lv:</arealevel/>)",
			// player, false));
			if (player.isOp())
				info.add(TString.Color(4) + "OP");
			if (player.getAllowFlight())
				info.add(TString.Color(8) + "允许飞行");
			if (player.isFlying())
				info.add(TString.Color(8) + "正在飞行");
		}
		return info;
	}

	public Player getPlayer() {
		return player;
	}

	public PigData getData() {
		return data;
	}
	
	public User saveData(){
		data.saveToFile(dataFile);
		Debuger.print(getPlayer().getName()+"的数据已保存");
		return this;
	}

	public String getName() {
		return name;
	}
	
	public void sendPluginMessage(String message) {
		for (String mes : (TString.Prefix("PigRPG", 3) + message.replaceAll(
				"&", "§")).split("\n"))
			TMessage.getBy(mes, true).send(player);
	}

	public Eco getEcoAPI() {
		return this.eco;
	}
}
