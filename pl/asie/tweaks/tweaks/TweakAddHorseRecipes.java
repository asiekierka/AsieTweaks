package pl.asie.tweaks.tweaks;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.ITweak;

public class TweakAddHorseRecipes implements ITweak {
	@Override
	public String getConfigKey() {
		return "addHorseRecipes";
	}

	@Override
	public boolean getDefaultConfigOption() {
		return false;
	}

	@Override
	public boolean isCompatible() {
		return true;
	}

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		return false;
	}

	@Override
	public void onPreRecipe() {
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onPostRecipe() {
		GameRegistry.addShapedRecipe(new ItemStack(Item.horseArmorDiamond, 1), "h  ", "ooo", "o o", 'h', Item.helmetDiamond, 'o', Item.diamond);
		GameRegistry.addShapedRecipe(new ItemStack(Item.horseArmorDiamond, 1), "  h", "ooo", "o o", 'h', Item.helmetDiamond, 'o', Item.diamond);
		GameRegistry.addShapedRecipe(new ItemStack(Item.horseArmorGold, 1), "h  ", "ooo", "o o", 'h', Item.helmetGold, 'o', Item.ingotGold);
		GameRegistry.addShapedRecipe(new ItemStack(Item.horseArmorGold, 1), "  h", "ooo", "o o", 'h', Item.helmetGold, 'o', Item.ingotGold);
		GameRegistry.addShapedRecipe(new ItemStack(Item.horseArmorIron, 1), "h  ", "ooo", "o o", 'h', Item.helmetIron, 'o', Item.ingotIron);
		GameRegistry.addShapedRecipe(new ItemStack(Item.horseArmorIron, 1), "  h", "ooo", "o o", 'h', Item.helmetIron, 'o', Item.ingotIron);
	}

}
