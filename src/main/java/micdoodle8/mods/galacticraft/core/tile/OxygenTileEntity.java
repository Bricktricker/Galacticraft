package micdoodle8.mods.galacticraft.core.tile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class OxygenTileEntity extends EnergyInventoryTileEntity implements ITickableTileEntity {
	private static final int TANK_CAPACITY = 1000;
	protected static final int ENERGY_USAGE = 30;

	protected final FluidTank oxygenTank;
	private final LazyOptional<IFluidHandler> oxygenCap;
	
	protected IIntArray containerStats = new IIntArray() {
		
		@Override
		public int size() {
			return 2;
		}
		
		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0: OxygenTileEntity.this.energyStorage.setEnergy(value); break;
				case 1: OxygenTileEntity.this.oxygenTank.getFluid().setAmount(value); break;
			}
		}
		
		@Override
		public int get(int index) {
			switch(index) {
				case 0: return OxygenTileEntity.this.energyStorage.getEnergyStored();
				case 1: return OxygenTileEntity.this.oxygenTank.getFluidAmount();
				default: return 0;
			}
		}
	};

	public OxygenTileEntity(TileEntityType<?> type, int maxOxygen, int slots) {
		super(type, 10000, slots);
		this.oxygenTank = this.getTank();
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

	public int getMaxOxygen() {
		return this.oxygenTank.getCapacity();
	}
}
