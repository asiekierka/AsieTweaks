package pl.asie.tweaks.tweaks;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mekanism.api.RecipeHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.util.CrossMod;

public class TweakAsiepackRecipes extends TweakBaseConfig {
	public TweakAsiepackRecipes() {
		super();
		removeBlock("Artifice", "blockFrame");
		removeBlock("Artifice", "blockGlassWall");
		removeBlock("Artifice", "blockScaffold");
		removeBlock("BuildCraft", "miningWell.id");
		removeBlock("BuildCraft", "quarry.id");
		removeBlock("BuildCraft", "hopper.id");
		removeBlock("BuildCraft", "filler.id");
		removeBlock("BuildCraft", "engine.id", 1);
		removeBlock("BuildCraft", "engine.id", 2);
		removeBlock("BuildCraft", "refinery.id");
		removeItem("BuildCraft", "pipeItemsDiamond.id");
		removeItem("BuildCraft", "pipeItemsDaizuli.id");
		removeItem("BuildCraft", "pipeItemsEmzuli.id");
		removeItem("BuildCraft", "pipeItemsEmerald.id");
		removeType("TMechworks", "logicblock", "SignalTerminal");
		removeType("TMechworks", "machines", "Redstone");
		removeType("TMechworks", "logicitem", "LengthWire");
		removeItem("immibis", "itemSaw");
		removeBlock("betterstorage", "craftingStation");
		removeBlock("immibis", "chunkloader.id");
		
		removeItem("BuildCraft", "pipeFluidsVoid.id");
		removeItem("BuildCraft", "pipeItemsVoid.id");
	}
	
	@Override
	public String getConfigKey() {
		return "asiepackRecipes";
	}

	@Override
	public boolean isCompatible() {
		return true;
	}
	
	@Override
	public void onInit() {
		LanguageRegistry l = LanguageRegistry.instance();
		l.addStringLocalization("item.asietweaks.platinumItemPipe", "Platinum Transport Pipe");
		l.addStringLocalization("item.asietweaks.angmallenItemPipe", "Angmallen Transport Pipe");
		l.addStringLocalization("item.asietweaks.electrumItemPipe", "Electrum Transport Pipe");
		l.addStringLocalization("item.asietweaks.silverFluidPipe", "Silver Fluid Pipe");
		l.addStringLocalization("item.asietweaks.silverItemPipe", "Silver Transport Pipe");
		l.addStringLocalization("item.asietweaks.saw", "Saw");
	}
	
