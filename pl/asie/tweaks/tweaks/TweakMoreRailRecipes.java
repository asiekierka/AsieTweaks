package pl.asie.tweaks.tweaks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import pl.asie.tweaks.api.TweakBase;

public class TweakMoreRailRecipes extends TweakBaseConfig {
	private static final String[] oresNormal = {
		"Copper", "Bronze", "Hepatizon", "DamascusSteel", "Angmallen"
	};
	
	private static final String[] oresPowered = {
		"Brass", "Electrum"
	};
	
	private static final float[] multNormal = { 0.25f, 0.625f, 0.875f, 1.625f, 2.0f };
	private static final float[] multPowered = { 0.5f, 1.5f };
	
	@Override
	public String getConfigKey() {
		// TODO Auto-generated method stub
		return "moreRailRecipes";
	}

	@Override
	public boolean isCompatible() {
		// TODO Auto-generated method stub
		return Loader.isModLoaded("Metallurgy3Core");
	}
	
	@Override
	public void onPostRecipe() {
		for(int i = 0; i < oresPowered.length; i++) {
			String ore = oresPowered[i];
			float mult = multPowered[i];
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.railPowered, (int)Math.round(6*mult), 0), "i i", "isi", "iri", 'i', "ingot"+ore, 'r', Item.redstone, 's', Item.stick));
		}
		
		for(int i = 0; i < oresNormal.length; i++) {
			String ore = oresNormal[i];
			float mult = multNormal[i];
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.railDetector, (int)Math.round(6*mult), 0), "i i", "isi", "iri", 'i', "ingot"+ore, 'r', Item.redstone, 's', Block.pressurePlateStone));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.railActivator, (int)Math.round(6*mult), 0), "iri", "isi", "iri", 'i', "ingot"+ore, 'r', Item.stick, 's', Block.torchRedstoneActive));

			if(mult >= 0.5f) { // Dual recipes
				Block advRail = getBlock("StevesCarts", "AdvancedDetector");
				if(advRail != null)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advRail, (int)Math.round(2*mult), 0), "iri", "isi", "iri", 'i', "ingot"+ore, 'r', Block.pressurePlateStone, 's', Item.redstone));
			}
		}
	}
}
