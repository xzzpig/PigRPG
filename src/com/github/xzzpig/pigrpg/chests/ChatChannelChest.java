package com.github.xzzpig.pigrpg.chests;
import org.bukkit.inventory.*;
import org.bukkit.*;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.pigrpg.*;
import com.github.xzzpig.pigrpg.chat.*;
import java.util.*;

public class ChatChannelChest
{
	public static Inventory getAcceptInventory(User user)
	{
		Inventory inv =Bukkit.createInventory(null, 9,TString.Color(5)+ "频道接受界面");
		List<ChatChannel> list = ChatChannel.DefList();
		list.remove(ChatChannel.Disable);
		for(ChatChannel c : list){
			if(user.isAcceptChatChannel(c))
				inv.addItem(ItemForChest.customItem(TString.Color(3)+c.getName(),76,null));
			else
				inv.addItem(ItemForChest.customItem(TString.Color(3)+c.getName(),50,null));
		}
		return inv;
	}
	
	public static Inventory getChooseInventory(User user)
	{
		Inventory inv =Bukkit.createInventory(null, 9,TString.Color(5)+ "频道选择界面");
		List<ChatChannel> list = ChatChannel.DefList();
		list.remove(ChatChannel.Disable);
		list.remove(ChatChannel.Self);
		for(ChatChannel c : list){
			if(user.getChatchannel() == c)
				inv.addItem(ItemForChest.customItem(TString.Color(3)+c.getName(),76,null));
			else
				inv.addItem(ItemForChest.customItem(TString.Color(3)+c.getName(),50,null));
		}
		return inv;
	}
}
