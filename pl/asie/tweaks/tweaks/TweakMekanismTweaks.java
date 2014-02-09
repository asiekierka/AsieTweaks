package pl.asie.tweaks.tweaks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import mekanism.api.RecipeHelper;
import cpw.mods.fml.common.Loader;
import pl.asie.tweaks.api.TweakBase;

public class TweakMekanismTweaks extends TweakBase {

	@Override
	public String getConfigKey() {
		return "mekanismTweaks";
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("Mekanism");
	}

	@Override
	public void onPostRecipe() {
		RecipeHelper.addCrusherRecipe(new ItemStack(Block.furnaceIdle, 1), new ItemStack(Block.cobblestone, 5));
	}
}
