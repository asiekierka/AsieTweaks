package pl.asie.tweaks.tweaks;

import java.util.HashSet;
import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public class TweakOpenBlocksNerf extends TweakBaseConfig {
	public TweakOpenBlocksNerf() {
		super();
		this.removeBlock("OpenBlocks", "blockCannonId");
		this.removeBlock("OpenBlocks", "blockHealId");
		this.removeBlock("OpenBlocks", "blockImaginaryId");
		this.removeBlock("OpenBlocks", "blockVillageHighlighterId");
		this.removeBlock("OpenBlocks", "blockMachineOreCrusherId");
		this.removeBlock("OpenBlocks", "blockProjectorId");
		this.removeItem("OpenBlocks", "itemCraneControl");
		this.removeItem("OpenBlocks", "itemCraneId");
		this.removeItem("OpenBlocks", "itemSlimalyzerId");
	}
	
	@Override
	public String getConfigKey() {
		return "openBlocksNerf";
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("OpenBlocks");
	}
}
