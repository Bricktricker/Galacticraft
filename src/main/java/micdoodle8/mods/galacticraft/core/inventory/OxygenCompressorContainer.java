package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.tile.OxygenCompressorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ObjectHolder;

public class OxygenCompressorContainer extends Container {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + GCContainerNames.OXYGEN_COMPRESSOR)
	public static ContainerType<OxygenCompressorContainer> TYPE;

	private final OxygenCompressorTileEntity compressor;

	public OxygenCompressorContainer(int windowId, PlayerInventory playerInv, PacketBuffer buf) {
		super(TYPE, windowId);
		TileEntity te = playerInv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof OxygenCompressorTileEntity) {
			this.compressor = (OxygenCompressorTileEntity) te;
			this.init(playerInv);
		}else {
			this.compressor = null;
		}
	}

	public OxygenCompressorContainer(int windowId, PlayerInventory playerInv, OxygenCompressorTileEntity compressor) {
		super(TYPE, windowId);
		this.compressor = compressor;
		this.init(playerInv);
	}

	private void init(PlayerInventory playerInv) {
		this.addSlot(new SlotItemHandler(compressor.getInventory(), 0, 133, 66));

		// Slots for the main inventory
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 110 + i * 18));
			}
		}

		// Slots for the hotbar
		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInv, k, 8 + k * 18, 168));
		}
	}

	public OxygenCompressorTileEntity getCompressor() {
		return compressor;
	}

	@Override
	public boolean canInteractWith(PlayerEntity var1) {
		return this.compressor.isUsableByPlayer(var1);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if(slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			returnStack = stackInSlot.copy();

			// compressor -> player inventory
			if(index == 0) {
				if(!this.mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			}else {
				// player inventory -> compressor
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
