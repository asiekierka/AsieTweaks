package pl.asie.tweaks.tweaks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
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
		removeBlock("BuildCraft", "quarry.id");
		removeBlock("BuildCraft", "hopper.id");
		removeBlock("BuildCraft", "filler.id");
		removeBlock("Mekanism", "MachineBlock", 8); // Metallurgic Infuser
		
		removeItem("BuildCraft", "pipeItemsDiamond.id");
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
	public void onInit() {
		LanguageRegistry l = LanguageRegistry.instance();
		l.addStringLocalization("item.asietweaks.platinumItemPipe.name", "Platinum Transport Pipe");
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
			if(miningWell != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(miningWell, 1, 0), "srs", "sfs", "sgs", 's', "ingotSteel", 'r', Item.redstone, 'f', new ItemStack(frame, 1, 3), 'g', "gearSteel"));
			
				Block quarry = getBlock("BuildCraft", "quarry.id");
				if(quarry != null)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(quarry, 1, 0), "srs", "sgs", "sms", 'g', "gearPlatinum", 's', "ingotSteel", 'r', Item.redstone, 'm', miningWell));
			}
			
			Block chute = getBlock("BuildCraft", "hopper.id");
			if(chute != null)
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chute, 1, 0), "igi", " h ", 'h', Block.hopperBlock, 'g', "gearIron", 'i', "ingotIron"));
			
			Block filler = getBlock("BuildCraft", "filler.id");
			if(filler != null)
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(filler, 1, 0), "iri", "igi", "iwi", 'i', "ingotIron", 'g', "gearIron", 'w', CrossMod.getItemStack("TConstruct", "craftingStationWood", 1, 0), 'r', Item.redstone));
			
			Item diamondPipe = getItem("BuildCraft", "pipeItemsDiamond.id");
			if(diamondPipe != null) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamondPipe, 1, 0), "igi", 'g', Block.glass, 'i', "ingotPlatinum"));
				CrossMod.renameItem(diamondPipe, "asietweaks.platinumItemPipe");
			}
		}
	}
}
