package pl.asie.tweaks.tweaks.asiepack;

import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public class TweakAsiepackReworkCraftingTables extends TweakBase {
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
		if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, new ItemStack(Block.workbench), true)) return true;
		else {
			recipe = CraftingTweaker.replaceInRecipe(recipeList, recipe, new ItemStack(Block.workbench),
					CrossMod.getItemStack("TConstruct", "craftingStationWood", 1, 0), true);
			recipe = CraftingTweaker.replaceInRecipe(recipeList, recipe, "craftingTableWood",
					CrossMod.getItemStack("TConstruct", "craftingStationWood", 1, 0), true);
			return false;
		}
	}
	
	@Override
	public void onPostRecipe() {
		GameRegistry.addRecipe(new ShapedOreRecipe(CrossMod.getItemStack("TConstruct", "craftingStationWood", 1, 0), "bb", "bb", 'b', "plankWood"));
		
		if(Loader.isModLoaded("betterstorage")) {
			LanguageRegistry.instance().addStringLocalization("tile.asietweaks.automaticcraftingstation.name", "Automatic Crafting Station");
			CrossMod.renameItemStack(CrossMod.getItemStack("betterstorage", "craftingStation", 1, 0), "asietweaks.automaticcraftingstation");
		}
	}

}
