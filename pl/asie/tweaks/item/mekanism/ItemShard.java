package pl.asie.tweaks.item.mekanism;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.OreDictionary;

public class ItemShard extends ItemMekanismBase {
	private String[] textureNames = new String[128];
	
	public ItemShard(int id) {
		super(id, "shard");
	}
	
	public int addOre(String category, IOreInfo ore) {
		textureNames[arrayPosition] = "asietweaks:mekanism/" + ore.getName() + "Shard";
		OreDictionary.registerOre("shard" + ore.getName().replaceAll(" ", ""), new ItemStack(this, 1, arrayPosition));
		LanguageRegistry.instance().addStringLocalization("item.asietweaks."+prefix+arrayPosition+".name", ore.getName() + " Shard");
		arrayPosition++;
		return arrayPosition-1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		for(int i = 0; i < arrayPosition; i++) {
			icons[i] = ir.registerIcon(textureNames[i]);
		}
	}
}
