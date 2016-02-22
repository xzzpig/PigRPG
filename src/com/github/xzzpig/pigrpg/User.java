package com.github.xzzpig.pigrpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import me.confuser.barapi.BarAPI;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.xzzpig.BukkitTools.TConfig;
import com.github.xzzpig.BukkitTools.TData;
import com.github.xzzpig.BukkitTools.TEntity;
import com.github.xzzpig.BukkitTools.TPremission;
import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.BukkitTools.scoreboard.ScoreboardUtil;
import com.github.xzzpig.pigrpg.chat.Chat;
import com.github.xzzpig.pigrpg.chat.ChatChannel;
import com.github.xzzpig.pigrpg.commands.Help;
import com.github.xzzpig.pigrpg.equip.EquipType;
import com.github.xzzpig.pigrpg.equip.Equipment;
import com.github.xzzpig.pigrpg.friend.Friend;
import com.github.xzzpig.pigrpg.rpg.RPGListener;
import com.github.xzzpig.pigrpg.rpg.RpgClass;
import com.github.xzzpig.pigrpg.rpgworld.RpgChunk;
import com.github.xzzpig.pigrpg.rpgworld.RpgWorld;
import com.github.xzzpig.pigrpg.score.CustomScore;
import com.github.xzzpig.pigrpg.team.Team;
import com.github.xzzpig.pigrpg.teleport.Warp;

public class User {
	private static HashMap<Player, User> userlist = new HashMap<Player, User>();

	private Player player;
	private User chatTarget, willChat;
	private ChatChannel chatchannel;
	private List<ChatChannel> acceptchannel;
	private HashMap<EquipType, Equipment> equiplist = new HashMap<EquipType, Equipment>();
	private String justsay = "", prefix;
	private Chat chat;
	private TData data = new TData();
	private Eco eco;
	private State state;
	private ItemStack handitem;
	private Equipment handequip;
	private int exp;
	private HashMap<String, Object> scorelist = new HashMap<String, Object>();
	public boolean chatHighLight = true;
	private String rpgclass;

	public User(Player player) {
		this.player = player;
		userlist.put(player, this);
		autoRemove();
		chatchannel = ChatChannel.All;
		this.acceptchannel = ChatChannel.DefList();
		this.chatTarget = this;
		this.willChat = this;
		this.chat = new Chat(this);
		this.eco = new Eco(this);
		this.state = new State(player);
		this.prefix = TConfig.getConfigFile("PigRPG", "userdata.yml")
				.getString(player.getName() + ".prefix", "null");
		this.exp = TConfig.getConfigFile("PigRPG", "userdata.yml").getInt(
				player.getName() + ".exp", 0);
		this.rpgclass = TConfig.getConfigFile("PigRPG", "userdata.yml")
				.getString(player.getName() + ".rpgclass", "default");
		freshDisplayName();
		loadEquis();
		buildScore();
	}

	public void buildScore() {
		if (!Vars.ScoreSystem)
			return;
		Iterator<Entry<String, Object>> ir = CustomScore.scores.entrySet()
				.iterator();
		while (ir.hasNext()) {
			Entry<String, Object> next = ir.next();
			this.scorelist
					.put(next.getKey(), StringMatcher.buildStr(next.getValue()
							+ "", player, false));
		}
		ir = this.scorelist.entrySet().iterator();
		List<String> list = new ArrayList<String>();
		list.add("§6§l[PigRPG]");
		while (ir.hasNext()) {
			Entry<String, Object> next = ir.next();
			list.add(("&l" + next.getKey() + ":&f" + next.getValue())
					.replaceAll("&", TString.s));
		}
		if (this.hasTeam()) {
			list.add("&5&l[TEAM]".replaceAll("&", TString.s));
			if (this.getTeam().getLeader() != this)
				list.add(("&l*" + getTeam().getLeader().getPlayer().getName()
						+ ":&4"
						+ TEntity.getHealth(getTeam().getLeader().getPlayer())
						+ "/" + TEntity.getMaxHealth(getTeam().getLeader()
						.getPlayer())).replaceAll("&", TString.s));
			for (User user : this.getTeam().getNoLeaderMembers()) {
				if (user == this)
					continue;
				if (!user.getPlayer().isOnline()) {
					this.getTeam().removeMember(user);
					continue;
				}
				list.add(("&l" + user.getPlayer().getName() + ":&4"
						+ TEntity.getHealth(user.getPlayer()) + "/" + TEntity
						.getMaxHealth(user.getPlayer())).replaceAll("&",
						TString.s));
			}
		}
		ScoreboardUtil.unrankedSidebarDisplay(player,
				list.toArray(new String[0]));
	}

