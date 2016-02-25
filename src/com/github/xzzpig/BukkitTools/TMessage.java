package com.github.xzzpig.BukkitTools;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
import org.bukkit.entity.Player;

public class TMessage{
	
	private static String version = null;
	static{
		for(int a=1;a<=8;a++){
			for(int b=1;b<=9;b++){
				String testversion = "v1_"+a+"_R"+b;
				try{
					Class.forName("org.bukkit.craftbukkit."+testversion+".entity.CraftPlayer");
					version = testversion;
					System.out.println("MC版本为"+version+",FM将以TellRaw方式发送");
					break;
				}catch(Exception e){}
			}
		}		
		if(version==null)
			System.out.println("版本获取失败,FM将以普通方式发送");
	}

	private List<MessagePart> messageParts;
	public TMessage() {
		messageParts = new ArrayList<MessagePart>();
		messageParts.add(new MessagePart(""));
	}
	public TMessage(String firstPartText) {
		messageParts = new ArrayList<MessagePart>();
		messageParts.add(new MessagePart(firstPartText));
	}

	public TMessage color(ChatColor color) {
		if (!color.isColor()) {
			throw new IllegalArgumentException(color.name() + " is not a color");
		}
		latest().color = color;
		return this;
	}

	public TMessage style(ChatColor... styles) {
		for (ChatColor style : styles) {
			if (!style.isFormat()) {
				throw new IllegalArgumentException(style.name()
						+ " is not a style");
			}
		}
		latest().styles = styles;
		return this;
	}

	public TMessage file(String path) {
		onClick("open_file", path);
		return this;
	}

	public TMessage link(String url) {
		onClick("open_url", url);
		return this;
	}

	public TMessage suggest(String command) {
		onClick("suggest_command", command);
		return this;
	}

	public TMessage command(String command) {
		onClick("run_command", command);
		return this;
	}

	public TMessage tooltip(String text) {
		onHover("show_text", text);
		return this;
	}

	public TMessage then(Object obj) {
		messageParts.add(new MessagePart(obj.toString()));
		return this;
	}

	public String toJSONString() {
		StringWriter stringWriter = new StringWriter();
		JsonWriter json = new JsonWriter(stringWriter);

		try {
			if (messageParts.size() == 1) {
				latest().writeJson(json);
			} else {
				json.beginObject().name("text").value("").name("extra")
						.beginArray();
				for (MessagePart part : messageParts) {
					part.writeJson(json);
				}
				json.endArray().endObject();
			}

		} catch (IOException e) {
			throw new RuntimeException("invalid message");
		}
		return stringWriter.toString();
	}

	public void send(Player player) {
		if(version==null){
			player.sendMessage(this.toString());
			return;
		}
		try {
			Object EntityPlayer = Class
					.forName(
							"org.bukkit.craftbukkit."+version+".entity.CraftPlayer")
					.getMethod("getHandle").invoke(player);
			Object playerConnection = Class
					.forName(
							"net.minecraft.server."+version+".EntityPlayer")
					.getField("playerConnection").get(EntityPlayer);
			Object IChatBaseComponent = Class
					.forName(
							"net.minecraft.server."+version+".ChatSerializer")
					.getMethod("a", String.class)
					.invoke(null, toJSONString());
			Class<?> cls = Class
					.forName("net.minecraft.server."+version+".PacketPlayOutChat");
			Class<?>[] paramTypes = { Class
					.forName("net.minecraft.server."+version+".IChatBaseComponent") };
			Object[] params = { IChatBaseComponent }; // 方法传入的参数

			Constructor<?> con = cls.getConstructor(paramTypes); // 主要就是这句了
			Object PacketPlayOutChat = con.newInstance(params); // BatcherBase
																// 为自定义类
			Method sendPack = null;
			for (Method meth : Class.forName(
					"net.minecraft.server."+version+".PlayerConnection")
					.getMethods()) {
				if(meth.getName().equalsIgnoreCase("sendPacket"))
					sendPack = meth;
			}
			sendPack.invoke(playerConnection, PacketPlayOutChat);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | ClassNotFoundException
				| NoSuchFieldException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	private MessagePart latest() {
		return messageParts.get(messageParts.size() - 1);
	}

	private void onClick(String name, String data) {
		MessagePart latest = latest();
		latest.clickActionName = name;
		latest.clickActionData = data;
	}

	private void onHover(String name, String data) {
		MessagePart latest = latest();
		latest.hoverActionName = name;
		latest.hoverActionData = data;
	}

	static class MessagePart {

		public ChatColor color = null;
		public ChatColor[] styles = null;
		public String clickActionName = null;
		public String clickActionData = null;
		public String hoverActionName = null;
		public String hoverActionData = null;
		public final String text;

		public MessagePart(final String text) {
			this.text = text;
		}

		public JsonWriter writeJson(final JsonWriter json) throws IOException {
			json.beginObject().name("text").value(text);
			if (color != null) {
				json.name("color").value(color.name().toLowerCase());
			}
			if (styles != null) {
				for (final ChatColor style : styles) {
					json.name(
							style == ChatColor.UNDERLINE ? "underlined" : style
									.name().toLowerCase()).value(true);
				}
			}
			if (clickActionName != null && clickActionData != null) {
				json.name("clickEvent").beginObject().name("action")
						.value(clickActionName).name("value")
						.value(clickActionData).endObject();
			}
			if (hoverActionName != null && hoverActionData != null) {
				json.name("hoverEvent").beginObject().name("action")
						.value(hoverActionName).name("value")
						.value(hoverActionData).endObject();
			}
			return json.endObject();
		}

	}
	@Override
	public String toString() {
		String message = "";
		for(MessagePart mp:messageParts)
			message=message+ChatColor.RESET+mp.text;
		return message;
	}
}
