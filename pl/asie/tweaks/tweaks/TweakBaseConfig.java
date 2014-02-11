package pl.asie.tweaks.tweaks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.util.CraftingTweaker;
import pl.asie.tweaks.util.CrossMod;

public abstract class TweakBaseConfig extends TweakBase {
	private ArrayList<String> types;

	@Override
	public boolean onRecipe(List recipeList, IRecipe recipe) {
		for(String s: types) {
			ItemStack is = CrossMod.getItemStackFromConfig(s.split(",")[0], s.split(",")[1], s.split(",")[2], 1, 0, s.split(",")[1].equals("item"));
			String meta = s.split(",")[3];
			if(meta.equals("all")) {
				if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, is, true)) return true;
			} else {
				is.setItemDamage(new Integer(meta).intValue());
				if(CraftingTweaker.removeOutputRecipe(recipeList, recipe, is, false)) return true;
			}
		}
		return false;
	}
	
	public TweakBaseConfig() {
		types = new ArrayList<String>();
	}
	
	public void removeType(String mod, String category, String name, int meta) {
		types.add(mod + "," + category + "," + name + "," + meta);
	}
	
	public void removeType(String mod, String category, String name) {
		types.add(mod + "," + category + "," + name + ",all");
	}
	
	public void removeBlock(String mod, String name) {
		removeType(mod, "block", name);
	}
	
	public void removeBlock(String mod, String name, int meta) {
		removeType(mod, "block", name, meta);
	}
	
	public void removeItem(String mod, String name) {
		removeType(mod, "item", name);
	}
	
	public void removeItem(String mod, String name, int meta) {
		removeType(mod, "item", name, meta);
	}
	
	private ItemStack getItemStack(String mod, String category, String name, int stackSize, int metadata) {
		return CrossMod.getItemStackFromConfig(mod, category, name, stackSize, metadata, category.equals("item"));
	}
	
	public Block getBlock(String mod, String name) {
		ItemStack is = getItemStack(mod, "block", name, 1, 0);
		if(is != null && is.getItem() instanceof ItemBlock) {
			return Block.blocksList[((ItemBlock)is.getItem()).itemID];
		} else return null;
	}
	
	public Item getItem(String mod, String name) {
		ItemStack is = getItemStack(mod, "item", name, 1, 0);
		if(is != null) return is.getItem();
		else return null;
	}
	
	@Override
	public boolean getDefaultConfigOption() {
		return false;
	}
}
