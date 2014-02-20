package pl.asie.tweaks.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFluidDye extends ItemBlock {
	public ItemBlockFluidDye(int id) {
		super(id);
		this.setTextureName("asietweaks:dye/dye_still");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int meta) {
		return ((BlockFluidDye)Block.blocksList[this.itemID]).getColor();
	}
}
