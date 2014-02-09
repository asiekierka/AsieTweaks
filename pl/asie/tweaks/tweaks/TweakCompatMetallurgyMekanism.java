package pl.asie.tweaks.tweaks;

import pl.asie.tweaks.api.ITweak;
import cpw.mods.fml.common.Loader;

public class TweakCompatMetallurgyMekanism extends ITweak {
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
}
