package micdoodle8.mods.galacticraft.core.tile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class OxygenTileEntity extends EnergyInventoryTileEntity implements ITickableTileEntity {
	private static final int TANK_CAPACITY = 1000;
	protected static final int ENERGY_USAGE = 30;

	protected int oxygenPerTick;
	protected final FluidTank oxygenTank;
	protected static int timeSinceOxygenRequest;
	private final LazyOptional<IFluidHandler> oxygenCap;

	public OxygenTileEntity(TileEntityType<?> type, int maxOxygen, int oxygenPerTick, int slots) {
		super(type, 10000, slots);
		this.oxygenTank = this.getTank();
		this.oxygenPerTick = oxygenPerTick;
		this.oxygenCap = LazyOptional.of(() -> oxygenTank);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return oxygenCap.cast();
		}

		return super.getCapability(capability, facing);
	}

	protected FluidTank getTank() {
		// TODO: currenty accepts only water
		return new FluidTank(TANK_CAPACITY, f -> f.getFluid().isIn(FluidTags.WATER)) {
			@Override
			protected void onContentsChanged() {
				OxygenTileEntity.this.markDirty();
			}
		};
	}

	public int getScaledOxygenLevel(int scale) {
		return (int) Math.floor(this.getOxygenStored() * scale / (this.getMaxOxygenStored() - this.oxygenPerTick));
	}

	public int getCappedScaledOxygenLevel(int scale) {
		return (int) Math.max(Math.min(Math.floor((double) this.oxygenTank.getFluidAmount() / (double) this.oxygenTank.getCapacity() * scale), scale), 0);
	}

//	@Override
//	public void tick() {
//
//		if(!this.world.isRemote) {
//			if(TileEntityOxygen.timeSinceOxygenRequest > 0) {
//				TileEntityOxygen.timeSinceOxygenRequest--;
//			}
//
//			if(this.energyStorage.getEnergyStored() >= ENERGY_USAGE && this.shouldUseOxygen()) {
//				if(this.oxygenTank.getFluid() != FluidStack.EMPTY) {
//					this.energyStorage.extractEnergy(ENERGY_USAGE, false);
//					this.markDirty();
//				}
//			}
//		}
//	}

	@Override
	public void read(CompoundNBT nbt) {
		super.read(nbt);
		this.oxygenTank.readFromNBT(nbt.getCompound("oxygen"));
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		super.write(nbt);
		this.oxygenTank.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public void remove() {
		this.oxygenCap.invalidate();
		super.remove();
	}

	public int getOxygenStored() {
		return this.oxygenTank.getFluidAmount();
	}

	public int getMaxOxygenStored() {
		return this.oxygenTank.getCapacity();
	}
}
