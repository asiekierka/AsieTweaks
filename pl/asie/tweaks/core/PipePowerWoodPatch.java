package pl.asie.tweaks.core;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

public class PipePowerWoodPatch implements IPowerReceptor, IEnergyHandler {
	private PowerHandler powerHandler;
	public TileEntity container;
	
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		System.out.println("leceive energy");
		return (int)Math.floor(this.powerHandler.useEnergy(0.0f, maxReceive / 10, !simulate) * 10);
	}
	
	public int receiveEnergy__nil(ForgeDirection from, int maxReceive,
			boolean simulate) {
		return 0;
	}

	@Override	
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(simulate)
			return (int)Math.min(Math.floor(this.powerHandler.getMaxEnergyReceived() * 10), this.getMaxEnergyStored(from) - this.getEnergyStored(from));
		else return (int)Math.floor(this.powerHandler.addEnergy(maxExtract / 10) * 10);
	}

	public int extractEnergy__nil(ForgeDirection from, int maxExtract,
			boolean simulate) {
		return 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return (int)Math.floor(this.powerHandler.getEnergyStored() * 10);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return (int)Math.floor(this.powerHandler.getMaxEnergyStored() * 10);
	}
	
	public void iWantToSayHiToRF() {
		System.out.println("Saying hi to RF");
		for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity sideEntity = container.worldObj.getBlockTileEntity(container.xCoord + o.offsetX, container.yCoord + o.offsetY, container.zCoord + o.offsetZ);
			if(sideEntity instanceof IEnergyHandler) {
				int maxRec = ((IEnergyHandler) sideEntity).extractEnergy(o.getOpposite(), (int)Math.floor(this.powerHandler.getMaxEnergyReceived() * 10), true);
				float realRec = this.powerHandler.getPowerReceiver().receiveEnergy(Type.ENGINE, maxRec / 10.0f, o.getOpposite());
				((IEnergyHandler) sideEntity).extractEnergy(o.getOpposite(), (int)Math.floor(realRec * 10), false);
			}
		}
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return null;
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		
	}

	@Override
	public World getWorld() {
		return null;
	}
	
	public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
		if(tile instanceof IEnergyHandler)
			return true;
		
		return false;
	}
	
	public void updateEnergy() {
		iWantToSayHiToRF();
	}
}
