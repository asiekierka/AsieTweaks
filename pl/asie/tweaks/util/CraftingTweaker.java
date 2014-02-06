package pl.asie.tweaks.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingTweaker {
	public static boolean equal(Object from, Object to, boolean ignoreMeta) {
		if(from == null || to == null) return false;
		else if(from instanceof ItemStack && to instanceof ItemStack) {
			ItemStack ifrom = (ItemStack)from;
			ItemStack ito = (ItemStack)to;
			return ifrom.itemID == ito.itemID && (ignoreMeta || ifrom.getItemDamage() == ito.getItemDamage());
		} else if(from instanceof String && to instanceof String) return ((String)from).equalsIgnoreCase((String)to);
		else if(from instanceof ItemStack && to instanceof String) {
			return OreDictionary.getOreID((ItemStack)from) == OreDictionary.getOreID((String)to);
		} else if(from instanceof String && to instanceof ItemStack) {
			return OreDictionary.getOreID((String)from) == OreDictionary.getOreID((ItemStack)to);			
		} else return false;
	}
	
	public static boolean removeOutputRecipe(List list, IRecipe recipe, ItemStack out, boolean ignoreMeta) {
		if(equal(out, recipe.getRecipeOutput(), ignoreMeta)) { list.remove(recipe); return true; }
		else return false;
	}
	
	public static IRecipe replaceInRecipe(List list, IRecipe recipe, Object from, Object to, boolean ignoreMeta) {
		boolean changed = false;
		IRecipe newRecipe = null;
		
		if(from instanceof ItemStack && to instanceof ItemStack) {
			if(recipe instanceof ShapedRecipes) {
				ShapedRecipes shaped = (ShapedRecipes)recipe;
				
				ItemStack[] input = new ItemStack[shaped.recipeItems.length];
				// Correct input
				for(int i = 0; i < shaped.recipeItems.length; i++) {
					if(equal(shaped.recipeItems[i], (ItemStack)from, ignoreMeta)) {
						input[i] = (ItemStack)to;
						changed = true;
					} else input[i] = shaped.recipeItems[i];
				}
				
				// Correct output
				ItemStack output = shaped.getRecipeOutput();
				if(equal(output, (ItemStack)from, ignoreMeta)) { output = (ItemStack)to; changed = true; }
				
				if(changed) newRecipe = new ShapedRecipes(shaped.recipeWidth, shaped.recipeHeight, input, output);
			} else if(recipe instanceof ShapelessRecipes) {
				ShapelessRecipes shapeless = (ShapelessRecipes)recipe;
				ArrayList input = new ArrayList();
				
				// Correct input
				for(Object o: shapeless.recipeItems) {
					if(!(o instanceof ItemStack)) { input.add(o); continue; }
					ItemStack is = (ItemStack)o;
					if(equal(is, (ItemStack)from, ignoreMeta)) {
						input.add((ItemStack)to);
						changed = true;
					} else input.add(o);
				}
				
				// Correct output
				ItemStack output = shapeless.getRecipeOutput();
				if(equal(output, (ItemStack)from, ignoreMeta)) { output = (ItemStack)to; changed = true; }
				
				if(changed) newRecipe = new ShapelessRecipes(output, input);
			}
		}
		if(recipe instanceof ShapedOreRecipe) {
			ShapedOreRecipe shaped = (ShapedOreRecipe)recipe;
			
			// Correct input - changing via array change is documented here
			Object[] input = shaped.getInput();
			for(int i = 0; i < input.length; i++) {
				if(equal(input[i], from, ignoreMeta)) input[i] = (ItemStack)to;
			}
			
			// Correct output
			ItemStack output = shaped.getRecipeOutput();
			if(to instanceof ItemStack && equal(output, from, ignoreMeta)) {
				output = (ItemStack)to;
				// Reflection!
				try {
					shaped.getClass().getField("output").set(shaped, output);
				} catch(Exception e) { e.printStackTrace(); }
			}
			
		} else if(recipe instanceof ShapelessOreRecipe) {
			ShapelessOreRecipe shapeless = (ShapelessOreRecipe)recipe;
			ArrayList input = shapeless.getInput();
			
			// Correct input
			for(int i = 0; i < input.size(); i++) {
				Object o = input.get(i);
				if(equal(o, from, ignoreMeta)) {
					input.set(i, to);
					changed = true;
				}
			}
			
			// Correct output
			ItemStack output = shapeless.getRecipeOutput();
			if(to instanceof ItemStack && equal(output, from, ignoreMeta)) { output = (ItemStack)to; changed = true; }
			
			if(changed) newRecipe = new ShapelessOreRecipe(output, input);
		}
		if(newRecipe != null) {
			list.remove(recipe);
			list.add(newRecipe);
			return newRecipe;
		} else return recipe;
	}
}
