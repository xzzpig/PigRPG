package com.github.xzzpig.pigrpg.chat;

import com.github.xzzpig.pigrpg.User;

public class Chat {
	static boolean muteall = false;

	User user;
	boolean mute = false;

	public Chat(User user) {
		this.user = user;
	}

	public static void setMuteAll(boolean b) {
		muteall = b;
	}

	public static boolean isMuteAll() {
		return muteall;
	}

	public boolean isAcceptChatChannel(ChatChannel c) {
		return user.isAcceptChatChannel(c);
	}

	public void addAcceptChatChannel(ChatChannel c) {
		user.addAcceptChatChannel(c);
	}

	public void delAcceptChatChannel(ChatChannel c) {
		user.delAcceptChatChannel(c);
	}

	public void mute() {
		this.mute = true;
		this.setChatchannel(ChatChannel.Disable);
	}

	public void mute(boolean b) {
		this.mute = b;
		if (b)
			this.setChatchannel(ChatChannel.Disable);
		else
			this.setChatchannel(ChatChannel.All);
	}

	public boolean ismute() {
		return (this.mute || muteall);
	}

	public void setChatchannel(ChatChannel chatchannel) {
		user.setChatchannel(chatchannel);
	}

	public ChatChannel getChatchannel() {
		return user.getChatchannel();
	}

	public void setJustSay(String justsay) {
		user.setJustSay(justsay);
	}

	public String getJustSay() {
		return user.getJustSay();
	}

	public void setSelfChat(User target) {
		user.setSelfChat(target);
	}

	public void setSelfChat() {
		user.setSelfChat();
	}

	public void sendChatMessage(User fromuser) {
		user.sendChatMessage(fromuser);
	}

}
