package pl.asie.tweaks.tweaks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public class TweakExpensiveComputers extends TweakBase {
	@Override
	public String getConfigKey() {
		return "ccExpensiveComputers";
	}
	
	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("ComputerCraft");
	}

	private HashMap<String, ItemStack> blocks = new HashMap<String, ItemStack>();
	private String[] blockStrings = {
			"ComputerCraft,block,computerBlockID,0",
			"ComputerCraft,block,cableBlockID,0",
			"ComputerCraft,block,cableBlockID,1",
			"ComputerCraft,block,peripheralBlockID,1",
			"ComputerCraft,block,peripheralBlockID,2"
	};
	
	@Override
	public void onPreRecipe() {
		for(String s: blockStrings) {
			ItemStack is = CrossMod.getItemStackFromConfig(s.split(",")[0], s.split(",")[1], s.split(",")[2], 1, new Integer(s.split(",")[3]).intValue(), false);
			if(is != null && !blocks.containsKey(s.split(",")[2])) blocks.put(s.split(",")[2], is);
		}
	}
	
	public ItemStack withMetadata(String name, int size, int meta) {
		ItemStack is = blocks.get(name);
		if(is == null) return null;
		is = is.copy();
		is.stackSize = size;
		is.setItemDamage(meta);
		return is;
	}

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		for(ItemStack is: blocks.values()) {
			if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, is, false)) return true;
		}
		return false;
	}

	@Override
	public void onPostRecipe() {
		// Computer
		GameRegistry.addShapedRecipe(withMetadata("computerBlockID", 1, 0), "iii", "iri", "igi", 'i', Item.ingotIron, 'r', Item.redstone, 'g', Block.thinGlass);
		
		// Monitor
		GameRegistry.addShapedRecipe(withMetadata("peripheralBlockID", 4, 2), "iii", "igi", "iii", 'i', Item.ingotIron, 'g', Block.thinGlass);
		GameRegistry.addRecipe(new ShapedOreRecipe(withMetadata("peripheralBlockID", 8, 2), "iii", "igi", "iii", 'i', "ingotSteel", 'g', Block.thinGlass));
		
		// Peripheral Cable
		GameRegistry.addRecipe(new ShapedOreRecipe(withMetadata("cableBlockID", 6, 0), " s ", "srs", " s ", 's', "ingotCopper", 'r', Item.redstone));
		
		// Wireless Modem
		GameRegistry.addRecipe(new ShapedOreRecipe(withMetadata("peripheralBlockID", 1, 1), "iii", "iei", "iii", 'i', "ingotCopper", 'e', Item.enderPearl));
		
		// Wired Modem
		GameRegistry.addRecipe(new ShapedOreRecipe(withMetadata("cableBlockID", 2, 1), "iii", "iri", "iii", 'i', "ingotCopper", 'r', Item.redstone));
	}
}
