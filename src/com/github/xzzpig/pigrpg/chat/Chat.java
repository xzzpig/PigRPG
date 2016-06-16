package com.github.xzzpig.pigrpg.chat;

import com.github.xzzpig.pigrpg.User;

public class Chat {
	static boolean muteall = false;

	public static boolean isMuteAll() {
		return muteall;
	}
	public static void setMuteAll(boolean b) {
		muteall = b;
	}

	User user;

	boolean mute = false;

	public Chat(User user) {
		this.user = user;
	}

	public void addAcceptChatChannel(ChatChannel c) {
		user.addAcceptChatChannel(c);
	}

	public void delAcceptChatChannel(ChatChannel c) {
		user.delAcceptChatChannel(c);
	}

	public ChatChannel getChatchannel() {
		return user.getChatchannel();
	}

	public String getJustSay() {
		return user.getJustSay();
	}

	public boolean isAcceptChatChannel(ChatChannel c) {
		return user.isAcceptChatChannel(c);
	}

	public boolean ismute() {
		return (this.mute || muteall);
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

	public void sendChatMessage(User fromuser) {
		user.sendChatMessage(fromuser);
	}

	public void setChatchannel(ChatChannel chatchannel) {
		user.setChatchannel(chatchannel);
	}

	public void setJustSay(String justsay) {
		user.setJustSay(justsay);
	}

	public void setSelfChat() {
		user.setSelfChat();
	}

	public void setSelfChat(User target) {
		user.setSelfChat(target);
	}

}
