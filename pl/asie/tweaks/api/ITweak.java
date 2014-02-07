package pl.asie.tweaks.api;

import java.util.List;

import net.minecraft.item.crafting.IRecipe;

public interface ITweak {
	public String getConfigKey();
	public boolean getDefaultConfigOption();
	public boolean isCompatible();
	public boolean onRecipe(List recipeList, IRecipe recipe);
	public void onPreRecipe();
	public void onInit();
	public void onPostRecipe();
}
