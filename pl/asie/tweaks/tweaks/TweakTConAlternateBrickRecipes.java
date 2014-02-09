package pl.asie.tweaks.tweaks;

import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.util.CrossMod;

public class TweakTConAlternateBrickRecipes extends TweakBase {

	@Override
	public String getConfigKey() {
		return "tConAlternateBrickRecipes";
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("TConstruct");
	}
	
	@Override
	public void onPostRecipe() {
		// Obsidian bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 0), "bb", "bb", 'b', new ItemStack(Block.obsidian, 1));
		// Conversion between the two types
		GameRegistry.addShapelessRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 1, 13), new Object[]{CrossMod.getItemStack("TConstruct", "multiBrick", 1, 0)});
		GameRegistry.addShapelessRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 1, 0), new Object[]{CrossMod.getItemStack("TConstruct", "multiBrick", 1, 13)});
		// Sandstone bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 1), "bb", "bb", 'b', new ItemStack(Block.sandStone, 1, 1));
		// Iron bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 4), "bb", "bb", 'b', new ItemStack(Item.ingotIron, 1));
		// Gold bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 5), "bb", "bb", 'b', new ItemStack(Item.ingotGold, 1));
		// Lapis bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 6), "bb", "bb", 'b', new ItemStack(Item.dyePowder, 1, 4));
		// Diamond bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 7), "bb", "bb", 'b', new ItemStack(Item.diamond, 1));
		// Redstone bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 8), "bb", "bb", 'b', new ItemStack(Item.redstone, 1));
		// Bone bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 9), "bb", "bb", 'b', new ItemStack(Item.bone, 1));
		// Slime bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 11), "bb", "bb", 'b', CrossMod.getItemStack("TConstruct", "slimeGel", 1, 0));
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 10), "bb", "bb", 'b', CrossMod.getItemStack("TConstruct", "slimeGel", 1, 1));
		// Endstone bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrick", 4, 12), "bb", "bb", 'b', new ItemStack(Block.whiteStone, 1));

		// Fancy versions
		int maxFancy = 13;
		for(int meta = 0; meta <= maxFancy; meta++) {
			GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrickFancy", 4, meta), "bb", "bb", 'b', CrossMod.getItemStack("TConstruct", "multiBrick", 1, meta));
		}
		
		// Stone roads and fancy stone bricks
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrickFancy", 4, 15), "bb", "bb", 'b', new ItemStack(Block.stoneBrick, 1));
		GameRegistry.addShapedRecipe(CrossMod.getItemStack("TConstruct", "multiBrickFancy", 4, 14), "bb", "bb", 'b', CrossMod.getItemStack("TConstruct", "multiBrickFancy", 1, 15));
		
		// Polished Stone brick - smelting of stone bricks
		GameRegistry.addSmelting(Block.stoneBrick.blockID, CrossMod.getItemStack("TConstruct", "multiBrick", 1, 3), 0.0F);
	}
}
