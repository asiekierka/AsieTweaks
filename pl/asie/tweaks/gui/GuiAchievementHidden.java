package pl.asie.tweaks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.Achievement;

public class GuiAchievementHidden extends GuiAchievement {

	public GuiAchievementHidden(Minecraft par1Minecraft) {
		super(par1Minecraft);
	}
	
    @Override
    public void queueTakenAchievement(Achievement par1Achievement) { }

    @Override
    public void queueAchievementInformation(Achievement par1Achievement) { }

    @Override
    public void updateAchievementWindow() { }
}
