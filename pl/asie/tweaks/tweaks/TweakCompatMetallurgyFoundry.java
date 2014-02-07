package pl.asie.tweaks.tweaks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.Loader;
import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.api.ITweak;
import pl.asie.tweaks.util.CrossMod;
import exter.foundry.api.FoundryUtils;
import exter.foundry.api.recipe.FoundryRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;

public class TweakCompatMetallurgyFoundry implements ITweak {
	private Fluid getFluid(String name) {
		String fluidName = "molten." + name.toLowerCase().replace(' ', '.');
		// Abuse behaviour: TiC-handled metals END with ".molten", not START, so those will return null
		return FluidRegistry.getFluid(fluidName);
	}

	private Fluid getFluidAny(String name) {
		String fluidName = name.toLowerCase().replace(' ', '.');
		if(name.startsWith("dust")) fluidName = fluidName.substring("dust".length());
		if(name.startsWith("ingot")) fluidName = fluidName.substring("ingot".length());
		Fluid fluid = FluidRegistry.getFluid("molten." + fluidName);
		if(fluid == null) fluid = FluidRegistry.getFluid(fluidName + ".molten");
		if(fluid == null) fluid = FluidRegistry.getFluid("liquid" + fluidName.substring(0, 1).toUpperCase() + fluidName.substring(1));
		if(fluid == null) fluid = FluidRegistry.getFluid("liquid" + fluidName);
		if(fluid == null) AsieTweaks.log.severe("Could not find fluid for " + name + "!");
		return fluid;
	}
	
	private String getPartial(String name) {
		return name.replaceAll(" ", "");
	}
	
	public String getConfigKey() {
		return "compatibilityMetallurgyFoundry";
	}
	
	public boolean isCompatible() {
		return Loader.isModLoaded("ExtraTiC") && Loader.isModLoaded("Metallurgy3Core") && Loader.isModLoaded("foundry");
	}
	
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		return false;
	}
	
	public void onPostRecipe() {
		ItemStack moldIngot = CrossMod.getItemStack("Foundry", "item_mold", 1, 0);
		ItemStack moldBlock = CrossMod.getItemStack("Foundry", "item_mold", 1, 6);
		
		for(String group : MetallurgyAPI.getMetalSetNames()) {
			if(group.equals("vanilla")) continue;
			for(IOreInfo ore : MetallurgyAPI.getMetalSet(group).getOreList().values()) {
				Fluid fluid = getFluid(ore.getName());
				if(fluid != null) { // Fluid exists, handled by ExtraTiC
					// Add melting recipes
				    FoundryRecipes.melting.AddRecipe(ore.getIngot(), new FluidStack(fluid, FoundryRecipes.FLUID_AMOUNT_INGOT));
			    	FoundryRecipes.melting.AddRecipe(ore.getDust(), new FluidStack(fluid, FoundryRecipes.FLUID_AMOUNT_INGOT));
				    if(ore.getBlock() != null)
				    	FoundryRecipes.melting.AddRecipe(ore.getBlock(), new FluidStack(fluid, FoundryRecipes.FLUID_AMOUNT_BLOCK));
				    if(ore.getOre() != null)
				    	FoundryRecipes.melting.AddRecipe(ore.getOre(), new FluidStack(fluid, FoundryRecipes.FLUID_AMOUNT_ORE));
				      
 					// Add casting recipes
 					FoundryRecipes.casting.AddRecipe(ore.getIngot(), new FluidStack(fluid, FoundryRecipes.FLUID_AMOUNT_INGOT), moldIngot, null);
 					if(ore.getBlock() != null)
 						FoundryRecipes.casting.AddRecipe(ore.getBlock(), new FluidStack(fluid, FoundryRecipes.FLUID_AMOUNT_BLOCK), moldBlock, null);
 					
 					// Add alloy recipes
 					if(ore.getAlloyRecipe() != null && ore.getAlloyRecipe().length > 0 && !(ore.getAlloyRecipe()[0].equals("dust0"))) {
 						AsieTweaks.log.finer("Adding alloy recipes for " + ore.getName() + " <- " + StringUtils.join(ore.getAlloyRecipe(), ", "));
 						FluidStack[] input = new FluidStack[ore.getAlloyRecipe().length];
 						for(int i = 0; i < ore.getAlloyRecipe().length; i++) {
 							input[i] = new FluidStack(getFluidAny(ore.getAlloyRecipe()[i]), 6);
 						}
 						FoundryRecipes.alloy.AddRecipe(new FluidStack(fluid, 6 * input.length), input);
 					}
				}
			}
		}
	}

	@Override
	public boolean getDefaultConfigOption() {
		return true;
	}

	@Override
	public void onPreRecipe() {
	}

	@Override
	public void onInit() {
	}
}
