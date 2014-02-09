package pl.asie.tweaks.api;

import java.util.List;

import net.minecraft.item.crafting.IRecipe;

public abstract class ITweak {
	public abstract String getConfigKey();
	public boolean getDefaultConfigOption() {
		return false;
	}
	public abstract boolean isCompatible();
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		return false;
	}
	public void onPreRecipe() {
		
	}
	public void onPreInit() {
		
	}
	public void onInit() {
		
	}
	public void onPostRecipe() {
		
	}
}
