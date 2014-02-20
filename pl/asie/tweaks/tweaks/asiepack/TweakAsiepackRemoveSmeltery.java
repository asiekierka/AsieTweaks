package pl.asie.tweaks.tweaks.asiepack;

import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public class TweakAsiepackRemoveSmeltery extends TweakBase {
	
	private static final String[] smelteryParts = {
		"smeltery", "lavaTank", "searedBlock", "castingChannel",
		"smelteryNether", "lavaTankNether", "searedBlockNether"
	};
	
	@Override
	public String getConfigKey() {
		return "asiepackRecipes";
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("TConstruct");
	}

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		for(String s: smelteryParts) {
			if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, CrossMod.getItemStack("TConstruct", s, 1, 0), true))
				return true;
		}
		return false; // Nothing removed
	}

	@Override
	public void onPostRecipe() {
		// Add missing recipes
		// - Clear Glass (smelting)
		GameRegistry.addSmelting(Block.glass.blockID, CrossMod.getItemStack("TConstruct", "clearGlass", 1, 0), 0.0F);
	}

}
