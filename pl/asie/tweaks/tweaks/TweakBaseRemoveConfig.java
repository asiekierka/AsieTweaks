package pl.asie.tweaks.tweaks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public abstract class TweakBaseRemoveConfig extends TweakBase {
	private ArrayList<String> types;

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		for(String s: types) {
			ItemStack is = CrossMod.getItemStackFromConfig(s.split(",")[0], s.split(",")[1], s.split(",")[2], 1, 0);
			String meta = s.split(",")[3];
			if(meta.equals("all")) {
				if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, is, true)) return true;
			} else {
				is.setItemDamage(new Integer(meta).intValue());
				if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, is, false)) return true;
			}
		}
		return false;
	}
	
	public TweakBaseRemoveConfig() {
		types = new ArrayList<String>();
	}
	
	public void addType(String mod, String category, String name, int meta) {
		types.add(mod + "," + category + "," + name + "," + meta);
	}
	
	public void addType(String mod, String category, String name) {
		types.add(mod + "," + category + "," + name + ",all");
	}
	
	public void addBlock(String mod, String name) {
		addType(mod, "block", name);
	}
	
	public void addBlock(String mod, String name, int meta) {
		addType(mod, "block", name, meta);
	}
	
	public void addItem(String mod, String name) {
		addType(mod, "item", name);
	}
	
	public void addItem(String mod, String name, int meta) {
		addType(mod, "item", name, meta);
	}
	
	@Override
	public boolean getDefaultConfigOption() {
		return false;
	}
}
