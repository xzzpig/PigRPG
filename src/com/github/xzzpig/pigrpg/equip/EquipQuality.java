package com.github.xzzpig.pigrpg.equip;

public enum EquipQuality {
	Trash("粗糙", '7'), Common("普通", 'f'), Uncommn("优秀", 'a'), Rare("稀有", '5'), Epic(
			"史诗", 'd'), Legendary("传说", '6');
	/*
	 * &7灰色 &f白色 &a绿色 &5紫色 &d粉色 &6橙色
	 */
	private static final char c = '§';

	public static EquipQuality fromColor(String color) {
		for (EquipQuality eq : values()) {
			if (color.startsWith(eq.toString()))
				return eq;
		}
		return null;
	}
	public static EquipQuality fromName(String name) {
		for (EquipQuality eq : values()) {
			if (eq.name.equalsIgnoreCase(name))
				return eq;
		}
		return null;
	}

	private String name;

	private char color;

	EquipQuality(String name, char color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return new String(new char[] { c, color, c, 'l' });
	}
}
