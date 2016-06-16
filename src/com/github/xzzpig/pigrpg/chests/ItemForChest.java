package com.github.xzzpig.pigrpg.chests;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.friend.Friend;
import com.github.xzzpig.pigrpg.teleport.Warp;

public class ItemForChest {
	protected static ItemStack AddFriend(String player, String target) {
		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(154);
		ItemMeta im = is.getItemMeta();
		if (Friend.hasFriend(player, target))
			im.setDisplayName(TString.Color(3) + "对方已是你的好友");
		else
			im.setDisplayName(TString.Color(3) + "添加好友");
		List<String> lore = new ArrayList<String>();
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack customItem(String displayname, int type,
			List<String> lore) {
		ItemStack is = new ItemStack(type);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3) + displayname);
		if (lore != null)
			im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@SuppressWarnings("deprecation")
	protected static ItemStack friendInform(String player) {
		ItemStack is = new ItemStack(397);
		MaterialData data = is.getData();
		data.setData((byte) 3);
		is.setData(data);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3) + player);
		List<String> lore = new ArrayList<String>();
		OfflinePlayer fplayer = Bukkit.getOfflinePlayer(player);
		if (fplayer.isBanned())
			lore.add(TString.Color(4) + "被BAN");
		if (fplayer.isOnline())
			lore.add(TString.Color(3) + "状态:在线");
		else
			lore.add(TString.Color(8) + "状态:离线");
		if (fplayer.isOp())
			lore.add(TString.Color(4) + "OP");
		if (Bukkit.hasWhitelist())
			if (fplayer.isWhitelisted())
				lore.add(TString.Color(3) + "白名单");
			else
				lore.add(TString.Color(8) + "未在白名单");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@SuppressWarnings("deprecation")
	protected static ItemStack playerInform(Player player) {
		ItemStack is = new ItemStack(397);
		MaterialData data = is.getData();
		data.setData((byte) 3);
		is.setData(data);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3) + "玩家信息");
		List<String> lore = User.getUser(player).getInfo();
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	protected static ItemStack teamAsk(Player player, Player opener) {
		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(1);
		User target = User.getUser(player), launcher = User.getUser(opener);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3) + "组队信息");
		List<String> lore = new ArrayList<String>();

		if (launcher.hasTeam()) {
			if (target.hasTeam())
				if (launcher.getTeam() == target.getTeam())
					lore.add(TString.Color(3) + "对方已是你队友");
				else
					lore.add(TString.Color(7) + "对方已有队伍，不可重复邀请");
			else {
				if (launcher.getTeam().getLeader() == launcher)
					lore.add(TString.Color(2) + "点击邀请对方加入队伍");
				else
					lore.add(TString.Color(7) + "你不是队长,无法邀请对方加入队伍");
			}
		} else {
			if (target.hasTeam())
				lore.add(TString.Color(6) + "申请加入对方队伍");
			else
				lore.add(TString.Color(2) + "创建并邀请对方加入队伍");
		}

		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	protected static ItemStack tradeQue() {
		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(399);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3) + "申请交易");
		List<String> lore = new ArrayList<String>();
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	protected static ItemStack warpInfo(Warp warp) {
		@SuppressWarnings("deprecation")
		ItemStack is = new ItemStack(368);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(TString.Color(3) + warp.getName());
		List<String> lore = new ArrayList<String>();
		lore.add(TString.Color(7) + "所在世界:"
				+ warp.getLocation().getWorld().getName());
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
