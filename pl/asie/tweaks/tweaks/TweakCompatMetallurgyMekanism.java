package pl.asie.tweaks.tweaks;

import org.apache.commons.lang3.StringUtils;

import mekanism.api.AdvancedInput;
import mekanism.api.RecipeHelper;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
import net.minecraft.item.ItemStack;
import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.api.TweakBase;
import pl.asie.tweaks.item.mekanism.ItemClump;
import pl.asie.tweaks.item.mekanism.ItemCrystal;
import pl.asie.tweaks.item.mekanism.ItemDirtyDust;
import pl.asie.tweaks.item.mekanism.ItemShard;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;
import rebelkeithy.mods.metallurgy.api.OreType;
import cpw.mods.fml.common.Loader;

public class TweakCompatMetallurgyMekanism extends TweakBase {
	private ItemDirtyDust dirtyDust;
	private ItemClump clump;
	private ItemShard shard;
	private ItemCrystal crystal;
	
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
		clump = new ItemClump(AsieTweaks.instance.config.getItem("mekanismClump", 24898).getInt());
		shard = new ItemShard(AsieTweaks.instance.config.getItem("mekanismShard", 24897).getInt());
		crystal = new ItemCrystal(AsieTweaks.instance.config.getItem("mekanismCrystal", 24896).getInt());
	}
	
	public void onInit() {
		for(String group : MetallurgyAPI.getMetalSetNames()) {
			if(group.equals("vanilla")) continue;
			for(IOreInfo ore : MetallurgyAPI.getMetalSet(group).getOreList().values()) {
				if((ore.getType() == OreType.ORE || ore.getType() == OreType.CATALYST) && ore.getDust() != null) {
					String name = ore.getName().replace(" ", "");
					name = name.substring(0, 1).toLowerCase() + name.substring(1);
					
					ItemStack isDirtyDust = new ItemStack(dirtyDust, 1, dirtyDust.addOre(group, ore));
					ItemStack isClump = new ItemStack(clump, 1, clump.addOre(group, ore));
					ItemStack isShard = new ItemStack(shard, 1, shard.addOre(group, ore));
					ItemStack isCrystal = new ItemStack(crystal, 1, crystal.addOre(group, ore));
					RecipeHelper.addEnrichmentChamberRecipe(isDirtyDust, ore.getDust());
					// 3x
					RecipeHelper.addPurificationChamberRecipe(ore.getOre(), new ItemStack(clump, 3, isClump.getItemDamage()));
					RecipeHelper.addCrusherRecipe(isClump, isDirtyDust);
					// 4x - method 1
					RecipeHelper.addPurificationChamberRecipe(isShard, isClump);
					RecipeHelper.addChemicalInjectionChamberRecipe(new AdvancedInput(ore.getOre(), GasRegistry.getGas("hydrogenChloride")), new ItemStack(shard, 4, isShard.getItemDamage()));
					// 4x - gas
					OreGas clean = new OreGas("clean" + StringUtils.capitalize(name), ore.getName());
					clean.setVisible(false);
					OreGas normal = new OreGas(name, ore.getName());
					normal.setCleanGas(clean);
					normal.setVisible(false);
					GasRegistry.register(normal);
					// 4x - method 2
					RecipeHelper.addChemicalDissolutionChamberRecipe(ore.getOre(), new GasStack(normal, 1000));
					RecipeHelper.addChemicalWasherRecipe(new GasStack(normal, 1), new GasStack(clean, 1));
					RecipeHelper.addChemicalCrystalizerRecipe(new GasStack(normal, 200), isCrystal);
				}
			}
		}
	}
}
