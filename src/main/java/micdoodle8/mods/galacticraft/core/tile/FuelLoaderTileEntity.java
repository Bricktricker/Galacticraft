package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.prefab.entity.IRocket;
import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.blocks.PadFullBlock;
import micdoodle8.mods.galacticraft.core.fluid.GCFluids;
import micdoodle8.mods.galacticraft.core.inventory.FuelLoaderContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;

public class FuelLoaderTileEntity extends EnergyTileEntity implements ITickableTileEntity, INamedContainerProvider {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.fuelLoader)
	public static TileEntityType<FuelLoaderTileEntity> TYPE;

	public static final int TANK_CAPACITY = 12000;
	private static final int ENERGY_USAGE = 30;

	public final FluidTank fuelTank = this.getTank();
	private LazyOptional<IFluidHandler> tankCap = LazyOptional.of(() -> this.fuelTank);

	protected LazyOptional<IFluidHandler> attachedFuelable;

	private int ticks;

	public FuelLoaderTileEntity() {
		super(TYPE, 10000);
		this.attachedFuelable = LazyOptional.empty();
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

			if(!this.attachedFuelable.isPresent() && this.ticks % 100 == 0) {
				
				for(final Direction dir : Direction.values()) {
					BlockPos pos = getPos().offset(dir);
					BlockState state = this.world.getBlockState(pos);
					if(state.getBlock() instanceof PadFullBlock) {
						PadFullBlock pad = (PadFullBlock) state.getBlock();
						TileEntity mainTile = pad.getMainTE(state, this.world, pos);
						if(mainTile instanceof TileEntityLandingPad) {
							TileEntityLandingPad padTE = (TileEntityLandingPad) mainTile;
							
							LazyOptional<IRocket> rocket = padTE.getDockedRocket();
							boolean found = rocket.map(r -> {
								this.attachedFuelable = r.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
								return this.attachedFuelable.isPresent();
							}).orElse(false);
							
							if(found)
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
				
				this.attachedFuelable.ifPresent(rocket -> {
					int filled = rocket.fill(liquid, IFluidHandler.FluidAction.EXECUTE);
					this.fuelTank.drain(filled, IFluidHandler.FluidAction.EXECUTE);

					if(filled > 0) {
						this.energyStorage.extractEnergy(ENERGY_USAGE, false);
						this.markDirty();
					}
				});
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
		// TODO: currenty accepts only lava
		return new FluidTank(TANK_CAPACITY, f -> f.getFluid().isIn(FluidTags.LAVA)) {
			@Override
			protected void onContentsChanged() {
				FuelLoaderTileEntity.this.markDirty();
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

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new FuelLoaderContainer(p_createMenu_1_, p_createMenu_2_, this);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.fuel_loader");
	}
	
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		}else{
			return !(player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
		}
	}
}
