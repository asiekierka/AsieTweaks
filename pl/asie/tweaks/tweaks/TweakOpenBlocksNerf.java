package pl.asie.tweaks.tweaks;

import java.util.HashSet;
import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.ITweak;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public class TweakOpenBlocksNerf extends ITweak {
	private HashSet<ItemStack> blocks = new HashSet<ItemStack>();
	public static final String[] blockStrings = {
		"block,blockCannonId", "block,blockHealId", "block,blockImaginaryId",
		"block,blockVillageHighlighterId", "block,blockMachineOreCrusherId", "block,blockProjectorId",
		"item,itemCraneControl", "item,itemCraneId", "item,itemSlimalyzerId"
	};
	
	@Override
	public String getConfigKey() {
		return "openBlocksNerf";
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("OpenBlocks");
	}

	@Override
	public void onPreRecipe() {
		for(String s: blockStrings) {
			ItemStack is = CrossMod.getItemStackFromConfig("OpenBlocks", s.split(",")[0], s.split(",")[1], 1, 0);
			if(is != null) blocks.add(is);
		}
	}

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		for(ItemStack is: blocks) {
			if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, is, true)) return true;
		}
		return false;
	}
}
