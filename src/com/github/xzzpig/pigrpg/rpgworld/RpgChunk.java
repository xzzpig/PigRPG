package com.github.xzzpig.pigrpg.rpgworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.xzzpig.pigapi.bukkit.TConfig;
import com.github.xzzpig.pigapi.bukkit.TString;

public class RpgChunk {
	private static final Random random = new Random();
	public static HashMap<Biome, String> chbiome = new HashMap<Biome, String>();
	static {
		FileConfiguration config = TConfig.getConfigFile("PigRPG",
				"rpgworld.yml");
		for (Biome biome : Biome.values()) {
			chbiome.put(
					biome,
					config.getString("rpgworld.biomename." + biome,
							biome.name()));
			TConfig.saveConfig("PigRPG", "rpgworld.yml", "rpgworld.biomename."
					+ biome, chbiome.get(biome));
		}
	}

	private Chunk chunk;

	public RpgChunk(Chunk chunk) {
		this.chunk = chunk;
	}

	public RpgChunk change() {
		if (!isChanged())
			this.chunk.getBlock(0, 0, 0).setType(Material.CHEST);
		String name = TString.getRandomCH(Math.abs(random.nextInt(4)) + 1);
		for (Chunk ch : getNextChunks()) {
			RpgChunk rctest = new RpgChunk(ch);
			if (ch.getBlock(0, 0, 0).getBiome() == this.chunk.getBlock(0, 0, 0)
					.getBiome() && rctest.getData("name") != null) {
				name = rctest.getData("name");
				break;
			}
		}
		setData("name", name);
		// if (Debuger.isdebug) {
		// Debuger.print("change");
		// try {
		// change(chunk);
		// } catch (Exception e) {
		// }
		// }
		return this;
	}

	public int getBasicLevel() {
		Block block = this.chunk.getBlock(0, 0, 0);
		Location spawn = block.getWorld().getSpawnLocation();
		double distance = spawn.distance(block.getLocation());
		int level = (int) distance / RpgWorld.part;
		return level;
	}

	public Biome getBiome() {
		return chunk.getBlock(0, 100, 0).getBiome();
	}

	public String getData(String key) {
		ItemStack item = null;
		if (getDatas() == null)
			return null;
		for (ItemStack is : getDatas().getContents()) {
			if (is == null)
				continue;
			String displayname = is.getItemMeta().getDisplayName();
			if (displayname == null)
				continue;
			if (is.getItemMeta().getDisplayName().equalsIgnoreCase(key)) {
				item = is;
				break;
			}
		}
		if (item == null || item.getItemMeta().getLore() == null
				|| item.getItemMeta().getLore().size() == 0)
			return null;
		return item.getItemMeta().getLore().get(0);
	}

	public Inventory getDatas() {
		Block block = this.chunk.getBlock(0, 0, 0);
		if (block.getState() instanceof Chest)
			return ((Chest) block.getState()).getBlockInventory();
		return null;
	}

	public Chunk[] getNextChunks() {
		Chunk[] cs = new Chunk[4];
		Location loc = chunk.getBlock(5, 5, 5).getLocation();
		cs[0] = loc.clone().add(16, 0, 0).getChunk();
		cs[1] = loc.clone().add(-16, 0, 0).getChunk();
		cs[2] = loc.clone().add(0, 0, 16).getChunk();
		cs[3] = loc.clone().add(0, 0, -16).getChunk();
		return cs;
	}

	public boolean isChanged() {
		Block block = this.chunk.getBlock(0, 0, 0);
		if (block.getType() == Material.CHEST)
			return true;
		return false;
	}

	public RpgChunk setData(String key, String value) {
		for (ItemStack is : getDatas().getContents()) {
			if (is == null)
				continue;
			String displayname = is.getItemMeta().getDisplayName();
			if (displayname == null)
				continue;
			if (is.getItemMeta().getDisplayName().equalsIgnoreCase(key))
				is.setType(Material.AIR);
		}
		ItemStack item = new ItemStack(Material.SIGN);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(key);
		List<String> lore = new ArrayList<String>();
		lore.add(value);
		im.setLore(lore);
		item.setItemMeta(im);
		getDatas().addItem(item);
		return this;
	}

	// private void change(final Chunk c) throws Exception {
	// new Thread(new Runnable() {
	// @SuppressWarnings("deprecation")
	// public void run() {
	// for(int i1 = 0;i1<16;i1++){
	// for(int i2 = 0;i2<16;i2++){
	// c.getBlock(i1, 100, i2).setTypeId(getid());
	// }
	// }
	// }
	// }).start();
	// }
	//
	// private int getid(){
	// for(int i = 0;i<Biome.values().length;i++)
	// if(getBiome()==Biome.values()[i])
	// return i+1;
	// return 1;
	// }
}
