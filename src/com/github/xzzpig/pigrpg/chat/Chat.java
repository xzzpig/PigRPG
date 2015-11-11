package com.github.xzzpig.pigrpg.chat;
import com.github.xzzpig.pigrpg.*;

public class Chat
{
	User user;
	
	public Chat(User user){
		this.user = user;
	}
	
	public boolean isAcceptChatChannel(ChatChannel c){
		return user.isAcceptChatChannel(c);
	}
	
	public void addAcceptChatChannel(ChatChannel c){
		user.addAcceptChatChannel(c);
	}
	public void delAcceptChatChannel(ChatChannel c){
		user.delAcceptChatChannel(c);
	}
	
	public void setChatchannel(ChatChannel chatchannel)
	{
		user.setChatchannel(chatchannel);
	}
	public ChatChannel getChatchannel()
	{
		return user.getChatchannel();
	}
	
	public void setJustSay(String justsay)
	{
		user.setJustSay(justsay);
	}
	public String getJustSay()
	{
		return user.getJustSay();
	}
	
	public void setSelfChat(User target){
		user.setSelfChat(target);
	}
	public void setSelfChat(){
		user.setSelfChat();
	}

	public void sendChatMessage(User fromuser){
		user.sendChatMessage(fromuser);
	}
} 