	public void addExp(int exp) {
		this.setExp(this.exp + exp);
	}

	public void setExp(int exp) {
		this.exp = exp;
		TConfig.saveConfig("PigRPG", "userdata.yml", player.getName() + ".exp",
				exp);
	}

	public int getExp() {
		return exp;
	}

	public User freshDisplayName() {
		player.setCustomName(TString.Color(6) + "["
				+ prefix.replaceAll("&", TString.s) + TString.Color(6) + "]\n"
				+ TString.Color("f") + player.getName());
		return this;
	}

	public void setJustsay(String justsay) {
		this.justsay = justsay;
	}

	public String getJustsay() {
		return justsay;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
		if (prefix.contains("STONE") || prefix.contains("称号"))
			this.prefix = "null";
		TConfig.saveConfig("PigRPG", "userdata.yml", player.getName()
				+ ".prefix", this.prefix);
		freshDisplayName();
	}

	public String getPrefix() {
		return prefix;
	}

	public User setRpgClass(RpgClass rpgclass) {
		this.rpgclass = rpgclass.getName();
		TConfig.saveConfig("PigRPG", "userdata.yml", player.getName()
				+ ".rpgclass", this.rpgclass);
		return this;
	}

	public RpgClass getRpgClass() {
		return RpgClass.valueOf(rpgclass);
	}

	public Team getTeam() {
		return Team.getFrom(this);
	}

	public boolean hasTeam() {
		return (getTeam() != null);
	}

	public static User getUser(Player player) {
		if (!userlist.containsKey(player))
			return new User(player);
		return userlist.get(player);
	}

