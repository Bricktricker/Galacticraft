package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.tile.NasaWorkbenchTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class NasaWorkbenchContainer extends Container {

	private final NasaWorkbenchTileEntity workbench;

	public NasaWorkbenchContainer(int windowID, PlayerInventory inv, PacketBuffer buf) {
		super(GCContainers.NASA_WORKBENCH.get(), windowID);
		TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof NasaWorkbenchTileEntity) {
			this.workbench = (NasaWorkbenchTileEntity) te;
			this.workbench.readSchematics(buf);
			this.init(inv);
		}else {
			this.workbench = null;
		}
	}

	public NasaWorkbenchContainer(int windowID, PlayerInventory playerInv, NasaWorkbenchTileEntity workbench) {
		super(GCContainers.NASA_WORKBENCH.get(), windowID);
		this.workbench = workbench;
		this.init(playerInv);
	}

	private void init(PlayerInventory playerInv) {
		// Slots for the main inventory
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 89 + i * 18));
			}
		}

		// Slots for the hotbar
		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInv, k, 8 + k * 18, 147));
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

}
