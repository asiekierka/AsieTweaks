package pl.asie.tweaks.item.mekanism;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import rebelkeithy.mods.metallurgy.api.IOreInfo;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemGear extends Item {
	private String[] names;
	private int[] colors;
	private int arrayLength = 0;
	
	public ItemGear(int id) {
		super(id);
		names = new String[64];
		colors = new int[64];
		this.setTextureName("asietweaks:gear_generic");
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	public int addGear(int color, String name, String recipeName, String recipeBase, String recipeMaterial) {
		LanguageRegistry.instance().addStringLocalization("item.asietweaks." + recipeName + ".name", name);
		OreDictionary.registerOre(recipeName, new ItemStack(this, 1, arrayLength));
		if(recipeBase != null)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, arrayLength), " m ", "mbm", " m ", 'm', recipeMaterial, 'b', recipeBase));
		else
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, arrayLength), " m ", "m m", " m ", 'm', recipeMaterial));
		names[arrayLength] = "item.asietweaks." + recipeName;
		colors[arrayLength] = color;
		arrayLength++;
		return arrayLength-1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return colors[stack.getItemDamage() % arrayLength];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return names[stack.getItemDamage() % arrayLength];
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list) {
		for(int i = 0; i < arrayLength; i++) {
			list.add(new ItemStack(this.itemID, 1, i));
		}
	}
}
