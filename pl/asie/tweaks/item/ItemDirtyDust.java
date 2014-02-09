package pl.asie.tweaks.item;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemDirtyDust extends Item {
	private Icon[] icons = new Icon[256];
	private int arrayPosition = 0;
	
	public ItemDirtyDust(int id) {
		super(id);
		this.setUnlocalizedName("dirtyDust");
		this.setHasSubtypes(true);
	}
	
	public void addOre(IOreInfo ore) {
		icons[arrayPosition] = ore.getDust().getIconIndex();
		LanguageRegistry.instance().addStringLocalization("item.asietweaks.dirtyDust"+arrayPosition+".name", "Dirty " + ore.getName() + " Dust");
		arrayPosition++;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item.asietweaks.dirtyDust" + stack.getItemDamage();
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
