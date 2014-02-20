package pl.asie.tweaks.tweaks.asiepack;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import pl.asie.tweaks.api.TweakBase;

public class TweakAsiepackRemoveAlloyCrafting extends TweakBase {

	@Override
	public String getConfigKey() {
		return "asiepackRecipes";
	}

	@Override
	public boolean isCompatible() {
		return true;
	}

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		ItemStack output = recipe.getRecipeOutput();
		int oreID = OreDictionary.getOreID(output);
		if(oreID < 0) return false;
		String oreName = OreDictionary.getOreName(oreID);
		if(oreName.indexOf("dust") >= 0 || oreName.indexOf("Dust") >= 0) {
			recipeList.remove(recipe);
			return true;
		}
		return false;
	}
}
