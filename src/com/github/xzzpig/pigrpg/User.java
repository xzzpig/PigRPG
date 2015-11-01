package com.github.xzzpig.pigrpg;

import org.bukkit.entity.*;
import java.util.*;
import com.github.xzzpig.pigrpg.chat.*;
import org.bukkit.*;
import com.github.xzzpig.pigrpg.friend.*;
import com.github.xzzpig.BukkitTools.*;

public class User
{
	private static HashMap<Player,User> userlist = new HashMap<Player,User>();
	
	private Player player;
	private User chatTarget,willChat;
	private ChatChannel chatchannel;
	private List<ChatChannel> acceptchannel;
	private String justsay = "";
	
	public User(Player player){
		this.player = player;
		userlist.put(player,this);
		autoRemove();
		chatchannel = ChatChannel.All;
		this.acceptchannel = ChatChannel.DefList();
		this.chatTarget = this;
		this.willChat = this;
	}
	
	public static User getUser(Player player){
		if(!userlist.containsKey(player))
			return new User(player);
		return userlist.get(player);
	}
	
	public void autoRemove(){
		new Thread(new Runnable(){
				@Override
				public void run()
				{
					while(userlist.containsKey(player)&&player.isOnline()){
						try
						{
							Thread.sleep(10000);
						}
						catch (InterruptedException e)
						{}
					}
					userlist.remove(player);
				}
		});
	}
	
	public boolean isAcceptChatChannel(ChatChannel c){
		if(acceptchannel.contains(c))
			return true;
		else
			return false;
	}
	public void addAcceptChatChannel(ChatChannel c){
		if(!this.isAcceptChatChannel(c))
			acceptchannel.add(c);
	}
	public void delAcceptChatChannel(ChatChannel c){
		if(this.isAcceptChatChannel(c))
			acceptchannel.remove(c);
	}
	
	public void setChatchannel(ChatChannel chatchannel)
	{
		this.chatchannel = chatchannel;
	}
	public ChatChannel getChatchannel()
	{
		return chatchannel;
	}
	
	public boolean hasFriend(String friend){
		return Friend.hasFriend(player.getName(),friend);
	}
	
	public void setJustSay(String justsay)
	{
		this.justsay = justsay;
	}
	public String getJustSay()
	{
		return justsay;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public void setSelfChat(User target){
		this.setChatchannel(ChatChannel.Self);
		this.chatTarget = target;
		target.willChat = this;
		this.sendPluginMessage("&2你进入了私聊频道，你之后的每句话将只有"+target.getPlayer().getName()+"才能看到");
		this.sendPluginMessage("&7输入/pr chat change 更换聊天频道");
		target.sendPluginMessage(this.getPlayer().getName()+"与你发起了私聊");
		target.sendPluginMessage("&7输入/pr chat self 进入私聊频道");
	}
	public void setSelfChat(){
		this.setChatchannel(ChatChannel.Self);
		this.chatTarget = this.willChat;
		User target = this.willChat;
		this.willChat = this;
		this.sendPluginMessage("&2你进入了私聊频道，你之后的每句话将只有" + target.getPlayer().getName() + "才能看到");
		this.sendPluginMessage("&7输入/pr chat change 更换聊天频道");
		target.sendPluginMessage(this.getPlayer().getName()+"与你发起了私聊");
	}
	
	public void sendChatMessage(User fromuser){
		if(!this.isAcceptChatChannel(fromuser.getChatchannel()))
			return;
		if(fromuser.getChatchannel() == ChatChannel.World&&fromuser.getPlayer().getWorld() != this.getPlayer().getWorld())
			return;
		if(fromuser.getChatchannel() == ChatChannel.Friend&&(!fromuser.hasFriend(player.getName()))&&fromuser != this)
			return;
		if(fromuser.getChatchannel() == ChatChannel.Self&&fromuser.chatTarget != this&&fromuser != this)
			return;
		String prefix = ChatColor.GREEN +"[" + fromuser.getChatchannel().getName();
		if(fromuser.getChatchannel() != ChatChannel.World)
			prefix = prefix+"_" + fromuser.getPlayer().getWorld().getName();
		prefix = prefix + "]\n\t" +ChatColor.RESET;
		if(fromuser.getPlayer().isOp())
			prefix = prefix+ChatColor.RED;
		prefix = prefix+fromuser.getPlayer().getName()+ChatColor.RESET + ":";
		this.player.sendMessage(prefix+fromuser.getJustSay());
	}
	
	public void sendPluginMessage(String message){
		this.player.sendMessage(TString.Prefix("PigRPG",3)+message.replaceAll("&","§"));
	}
}
