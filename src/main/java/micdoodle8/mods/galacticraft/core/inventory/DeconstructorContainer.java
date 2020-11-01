package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.tile.DeconstructorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class DeconstructorContainer extends ProcessingContainer {

	public DeconstructorContainer(int windowId, PlayerInventory inv, DeconstructorTileEntity deconstructor, IIntArray containerStats) {
		super(GCContainers.DECONSTRUCTOR.get(), windowId, inv, deconstructor, containerStats);
	}

	public DeconstructorContainer(int windowId, PlayerInventory inv, PacketBuffer buf) {
		super(GCContainers.DECONSTRUCTOR.get(), windowId, inv, buf);
	}

	@Override
	protected void addOwnSlots(IItemHandler itemHandler) {
		int slotIndex = 0;

		addSlot(new SlotItemHandler(itemHandler, slotIndex++, 26, 36)); // input slot

		// output slots
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				addSlot(new SlotItemHandler(itemHandler, slotIndex++, 112 + y * 18, 18 + x * 18));
			}
		}
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

			// move deconstructor -> player inventory
			if(index < DeconstructorTileEntity.INV_SIZE) {
				if(!this.mergeItemStack(stackInSlot, DeconstructorTileEntity.INV_SIZE, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			}else {
				// player inventory -> deconstructor
				if(!this.mergeItemStack(stackInSlot, 0, 1, false)) {
					return ItemStack.EMPTY;
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
