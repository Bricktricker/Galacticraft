package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.tile.OxygenCompressorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class OxygenCompressorContainer extends Container {

	private final OxygenCompressorTileEntity compressor;
	private IIntArray containerStats;

	public OxygenCompressorContainer(int windowId, PlayerInventory playerInv, PacketBuffer buf) {
		super(GCContainers.OXYGEN_COMPRESSOR.get(), windowId);
		TileEntity te = playerInv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof OxygenCompressorTileEntity) {
			this.compressor = (OxygenCompressorTileEntity) te;
			this.init(playerInv, new IntArray(2));
		}else {
			this.compressor = null;
		}
	}

	public OxygenCompressorContainer(int windowId, PlayerInventory playerInv, OxygenCompressorTileEntity compressor, IIntArray containerStats) {
		super(GCContainers.OXYGEN_COMPRESSOR.get(), windowId);
		this.compressor = compressor;
		this.init(playerInv, containerStats);
	}

	private void init(PlayerInventory playerInv, IIntArray containerStats) {
		this.containerStats = containerStats;
		this.trackIntArray(this.containerStats);
		
		this.addSlot(new SlotItemHandler(compressor.getInventory(), 0, 133, 71));

		// Slots for the main inventory
		for(int row = 0; row < 3; ++row) {
			for(int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 104 + row * 18));
			}
		}

		// Slots for the hotbar
		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInv, k, 8 + k * 18, 162));
		}
	}

	public int getStoredEnergy() {
		return this.containerStats.get(0);
	}
	
	public int getMaxEnergy() {
		return this.compressor.getMaxEnergy();
	}
	
	public int getStoredOxygen() {
		return this.containerStats.get(1);
	}
	
	public int getMaxOxygen() {
		return this.compressor.getMaxOxygen();
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