	private void autoRemove() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (userlist.containsKey(player) && player.isOnline()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					User.this.buildScore();
				}
				userlist.remove(player);
			}
		}).start();
	}

	public boolean isAcceptChatChannel(ChatChannel c) {
		return acceptchannel.contains(c);
	}

	public void addAcceptChatChannel(ChatChannel c) {
		if (!this.isAcceptChatChannel(c))
			acceptchannel.add(c);
	}

	public void delAcceptChatChannel(ChatChannel c) {
		if (this.isAcceptChatChannel(c))
			acceptchannel.remove(c);
	}

	public void setChatchannel(ChatChannel chatchannel) {
		this.chatchannel = chatchannel;
	}

	public ChatChannel getChatchannel() {
		return chatchannel;
	}

	private void loadEquis() {
		for (EquipType type : EquipType.values()) {
			ItemStack iequip = TConfig.getConfigFile("PigRPG",
					"equip" + "_" + type + ".yml").getItemStack(
					"equip." + player.getName());
			if (iequip == null)
				continue;
			setEquip(new Equipment(iequip, player));
		}
	}

	public void setEquip(Equipment equip) {
		equiplist.put(equip.getEquiptype().getFinalParent(), equip);
		TConfig.saveConfig("PigRPG", "equip" + "_" + equip.getEquiptype()
				+ ".yml", "equip." + player.getName(), new ItemStack(equip));
	}

	public Equipment getEquip(EquipType type) {
		if (equiplist.containsKey(type))
			return equiplist.get(type);
		return new Equipment(1).setEquiptype(type);
	}

	@SuppressWarnings("deprecation")
	public Equipment getHandEquip() {
		if (this.handitem != null
				&& handitem.toString().equalsIgnoreCase(
						player.getItemInHand().toString()))
			return this.handequip;
		Equipment equip = new Equipment(player.getItemInHand(), player);
		if (equip.getEquiptype() != EquipType.Default)
			player.setItemInHand(equip);
		player.updateInventory();
		this.handitem = player.getItemInHand();
		this.handequip = equip;
		return equip;
	}

	public boolean hasFriend(String friend) {
		return Friend.hasFriend(player.getName(), friend);
	}

	public Chat getChatManager() {
		return this.chat;
	}

	public TData getDatas() {
		return this.data;
	}

	public Eco getEcoAPI() {
		return this.eco;
	}

	public List<String> getInfo() {
		List<String> info = new ArrayList<String>();
		info.add(TString.Color(2) + "昵名:" + player.getDisplayName());
		info.add(TString.Color(2)
				+ "生命:"
				+ StringMatcher.buildStr("</currenthealth/>/</maxhealth/>",
						player, false));
		info.add(TString.Color(2) + "位置:" + player.getWorld().getName() + ","
				+ player.getLocation().getBlockX() + ","
				+ player.getLocation().getBlockY() + ","
				+ player.getLocation().getBlockZ());
		info.add(TString.Color(2) + "游戏等级:"
				+ (player.getLevel() + player.getExp()));
		info.add(TString.Color(2) + "职业:" + getRpgClass().getDisplayName());
		info.add(TString.Color(2)
				+ "RPG经验:"
				+ StringMatcher.buildStr("</rpgexp/>(</rpglevel/>)", player,
						false));
		info.add(TString.Color(2) + "游戏模式:"
				+ StringMatcher.buildStr("</gamemode/>", player, false));
		info.add(TString.Color(2)
				+ "区域:"
				+ StringMatcher.buildStr("</areaname/>(Lv:</arealevel/>)",
						player, false));
		if (player.isOp())
			info.add(TString.Color(4) + "OP");
		if (player.getAllowFlight())
			info.add(TString.Color(8) + "允许飞行");
		if (player.isFlying())
			info.add(TString.Color(8) + "正在飞行");
		return info;

	}

	public void setJustSay(String justsay) {
		this.justsay = justsay;
	}

	public String getJustSay() {
		return justsay.replaceAll("&", TString.s);
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getLevel() {
		return RPGListener.getLevel(getExp());
	}

	public boolean hasPremission(String prmission) {
		if (player.hasPermission(prmission))
			return true;
		TPremission pre = TPremission.valueOf(prmission);
		if (pre == null)
			return false;
		for (TPremission p : pre.getAllParents()) {
			if (player.hasPermission(p.getName()))
				return true;
		}
		return false;
	}

	public boolean hasPremission(TPremission prmission) {
		return this.hasPremission(prmission.getName());
	}

	public State getState() {
		return state;
	}

	public void setSelfChat(User target) {
		this.setChatchannel(ChatChannel.Self);
		this.chatTarget = target;
		target.willChat = this;
		this.sendPluginMessage("&2你进入了私聊频道，你之后的每句话将只有"
				+ target.getPlayer().getName() + "才能看到");
		Vars.nms.newFancyMessage("输入/pr chat change")
				.tooltip(
						CommandHelp.valueOf(Help.PIGRPG, "pigrpg chat change")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + " 更换聊天频道")
				.suggest("/pr chat change").tooltip("更换聊天频道")
				.send(this.getPlayer());
		// this.sendPluginMessage("&7输入/pr chat change 更换聊天频道");
		target.sendPluginMessage(this.getPlayer().getName() + "与你发起了私聊");
		Vars.nms.newFancyMessage("输入/pr chat self")
				.tooltip(
						CommandHelp.valueOf(Help.PIGRPG, "pigrpg chat self")
								.getDescribe())
				.then(ChatColor.BLUE + "" + ChatColor.UNDERLINE + " 更换聊天频道")
				.suggest("/pr chat self").tooltip("更换聊天频道")
				.send(target.getPlayer());
		// target.sendPluginMessage("&7输入/pr chat self 进入私聊频道");
	}

	public void setSelfChat() {
		this.setChatchannel(ChatChannel.Self);
		this.chatTarget = this.willChat;
		User target = this.willChat;
		this.willChat = this;
		this.sendPluginMessage("&2你进入了私聊频道，你之后的每句话将只有"
				+ target.getPlayer().getName() + "才能看到");
		this.sendPluginMessage("&7输入/pr chat change 更换聊天频道");
		target.sendPluginMessage(this.getPlayer().getName() + "与你发起了私聊");
	}

	public void setScore(String key, Object value) {
		this.scorelist.put(key, value);
		this.buildScore();
	}

	public void sendBroadMessage(String message, int second) {
		BarAPI.setMessage(player, message, second);
	}

	public void sendChatMessage(User fromuser) {
		for (String ban : Vars.banWords) {
			if (fromuser.getChatManager().getJustSay().contains(ban)) {
				if (fromuser == this)
					fromuser.sendPluginMessage("&4你的话语中含敏感词汇" + ban);
				return;
			}
		}
		if (fromuser.getChatManager().ismute()) {
			if (this == fromuser)
				fromuser.sendPluginMessage("&4你已被禁言");
			return;
		}
		if (!this.isAcceptChatChannel(fromuser.getChatchannel()))
			return;
		if (fromuser != this) {
			if (fromuser.getChatchannel() == ChatChannel.World
					&& fromuser.getPlayer().getWorld() != this.getPlayer()
							.getWorld())
				return;
			if (fromuser.getChatchannel() == ChatChannel.Friend
					&& (!fromuser.hasFriend(player.getName())))
				return;
			if (fromuser.getChatchannel() == ChatChannel.Self
					&& fromuser.chatTarget != this)
				return;
			if (fromuser.getChatchannel() == ChatChannel.Team
					&& (fromuser.getTeam() == null))
				return;
			if (fromuser.getChatchannel() == ChatChannel.Team
					&& (!fromuser.getTeam().hasMember(this)))
				return;
		}
		String prefix = "[" + ChatColor.GREEN
				+ fromuser.getChatchannel().getName()
				+ "^&3聊天频道(/pr chat change 更换频道)#c/pr chat change^";
		if (fromuser.getChatchannel() != ChatChannel.World) {
			prefix = prefix + "_^^" + ChatColor.YELLOW
					+ fromuser.getPlayer().getWorld().getName() + "^&3所在世界^";
		}
		if (RpgWorld.rpgworldlist.contains(fromuser.getPlayer().getWorld()
				.getName())) {
			RpgChunk rc = new RpgChunk(fromuser.getPlayer().getLocation()
					.getChunk());
			prefix = prefix + "_" + rc.getData("name")
					+ RpgChunk.chbiome.get(rc.getBiome()) + "^&3区域等级:Lv"
					+ rc.getBasicLevel() + "^";
		}
		if (fromuser.getChatchannel() == ChatChannel.Self) {
			prefix = prefix + "_=>^^"
					+ fromuser.chatTarget.getPlayer().getName();
			String info = "";
			for (String s : fromuser.chatTarget.getInfo()) {
				info = info + s + "\n";
			}
			prefix = prefix + "^" + info + "^";
		}
		prefix += "]^^";
		FanMessage.getBy(prefix, false).send(this.player);
		prefix = "";
		if (!fromuser.getPrefix().equalsIgnoreCase("null"))
			prefix = prefix + ChatColor.GOLD + "[" + fromuser.getPrefix()
					+ ChatColor.GOLD + "]" + "^&3玩家称号^";
		if (fromuser.getPlayer().isOp())
			prefix = prefix + ChatColor.RED;
		prefix = prefix + fromuser.getPlayer().getName();
		String info = "";
		for (String s : fromuser.getInfo()) {
			info = info + s + "\n";
		}
		prefix = prefix + "^" + info + "^";
		prefix = prefix + ChatColor.RESET + ":^^";
		FanMessage.getBy(FanMessage.getBy(prefix, false),
				fromuser.getJustSay(), fromuser.chatHighLight)
				.send(this.player);
		// this.player.sendMessage(prefix + fromuser.getJustSay());
	}

	public void sendPluginMessage(String message) {
		FanMessage.getBy(
				TString.Prefix("PigRPG", 3) + message.replaceAll("&", "§"),
				true).send(player);
		// this.player.sendMessage(TString.Prefix("PigRPG", 3)+
		// message.replaceAll("&", "§"));
	}

	public void teleport(Warp warp) {
		if (!(User.getUser(player).hasPremission(
				Premissions.pigrpg_teleport_warp_) || player
				.hasPermission("pigrpg.teleport.warp." + warp.getName()))) {
			player.sendMessage(TString.Prefix("PigRPG", 4) + "你没有权限传送Warp"
					+ warp.getName());
			return;
		}

		this.getPlayer().teleport(warp.getLocation());
		this.sendPluginMessage("&2已将你传送到&3" + warp.getName());
	}
}
