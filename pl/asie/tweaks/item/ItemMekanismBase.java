package pl.asie.tweaks.item;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemMekanismBase extends Item {
	protected Icon[] icons = new Icon[256];
	protected String prefix;
	protected int arrayPosition = 0;
	
	public ItemMekanismBase(int id, String prefix) {
		super(id);
		this.setUnlocalizedName(prefix);
		this.prefix = prefix;
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item.asietweaks." + prefix + stack.getItemDamage();
	}
	
	@Override
	public Icon getIconFromDamage(int damage) {
		return icons[damage % icons.length];
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list) {
		for(int i = 0; i < arrayPosition; i++) {
			list.add(new ItemStack(this.itemID, 1, i));
		}
	}
}
