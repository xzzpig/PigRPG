package com.github.xzzpig.pigrpg.chest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.xzzpig.pigapi.bukkit.TString;
import com.github.xzzpig.pigrpg.User;
import com.github.xzzpig.pigrpg.Vars;

public class RCChest {
	public static Inventory getInventory(Player player, Player opener) {
		Inventory inv = Bukkit.createInventory(null, 9, TString.Color(5)
				+ player.getName() + "的右键菜单");
		User user = User.getUser(player);
		//玩家信息
		ItemStack iuserinfo = new ItemStack(Material.BOOK,1);
		ItemMeta imuserinfo = iuserinfo.getItemMeta();
		imuserinfo.setDisplayName(TString.Color(3) + "玩家信息");
		imuserinfo.setLore(user.getInfos());
		iuserinfo.setItemMeta(imuserinfo);
		inv.addItem(iuserinfo);
		if (Vars.enables.containsKey("Friend") && Vars.enables.get("Friend")) {
			inv.addItem(ItemForChest.AddFriend(opener.getName(),
					player.getName()));
		}
		return inv;
	}
}
