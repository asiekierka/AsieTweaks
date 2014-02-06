package pl.asie.tweaks.skin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;

import pl.asie.tweaks.AsieTweaks;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.TextureManager;

public class SkinHandler {
	public static HashSet<AbstractClientPlayer> players = new HashSet<AbstractClientPlayer>();
	private static boolean playerLock = false;
	
	@SideOnly(Side.CLIENT)
	public static void setup(String skinURL, String capeURL) {
		Class klazz = AbstractClientPlayer.class;
		try {
			klazz.getField("skinURL").set(null, skinURL);
			klazz.getField("capeURL").set(null, capeURL);
			AsieTweaks.instance.log.info("Skin and Cape URLs configured");
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static boolean override(AbstractClientPlayer player) {
		players.add(player);
		return true;
	}
	
	public static void invalidate(String nickname) {
		invalidate(nickname, false);
	}
	
	private static void invalidate(AbstractClientPlayer player) {
		// Fiddle with TextureManager
		TextureManager tm = Minecraft.getMinecraft().getTextureManager();
		Field f = null;
		Object o = null;
		try { f = tm.getClass().getDeclaredField("mapTextureObjects"); }
		catch(Exception e) {
			try { f = tm.getClass().getDeclaredField("a"); }
			catch(Exception ee) { System.out.println("TextureManager field not found! Ignoring..."); f = null; }
		}
		if(f != null) { 
			f.setAccessible(true);
			try {
				o = f.get(tm);
			} catch(Exception e) { e.printStackTrace(); return; }
			if(o != null && o instanceof Map) {
				Map m = (Map)o;
				m.remove(player.getLocationSkin());
				m.remove(player.getLocationCape());
			}
		}
		// Reload
		try { AbstractClientPlayer.class.getMethod("setupCustomSkin").invoke(player); }
		catch(Exception e) {
			try { AbstractClientPlayer.class.getMethod("l").invoke(player); }
			catch(Exception ee) { ee.printStackTrace(); }
		}
	}
	
	private static AbstractClientPlayer[] getPlayerCopy() {
		return players.toArray(new AbstractClientPlayer[players.size()]);
	}
	
	public static void invalidate(String nickname, boolean ignoreCase) {
		AbstractClientPlayer[] players = getPlayerCopy();
		for(AbstractClientPlayer player: players) {
			if(player == null) continue;
			synchronized(player) {
				if(player.username.equals(nickname) || (ignoreCase && player.username.equalsIgnoreCase(nickname))) {
					invalidate(player);
				}
			}
		}
	}
	public static void invalidateAll() {
		AbstractClientPlayer[] players = getPlayerCopy();
		for(AbstractClientPlayer player: players) {
			if(player == null) continue;
			synchronized(player) {
				invalidate(player);
			}
		}
	}
}
