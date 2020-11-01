package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.tile.CircuitFabricatorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CircuitFabricatorContainer extends ProcessingContainer {

	public CircuitFabricatorContainer(int windowId, PlayerInventory inv, CircuitFabricatorTileEntity fabricator, IIntArray containerStats) {
		super(GCContainers.CIRCUT_FABIRCATOR.get(), windowId, inv, fabricator, containerStats);
	}

	public CircuitFabricatorContainer(int windowId, PlayerInventory inv, PacketBuffer buf) {
		super(GCContainers.CIRCUT_FABIRCATOR.get(), windowId, inv, buf);
	}

	@Override
	protected void addOwnSlots(IItemHandler itemHandler) {
		int slotIndex = 0;
		addSlot(new SlotItemHandler(itemHandler, slotIndex++, 15, 17)); // diamond slot
		addSlot(new SlotItemHandler(itemHandler, slotIndex++, 74, 46)); // silicon slot
		addSlot(new SlotItemHandler(itemHandler, slotIndex++, 74, 64)); // silicon slot
		addSlot(new SlotItemHandler(itemHandler, slotIndex++, 122, 46)); // redstone slot
		addSlot(new SlotItemHandler(itemHandler, slotIndex++, 145, 20)); // input slot
		addSlot(new SlotItemHandler(itemHandler, slotIndex++, 152, 86)); // output slot
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift
	 * clicking.
	 */
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if(slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			returnStack = stackInSlot.copy();

			// Move Fabricator -> Player inventory
			if(index < CircuitFabricatorTileEntity.INV_SIZE) {
				if(!this.mergeItemStack(stackInSlot, CircuitFabricatorTileEntity.INV_SIZE, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			}else {
				// player inventory -> Fabricator
				if(this.inventorySlots.get(0).isItemValid(stackInSlot)) { // diamond
					if(!this.mergeItemStack(stackInSlot, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				}else if(this.inventorySlots.get(1).isItemValid(stackInSlot)) { // two silicon slots
					if(!this.mergeItemStack(stackInSlot, 1, 3, false)) {
						return ItemStack.EMPTY;
					}
				}else if(this.inventorySlots.get(3).isItemValid(stackInSlot)) { // redstone dust
					if(!this.mergeItemStack(stackInSlot, 3, 4, false)) {
						return ItemStack.EMPTY;
					}
				}else if(index < 30) {
					if(!this.mergeItemStack(stackInSlot, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				}else if(index >= 30 && index < 39 && !this.mergeItemStack(stackInSlot, CircuitFabricatorTileEntity.INV_SIZE, 30, false)) {
					return ItemStack.EMPTY;
				}else {
					if(!this.mergeItemStack(stackInSlot, 4, 5, false)) {
						return ItemStack.EMPTY;
					}
				}
			}

			if(stackInSlot.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}else {
				slot.onSlotChanged();
			}

			if(stackInSlot.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, stackInSlot);
		}

		return returnStack;
	}

}
