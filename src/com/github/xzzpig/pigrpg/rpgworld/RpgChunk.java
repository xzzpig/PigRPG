package com.github.xzzpig.pigrpg.rpgworld;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

import com.github.xzzpig.BukkitTools.*;
import org.bukkit.configuration.file.*;

public class RpgChunk
{
	private static final Random random = new Random();
	public static HashMap<Biome,String> chbiome = new HashMap<Biome,String>();
	static{
		FileConfiguration config = TConfig.getConfigFile("PigRPG","rpgworld.yml");
		for(Biome biome:Biome.values()){
			chbiome.put(biome,config.getString("rpgworld.biomename."+biome,biome.name()));
			TConfig.saveConfig("PigRPG","rpgworld.yml","rpgworld.biomename."+biome,chbiome.get(biome));
		}
	}
	
	private Chunk chunk;
	public RpgChunk(Chunk chunk){
		this.chunk = chunk;
	}
	
	public RpgChunk change(){
		if(!isChanged())
			this.chunk.getBlock(1,1,1).setType(Material.CHEST);
		String name = TString.getRandomCH(random.nextInt(5));
		for(Chunk ch:getNextChunks()){
			RpgChunk rctest = new RpgChunk(ch);
			if(ch.getBlock(1,1,1).getBiome() == this.chunk.getBlock(1,1,1).getBiome()
					&&rctest.getData("name") != null){
				name = rctest.getData("name");
				break;
			}
		}
		setData("name",name);
		return this;
	}
	
	public Biome getBiome(){
		return chunk.getBlock(1,1,1).getBiome();
	}
	
	public boolean isChanged(){
		Block block = this.chunk.getBlock(1,1,1);
		if(block.getType() ==  Material.CHEST)
			return true;
		return false;
	}
	
	public Inventory getDatas(){
		Block block = this.chunk.getBlock(1,1,1);
		if(block.getState() instanceof Chest)
			return ((Chest)block.getState()).getBlockInventory();
		return null;
	}
	public String getData(String key){
		ItemStack item = null;
		if(getDatas() == null)
			return null;
		for(ItemStack is:getDatas().getContents()){
			if(is == null)
				continue;
			String displayname = is.getItemMeta().getDisplayName();
			if(displayname == null)
				continue;
			if(is.getItemMeta().getDisplayName().equalsIgnoreCase(key)){
				item = is;
				break;
			}
		}
		if(item == null||item.getItemMeta().getLore() == null||item.getItemMeta().getLore().size() == 0)
			return null;
		return item.getItemMeta().getLore().get(0);
	}
	public RpgChunk setData(String key,String value){
		for(ItemStack is:getDatas().getContents()){
			if(is == null)
				continue;
			String displayname = is.getItemMeta().getDisplayName();
			if(displayname == null)
				continue;
			if(is.getItemMeta().getDisplayName().equalsIgnoreCase(key))
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
	public Chunk[] getNextChunks(){
		Chunk[] cs = new Chunk[4];
		Location loc = chunk.getBlock(5,5,5).getLocation();
		cs[0] = loc.clone().add(16,0,0).getChunk();
		cs[1] = loc.clone().add(-16,0,0).getChunk();
		cs[2] = loc.clone().add(0,0,16).getChunk();
		cs[3] = loc.clone().add(0,0,-16).getChunk();
		return cs;
	}
}
