package com.github.xzzpig.pigrpg.chat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ChatChannel
{
	All("全体频道"),
	World("世界频道"),
	Team("队伍频道"),
	Friend("好友频道"),
	Self("私聊"),
	Disable();
	
	private String name = "";
	
	ChatChannel(){}
	ChatChannel(String name){
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
	public static final List<ChatChannel> DefList(){
		return new ArrayList<ChatChannel>(Arrays.asList(ChatChannel.values()));
	}
	public static ChatChannel getFromName(String name){
		for(ChatChannel c:ChatChannel.values()){
			if(c.getName().equalsIgnoreCase(name)){
				return c;
			}
		}
		return ChatChannel.Disable;
	}
}
