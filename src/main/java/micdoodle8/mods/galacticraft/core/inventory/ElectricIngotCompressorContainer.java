package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.tile.ElectricCompressorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ObjectHolder;

public class ElectricIngotCompressorContainer extends ProcessingContainer {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + GCContainerNames.ELECTRIC_INGOT_COMPRESSOR)
	public static ContainerType<ElectricIngotCompressorContainer> TYPE;

	public ElectricIngotCompressorContainer(int windowId, PlayerInventory playerInv, ElectricCompressorTileEntity tileEntity, IIntArray containerStats) {
		super(TYPE, windowId, playerInv, tileEntity, containerStats);
	}

	public ElectricIngotCompressorContainer(int windowId, PlayerInventory inv, PacketBuffer buf) {
		super(TYPE, windowId, inv, buf);
	}

	@Override
	protected void addOwnSlots(IItemHandler itemHandler) {
		int slotIndex = 0;

		// crafting grid
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				addSlot(new SlotItemHandler(itemHandler, slotIndex++, 19 + y * 18, 18 + x * 18));
			}
		}

		// Smelting result
		addSlot(new SlotItemHandler(itemHandler, slotIndex++, 138, 30));
		addSlot(new SlotItemHandler(itemHandler, slotIndex++, 138, 48));
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

			// move compressor -> player inventory
			if(index < ElectricCompressorTileEntity.INV_SIZE) {
				if(!this.mergeItemStack(stackInSlot, ElectricCompressorTileEntity.INV_SIZE, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			}else {
				// player inventory -> fabricator
				if(!this.mergeItemStack(stackInSlot, 0, ElectricCompressorTileEntity.INV_SIZE - 2, false)) {
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
