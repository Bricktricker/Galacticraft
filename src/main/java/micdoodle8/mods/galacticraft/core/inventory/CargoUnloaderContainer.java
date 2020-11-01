package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.tile.CargoUnloaderTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.items.SlotItemHandler;

public class CargoUnloaderContainer extends Container {

	private final CargoUnloaderTileEntity cargoTile;
	private IIntArray containerStats;
	
	public CargoUnloaderContainer(int windowID, PlayerInventory playerInv, CargoUnloaderTileEntity cargoTile, IIntArray containerStats) {
		super(GCContainers.CARGO_UNLOADER.get(), windowID);
		this.cargoTile = cargoTile;
		this.init(playerInv, containerStats);
	}
	
	public CargoUnloaderContainer(int windowId, PlayerInventory inv, PacketBuffer buf) {
		super(GCContainers.CARGO_UNLOADER.get(), windowId);
		TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof CargoUnloaderTileEntity) {
			this.cargoTile = (CargoUnloaderTileEntity) te;
			this.init(inv, new IntArray(3));
		}else {
			this.cargoTile = null;
		}
	}
	
	private void init(PlayerInventory playerInv, IIntArray containerStats) {
		this.trackIntArray(containerStats);
		this.containerStats = containerStats;
		
		for(int row = 0; row < 2; ++row) {
			for(int col = 0; col < 7; ++col) {
				this.addSlot(new SlotItemHandler(cargoTile.getInventory(), col + row * 7, 38 + col * 18, 27 + row * 18));
			}
		}

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
	
	public CargoUnloaderTileEntity getCargoTile() {
		return cargoTile;
	}
	
	public int getStoredEnergy() {
		return this.containerStats.get(0);
	}
	
	public boolean hasTarget() {
		return this.containerStats.get(1) != 0;
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity var1) {
		return this.cargoTile.isUsableByPlayer(var1);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if(slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			returnStack = stackInSlot.copy();

			// move loader -> player inventory
			if(index < 14) {
				if(!this.mergeItemStack(stackInSlot, 14, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			}else {
				// player inventory -> loader
				if(!this.mergeItemStack(stackInSlot, 0, 14, false)) {
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
