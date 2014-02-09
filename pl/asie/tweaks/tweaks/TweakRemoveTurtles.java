package pl.asie.tweaks.tweaks;

import java.util.List;
import java.util.HashSet;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.ITweak;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public class TweakRemoveTurtles extends ITweak {
	@Override
	public String getConfigKey() {
		return "ccRemoveTurtles";
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("CCTurtle");
	}

	private HashSet<ItemStack> turtles = new HashSet<ItemStack>();
	private String[] turtleStrings = {
			"CCTurtle,block,turtleBlockID",
			"CCTurtle,block,turtleAdvancedBlockID",
			"CCTurtle,block,turtleUpgradedBlockID"
	};
	
	@Override
	public void onPreRecipe() {
		for(String s: turtleStrings) {
			ItemStack is = CrossMod.getItemStackFromConfig(s.split(",")[0], s.split(",")[1], s.split(",")[2], 1, 0);
			if(is != null) turtles.add(is);
		}
	}

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		for(ItemStack is: turtles) {
			if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, is, true)) return true;
		}
		return false;
	}
}
