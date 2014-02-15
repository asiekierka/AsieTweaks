package pl.asie.tweaks.tweaks;

import cpw.mods.fml.common.Loader;
import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.item.mekanism.ItemGear;

public class TweakNewGears extends TweakBaseConfig {
	public static ItemGear gear;
	
	public TweakNewGears() {
		super();
		removeItem("BuildCraft", "diamondGear");
		removeItem("BuildCraft", "goldenGear");
		removeItem("BuildCraft", "ironGear");
		removeItem("BuildCraft", "stoneGear");
		removeItem("BuildCraft", "woodenGear");
	}
	
	@Override
	public String getConfigKey() {
		return "newGears";
	}

	@Override
	public boolean isCompatible() {
		return Loader.isModLoaded("Metallurgy3Core");
	}
	
	@Override
	public boolean getDefaultConfigOption() {
		return true;
	}

	public void onPreInit() {
		gear = new ItemGear(AsieTweaks.instance.config.getItem("gear", 24895).getInt());
	}
	
	public void onInit() {
		gear.addGear(0xE3C871, "Wooden Gear", "gearWood", null, "stickWood");
		
		// Progression 1: Machines
		gear.addGear(0xC0C0C0, "Tin Gear", "gearTin", "gearWood", "ingotTin");
		gear.addGear(0xD9D9D9, "Iron Gear", "gearIron", "gearTin", "ingotIron");
		gear.addGear(0xA0A0A0, "Steel Gear", "gearSteel", "gearIron", "ingotSteel");
		gear.addGear(0xBBDADE, "Platinum Gear", "gearPlatinum", "gearSteel", "ingotPlatinum");
		
		// Progression 2: Cabling
		gear.addGear(0xE26313, "Copper Gear", "gearCopper", "gearWood", "ingotCopper");
		gear.addGear(0xDCE0A1, "Zinc Gear", "gearZinc", "gearCopper", "ingotZinc");
		gear.addGear(0xFFFF8B, "Golden Gear", "gearGold", "gearZinc", "ingotGold");
		gear.addGear(0xDECFAA, "Electrum Gear", "gearElectrum", "gearGold", "ingotElectrum");
		
		// Alloy-plated gears
		gear.addGear(0xD99A3B, "Brass Gear", "gearBrass", "gearCopper", "ingotBrass");
		gear.addGear(0xD28844, "Bronze Gear", "gearBronze", "gearCopper", "ingotBronze");
		gear.addGear(0xDCDFA3, "Zinc Gear", "gearZinc", "gearWood", "ingotZinc");
		gear.addGear(0xE5E5E5, "Silver Gear", "gearSilver", "gearIron", "ingotSilver");
	}
}
