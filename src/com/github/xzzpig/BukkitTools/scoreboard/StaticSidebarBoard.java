package com.github.xzzpig.BukkitTools.scoreboard;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StaticSidebarBoard extends Board {
	private SidebarBoardType type;
	private Object data;

	public StaticSidebarBoard(String... elements) {
		this.data = elements;
		this.type = SidebarBoardType.UNRANKED;
	}

	public StaticSidebarBoard(String title, HashMap<String, Integer> elements) {
		this.data = new Object[] { title, elements };
		this.type = SidebarBoardType.UNRANKED;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void startDisplay(Player p) {
		switch (this.type) {
		case RANKED:
			ScoreboardUtil.rankedSidebarDisplay(p,
					(String) ((Object[]) data)[0],
					(HashMap<String, Integer>) ((Object[]) data)[1]);
			return;
		case UNRANKED:
			ScoreboardUtil.unrankedSidebarDisplay(p, (String[]) data);
			return;
		}

	}

	@Override
	public void stopDisplay(Player p) {
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

	public static enum SidebarBoardType {
		RANKED(), UNRANKED();
	}

}
