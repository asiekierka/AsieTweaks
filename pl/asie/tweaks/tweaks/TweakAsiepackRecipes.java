package pl.asie.tweaks.tweaks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import pl.asie.tweaks.util.CrossMod;

public class TweakAsiepackRecipes extends TweakBaseConfig {
	public TweakAsiepackRecipes() {
		super();
		removeBlock("Artifice", "blockFrame");
		removeBlock("Artifice", "blockGlassWall");
		removeBlock("Artifice", "blockScaffold");
		removeBlock("BuildCraft", "miningWell.id");
		removeBlock("Mekanism", "MachineBlock", 8); // Metallurgic Infuser
	}
	
	@Override
	public String getConfigKey() {
		return "asiepackRecipes";
	}

	@Override
	public boolean isCompatible() {
		return true;
	}

	@Override
	public void onPostRecipe() {
		// Artifice frames
		Block frame = getBlock("Artifice", "blockFrame");
		if(frame != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 0), "oso", "s s", "oso", 'o', "plankWood", 's', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 1), "oso", "s s", "oso", 'o', "ingotTin", 's', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 2), "oso", "s s", "oso", 'o', "ingotIron", 's', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 3), "oso", "s s", "oso", 'o', "ingotSteel", 's', "stickWood"));

			Block glassWall = getBlock("Artifice", "blockGlassWall");
			if(glassWall != null) {
				for(int i = 0; i < 4; i++)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glassWall, i > 1 ? 6 : 4, i), "ggg", "gfg", "ggg", 'g', "glass", 'f', new ItemStack(frame, 1, i)));
			}
			
			Block scaffold = getBlock("Artifice", "blockScaffold");
			if(scaffold != null) {
				for(int i = 0; i < 4; i++)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(scaffold, i > 1 ? 16 : 8, i), "www", " f ", "sss", 'w', "plankWood", 's', "stickWood", 'f', new ItemStack(frame, 1, i)));				
			}
			
			Block miningWell = getBlock("BuildCraft", "miningWell.id");
			if(miningWell != null)
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(miningWell, 1, 0), "srs", "sfs", "sgs", 's', "ingotSteel", 'r', Item.redstone, 'f', new ItemStack(frame, 1, 3), 'g', "gearSteel"));
		}
	}
}
