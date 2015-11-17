package com.github.xzzpig.pigrpg;
import com.github.xzzpig.BukkitTools.*;

public class Premissions
{
	public static final TPremission pigrpg_ = new TPremission("pigrpg.*",null);
	public static final TPremission pigrpg_admin_ = new TPremission("pigrpg.admin.*",null);
	public static final TPremission pigrpg_default_ = new TPremission("pigrpg.default.*",null);
	public static final TPremission pigrpg_command_ = new TPremission("pigrpg.command.*",null);
	public static final TPremission pigrpg_command_admin = new TPremission("pigrpg.command.admin",null);
	public static final TPremission pigrpg_command_default = new TPremission("pigrpg.command.default",null);
	public static final TPremission pigrpg_command_friend_default = new TPremission("pigrpg.command.friend.default",null);
	public static final TPremission pigrpg_command_chat_default = new TPremission("pigrpg.command.chat.default",null);
	public static final TPremission pigrpg_trade_default = new TPremission("pigrpg.trade.default",null);
	public static final TPremission pigrpg_teleport_default = new TPremission("pigrpg.teleport.default",null);
	public static final TPremission pigrpg_teleport_warp_ = new TPremission("pigrpg.teleport.warp.*",null);
	
	static
	{
		pigrpg_.addChild(pigrpg_admin_);
		pigrpg_admin_.addChild(pigrpg_default_).addChild(pigrpg_command_admin);
		pigrpg_default_.addChild(pigrpg_command_default).addChild(pigrpg_trade_default).addChild(pigrpg_teleport_default);
		pigrpg_command_.addChild(pigrpg_command_admin);
		pigrpg_command_admin.addChild(pigrpg_command_default);
		pigrpg_command_default.addChild(pigrpg_command_friend_default).addChild(pigrpg_command_chat_default);
		pigrpg_teleport_default.addChild(pigrpg_teleport_warp_);
	}
}