	@Override
	public void onPostRecipe() {
		// Artifice frames
		Block frame = getBlock("Artifice", "blockFrame");
		if(frame != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 0), "oso", "s s", "oso", 'o', "plankWood", 's', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 1), "oso", "s s", "oso", 'o', "ingotTin", 's', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 2), "oso", "s s", "oso", 'o', "ingotIron", 's', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 3), "oso", "s s", "oso", 'o', "ingotSteel", 's', "stickWood"));

			Block glassWall = getBlock("Artifice", "blockGlassWall");
			if(glassWall != null) {
				for(int i = 0; i < 4; i++)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glassWall, i > 1 ? 6 : 4, i), "ggg", "gfg", "ggg", 'g', "glass", 'f', new ItemStack(frame, 1, i)));
			}
			
			Block scaffold = getBlock("Artifice", "blockScaffold");
			if(scaffold != null) {
				for(int i = 0; i < 4; i++)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(scaffold, i > 1 ? 16 : 8, i), "www", " f ", "sss", 'w', "plankWood", 's', "stickWood", 'f', new ItemStack(frame, 1, i)));				
			}
			
			Block miningWell = getBlock("BuildCraft", "miningWell.id");
			if(miningWell != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(miningWell, 1, 0), "srs", "sfs", "sgs", 's', "ingotSteel", 'r', Item.redstone, 'f', new ItemStack(frame, 1, 3), 'g', "gearSteel"));
			
				Block quarry = getBlock("BuildCraft", "quarry.id");
				if(quarry != null && AsieTweaks.instance.config.get("misc", "keepQuarries", true).getBoolean(true))
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(quarry, 1, 0), "srs", "sgs", "sms", 'g', "gearPlatinum", 's', "ingotSteel", 'r', Item.redstone, 'm', miningWell));
			}
			
			Block chute = getBlock("BuildCraft", "hopper.id");
			if(chute != null)
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chute, 1, 0), "igi", " h ", 'h', Block.hopperBlock, 'g', "gearIron", 'i', "ingotIron"));
			
			Block filler = getBlock("BuildCraft", "filler.id");
			if(filler != null)
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(filler, 1, 0), "iri", "igi", "iwi", 'i', "ingotIron", 'g', "gearIron", 'w', CrossMod.getItemStack("TConstruct", "craftingStationWood", 1, 0), 'r', Item.redstone));
			
			Item diamondPipe = getItem("BuildCraft", "pipeItemsDiamond.id");
			if(diamondPipe != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamondPipe, 1, 0), "igi", 'g', Block.glass, 'i', "ingotPlatinum"));
				CrossMod.renameItem(diamondPipe, "asietweaks.platinumItemPipe");
			}
			
			Item daizuliPipe = getItem("BuildCraft", "pipeItemsDaizuli.id");
			if(daizuliPipe != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(daizuliPipe, 1, 0), "igi", 'g', Block.glass, 'i', "ingotAngmallen"));
				CrossMod.renameItem(daizuliPipe, "asietweaks.angmallenItemPipe");
			}
			
			Item emzuliPipe = getItem("BuildCraft", "pipeItemsEmzuli.id");
			if(emzuliPipe != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(emzuliPipe, 1, 0), "igi", 'g', Block.glass, 'i', "ingotElectrum"));
				CrossMod.renameItem(emzuliPipe, "asietweaks.electrumItemPipe");
			}
			
			Item emeraldPipe = getItem("BuildCraft", "pipeItemsEmerald.id");
			if(emeraldPipe != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(emeraldPipe, 1, 0), "igi", 'g', Block.glass, 'i', "ingotSilver"));
				CrossMod.renameItem(emeraldPipe, "asietweaks.silverItemPipe");
			}
			
			Item emeraldFPipe = getItem("BuildCraft", "pipeFluidsEmerald.id");
			if(emeraldFPipe != null) {
				CrossMod.renameItem(emeraldFPipe, "asietweaks.silverFluidPipe");
			}
			
			Block engine = getBlock("BuildCraft", "engine.id");
			if(engine != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(engine, 1, 1), "iii", " g ", "kpk", 'i', "ingotIron", 'k', "gearIron", 'g', Block.glass, 'p', Block.pistonBase));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(engine, 1, 2), "iii", " g ", "kpk", 'i', "ingotSteel", 'k', "gearSteel", 'g', Block.glass, 'p', Block.pistonBase));
			}
			
			Block tank = getBlock("BuildCraft", "tank.id");
			Block refinery = getBlock("BuildCraft", "refinery.id");
			if(tank != null && refinery != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refinery, 1, 0), "rtr", "tgt", 'r', Block.redstoneLampActive, 't', new ItemStack(tank, 1, 0), 'g', "gearPlatinum"));
			}
			
			Block drawbridge = getBlock("TMechworks", "machines", "Redstone");
			if(drawbridge != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(drawbridge, 1, 0), "igi", "tdt", "trt", 't', "ingotTin", 'i', "ingotIron", 'g', "gearIron", 'd', Block.dispenser, 'r', Item.redstone));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(drawbridge, 1, 2), "g", "a", "g", 'a', new ItemStack(drawbridge, 1, 0), 'g', "gearIron"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(drawbridge, 1, 3), "g", "a", "g", 'a', new ItemStack(drawbridge, 1, 2), 'g', "gearPlatinum"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(drawbridge, 1, 1), "ygy", "tdt", "trt", 't', "ingotTin", 'i', "ingotIron", 'g', "gearIron", 'd', Item.flintAndSteel, 'y', "ingotIgnatius", 'r', Item.redstone));
			}
			
			Block signalTerm = getBlock("TMechworks", "logicblock", "SignalTerminal");
			if(signalTerm != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(signalTerm, 1, 0), "i", "t", "i", 'i', new ItemStack(getBlock("TMechworks", "logicblock", "SignalBus"), 1, 0), 't', "ingotTin"));
			}
			
			Item lengthWire = getItem("TMechworks", "logicitem", "LengthWire");
			if(lengthWire != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lengthWire, 1, 0), "b", "b", "b", 'b', "ingotBrass"));
			}
			
			Item saw = getItem("immibis", "itemSaw");
			if(saw != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(saw, 1, 0), "is", "is", "g ", 'i', "ingotIron", 's', "ingotSteel", 'g', "gearSteel"));
				CrossMod.renameItem(saw, "asietweaks.saw");
			}
			
			Block aCraftStation = getBlock("betterstorage", "craftingStation");
			if(aCraftStation != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aCraftStation, 1, 0), "cgc", "iwi", "chc", 'c', Block.cobblestone, 'g', "gearIron", 'i', "ingotIron", 'h', Block.chest, 'w', CrossMod.getItemStack("TConstruct", "craftingStationWood", 1, 0)));
			}
			
			Block chunkloader = getBlock("immibis", "chunkloader.id");
			if(chunkloader != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chunkloader, 1, 0), " g ", "gag", " g ", 'g', "gearElectrum", 'a', "ingotAtlarus"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chunkloader, 1, 0), " g ", "gag", " g ", 'g', "gearElectrum", 'a', "ingotAdamantine"));
			}
		}
	}
}
