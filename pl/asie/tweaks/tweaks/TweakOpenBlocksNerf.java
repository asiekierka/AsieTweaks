package pl.asie.tweaks.tweaks;

import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.ITweak;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public class TweakOpenBlocksNerf implements ITweak {
	public static final String[] removedThings = {
		"cannon", "heal", "imaginary", "villageHighlighter", "machineOreCrusher", "projector",
		"sonicGlasses", "craneControl", "craneBackpack", "slimalyzer", "heightMap", "emptyMap", "cartographer"
	};
	
	@Override
	public String getConfigKey() {
		return "openBlocksNerf";
	}

	@Override
	public boolean getDefaultConfigOption() {
		return false;
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("OpenBlocks");
	}

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		for(String s: removedThings) {
			if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, CrossMod.getItemStack("OpenBlocks", s, 1, 0), true))
				return true;
		}
		return false; // Nothing removed
	}

	@Override
	public void onPreRecipe() {
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onPostRecipe() {
	}

}
