package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.prefab.entity.IRocket;
import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import micdoodle8.mods.galacticraft.core.GCTileEntities;
import micdoodle8.mods.galacticraft.core.blocks.PadFullBlock;
import micdoodle8.mods.galacticraft.core.entities.MoonBuggyEntity;
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
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;

public class FuelLoaderTileEntity extends EnergyTileEntity implements ITickableTileEntity, INamedContainerProvider, IDisableableMachine {

	public static final int TANK_CAPACITY = 12000;
	private static final int ENERGY_USAGE = 30;

	public final FluidTank fuelTank = this.getTank();
	private LazyOptional<IFluidHandler> tankCap = LazyOptional.of(() -> this.fuelTank);

	protected LazyOptional<IFluidHandler> attachedFuelable;
	
	private int ticks;
	private boolean isDiabled;
	
	private final IIntArray containerStats = new IIntArray() {

		@Override
		public int size() {
			return 3;
		}
		
		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0: FuelLoaderTileEntity.this.energyStorage.setEnergy(value); break;
				case 1: FuelLoaderTileEntity.this.fuelTank.getFluid().setAmount(value); break;
				case 2: FuelLoaderTileEntity.this.isDiabled = value != 0; break;
			}
		}
		
		@Override
		public int get(int index) {
			switch(index) {
				case 0: return FuelLoaderTileEntity.this.energyStorage.getEnergyStored();
				case 1: return FuelLoaderTileEntity.this.fuelTank.getFluidAmount();
				case 2: return FuelLoaderTileEntity.this.isDiabled ? 1 : 0;
				default: return 0;
			}
		}
		
	};

	public FuelLoaderTileEntity() {
		super(GCTileEntities.FUEL_LOADER.get(), 10000);
		this.attachedFuelable = LazyOptional.empty();
		this.isDiabled = false;
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
						TileEntity mainTile = PadFullBlock.getMainTE(state, this.world, pos);
						if(mainTile instanceof RocketPadTileEntity) {
							RocketPadTileEntity padTE = (RocketPadTileEntity) mainTile;
							
							LazyOptional<IRocket> rocket = padTE.getDockedRocket();
							boolean found = rocket.map(r -> {
								this.attachedFuelable = r.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
								return this.attachedFuelable.isPresent();
							}).orElse(false);
							
							if(found)
								break;
						}else if(mainTile instanceof BuggyPadTileEntity) {
							BuggyPadTileEntity padTE = (BuggyPadTileEntity) mainTile;
							LazyOptional<MoonBuggyEntity> buggy = padTE.getDocketBuggy();
							boolean found = buggy.map(b -> {
								this.attachedFuelable = b.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
								return this.attachedFuelable.isPresent();
							}).orElse(false);
							
							if(found)
								break;
						}
					}
				}

			}

			if(this.fuelTank.getFluid() != FluidStack.EMPTY && this.fuelTank.getFluid().getAmount() > 0) {
				if(this.energyStorage.getEnergyStored() < ENERGY_USAGE) {
					return;
				}
				this.energyStorage.extractEnergy(ENERGY_USAGE, false);
				
				this.attachedFuelable.ifPresent(rocket -> {
					FluidStack liquid = this.fuelTank.drain(20, IFluidHandler.FluidAction.EXECUTE);
					if(liquid.isEmpty() || liquid.getAmount() == 0) {
						return;
					}
					int filled = rocket.fill(liquid, IFluidHandler.FluidAction.EXECUTE);	
					if(filled < liquid.getAmount()) {
						liquid.setAmount(liquid.getAmount() - filled);
						this.fuelTank.fill(liquid, IFluidHandler.FluidAction.EXECUTE);
					}
				});
				this.markDirty();
			}
		}
	}

	@Override
	public void read(CompoundNBT tag) {
		super.read(tag);

		if(tag.contains("fuelTank")) {
			this.fuelTank.readFromNBT(tag.getCompound("fuelTank"));
		}
		this.isDiabled = tag.getBoolean("disabled");
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		super.write(tag);

		if(this.fuelTank.getFluid() != FluidStack.EMPTY) {
			tag.put("fuelTank", this.fuelTank.writeToNBT(new CompoundNBT()));
		}

		tag.putBoolean("disabled", this.isDiabled);
		return tag;
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
		return new FuelLoaderContainer(p_createMenu_1_, p_createMenu_2_, this, this.containerStats);
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

	@Override
	public void setDisabled(boolean disabled) {
		this.isDiabled = disabled;
		this.markDirty();
	}

	@Override
	public boolean isDisabled() {
		return this.isDiabled;
	}
}
