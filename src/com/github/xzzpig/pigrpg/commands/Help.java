package com.github.xzzpig.pigrpg.commands;
import com.github.xzzpig.pigrpg.*;

public class Help
{
	public static final CommandHelp PIGRPG = new CommandHelp("pigrpg","PigRPG的主命令","输入/pr help 查看帮助");
	
	static{
		CommandHelp pr_friend = PIGRPG.addSubCommandHelp("friend","获取 好友系统 的帮助","输入/pr friend help 查看帮助","");
		CommandHelp pr_trade = PIGRPG.addSubCommandHelp( "trade"," 获取 交易系统 的帮助","输入/pr trade help 查看帮助","");
		CommandHelp pr_chat = PIGRPG.addSubCommandHelp(  "chat","  获取 聊天系统 的帮助","输入/pr trade help 查看帮助","");
		CommandHelp pr_tel = PIGRPG.addSubCommandHelp(   "tel","   获取 传送系统 的帮助","输入/pr trade help 查看帮助","");
		CommandHelp pr_sale = PIGRPG.addSubCommandHelp(  "sale","  获取 拍卖系统 的帮助","输入/pr trade help 查看帮助","");
		@SuppressWarnings("unused")
		CommandHelp pr_showhand = PIGRPG.addSubCommandHelp( "showhand","展示手中物品", "输入/pr help 查看帮助","<展示玩家>(不填为全部)");
		
		pr_friend.addSubCommandHelp("list","  打开好友列表","","");
		pr_friend.addSubCommandHelp("del","   处理好友删除请求","","");
		pr_friend.addSubCommandHelp("accept","接受好友请求","","");
		pr_friend.addSubCommandHelp("deny","  拒绝好友请求","","");
		
		pr_trade.addSubCommandHelp("accept","接受交易请求","","");
		pr_trade.addSubCommandHelp("deny","  拒绝交易请求","","");
		
		pr_chat.addSubCommandHelp("setaccept","打开设置接受聊天频道列表","","");
		pr_chat.addSubCommandHelp("change","   打开设置接受聊天频道列表","","");
		pr_chat.addSubCommandHelp("self","     进入私聊频道\n在聊天栏中以@[玩家]开头发起与该玩家的私聊(非命令)","","");
		pr_chat.addSubCommandHelp("ban","屏蔽含关键字聊天","","[关键字]");
		pr_chat.addSubCommandHelp("mute","设置玩家禁言","未填默认为true","[玩家] <true|false>");
		pr_chat.addSubCommandHelp("muteall","设置群体玩家禁言","未填默认为true","<true|false>");
		pr_chat.addSubCommandHelp("broad","用Boss血条发送广播(空格用 _ 替代)","","[公告]");
		
		pr_tel.addSubCommandHelp("list","打开传送列表","","");
		pr_tel.addSubCommandHelp("setwarp","设置warp","","[地标名]");
		
		pr_sale.addSubCommandHelp("list","打开拍卖行","","");
		pr_sale.addSubCommandHelp("sell","出售手上物品到拍卖行","","<?>");
		
	}
}
