package pl.asie.tweaks.tweaks;

import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.api.ITweak;
import pl.asie.tweaks.item.ItemDirtyDust;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;
import rebelkeithy.mods.metallurgy.api.OreType;
import cpw.mods.fml.common.Loader;

public class TweakCompatMetallurgyMekanism extends ITweak {
	private ItemDirtyDust dirtyDust;
	
	@Override
	public boolean getDefaultConfigOption() {
		return true;
	}
	
	@Override
	public String getConfigKey() {
		return "compatibilityMetallurgyMekanism";
	}
	
	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("Metallurgy3Core") && Loader.isModLoaded("Mekanism");
	}
	
	public void onPreInit() {
		dirtyDust = new ItemDirtyDust(AsieTweaks.instance.config.getItem("mekanismDirtyDust", 24899).getInt());
	}
	
	public void onInit() {
		for(String group : MetallurgyAPI.getMetalSetNames()) {
			if(group.equals("vanilla")) continue;
			for(IOreInfo ore : MetallurgyAPI.getMetalSet(group).getOreList().values()) {
				if(ore.getType() == OreType.ORE || ore.getType() == OreType.CATALYST)
					dirtyDust.addOre(group, ore);
			}
		}
	}
}
