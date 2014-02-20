package pl.asie.tweaks.block;

import org.apache.commons.lang3.StringUtils;

import pl.asie.tweaks.util.CrossMod;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.api.recipe.FoundryRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class BlockFluidDye extends BlockFluidClassic {
	private Icon stillIcon;
	private Icon flowingIcon;
	private int dyeColor;
	private int dye;
	private static BlockFluidDye[] dyes = new BlockFluidDye[16];
	private static int[][] mixingRecipes = {
		{7, 15, 8}, // Light Gray
		{8, 15, 0}, // Gray
		{14, 1, 11}, // Orange
		{10, 15, 2}, // Lime
		{12, 15, 4}, // Light Blue
		{6, 4, 2}, // Cyan
		{5, 4, 1}, // Purple
		{13, 5, 9}, // Magenta <- Purple + Pink
		{13, 4, 15, 1 + (1<<8)}, // Magenta <- Blue + White + Twice Red
		{13, 4, 1, 9} // Magenta <- Lapis + Red + Pink
	};
	private static String[] dyeNames = {
		"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGrey",
		"dyeGrey", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"
	};
	
	public static Fluid createFluid(int color) {
		Fluid fluid = new FluidDye(dyeNames[color], getColorForDye(color));
		FluidRegistry.registerFluid(fluid);
		return fluid;
	}
	
	public static int getColorForDye(int dye) {
		float[] colors = EntitySheep.fleeceColorTable[15 - dye];
		return ((int)Math.round(colors[0] * 255) << 16) | ((int)Math.round(colors[1] * 255) << 8) | ((int)Math.round(colors[2] * 255));
	}
	
	public BlockFluidDye(int id, Fluid fluid, int color) {
		super(id, fluid, Material.water);
		
		dyes[color] = this;
		this.setUnlocalizedName("asietweaks.fluid" + StringUtils.capitalize(dyeNames[color]));
		this.setHardness(100.0F);
		this.setLightValue(0.0F);
		this.setCreativeTab(CreativeTabs.tabBrewing);
		this.dye = color;
		this.dyeColor = getColorForDye(this.dye);
	}
	
	public static void registerRecipes() {
		// Add melting and casting recipes
		if(Loader.isModLoaded("foundry")) {
			ItemStack moldFIngot = CrossMod.getItemStack("Foundry", "item_mold", 1, 0);
			ItemStack moldFBlock = CrossMod.getItemStack("Foundry", "item_mold", 1, 6);
			int aIngot = FoundryRecipes.FLUID_AMOUNT_INGOT;
			int aBlock = FoundryRecipes.FLUID_AMOUNT_BLOCK;
			
			for(int i = 0; i < 16; i++) {
				FoundryRecipes.casting.AddRecipe(new ItemStack(Item.dyePowder, 1, i), new FluidStack(dyes[i].getFluid(), aIngot), moldFIngot, null);

				for(ItemStack is: OreDictionary.getOres(dyeNames[i])) {
					FoundryRecipes.melting.AddRecipe(is.copy(), new FluidStack(dyes[i].getFluid(), aIngot));
				}
			}
		
			// Lapis Block
			FoundryRecipes.melting.AddRecipe(new ItemStack(Block.blockLapis, 1), new FluidStack(dyes[4].getFluid(), aBlock));
			FoundryRecipes.casting.AddRecipe(new ItemStack(Block.blockLapis, 1), new FluidStack(dyes[4].getFluid(), aBlock), moldFBlock, null);
			
			// Alloy creation recipes
			for(int[] recipe : mixingRecipes) {
				FluidStack[] input = new FluidStack[recipe.length - 1];
				int outputSize = 0;
				
				for(int i = 1; i < recipe.length; i++) {
					int size = (recipe[i] >> 8) + 1;
					outputSize += size;
					input[i - 1] = new FluidStack(dyes[(recipe[i] & 255)].getFluid(), 4 * size);
				}
				
				FoundryRecipes.alloy.AddRecipe(new FluidStack(dyes[recipe[0]].getFluid(), 4 * outputSize), input);
			}
		}
	}
	
	public int getColor() {
		return dyeColor;
	}
	
	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return dyeColor;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons (IconRegister icon) {
		this.stillIcon = icon.registerIcon("asietweaks:dye/dye_still");
		this.flowingIcon = icon.registerIcon("asietweaks:dye/dye_flow");
		
		this.getFluid().setIcons(this.stillIcon, this.flowingIcon);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon (int side, int meta) {
		return side <= 1 ? this.stillIcon : this.flowingIcon;
	}
}
