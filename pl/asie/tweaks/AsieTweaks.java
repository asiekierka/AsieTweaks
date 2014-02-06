package pl.asie.tweaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import pl.asie.tweaks.proxy.CommonProxy;
import pl.asie.tweaks.record.RecordRegistry;
import pl.asie.tweaks.util.CraftingTweaker;
import cpw.mods.fml.client.registry.RenderingRegistry;
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

@Mod(modid="asietweaks", name="AsieTweaks", version="0.1.0")
@NetworkMod(channels={"AsieTweaks"}, clientSideRequired=true, packetHandler=NetworkHandler.class)
public class AsieTweaks {
	public Configuration config;
	public static Logger log;
	public static RecordRegistry records;
	public static HashMap<String, String[]> classNames = new HashMap<String, String[]>();
	
	static {
		classNames.put("TConstruct", new String[]{"tconstruct.common.TContent"});
		classNames.put("TMechworks", new String[]{"tmechworks.common.MechContent"});
		classNames.put("BetterStorage", new String[]{"net.mcft.copy.betterstorage.content.Tiles", "net.mcft.copy.betterstorage.content.Items"});
	}
	
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
		
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		records = new RecordRegistry(config, event.getModConfigurationDirectory());
		proxy.registerSounds();
		
		forceTweakOverrides = config.get("tweaks", "forceOverrides", false).getBoolean(false);

		if(config.get("tweaks", "disableAchievements", false).getBoolean(false))
			proxy.disableAchievements();
		
		config.addCustomCategoryComment("skin", "Functions related to the skin changing functionality. NOTE: The URL parameters are intended for roleplay and/or NPC servers.");
    	skinURL = config.get("skin", "skinURL", "http://skins.minecraft.net/MinecraftSkins/%s.png").getString();
    	capeURL = config.get("skin", "capeURL", "http://skins.minecraft.net/MinecraftCloaks/%s.png").getString();
    	proxy.setSkin(skinURL, capeURL);
    	
