package pl.asie.tweaks;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ShoutKeyHandler implements ITickHandler {
	private boolean isShifting;
	private boolean pressed;
	
    @Override
    public final void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        keyTick(type, false);
    }
    
    @Override
    public final void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        keyTick(type, true);
    }

	@Override
	public String getLabel() {
		return "Chat - Shout";
	}
	
	private void launchGUI() {
		Minecraft minecraft = Minecraft.getMinecraft();
		if(minecraft.currentScreen == null) {
			minecraft.displayGuiScreen(new GuiChat());
		}
	}

    private void keyTick(EnumSet<TickType> type, boolean tickEnd)
    {
		isShifting = (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT));
		if(Keyboard.isKeyDown(Keyboard.KEY_1) && isShifting && !pressed) {
			pressed = true;
			launchGUI();
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_1) || !isShifting)
			pressed = false;
    }
    
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
