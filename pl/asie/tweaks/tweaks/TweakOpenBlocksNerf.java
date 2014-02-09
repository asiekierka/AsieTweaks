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

public class TweakOpenBlocksNerf extends TweakBaseRemoveConfig {
	public TweakOpenBlocksNerf() {
		super();
		this.addBlock("OpenBlocks", "blockCannonId");
		this.addBlock("OpenBlocks", "blockHealId");
		this.addBlock("OpenBlocks", "blockImaginaryId");
		this.addBlock("OpenBlocks", "blockVillageHighlighterId");
		this.addBlock("OpenBlocks", "blockMachineOreCrusherId");
		this.addBlock("OpenBlocks", "blockProjectorId");
		this.addItem("OpenBlocks", "itemCraneControl");
		this.addItem("OpenBlocks", "itemCraneId");
		this.addItem("OpenBlocks", "itemSlimalyzerId");
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
