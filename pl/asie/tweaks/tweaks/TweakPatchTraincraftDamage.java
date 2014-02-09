package pl.asie.tweaks.tweaks;

import pl.asie.tweaks.api.ITweak;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TweakPatchTraincraftDamage extends ITweak {
	@Override
	public String getConfigKey() {
		// TODO Auto-generated method stub
		return "fixTraincraftHitboxDamage";
	}

	@Override
	public boolean isCompatible() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(new TweakPatchTraincraftDamage());
	}
	
	@ForgeSubscribe
	public void onEntityDamage(LivingHurtEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer ep = (EntityPlayer)event.entity;
			if(ep.ridingEntity != null && ep.ridingEntity.getEntityName().indexOf("TC") >= 0 && event.source.getDamageType().indexOf("inWall") >= 0) {
				event.setCanceled(true);	
				event.ammount = 0.0F;
			}
		}
	}
}
