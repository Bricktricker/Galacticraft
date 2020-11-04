package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.tile.OxygenCollectorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class OxygenCollectorContainer extends Container {

	private final OxygenCollectorTileEntity collector;
	private IIntArray containerStats;

	public OxygenCollectorContainer(int windowId, PlayerInventory playerInv, PacketBuffer buf) {
		super(GCContainers.OXYGEN_COLLECTOR.get(), windowId);
		TileEntity te = playerInv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof OxygenCollectorTileEntity) {
			this.collector = (OxygenCollectorTileEntity) te;
			this.init(playerInv, new IntArray(3));
		}else {
			this.collector = null;
		}
	}

	public OxygenCollectorContainer(int windowId, PlayerInventory playerInv, OxygenCollectorTileEntity collector, IIntArray containerStats) {
		super(GCContainers.OXYGEN_COLLECTOR.get(), windowId);
		this.collector = collector;
		this.init(playerInv, containerStats);
	}

	private void init(PlayerInventory playerInv, IIntArray containerStats) {
		this.trackIntArray(containerStats);
		this.containerStats = containerStats;
		
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

	public int getStoredEnergy() {
		return this.containerStats.get(0);
	}
	
	public int getMaxEnergy() {
		return this.collector.getMaxEnergy();
	}
	
	public int getStoredOxygen() {
		return this.containerStats.get(1);
	}
	
	public int getMaxOxygen() {
		return this.collector.getMaxOxygen();
	}
	
	public int getLastProducedOxygen() {
		return this.containerStats.get(2);
	}

	@Override
	public boolean canInteractWith(PlayerEntity var1) {
		return this.collector.isUsableByPlayer(var1);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity par1EntityPlayer, int par1) {
		return ItemStack.EMPTY;
	}
}
