package com.github.xzzpig.pigrpg;

import com.github.xzzpig.BukkitTools.TPremission;

public class Premissions {
	public static final TPremission pigrpg_ = new TPremission("pigrpg.*", null);
	public static final TPremission pigrpg_admin_ = new TPremission(
			"pigrpg.admin.*", null);
	public static final TPremission pigrpg_default_ = new TPremission(
			"pigrpg.default.*", null);
	public static final TPremission pigrpg_command_ = new TPremission(
			"pigrpg.command.*", null);
	public static final TPremission pigrpg_command_admin = new TPremission(
			"pigrpg.command.admin", null);
	public static final TPremission pigrpg_command_default = new TPremission(
			"pigrpg.command.default", null);
	public static final TPremission pigrpg_command_friend_default = new TPremission(
			"pigrpg.command.friend.default", null);
	public static final TPremission pigrpg_command_chat_default = new TPremission(
			"pigrpg.command.chat.default", null);
	public static final TPremission pigrpg_command_equip_ = new TPremission(
			"pigrpg.command.equip.*", null);
	public static final TPremission pigrpg_command_equip_default = new TPremission(
			"pigrpg.command.equip.default", null);
	public static final TPremission pigrpg_command_equip_admin = new TPremission(
			"pigrpg.command.equip.admin", null);
	public static final TPremission pigrpg_command_equip_list = new TPremission(
			"pigrpg.command.equip.list", null);
	public static final TPremission pigrpg_command_equip_open = new TPremission(
			"pigrpg.command.equip.open", null);
	public static final TPremission pigrpg_command_equip_change = new TPremission(
			"pigrpg.command.equip.change", null);
	public static final TPremission pigrpg_command_equip_setdisplayname = new TPremission(
			"pigrpg.command.equip.setdisplayname", null);
	public static final TPremission pigrpg_command_equip_setid = new TPremission(
			"pigrpg.command.equip.setid", null);
	public static final TPremission pigrpg_command_equip_qualitylist = new TPremission(
			"pigrpg.command.equip.qualitylist", null);
	public static final TPremission pigrpg_command_equip_setquality = new TPremission(
			"pigrpg.command.equip.setquality", null);
	public static final TPremission pigrpg_command_equip_typelist = new TPremission(
			"pigrpg.command.equip.typelist", null);
	public static final TPremission pigrpg_command_equip_settype = new TPremission(
			"pigrpg.command.equip.settype", null);
	public static final TPremission pigrpg_command_equip_addlore = new TPremission(
			"pigrpg.command.equip.addlore", null);
	public static final TPremission pigrpg_command_equip_dellore = new TPremission(
			"pigrpg.command.equip.dellore", null);
	public static final TPremission pigrpg_command_list = new TPremission(
			"pigrpg.command.list", null);
	public static final TPremission pigrpg_trade_default = new TPremission(
			"pigrpg.trade.default", null);
	public static final TPremission pigrpg_teleport_default = new TPremission(
			"pigrpg.teleport.default", null);
	public static final TPremission pigrpg_teleport_warp_ = new TPremission(
			"pigrpg.teleport.warp.*", null);
	public static final TPremission pigrpg_equip_ = new TPremission(
			"pigrpg.equip.*", null);
	public static final TPremission pigrpg_equip_admin = new TPremission(
			"pigrpg.equip.admin", null);
	public static final TPremission pigrpg_equip_default = new TPremission(
			"pigrpg.equip.default", null);
	public static final TPremission pigrpg_equip_use_ = new TPremission(
			"pigrpg.equip.use.*", null);

	static {
		pigrpg_.addChild(pigrpg_admin_);
		pigrpg_admin_.addChild(pigrpg_default_).addChild(pigrpg_command_admin)
				.addChild(pigrpg_equip_);
		pigrpg_default_.addChild(pigrpg_command_default)
				.addChild(pigrpg_trade_default).addChild(pigrpg_equip_default);
		pigrpg_command_.addChild(pigrpg_command_admin);
		pigrpg_command_admin.addChild(pigrpg_command_default)
				.addChild(pigrpg_command_equip_).addChild(pigrpg_command_list);
		pigrpg_command_default.addChild(pigrpg_command_friend_default)
				.addChild(pigrpg_command_chat_default);
		pigrpg_command_equip_.addChild(pigrpg_command_equip_admin).addChild(
				pigrpg_command_equip_default);
		pigrpg_command_equip_admin.addChild(pigrpg_command_equip_list)
				.addChild(pigrpg_command_equip_setdisplayname)
				.addChild(pigrpg_command_equip_setid)
				.addChild(pigrpg_command_equip_qualitylist)
				.addChild(pigrpg_command_equip_setquality)
				.addChild(pigrpg_command_equip_typelist)
				.addChild(pigrpg_command_equip_settype)
				.addChild(pigrpg_command_equip_addlore)
				.addChild(pigrpg_command_equip_dellore);
		pigrpg_command_equip_default.addChild(pigrpg_command_equip_open)
				.addChild(pigrpg_command_equip_change);
		pigrpg_teleport_default.addChild(pigrpg_teleport_warp_);
		pigrpg_equip_.addChild(pigrpg_equip_default).addChild(
				pigrpg_equip_admin);
		pigrpg_equip_default.addChild(pigrpg_equip_use_).addChild(
				pigrpg_command_equip_default);
		pigrpg_equip_admin.addChild(pigrpg_command_equip_admin);
	}
}
