package com.github.xzzpig.pigrpg.commands;

import com.github.xzzpig.pigrpg.CommandHelp;

public class Help {
	public static final CommandHelp PIGRPG = new CommandHelp("pigrpg",
			"PigRPG的主命令", "输入/pr help 查看帮助");

	static {
		CommandHelp pr_friend = PIGRPG.addSubCommandHelp("friend",
				"获取 好友系统 的帮助", "输入/pr friend help 查看帮助", "");
		CommandHelp pr_trade = PIGRPG.addSubCommandHelp("trade",
				" 获取 交易系统 的帮助", "输入/pr trade help 查看帮助", "");
		CommandHelp pr_chat = PIGRPG.addSubCommandHelp("chat", "  获取 聊天系统 的帮助",
				"输入/pr chat help 查看帮助", "");
		CommandHelp pr_tel = PIGRPG.addSubCommandHelp("tel", "   获取 传送系统 的帮助",
				"输入/pr tel help 查看帮助", "");
		CommandHelp pr_sale = PIGRPG.addSubCommandHelp("sale", "  获取 拍卖系统 的帮助",
				"输入/pr sale help 查看帮助", "");
		CommandHelp pr_equip = PIGRPG.addSubCommandHelp("equip",
				" 获取 装备系统 的帮助", "输入/pr equip help 查看帮助", "");
		CommandHelp pr_team = PIGRPG.addSubCommandHelp("team", "  获取 组队系统 的帮助",
				"输入/pr team help 查看帮助", "");
		CommandHelp pr_list = PIGRPG.addSubCommandHelp("list", "  获取 所有列表",
				"输入/pr list help 查看帮助", "");
		PIGRPG.addSubCommandHelp("showhand", "展示手中物品", "输入/pr help 查看帮助",
				"<展示玩家>(不填为全部)");
		PIGRPG.addSubCommandHelp("reload", "重载插件", "", "");

		pr_friend.addSubCommandHelp("list", "  打开好友列表", "", "");
		CommandHelp pr_friend_del = pr_friend.addSubCommandHelp("del",
				"   处理好友删除请求", "", "");
		pr_friend.addSubCommandHelp("accept", "接受好友请求", "", "");
		pr_friend.addSubCommandHelp("deny", "  拒绝好友请求", "", "");

		pr_friend_del.addSubCommandHelp("accept", "接受好友删除请求", "", "");
		pr_friend_del.addSubCommandHelp("deny", "  拒绝好友删除请求", "", "");

		pr_trade.addSubCommandHelp("accept", "接受交易请求", "", "");
		pr_trade.addSubCommandHelp("deny", "  拒绝交易请求", "", "");

		pr_chat.addSubCommandHelp("setaccept", "打开设置接受聊天频道列表", "", "");
		pr_chat.addSubCommandHelp("change", "   打开设置接受聊天频道列表", "", "");
		pr_chat.addSubCommandHelp("self",
				"     进入私聊频道\n在聊天栏中以@[玩家]开头发起与该玩家的私聊(非命令)", "", "");
		pr_chat.addSubCommandHelp("ban", "屏蔽含关键字聊天", "", "[关键字]");
		pr_chat.addSubCommandHelp("mute", "设置玩家禁言", "未填默认为true",
				"[玩家] <true|false>");
		pr_chat.addSubCommandHelp("muteall", "设置群体玩家禁言", "未填默认为true",
				"<true|false>");
		pr_chat.addSubCommandHelp("broad", "用Boss血条发送广播(空格用 _ 替代)", "", "[公告]");

		pr_tel.addSubCommandHelp("list", "打开传送列表", "", "");
		pr_tel.addSubCommandHelp("setwarp", "设置warp", "", "[地标名]");

		pr_sale.addSubCommandHelp("list", "打开拍卖行", "", "");
		pr_sale.addSubCommandHelp(
				"sell",
				"出售手上物品到拍卖行",
				"<?>列表(可多个，空格隔开)  \n-\"-p:[整数_价格](默认1)\"  \n-\"-n:[整数_数量](默认 全部)\"",
				"<?>");

		pr_equip.addSubCommandHelp("open", "打开装备栏", "", "");
		pr_equip.addSubCommandHelp("list", "列出所有特殊lore(可快速匹配)", "", "");
		pr_equip.addSubCommandHelp("change", "将手中物品变成装备", "", "");
		pr_equip.addSubCommandHelp("setdisplayname", "设置手中装备的名称(开头无需加颜色)", "",
				"<装备名称>");
		pr_equip.addSubCommandHelp("setid", "设置手中装备的物品id", "", "<物品ID>");
		pr_equip.addSubCommandHelp("qualitylist", "列出所有装备品质", "", "");
		pr_equip.addSubCommandHelp("setquality", "设置手中装备的装备品质",
				"可输入/pr equip qualitylist 查看所有装备品质\n(默认品质 普通)", "<装备品质>");
		pr_equip.addSubCommandHelp("typelist", "列出所有装备类型", "", "");
		pr_equip.addSubCommandHelp("settype", "设置手中装备的装备类型",
				"可输入/pr equip typelist 查看所有装备类型\n(默认类型 无)", "<装备类型>");
		pr_equip.addSubCommandHelp("addlore", "给手中物品添加lore", "", "[lore] <行数>");
		pr_equip.addSubCommandHelp("dellore", "给手中物品删除lore", "", "[行数]");

		pr_team.addSubCommandHelp("accept", "接受组队请求", "", "");
		pr_team.addSubCommandHelp("deny", "  拒绝组队请求", "", "");
		pr_team.addSubCommandHelp("list", "  列出队伍所有成员", "", "");

		pr_list.addSubCommandHelp("lore", "列出所有特殊lore", "", "");
		pr_list.addSubCommandHelp("runtime", "列出所有Lore触发时间参数", "", "");
		pr_list.addSubCommandHelp("quality", "列出所有装备品质", "", "");
		pr_list.addSubCommandHelp("type", "列出所有装备类型", "", "");
		pr_list.addSubCommandHelp("potion", "列出所有药水效果", "", "");
		pr_list.addSubCommandHelp("effect", "列出所有粒子效果", "", "");
		pr_list.addSubCommandHelp("sound", "列出所有音效", "", "");
	}
}
