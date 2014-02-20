package pl.asie.tweaks.proxy;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraftforge.common.MinecraftForge;
import pl.asie.tweaks.AsieTweaks;
import pl.asie.tweaks.gui.GuiAchievementHidden;
import pl.asie.tweaks.ShoutKeyHandler;

public class ClientProxy extends CommonProxy {
	@Override
	public void disableAchievements() {
		GuiAchievement test = Minecraft.getMinecraft().guiAchievement;
		if(!test.getClass().getName().equals("net.minecraft.client.gui.achievement.GuiAchievement")
				&& !AsieTweaks.forceTweakOverrides) {
			AsieTweaks.log.warning("Not overriding guiAchievement unless you set forceOverrides to true ["+test.getClass().getName()+"]");
			return;
		}
		Minecraft.getMinecraft().guiAchievement = new GuiAchievementHidden(Minecraft.getMinecraft());
	}
	
	@Override
	public void registerSounds() {
		MinecraftForge.EVENT_BUS.register(AsieTweaks.records);
	}
	
	@Override
	public boolean isClient() { return true; }
	
	public void addShoutBinding() {
		TickRegistry.registerTickHandler(new ShoutKeyHandler(), Side.CLIENT);
	}
}
