package pl.asie.tweaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import pl.asie.tweaks.api.ITweak;
import pl.asie.tweaks.proxy.CommonProxy;
import pl.asie.tweaks.record.RecordRegistry;
import pl.asie.tweaks.tweaks.TweakAddHorseRecipes;
import pl.asie.tweaks.tweaks.TweakCompatMetallurgyFoundry;
import pl.asie.tweaks.tweaks.TweakCompatMetallurgyMekanism;
import pl.asie.tweaks.tweaks.TweakExpensiveComputers;
import pl.asie.tweaks.tweaks.TweakOldBookRecipe;
import pl.asie.tweaks.tweaks.TweakOpenBlocksNerf;
import pl.asie.tweaks.tweaks.TweakPatchTraincraftDamage;
import pl.asie.tweaks.tweaks.TweakRemoveTurtles;
import pl.asie.tweaks.tweaks.TweakReplaceMapAtlas;
import pl.asie.tweaks.tweaks.TweakReworkCraftingTables;
import pl.asie.tweaks.tweaks.TweakTConAlternateBrickRecipes;
import pl.asie.tweaks.tweaks.TweakTConRemoveSmeltery;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid="asietweaks", name="AsieTweaks", version="0.1.0", dependencies="after:Metallurgy3Core")
@NetworkMod(channels={"AsieTweaks"}, clientSideRequired=true, packetHandler=NetworkHandler.class)
public class AsieTweaks {
	public Configuration config;
	public static Logger log;
	public static RecordRegistry records;
	
	public static String skinURL, capeURL;
	public static boolean forceTweakOverrides, disableFoodBar;
	
	@Instance(value="asietweaks")
	public static AsieTweaks instance;
	
	@SidedProxy(clientSide="pl.asie.tweaks.proxy.ClientProxy", serverSide="pl.asie.tweaks.proxy.CommonProxy")	
	public static CommonProxy proxy;
	
	public boolean isItem(String name, int defaultID) {
		int itemID = config.getItem(name, defaultID).getInt(); 
		return itemID > 0 && itemID < 65536;
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		log = Logger.getLogger("asietweaks");
		log.setParent(FMLLog.getLogger());
		
		FMLCommonHandler.instance().registerCrashCallable(new CrashWarningCallable());

		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		records = new RecordRegistry(config, event.getModConfigurationDirectory());
		proxy.registerSounds();
		
		forceTweakOverrides = config.get("tweaks", "forceOverrides", false).getBoolean(false);
		
		config.addCustomCategoryComment("skin", "Functions related to the skin changing functionality. NOTE: The URL parameters are intended for roleplay and/or NPC servers.");
    	skinURL = config.get("skin", "skinURL", "http://skins.minecraft.net/MinecraftSkins/%s.png").getString();
    	capeURL = config.get("skin", "capeURL", "http://skins.minecraft.net/MinecraftCloaks/%s.png").getString();
    	proxy.setSkin(skinURL, capeURL);
    	
		if(System.getProperty("user.dir").indexOf(".asielauncher") >= 0) {
			log.info("Hey, you! Yes, you! Thanks for using AsieLauncher! ~asie");
		}

		// Vanilla
		addTweak(new TweakAddHorseRecipes());
		addTweak(new TweakOldBookRecipe());
		
		// Compat tweaks
		addTweak(new TweakCompatMetallurgyFoundry());
		addTweak(new TweakCompatMetallurgyMekanism());
		
		// Rework tweaks
		addTweak(new TweakReplaceMapAtlas());
		addTweak(new TweakReworkCraftingTables());
		addTweak(new TweakTConAlternateBrickRecipes());
		addTweak(new TweakTConRemoveSmeltery());
		addTweak(new TweakOpenBlocksNerf());
		addTweak(new TweakRemoveTurtles());
		addTweak(new TweakExpensiveComputers());
		
		// Bugfix tweaks
		addTweak(new TweakPatchTraincraftDamage());
		
		for(ITweak tweak: tweaks) {
			tweak.onPreInit();
		}
	}
	
	ArrayList<ITweak> tweaks = new ArrayList<ITweak>();
	
	public void addTweak(ITweak tweak) {
		if(tweak.isCompatible()) {
			if(config.get("tweaks", tweak.getConfigKey(), tweak.getDefaultConfigOption()).getBoolean(tweak.getDefaultConfigOption())) {
				log.info("Added tweak " + tweak.getClass().getName());
				tweaks.add(tweak);
			}
		}
	}
	
	public void handleTweaksPostInit() {
		List recipesOriginal = CraftingManager.getInstance().getRecipeList();
		List recipes = new ArrayList(recipesOriginal);
		
		for(ITweak tweak: tweaks) {
			tweak.onPreRecipe();
		}
		
		// Remove or modify recipes
		for (Iterator<Object> itr = recipes.iterator(); itr.hasNext();) {
			Object o = itr.next();
			if (!(o instanceof IRecipe)) continue;
			IRecipe recipe = (IRecipe)o;
			for(ITweak tweak: tweaks) {
				if(tweak.onRecipe(recipesOriginal, recipe)) break; // Recipe has been removed.
			}
		}
		
		for(ITweak tweak: tweaks) {
			tweak.onPostRecipe();
		}
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerConnectionHandler(new NetworkHandler());
		
		if(config.get("misc", "enableEssentialsShoutKey", false).getBoolean(false)) {
			proxy.addShoutBinding();
		}

		LanguageRegistry lr = LanguageRegistry.instance();
		lr.addStringLocalization("commands.skinreload.usage", "/skinreload [player]");

		for(ITweak tweak: tweaks) {
			tweak.onInit();
		}
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		if(config.get("misc", "enableDevCommands", true).getBoolean(true))
			event.registerServerCommand(new CommandBlockInfo());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		handleTweaksPostInit();
		
		records.addDungeonLoot();
		
		// Initialize config entries used later
		config.get("misc", "enableDevCommands", true);
		
		config.save();
	}
}
