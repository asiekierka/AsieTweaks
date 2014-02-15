package pl.asie.tweaks.tweaks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class TweakSimpleFoundryRecipes extends TweakBaseConfig {
	public TweakSimpleFoundryRecipes() {
		super();

		removeItem("foundry", "container");
		removeBlock("foundry", "foundry_crucible");
		removeBlock("foundry", "foundry_machine", 0);
		removeBlock("foundry", "foundry_machine", 2);
		removeBlock("foundry", "foundry_machine", 3);
	}
	
	@Override
	public String getConfigKey() {
		// TODO Auto-generated method stub
		return "simpleFoundryRecipes";
	}

	@Override
	public boolean isCompatible() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void onPostRecipe() {
		Item foundryContainer = getItem("foundry", "container");
		if(foundryContainer != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(foundryContainer, 2, 0), " i ", "iti", " i ", 'i', "ingotTin", 't', Block.thinGlass));
		}
		
		Block foundryCasing = getBlock("foundry", "foundry_crucible"); // ITT: misleading naming
		if(foundryCasing != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(foundryCasing, 1, 0), "ici", "c c", "ici", 'i', "ingotIron", 'c', "ingotCopper"));
			Block foundryMachine = getBlock("foundry", "foundry_machine");
			if(foundryMachine != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(foundryMachine, 1, 0), "cfc", "gag", "grg", 'c', "ingotCopper", 'g', "gearTin", 'f', Block.furnaceIdle, 'r', Item.redstone, 'a', new ItemStack(foundryContainer, 1, 0)));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(foundryMachine, 1, 2), "gig", "gag", "iri", 'c', "ingotCopper", 'g', "gearTin", 'i', "ingotIron", 'r', Item.redstone, 'a', new ItemStack(foundryContainer, 1, 0)));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(foundryMachine, 1, 3), "srs", "gag", "iri", 'i', "ingotIron", 'g', "gearIron", 's', "ingotSteel", 'r', Item.redstone, 'a', new ItemStack(foundryContainer, 1, 0)));
			}
		}
	}

}
