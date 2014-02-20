package pl.asie.tweaks.tweaks;

import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.block.BlockFluidDye;
import pl.asie.tweaks.block.ItemBlockFluidDye;

public class TweakAddLiquidDyes extends TweakBase {
	@Override
	public String getConfigKey() {
		return "addLiquidDyes";
	}

	@Override
	public boolean isCompatible() {
		return true;
	}
	
	@Override
	public boolean getDefaultConfigOption() {
		return true;
	}

	@Override
	public void onPreInit() {
		int startID = 1100 - 16;
		for(int i = 0; i < 16; i++) {
			BlockFluidDye bfd = new BlockFluidDye(AsieTweaks.instance.config.getBlock("liquidDye"+i, startID+i).getInt(), BlockFluidDye.createFluid(i), i);
			GameRegistry.registerBlock(bfd, ItemBlockFluidDye.class, bfd.getUnlocalizedName());
		}
	}
	
	@Override
	public void onInit() {
		BlockFluidDye.registerRecipes();
	}
}
