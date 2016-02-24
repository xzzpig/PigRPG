package com.github.xzzpig.pigrpg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.github.xzzpig.BukkitTools.TString;
import com.gmail.filoghost.holographicdisplays.nms.interfaces.FancyMessage;

public class CommandHelp {
	private String command, describe, useage, var;
	private List<CommandHelp> subs = new ArrayList<CommandHelp>();
	private CommandHelp uphelp;

	public CommandHelp(String command, String describe, String useage) {
		this.command = command;
		this.describe = describe;
		this.useage = useage;
	}

	private CommandHelp(String command, String describe, String useage,
			String var, CommandHelp uphelp) {
		if (command == null)
			command = "error";
		this.command = command;
		if (describe == null)
			describe = "无";
		this.describe = describe;
		if (useage == null)
			useage = "无";
		this.useage = useage;
		if (var == null)
			var = "";
		this.var = var;
		this.uphelp = uphelp;
	}

	public String getCommand() {
		return command;
	}

	public String getDescribe() {
		return describe;
	}

	public String getUseage() {
		return useage;
	}

	public String getVar() {
		return var;
	}

	public static CommandHelp valueOf(CommandHelp basichelp, String command) {
		String[] cmds = command.split(" ");
		for(int i=cmds.length-1;i>=0;i--){
			String cmd = cmds[0];
			for(int i2=1;i2<=i;i2++){
				cmd = cmd + " "+cmds[i2];
			}
			for (CommandHelp ch : basichelp.getAllSubs())
				if (ch.toString().equalsIgnoreCase(cmd))
					return ch;
		}
		return basichelp;
	}

	public CommandHelp getFinalUpHelp() {
		CommandHelp ch = this;
		while (ch.uphelp != null)
			ch = ch.uphelp;
		return ch;
	}

	public FancyMessage getHelpMessage() {
		FancyMessage help = Vars.nms.newFancyMessage(TString
				.Prefix("PigRPG", 3) + "/");
		String parts[] = this.toStrings();
		String com = "";
		for (String arg : parts) {
			com = com + arg;
			CommandHelp ch = CommandHelp.valueOf(this.getFinalUpHelp(), com);
			help.then(arg)
					.suggest("/" + com)
					.tooltip(
							ChatColor.GREEN + ch.command + " " + var + "\n"
									+ ChatColor.BLUE + ch.describe + "\n"
									+ ChatColor.GRAY + ch.useage).then(" ");
			com = com + " ";
		}
		help.then(ChatColor.BLUE + " -" + describe)
				.tooltip(ChatColor.GRAY + useage)
				.then("\n" + ChatColor.GREEN + "" + ChatColor.UNDERLINE + "点我")
				.suggest("/" + command + " " + var)
				.tooltip("快速匹配命令\n" + "/" + command + " " + var);
		return help;
	}

	public CommandHelp addSubCommandHelp(String command, String describe,
			String useage, String var) {
		CommandHelp sub = new CommandHelp(this.command + " " + command,
				describe, useage, var, this);
		subs.add(sub);
		return sub;
	}

	public CommandHelp getSubCommandHelp(String command) {
		for (CommandHelp c : subs) {
			if (command.equalsIgnoreCase(c.toString()))
				return c;
			if (c.toStrings()[c.toStrings().length - 1]
					.equalsIgnoreCase(command))
				return c;
		}
		return this;
	}

	public CommandHelp[] getSubCommandHelps() {
		return subs.toArray(new CommandHelp[0]);
	}

	public List<CommandHelp> getAllSubs() {
		List<CommandHelp> sublist = new ArrayList<CommandHelp>();
		for (CommandHelp pre : this.subs) {
			sublist.add(pre);
			List<CommandHelp> sub = pre.getAllSubs();
			if (sub != null) {
				for (CommandHelp sub2 : sub) {
					if (!sublist.contains(sub2))
						sublist.add(sub2);
				}
			}
		}
		return sublist;
	}

	@Override
	public String toString() {
		return command;
	}

	public String[] toStrings() {
		return command.split(" ");
	}
}
