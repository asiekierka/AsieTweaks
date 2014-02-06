package pl.asie.tweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TraincraftPatcher {
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
