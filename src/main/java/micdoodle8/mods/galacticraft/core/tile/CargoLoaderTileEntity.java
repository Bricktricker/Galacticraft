package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.entity.ICargoEntity.EnumCargoLoadingState;
import micdoodle8.mods.galacticraft.api.entity.ICargoEntity.RemovalResult;
import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.inventory.CargoLoaderContainer;
import micdoodle8.mods.galacticraft.core.util.RecipeUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ObjectHolder;

public class CargoLoaderTileEntity extends CargoBaseTileEntity {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.cargoLoader)
	public static TileEntityType<CargoLoaderTileEntity> TYPE;

	public boolean outOfItems;
	public boolean targetFull;
	public boolean targetNoInventory;
	public boolean noTarget;

	private int ticks = 0;

	public CargoLoaderTileEntity(int tier) {
		super(TYPE, 14, 10000, tier);
	}

	@Override
	public void tick() {

		if(!this.world.isRemote) {
			this.ticks++;
			if(this.ticks % 100 == 0) {
				this.checkForCargoEntity();
			}

			if(this.attachedFuelable != null) {
				this.noTarget = false;
				ItemStack stack = this.removeCargo(false).resultStack;

				if(!stack.isEmpty()) {
					this.outOfItems = false;

					EnumCargoLoadingState state = this.attachedFuelable.addCargo(stack, false);

					this.targetFull = state == EnumCargoLoadingState.FULL;
					this.targetNoInventory = state == EnumCargoLoadingState.NOINVENTORY;
					this.noTarget = state == EnumCargoLoadingState.NOTARGET;

					if(this.energyStorage.getEnergyStored() < ENERGY_USAGE) {
						return;
					}

					this.energyStorage.extractEnergy(ENERGY_USAGE, false);
					;
					if(this.ticks % (this.tier > 1 ? 9 : 15) == 0 && state == EnumCargoLoadingState.SUCCESS && !this.removed) {
						this.attachedFuelable.addCargo(this.removeCargo(true).resultStack, true);
					}
				}else {
					this.outOfItems = true;
				}
			}else {
				this.noTarget = true;
			}
		}
	}

	public RemovalResult removeCargo(boolean doRemove) {
		for(int i = 1; i < this.getInventory().getSlots(); i++) {
			ItemStack stackAt = this.getInventory().getStackInSlot(i);

			if(!stackAt.isEmpty()) {
				ItemStack resultStack = stackAt.copy();
				resultStack.setCount(1);

				if(doRemove) {
					stackAt.shrink(1);
				}

				if(doRemove) {
					this.markDirty();
				}

				return new RemovalResult(EnumCargoLoadingState.SUCCESS, resultStack);
			}
		}

		return new RemovalResult(EnumCargoLoadingState.EMPTY, ItemStack.EMPTY);
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

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.cargo_loader");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new CargoLoaderContainer(p_createMenu_1_, p_createMenu_2_, this);
	}
	
}
