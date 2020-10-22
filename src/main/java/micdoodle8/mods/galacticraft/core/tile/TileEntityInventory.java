package micdoodle8.mods.galacticraft.core.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public abstract class TileEntityInventory extends TileEntity implements INamedContainerProvider {
	protected final ItemStackHandler inventory;
	private final LazyOptional<IItemHandlerModifiable> inventoryCap;

	public TileEntityInventory(TileEntityType<?> type, int slots) {
		super(type);
		this.inventory = this.createItemHandler(slots);
		this.inventoryCap = LazyOptional.of(() -> this.inventory);
	}

	private ItemStackHandler createItemHandler(int slots) {
		return new ItemStackHandler(slots) {
			@Override
			protected void onContentsChanged(int slot) {
				TileEntityInventory.this.markDirty();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return TileEntityInventory.this.isItemValidForSlot(slots, stack);
			}
		};
	}

	public ItemStackHandler getInventory() {
		return inventory;
	}

	protected boolean handleInventory() {
		return true;
	}

	@Override
	public void read(CompoundNBT tags) {
		super.read(tags);

		if(handleInventory()) {
			this.inventory.deserializeNBT((CompoundNBT) tags.get("Items"));
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT tags) {
		super.write(tags);

		if(handleInventory()) {
			tags.put("Items", this.inventory.serializeNBT());
		}

		return tags;
	}

	public int getSizeInventory() {
		return this.inventory.getSlots();
	}

	public ItemStack getStackInSlot(int slot) {
		return getSizeInventory() > slot ? ItemStack.EMPTY : this.inventory.getStackInSlot(slot);
	}

	public ItemStack decrStackSize(int slot, int amount, boolean simulate) {
		return this.inventory.extractItem(slot, amount, simulate);
	}

	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory.setStackInSlot(slot, stack);
	}

	public boolean isUsableByPlayer(PlayerEntity player) {
		return !isRemoved() && this.world.isAreaLoaded(this.pos, 1);
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
		if(!this.removed && cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			this.inventoryCap.cast();
		}
		return super.getCapability(cap, side);
	}

	public void clear() {
		for(int i = 0; i < this.getSizeInventory(); ++i) {
			this.setInventorySlotContents(i, ItemStack.EMPTY);
		}
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		this.read(tag);
	}
}
