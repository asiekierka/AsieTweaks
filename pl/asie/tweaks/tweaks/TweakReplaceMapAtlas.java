package pl.asie.tweaks.tweaks;

import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.ITweak;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public class TweakReplaceMapAtlas implements ITweak {

	@Override
	public String getConfigKey() {
		return "antiqueAtlasReplaceMap";
	}

	@Override
	public boolean getDefaultConfigOption() {
		return false;
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("antiqueatlas");
	}

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, new ItemStack(Item.emptyMap), true)) return true;
		return CraftingTweaker.removeOutputRecipe(recipeList, recipe, CrossMod.getItemStack("AntiqueAtlas", "itemEmptyAtlas", 1, 0), true);
	}

	@Override
	public void onPreRecipe() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPostRecipe() {
		LanguageRegistry lr = LanguageRegistry.instance();
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("AntiqueAtlas", "itemEmptyAtlas", 1, 0), "ppp", "pcp", "ppp", 'p', Item.paper, 'c', Item.compass);
		CrossMod.renameItemStack(CrossMod.getItemStack("AntiqueAtlas", "itemEmptyAtlas", 1, 0), "emptyAnitqueAtlas");
		CrossMod.renameItemStack(CrossMod.getItemStack("AntiqueAtlas", "itemAtlas", 1, 0), "antiqueAtlas");
		lr.addStringLocalization("item.emptyAntiqueAtlas.name", "Empty Atlas");
		lr.addStringLocalization("item.antiqueAtlas.name", "Atlas");
	}

}
