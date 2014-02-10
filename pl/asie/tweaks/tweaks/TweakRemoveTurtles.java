package pl.asie.tweaks.tweaks;

import java.util.List;
import java.util.HashSet;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public class TweakRemoveTurtles extends TweakBaseConfig {
	public TweakRemoveTurtles() {
		super();
		this.removeBlock("CCTurtle", "turtleBlockID");
		this.removeBlock("CCTurtle", "turtleAdvancedBlockID");
		this.removeBlock("CCTurtle", "turtleUpgradedBlockID");
	}
	
	@Override
	public String getConfigKey() {
		return "ccRemoveTurtles";
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("CCTurtle");
	}
}
