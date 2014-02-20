package pl.asie.tweaks.block;

import net.minecraftforge.fluids.Fluid;

public class FluidDye extends Fluid {
	private int color;
	
	public FluidDye(String fluidName, int color) {
		super(fluidName);
		this.color = color;
	}
	
	@Override
	public int getColor() {
		return color;
	}
}
