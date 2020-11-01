package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.tile.CircuitFabricatorTileEntity;
import micdoodle8.mods.galacticraft.core.tile.ParaChestTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.SlotItemHandler;

public class ParaChestContainer extends Container {

	private ParaChestTileEntity paraChest;
	private int numRows;

	public ParaChestContainer(int windowId, PlayerInventory playerInventory, ParaChestTileEntity paraChest) {
		super(GCContainers.PARACHEST.get(), windowId);
		this.paraChest = paraChest;
		
		this.init(playerInventory);
	}
	
	public ParaChestContainer(int windowId, PlayerInventory inv, PacketBuffer buf) {
		super(GCContainers.PARACHEST.get(), windowId);
		TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
    	if(te instanceof ParaChestTileEntity) {
    		this.paraChest = (ParaChestTileEntity)te;
    		this.init(inv);
    	}
	}
	
	private void init(PlayerInventory playerInventory) {
		this.numRows = (paraChest.getSizeInventory() - 3) / 9;

		int slotIndex = 0;
		// bottom slots
		addSlot(new SlotItemHandler(paraChest.getInventory(), slotIndex++, 75, 24 + this.numRows * 18));
		addSlot(new SlotItemHandler(paraChest.getInventory(), slotIndex++, 125, 24 + this.numRows * 18));
		addSlot(new SlotItemHandler(paraChest.getInventory(), slotIndex++, 143, 24 + this.numRows * 18));

		// top chest slots
		for(int y = 0; y < this.numRows; y++) {
			for(int x = 0; x < 9; x++) {
				addSlot(new SlotItemHandler(paraChest.getInventory(), slotIndex++, 8 + x * 18, 18 + y * 18));
			}
		}

		// player slots
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 110 + i * 18));
			}
		}

		// Slots for the hotbar
		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 168));
		}
	}

	public ParaChestTileEntity getParaChest() {
		return paraChest;
	}

	@Override
	public boolean canInteractWith(PlayerEntity par1EntityPlayer) {
		return this.paraChest.isUsableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if(slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			returnStack = stackInSlot.copy();

			// move chest -> player
			if(index < paraChest.getInventory().getSlots()) {
				if(!this.mergeItemStack(stackInSlot, CircuitFabricatorTileEntity.INV_SIZE, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			}else {
				// player -> chest
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
