package pl.asie.tweaks.tweaks.asiepack;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import mekanism.api.RecipeHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.tweaks.TweakBaseConfig;

public class TweakAsiepackMekanism extends TweakBaseConfig {
	public TweakAsiepackMekanism() {
		super();
		
		removeBlock("Mekanism", "Generator");
		removeItem("Mekanism", "SolarPanel");
		removeBlock("Mekanism", "Transmitter", 1); // Universal Cable
		removeBlock("Mekanism", "GasTank", 100); // Gas Tank (recipe)
		removeBlock("Mekanism", "BasicBlock", 8); // Steel Casing
		removeItem("Mekanism", "ElectrolyticCore");
		removeItem("Mekanism", "AtomicCore");
		removeItem("Mekanism", "SpeedUpgrade");
		removeBlock("Mekanism", "MachineBlock", 8); // Metallurgic Infuser
		removeBlock("Mekanism", "MachineBlock", 12); // Electric Pump
		
		removeBlock("Mekanism", "MachineBlock", 4); // Digital Miner
		removeItem("Mekanism", "WalkieTalkie");
		removeItem("Mekanism", "AtomicDisassembler");
		removeItem("Mekanism", "ElectricBow");
	}
	
	@Override
	public String getConfigKey() {
		return "asiepackRecipes";
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("Mekanism");
	}

	@Override
	public void onPostRecipe() {
		RecipeHelper.addCrusherRecipe(new ItemStack(Block.furnaceIdle, 1), new ItemStack(Block.cobblestone, 5));
		
		Block mekGen = getBlock("Mekanism", "Generator");
		if(mekGen != null) {
			// Mekanism
			Item enrichedAlloy = getItem("Mekanism", "EnrichedAlloy");
			
			// Heat Generator
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekGen, 1, 0), "zzz", "wgw", "cfc", 'z', "ingotZinc", 'c', "ingotCopper", 'w', "plankWood", 'f', Block.furnaceIdle, 'g', "gearZinc"));
			
			// Solar panels
			Item solarPanel = getItem("Mekanism", "SolarPanel");
			Item energyTablet = getItem("Mekanism", "EnergyTablet");
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(solarPanel, 1, 0), "ppp", "rer", "bbb", 'b', "ingotBrass", 'e', enrichedAlloy, 'r', Item.redstone, 'p', Block.thinGlass));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekGen, 1, 1), "sss", "ege", "btb", 'b', "ingotBrass", 's', solarPanel, 't', energyTablet, 'e', enrichedAlloy, 'g', "gearBrass"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekGen, 1, 3), "pep", "pep", "iii", 'i', "ingotElectrum", 'e', enrichedAlloy, 'p', new ItemStack(mekGen, 1, 1)));
			
			// Wind turbine
			Item controlCircuit = getItem("Mekanism", "ControlCircuit");
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekGen, 1, 6), " g ", "gGg", "tct", 'c', controlCircuit, 't', energyTablet, 'g', "ingotZinc", 'G', "gearZinc"));
			
			Block mekB1 = getBlock("Mekanism", "BasicBlock");
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekB1, 1, 7), "aaa", "aba", "aaa", 'b', Block.glowStone, 'a', "ingotElectrum"));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekB1, 2, 8), " s ", "sgs", " s ", 's', "ingotSteel", 'g', "gearSteel"));
			
			Item mekAC = getItem("Mekanism", "AtomicCore");
			if(mekAC != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekAC, 1, 0), "epe", "pgp", "epe", 'e', enrichedAlloy, 'p', "ingotSteel", 'g', "gearPlatinum"));
			}
			
			Item mekEC = getItem("Mekanism", "ElectrolyticCore");
			if(mekEC != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekEC, 1, 0), "aia", "iag", "aga", 'a', enrichedAlloy, 'i', "ingotIron", 'g', "ingotGold"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekEC, 1, 0), "aga", "iag", "aia", 'a', enrichedAlloy, 'i', "ingotIron", 'g', "ingotGold"));
			}
			
			Block mekMB = getBlock("Mekanism", "MachineBlock");
			if(mekMB != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekMB, 1, 8), "ifi", "rgr", "ifi", 'i', "ingotIron", 'r', Item.redstone, 'f', Block.furnaceIdle, 'g', "gearIron"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekMB, 1, 12), " b ", "aca", "sss", 'b', Item.bucketEmpty, 'a', enrichedAlloy, 'c', new ItemStack(mekB1, 1, 8), 's', "ingotSteel"));
			}
		}
		
		Block mekTran = getBlock("Mekanism", "Transmitter");
		if(mekTran != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekTran, 6, 1), "c", "c", "c", 'c', "ingotCopper"));
		}
		
		Block mekGTank = getBlock("Mekanism", "GasTank");
		if(mekGTank != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekGTank, 1, 100), "bdb", "b b", "bbb", 'b', "ingotZinc", 'd', "ingotSilver"));
		}
		
		Item mekSU = getItem("Mekanism", "SpeedUpgrade");
		if(mekSU != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mekSU, 1, 0), " g ", "gGg", " g ", 'g', Block.glass, 'G', "gearBrass"));
		}
	}
}
