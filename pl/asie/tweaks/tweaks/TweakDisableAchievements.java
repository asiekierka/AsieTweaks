package pl.asie.tweaks.tweaks;

import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.api.ITweak;

public class TweakDisableAchievements extends ITweak {
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
