package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.tile.ProcessingTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.items.IItemHandler;

public abstract class ProcessingContainer extends Container {
	
	protected ProcessingTileEntity<?> te;
	private IIntArray containerStats;

	protected ProcessingContainer(ContainerType<?> type, int windowId, PlayerInventory inv, ProcessingTileEntity<?> te, IIntArray containerStats) {
		super(type, windowId);
		this.te = te;
	}
	
	protected ProcessingContainer(ContainerType<?> type, int windowId, PlayerInventory inv, PacketBuffer buf) {
		super(type, windowId);
		TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
    	if(te instanceof ProcessingTileEntity) {
    		this.te = (ProcessingTileEntity<?>)te;
    		this.init(inv, new IntArray(3));
    	}else {
    		this.te = null;
    	}
	}
	
	private void init(PlayerInventory playerInv, IIntArray containerStats) {
    	this.containerStats = containerStats;
    	this.trackIntArray(containerStats);
    	
    	this.addOwnSlots(this.te.getInventory());
    	this.addPlayerSlots(playerInv);
    }
	
	protected void addPlayerSlots(PlayerInventory playerInventory) {
		// Slots for the main inventory
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
	
	protected abstract void addOwnSlots(IItemHandler itemHandler);
	
	public int getProgress() {
		return this.containerStats.get(0);
	}
	
	public int getRecipeDuration() {
		return this.containerStats.get(1);
	}
	
	public int getStoredEnergy() {
		return this.containerStats.get(2);
	}
	
	public int getMaxEnergy() {
		return te.getMaxEnergy();
	}
	
	@Override
    public boolean canInteractWith(PlayerEntity par1EntityPlayer)
    {
        return this.te.isUsableByPlayer(par1EntityPlayer);
    }

}
