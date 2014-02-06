package pl.asie.tweaks;

import java.util.Map;

import pl.asie.tweaks.skin.SkinClassTransformer;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class AsieTweaksLoadingPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{SkinClassTransformer.class.getName()};
	}
	
	@Override
	public String[] getLibraryRequestClass() { return null; }
	
	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}
}
