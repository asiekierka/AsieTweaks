package pl.asie.tweaks.tweaks;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.ITweak;

public class TweakOldBookRecipe extends ITweak {

	@Override
	public String getConfigKey() {
		return "oldBookRecipe";
	}
	
	@Override
	public boolean isCompatible() {
		return true;
	}
	
	@Override
	public void onPostRecipe() {
		GameRegistry.addShapedRecipe(new ItemStack(Item.book), "x", "x", "x", 'x', Item.paper);
	}
}
