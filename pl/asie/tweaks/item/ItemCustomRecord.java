package pl.asie.tweaks.item;

import java.util.ArrayList;
import java.util.List;

import pl.asie.tweakjunk.EntityCustomRecord;
import pl.asie.tweaks.record.RecordData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemCustomRecord extends ItemRecord {
	private Icon iconRecord, iconRecordRing;
	private final int color;
	public final RecordData data;
	
	public ItemCustomRecord(int id, String name, String sound, String color) {
		super(id, sound);
		this.setUnlocalizedName("record");
		this.setTextureName("minecraft:record_cat");
		this.color = Integer.parseInt(color.replaceAll("#", ""), 16);
		this.data = new RecordData(name, sound, color);
	}
	
	public ItemCustomRecord(int id, RecordData data) {
		super(id, data.getSoundName());
		this.setUnlocalizedName("record");
		this.setTextureName("minecraft:record_cat");
		this.color = Integer.parseInt(data.getColor().replaceAll("#", ""), 16);
		this.data = data;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        super.registerIcons(ir);
        this.iconRecord = ir.registerIcon("minecraft:record_cat"); // can be most records
        this.iconRecordRing = ir.registerIcon("asietweaks:record_ring");
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return (pass > 0 ? color : 16777215);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int damage, int pass)
    {
        return pass == 1 ? iconRecordRing : iconRecord;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        list.add(data.getName());
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public String getRecordTitle()
    {
        return data.getName();
    }
}
