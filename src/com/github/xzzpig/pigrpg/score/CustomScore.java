package com.github.xzzpig.pigrpg.score;

import java.util.ArrayList;
import java.util.List;

import com.github.xzzpig.pigrpg.User;

public class CustomScore {
	public static boolean AUTO_FRESH;
	public static int FRESH_TIME;
	public static List<String> scores = new ArrayList<String>();
	public static String HEAD = "§6§l[PigRPG]";
	
	public static Thread freshthread = new FreshThread();

}

class FreshThread extends Thread {
	@Override
	public void run() {
		while (true) {
			for (User user : User.users) {
				user.buildScore();
			}
			try {
				Thread.sleep(CustomScore.FRESH_TIME);
			} catch (InterruptedException e) {
			}
		}
	}
}