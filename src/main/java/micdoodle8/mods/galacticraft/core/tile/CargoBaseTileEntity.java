package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.entity.ICargoEntity;
import micdoodle8.mods.galacticraft.api.entity.ICargoEntity.EnumCargoLoadingState;
import micdoodle8.mods.galacticraft.core.blocks.PadFullBlock;
import micdoodle8.mods.galacticraft.core.util.RecipeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public abstract class CargoBaseTileEntity extends EnergyInventoryTileEntity implements ITickableTileEntity {
	protected final int ENERGY_USAGE = 20;

	protected ICargoEntity attachedFuelable;
	protected int tier;

	public CargoBaseTileEntity(TileEntityType<?> type, int tier, int slots, int maxEnergy) {
		super(type, slots, maxEnergy);
		this.tier = tier;
	}

	public void checkForCargoEntity() {
		boolean foundFuelable = false;

		for(final Direction dir : Direction.values()) {
			BlockPos pos = getPos().offset(dir);
			BlockState state = this.world.getBlockState(pos);
			if(state.getBlock() instanceof PadFullBlock) {
				PadFullBlock pad = (PadFullBlock) state.getBlock();
				TileEntity mainTile = pad.getMainTE(state, this.world, pos);
				if(mainTile instanceof ICargoEntity) {
					this.attachedFuelable = (ICargoEntity) mainTile;
					foundFuelable = true;
					break;
				}
			}
		}

		if(!foundFuelable) {
			this.attachedFuelable = null;
		}
	}

	@Override
	public void read(CompoundNBT tag) {
		this.tier = tag.getByte("tier");
		super.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.putByte("tier", (byte) this.tier);
		return super.write(tag);
	}

	// Used by Abandoned Base worldgen
	public EnumCargoLoadingState addCargo(ItemStack stack, boolean doAdd) {
		int count = 1;

		for(count = 1; count < this.getInventory().getSlots(); count++) {
			ItemStack stackAt = this.getInventory().getStackInSlot(count);

			if(RecipeUtil.stacksMatch(stack, stackAt) && stackAt.getCount() < stackAt.getMaxStackSize()) {
				if(stackAt.getCount() + stack.getCount() <= stackAt.getMaxStackSize()) {
					if(doAdd) {
						stackAt.grow(stack.getCount());
						this.markDirty();
					}

					return EnumCargoLoadingState.SUCCESS;
				}else {
					// Part of the stack can fill this slot but there will be some left over
					int origSize = stackAt.getCount();
					int surplus = origSize + stack.getCount() - stackAt.getMaxStackSize();

					if(doAdd) {
						stackAt.setCount(stackAt.getMaxStackSize());
						this.markDirty();
					}

					stack.setCount(surplus);
					if(this.addCargo(stack, doAdd) == EnumCargoLoadingState.SUCCESS) {
						return EnumCargoLoadingState.SUCCESS;
					}

					stackAt.setCount(origSize);
					return EnumCargoLoadingState.FULL;
				}
			}
		}

		int size = this.getInventory().getSlots();
		for(count = 1; count < size; count++) {
			ItemStack stackAt = this.getInventory().getStackInSlot(count);

			if(stackAt.isEmpty()) {
				if(doAdd) {
					this.getInventory().insertItem(count, stack, false);
					this.markDirty();
				}

				return EnumCargoLoadingState.SUCCESS;
			}
		}

		return EnumCargoLoadingState.FULL;
	}
}