		if(System.getProperty("user.dir").indexOf(".asielauncher") >= 0) {
			log.info("Hey, you! Yes, you! Thanks for using AsieLauncher! ~asie");
		}
	}
	
	private ItemStack getItemStack(String modid, String name, int stackSize, int metadata) {
		 if(classNames.containsKey(modid)) { // Use classNames
			for(String classname: classNames.get(modid)) {
				try {
					Class klass = this.getClass().getClassLoader().loadClass(classname);
					if(klass != null) {
						Object o = klass.getField(name).get(null);
						if(o instanceof Block) return new ItemStack((Block)o, stackSize, metadata);
						else if(o instanceof Item) return new ItemStack((Item)o, stackSize, metadata);
						else log.warning("Could not get right object for field " + name + " in class " + classNames.get(modid));
					}
				} catch(Exception e) { e.printStackTrace(); }
			}
			// We're still here!?
			log.severe("Could not load class for mod " + modid + ", trying worst-case alternatives - PROBABLY BROKEN");
		}
		if(GameRegistry.findBlock(modid, name) != null)
			return new ItemStack(GameRegistry.findBlock(modid, name), stackSize, metadata);
		else if(GameRegistry.findItem(modid, name) != null)
			return new ItemStack(GameRegistry.findItem(modid, name), stackSize, metadata);
		else if(GameRegistry.findItemStack(modid, name, stackSize) != null) {
			ItemStack stack = GameRegistry.findItemStack(modid, name, stackSize);
			if(stack != null) stack.setItemDamage(metadata);
			return stack;
		}
		return null; // in case we already haven't
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerConnectionHandler(new NetworkHandler());
		
		if(config.get("tweaks", "oldBookRecipe", false).getBoolean(false)) {
			GameRegistry.addShapedRecipe(new ItemStack(Item.book), "x", "x", "x", 'x', Item.paper);
		}
		
		if(Loader.isModLoaded("TMechworks") && config.get("tweaks", "tMechAlternateDrawbridgeRecipes", false).getBoolean(false)) {
			GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack("TMechworks", "redstoneMachine", 1, 0), false, new Object[]{"cpc", "tdt", "trt", 'c', "ingotCopper", 't', "ingotTin", 'p', new ItemStack(Block.pistonBase, 1), 'd', new ItemStack(Block.dispenser, 1), 'r', new ItemStack(Item.redstone, 1)}));
			GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack("TMechworks", "redstoneMachine", 1, 2), false, new Object[]{"ccc", "rdr", "ttt", 'c', "ingotCopper", 't', "ingotTin", 'r', new ItemStack(Item.redstone, 1), 'd', getItemStack("TMechworks", "redstoneMachine", 1, 0)}));
		}
		
		if(Loader.isModLoaded("TConstruct") && config.get("tweaks", "tConSmeltForClearGlass", false).getBoolean(false)) {
			GameRegistry.addSmelting(Block.glass.blockID, getItemStack("TConstruct", "clearGlass", 1, 0), 0.0F);
		}
		
		if(Loader.isModLoaded("TConstruct") && config.get("tweaks", "tConAlternateBrickRecipes", false).getBoolean(false)) {
			// Obsidian bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 0), "bb", "bb", 'b', new ItemStack(Block.obsidian, 1));
			// Conversion between the two types
			GameRegistry.addShapelessRecipe(getItemStack("TConstruct", "multiBrick", 1, 13), new Object[]{getItemStack("TConstruct", "multiBrick", 1, 0)});
			GameRegistry.addShapelessRecipe(getItemStack("TConstruct", "multiBrick", 1, 0), new Object[]{getItemStack("TConstruct", "multiBrick", 1, 13)});
			// Sandstone bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 1), "bb", "bb", 'b', new ItemStack(Block.sandStone, 1, 1));
			// Iron bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 4), "bb", "bb", 'b', new ItemStack(Item.ingotIron, 1));
			// Gold bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 5), "bb", "bb", 'b', new ItemStack(Item.ingotGold, 1));
			// Lapis bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 6), "bb", "bb", 'b', new ItemStack(Item.dyePowder, 1, 4));
			// Diamond bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 7), "bb", "bb", 'b', new ItemStack(Item.diamond, 1));
			// Redstone bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 8), "bb", "bb", 'b', new ItemStack(Item.redstone, 1));
			// Bone bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 9), "bb", "bb", 'b', new ItemStack(Item.bone, 1));
			// Slime bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 11), "bb", "bb", 'b', getItemStack("TConstruct", "slimeGel", 1, 0));
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 10), "bb", "bb", 'b', getItemStack("TConstruct", "slimeGel", 1, 1));
			// Endstone bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrick", 4, 12), "bb", "bb", 'b', new ItemStack(Block.whiteStone, 1));

			// Fancy versions
			int maxFancy = 13;
			for(int meta = 0; meta <= maxFancy; meta++) {
				GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrickFancy", 4, meta), "bb", "bb", 'b', getItemStack("TConstruct", "multiBrick", 1, meta));
			}
			
			// Stone roads and fancy stone bricks
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrickFancy", 4, 15), "bb", "bb", 'b', new ItemStack(Block.stoneBrick, 1));
			GameRegistry.addShapedRecipe(getItemStack("TConstruct", "multiBrickFancy", 4, 14), "bb", "bb", 'b', getItemStack("TConstruct", "multiBrickFancy", 1, 15));
			
			// Polished Stone brick - smelting of stone bricks
			GameRegistry.addSmelting(Block.stoneBrick.blockID, getItemStack("TConstruct", "multiBrick", 1, 3), 0.0F);
		}
		
		if(config.get("misc", "fixTraincraftRandomDamage", false).getBoolean(false)) {
			MinecraftForge.EVENT_BUS.register(new TraincraftPatcher());
		}
		
		if(config.get("misc", "enableEssentialsShoutKey", false).getBoolean(false)) {
			proxy.addShoutBinding();
		}

		LanguageRegistry lr = LanguageRegistry.instance();
		lr.addStringLocalization("commands.skinreload.usage", "/skinreload [player]");
		lr.addStringLocalization("tile.asietweaks.automaticcraftingstation.name", "Automatic Crafting Station");
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		if(config.get("misc", "enableDevCommands", true).getBoolean(true))
			event.registerServerCommand(new CommandBlockInfo());
	}
	
	public static void renameItemStack(ItemStack target, String name) {
		if(target == null) return;
		Item item = target.getItem();
		item.setUnlocalizedName(name);
		if(item instanceof ItemBlock) {
			Block.blocksList[((ItemBlock)item).getBlockID()].setUnlocalizedName(name);
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		boolean replaceWorkbenchWithCStation = Loader.isModLoaded("TConstruct") && config.get("tweaks", "tConUseCraftingStationAsWorkbench", false).getBoolean(false);
		List recipesOriginal = CraftingManager.getInstance().getRecipeList();
		List recipes = new ArrayList(recipesOriginal);
		
		// Remove or modify recipes
		for (Iterator<Object> itr = recipes.iterator(); itr.hasNext();) {
			Object o = itr.next();
			if (!(o instanceof IRecipe)) continue;
			IRecipe recipe = (IRecipe)o;
			if(replaceWorkbenchWithCStation) {
				if(!CraftingTweaker.removeOutputRecipe(recipesOriginal, recipe, new ItemStack(Block.workbench), true)) {
					// Not removed
					recipe = CraftingTweaker.replaceInRecipe(recipesOriginal, recipe, new ItemStack(Block.workbench),
							getItemStack("TConstruct", "craftingStationWood", 1, 0), true);
					recipe = CraftingTweaker.replaceInRecipe(recipesOriginal, recipe, "craftingTableWood",
							getItemStack("TConstruct", "craftingStationWood", 1, 0), true);
				}
			}
		}
		
		// Add recipes
		if(replaceWorkbenchWithCStation)
			GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack("TConstruct", "craftingStationWood", 1, 0), "bb", "bb", 'b', "plankWood"));
	
		// Handle renaming
		if(Loader.isModLoaded("betterstorage") && config.get("tweaks", "renameBetterStorageCraftStationAutomatic", false).getBoolean(false)) {
			renameItemStack(getItemStack("betterstorage", "craftingStation", 1, 0), "asietweaks.automaticcraftingstation");
		}
		records.addDungeonLoot();
		
		// Initialize config entries used later
		config.get("misc", "enableDevCommands", true);
		
		config.save();
	}
}
