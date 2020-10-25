package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.entity.IFuelable;
import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.blocks.PadFullBlock;
import micdoodle8.mods.galacticraft.core.fluid.GCFluids;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;

public class TileEntityFuelLoader extends EnergyTileEntity implements ITickableTileEntity {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.fuelLoader)
	public static TileEntityType<TileEntityFuelLoader> TYPE;

	public static final int TANK_CAPACITY = 12000;
	private static final int ENERGY_USAGE = 30;

	public final FluidTank fuelTank = this.getTank();
	private LazyOptional<IFluidHandler> tankCap = LazyOptional.of(() -> this.fuelTank);

	public IFuelable attachedFuelable;

	private int ticks;

	public TileEntityFuelLoader() {
		super(TYPE, 10000);
	}

	public int getScaledFuelLevel(int i) {
		final double fuelLevel = this.fuelTank.getFluid() == FluidStack.EMPTY ? 0 : this.fuelTank.getFluid().getAmount();

		return (int) (fuelLevel * i / TANK_CAPACITY);
	}

	@Override
	public void tick() {

		if(!this.world.isRemote) {
			this.ticks++;

//            final FluidStack liquidContained = FluidUtil.getFluidContained(this.getInventory().get(1));
//            if (FluidUtil.isFuel(liquidContained))
//            {
//                FluidUtil.loadFromContainer(this.fuelTank, GCFluids.FUEL.getFluid(), this.getInventory(), 1, liquidContained.getAmount());
//            }

			if(this.ticks % 100 == 0) {
				this.attachedFuelable = null;

				for(final Direction dir : Direction.values()) {
					BlockPos pos = getPos().offset(dir);
					BlockState state = this.world.getBlockState(pos);
					if(state.getBlock() instanceof PadFullBlock) {
						PadFullBlock pad = (PadFullBlock) state.getBlock();
						TileEntity mainTile = pad.getMainTE(state, this.world, pos);
						if(mainTile instanceof IFuelable) {
							this.attachedFuelable = (IFuelable) mainTile;
							break;
						}
					}
				}

			}

			if(this.fuelTank.getFluid() != FluidStack.EMPTY && this.fuelTank.getFluid().getAmount() > 0) {
				final FluidStack liquid = new FluidStack(GCFluids.FUEL.getFluid(), 2);

				if(this.energyStorage.getEnergyStored() < ENERGY_USAGE) {
					return;
				}

				boolean useEnergy = true;

				if(this.attachedFuelable != null && !this.removed) {
					int filled = this.attachedFuelable.addFuel(liquid, IFluidHandler.FluidAction.EXECUTE);
					useEnergy = filled > 0;
					this.fuelTank.drain(filled, IFluidHandler.FluidAction.EXECUTE);
				}

				if(useEnergy) {
					this.energyStorage.extractEnergy(ENERGY_USAGE, false);
					this.markDirty();
				}
			}
		}
	}

	@Override
	public void read(CompoundNBT par1NBTTagCompound) {
		super.read(par1NBTTagCompound);

		if(par1NBTTagCompound.contains("fuelTank")) {
			this.fuelTank.readFromNBT(par1NBTTagCompound.getCompound("fuelTank"));
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		super.write(nbt);

		if(this.fuelTank.getFluid() != FluidStack.EMPTY) {
			nbt.put("fuelTank", this.fuelTank.writeToNBT(new CompoundNBT()));
		}

		return nbt;
	}

	protected FluidTank getTank() {
		// TODO: currenty accepts only water
		return new FluidTank(TANK_CAPACITY, f -> f.getFluid().isIn(FluidTags.WATER)) {
			@Override
			protected void onContentsChanged() {
				TileEntityFuelLoader.this.markDirty();
			}
		};
	}

	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return tankCap.cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void remove() {
		this.tankCap.invalidate();
		super.remove();
	}
}
