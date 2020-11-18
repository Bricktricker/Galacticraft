package micdoodle8.mods.galacticraft.core.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Dont forget to call init in your constructor
 */
public abstract class FuelInventoryEntityContainer extends Container {
	
	protected final PlayerInventory playerInv;
	protected final IntReferenceHolder fuelReference;
	
	public FuelInventoryEntityContainer(ContainerType<?> type, int containerId, PlayerInventory playerInv, IntReferenceHolder fuelReference) {
		super(type, containerId);
		this.playerInv = playerInv;
		this.fuelReference = fuelReference;
	}
	
	protected void init() {
		this.trackInt(fuelReference);
		
		int invSlots = this.getInventoryHandler().getSlots();

		int ySize = 145 + (invSlots - 2) * 2;
		int lastRow = invSlots / 9;

		for(int y = 0; y < lastRow; ++y) {
			for(int x = 0; x < 9; ++x) {
				this.addSlot(new SlotItemHandler(this.getInventoryHandler(), x + y * 9, 8 + x * 18, 50 + y * 18));
			}
		}

		//Inventory slots
		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 9; ++x) {
				this.addSlot(new Slot(this.playerInv, x + y * 9 + 9, 8 + x * 18, ySize - 95 + y * 18));
			}
		}

		//hotbar slots
		for(int y = 0; y < 9; ++y) {
			this.addSlot(new Slot(this.playerInv, y, 8 + y * 18, ySize - 37));
		}
	}
	
	public abstract IItemHandler getInventoryHandler();
	
	public int getStoredFuel() {
		return this.fuelReference.get();
	}
	
	public abstract int getMaxFuel();
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity par1EntityPlayer, int par2) {
		ItemStack var3 = ItemStack.EMPTY;
		final Slot var4 = this.inventorySlots.get(par2);
		final int b = this.inventorySlots.size() - 36;

		if(var4 != null && var4.getHasStack()) {
			final ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 < b) {
				if(!this.mergeItemStack(var5, b, b + 36, true)) {
					return ItemStack.EMPTY;
				}
			}else if(!this.mergeItemStack(var5, 0, b, false)) {
				return ItemStack.EMPTY;
			}

			if(var5.getCount() == 0) {
				var4.putStack(ItemStack.EMPTY);
			}else {
				var4.onSlotChanged();
			}
		}

		return var3;
	}

}