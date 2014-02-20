package pl.asie.tweaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.proxy.CommonProxy;
import pl.asie.tweaks.record.RecordRegistry;
import pl.asie.tweaks.tweaks.TweakAddHorseRecipes;
import pl.asie.tweaks.tweaks.TweakAddLiquidDyes;
import pl.asie.tweaks.tweaks.TweakCompatMetallurgyFoundry;
import pl.asie.tweaks.tweaks.TweakCompatMetallurgyMekanism;
import pl.asie.tweaks.tweaks.TweakDisableAchievements;
import pl.asie.tweaks.tweaks.TweakExpensiveComputers;
import pl.asie.tweaks.tweaks.TweakMoreRailRecipes;
import pl.asie.tweaks.tweaks.TweakNewGears;
import pl.asie.tweaks.tweaks.TweakOldBookRecipe;
import pl.asie.tweaks.tweaks.TweakPatchTraincraftDamage;
import pl.asie.tweaks.tweaks.TweakRemoveTurtles;
import pl.asie.tweaks.tweaks.TweakReplaceMapAtlas;
import pl.asie.tweaks.tweaks.TweakSimpleFoundryRecipes;
import pl.asie.tweaks.tweaks.TweakTConAlternateBrickRecipes;
import pl.asie.tweaks.tweaks.asiepack.TweakAsiepackRecipes;
import pl.asie.tweaks.tweaks.asiepack.TweakAsiepackMekanism;
import pl.asie.tweaks.tweaks.asiepack.TweakAsiepackOpenBlocks;
import pl.asie.tweaks.tweaks.asiepack.TweakAsiepackRemoveAlloyCrafting;
import pl.asie.tweaks.tweaks.asiepack.TweakAsiepackReworkCraftingTables;
import pl.asie.tweaks.tweaks.asiepack.TweakAsiepackRemoveSmeltery;
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

@Mod(modid="asietweaks", name="AsieTweaks", version="0.1.0", dependencies="after:Metallurgy3Core;after:LogisticsPipes|Main;after:BuildCraft|Core;after:BuildCraft|Silicon")
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
    	
		if(System.getProperty("user.dir").indexOf(".asielauncher") >= 0) {
			log.info("Hey, you! Yes, you! Thanks for using AsieLauncher! ~asie");
		}

		addTweak(new TweakAsiepackRecipes());
		addTweak(new TweakAsiepackRemoveAlloyCrafting());
		addTweak(new TweakAsiepackMekanism());
		addTweak(new TweakAsiepackReworkCraftingTables());
		addTweak(new TweakAsiepackRemoveSmeltery());
		addTweak(new TweakAsiepackOpenBlocks());
		
		// Vanilla
		addTweak(new TweakAddHorseRecipes());
		addTweak(new TweakOldBookRecipe());
		addTweak(new TweakDisableAchievements());
		addTweak(new TweakAddLiquidDyes());
		
		// Compat tweaks
		addTweak(new TweakCompatMetallurgyFoundry());
		addTweak(new TweakCompatMetallurgyMekanism());
		
		// Rework tweaks
		addTweak(new TweakReplaceMapAtlas());
		addTweak(new TweakTConAlternateBrickRecipes());
		addTweak(new TweakRemoveTurtles());
		addTweak(new TweakExpensiveComputers());
		addTweak(new TweakSimpleFoundryRecipes());
		
		// New content
		addTweak(new TweakNewGears());
		addTweak(new TweakMoreRailRecipes());
		
		// Bugfix tweaks
		addTweak(new TweakPatchTraincraftDamage());
		
		for(TweakBase tweak: tweaks) {
			tweak.onPreInit();
		}
	}
	
	ArrayList<TweakBase> tweaks = new ArrayList<TweakBase>();
	
	public void addTweak(TweakBase tweak) {
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
		
		for(TweakBase tweak: tweaks) {
			tweak.onPreRecipe();
		}
		
		// Remove or modify recipes
		for (Iterator<Object> itr = recipes.iterator(); itr.hasNext();) {
			Object o = itr.next();
			if (!(o instanceof IRecipe)) continue;
			IRecipe recipe = (IRecipe)o;
			for(TweakBase tweak: tweaks) {
				if(tweak.onRecipe(recipesOriginal, recipe)) break; // Recipe has been removed.
			}
		}
		
		for(TweakBase tweak: tweaks) {
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

		for(TweakBase tweak: tweaks) {
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
