package pl.asie.tweaks.core;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileGenericPipePatch extends TileEntity implements IEnergyHandler {
	private IEnergyHandler pipe;
	
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		if(this.pipe instanceof IEnergyHandler)
			return this.pipe.extractEnergy(from, maxReceive, simulate);
		return 0;
	}

	@Override	
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(this.pipe instanceof IEnergyHandler)
			return this.pipe.extractEnergy(from, maxExtract, simulate);
		return 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return this.canPipeConnect(this.worldObj.getBlockTileEntity(this.xCoord + from.offsetX, this.yCoord + from.offsetY, this.zCoord + from.offsetZ), from.getOpposite());
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if(this.pipe instanceof IEnergyHandler)
			return this.pipe.getEnergyStored(from);
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if(this.pipe instanceof IEnergyHandler)
			return this.pipe.getMaxEnergyStored(from);
		return 0;
	}
	
	protected boolean canPipeConnect(TileEntity with, ForgeDirection side) {
		return false;
	}
}
