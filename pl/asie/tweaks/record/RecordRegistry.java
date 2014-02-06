package pl.asie.tweaks.record;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.item.ItemCustomRecord;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.resources.Resource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

public class RecordRegistry {
	private int currentID;
	private int startID;
	
	private ArrayList<ItemCustomRecord> records = new ArrayList<ItemCustomRecord>();
	
	public RecordRegistry(Configuration config, File configDirectory) {
		currentID = config.getItem("customRecordIDStart", 24900).getInt();
		startID = currentID;
		File recordsDirectory = new File(configDirectory, "records");
		if(recordsDirectory.isDirectory()) {
			for(File file: recordsDirectory.listFiles()) {
				if(!file.getName().endsWith(".json") || file.isDirectory()) continue;
				try {
					loadJSONFile(new FileInputStream(file));
				} catch(Exception e) { e.printStackTrace(); }
			}
		}
	}
	
	public void loadJSONFile(InputStream is) {
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<List<RecordData>>(){}.getType();
			List<RecordData> records = gson.fromJson(new InputStreamReader(is), type);
			for(RecordData record: records) {
				AsieTweaks.log.info("Loading " + record.getSoundName() + " as ID " + currentID);
				record.setSoundPrefix("customrecords");
				addRecord(record);
			}
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public void addDungeonLoot() {
		int rarity = (int)Math.ceil(75.0F / records.size());
		for(ItemCustomRecord record: records) {
			WeightedRandomChestContent item = new WeightedRandomChestContent(new ItemStack(record, 1), rarity, 1, 1);
			if(record.data.spawnInDungeons) {
				ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, item);
				ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, item);
			}
			if(record.data.spawnInPyramids) {
				ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, item);
				ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, item);
			}
			if(record.data.spawnInStrongholds) {
				ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, item);
			}
			if(record.data.spawnInVillages) {
				ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, item);
			}
		}
	}
	
	public int getStartID() { return startID; }
	public int getRecordCount() { return currentID - startID; }
	
	public void addRecord(RecordData record) {
		ItemCustomRecord itemRecord = new ItemCustomRecord(currentID, record);
		GameRegistry.registerItem(itemRecord, "record");
		records.add(itemRecord);
		currentID++;
	}
	
	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent event) {
		if(!AsieTweaks.proxy.isClient()) return;
		SoundManager manager = event.manager;
		for(ItemCustomRecord record: records) {
			manager.addStreaming(record.data.getSoundName()+".ogg");
		}
	}
}
