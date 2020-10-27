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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public abstract class InventoryTileEntity extends TileEntity implements INamedContainerProvider {
	protected ItemStackHandler inventory;
	protected LazyOptional<IItemHandlerModifiable> inventoryCap;

	public InventoryTileEntity(TileEntityType<?> type, int slots) {
		super(type);
		this.inventory = this.createItemHandler(slots);
		this.inventoryCap = LazyOptional.of(() -> this.inventory);
	}

	protected ItemStackHandler createItemHandler(int slots) {
		return new ItemStackHandler(slots) {
			@Override
			protected void onContentsChanged(int slot) {
				InventoryTileEntity.this.markDirty();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return InventoryTileEntity.this.isItemValidForSlot(slots, stack);
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
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		}else{
			return !(player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
		}
	}

	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.inventory.getSlots() > 0) {
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
	public void remove() {
		this.inventoryCap.invalidate();
		super.remove();
	}
}
