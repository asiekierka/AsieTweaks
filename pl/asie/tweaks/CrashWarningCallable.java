package pl.asie.tweaks;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import pl.asie.tweaks.api.TweakBase;
import cpw.mods.fml.common.ICrashCallable;

public class CrashWarningCallable implements ICrashCallable {

	@Override
	public String call() throws Exception {
		String crashInformation = "\n\n### WARNING! WARNING! WARNING! WARNING! WARNING! WARNING! WARNING! WARNING! WARNING! WARNING!\n###\n### DO NOT REPORT THIS CRASH TO THE MOD AUTHOR!\n###\n### REPORT THIS CRASH AT:\n### https://github.com/asiekierka/AsiePack/issues\n###\n### DO NOT MAKE OUR AWESOME MOD AUTHORS SUFFER. WE ARE HERE FOR YOU.";
		crashInformation += "\n###\n### Debug information\n### ---\n### ";
		try {
			ArrayList<String> tweaks = new ArrayList<String>();
			for(TweakBase tweak: AsieTweaks.instance.tweaks) {
				if(tweak != null) tweaks.add(tweak.getConfigKey());
			}
			crashInformation += "Tweaks applied: " + StringUtils.join(tweaks, ", ");
		} catch(Exception e) { crashInformation += "Tweaks could not be detected! " + e.getMessage(); }
		return crashInformation + "\n\n";
	}

	@Override
	public String getLabel() {
		return "AsieTweaks Warning";
	}

}
