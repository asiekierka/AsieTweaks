package pl.asie.tweaks.tweaks;

import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.api.TweakBase;

public class TweakDisableAchievements extends TweakBase {
	@Override
	public String getConfigKey() {
		return "disableAchievements";
	}

	@Override
	public boolean isCompatible() {
		return true;
	}
	
	@Override
	public void onPreInit() {
		AsieTweaks.proxy.disableAchievements();
	}
}
