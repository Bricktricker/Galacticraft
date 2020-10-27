package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.tile.OxygenCollectorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.registries.ObjectHolder;

public class OxygenCollectorContainer extends Container {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + GCContainerNames.OXYGEN_COLLECTOR)
	public static ContainerType<OxygenCollectorContainer> TYPE;

	private final OxygenCollectorTileEntity collector;

	public OxygenCollectorContainer(int windowId, PlayerInventory playerInv, PacketBuffer buf) {
		super(TYPE, windowId);
		TileEntity te = playerInv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof OxygenCollectorTileEntity) {
			this.collector = (OxygenCollectorTileEntity) te;
			this.init(playerInv);
		}else {
			this.collector = null;
		}
	}

	public OxygenCollectorContainer(int windowId, PlayerInventory playerInv, OxygenCollectorTileEntity collector) {
		super(TYPE, windowId);
		this.collector = collector;
		this.init(playerInv);
	}

	private void init(PlayerInventory playerInv) {
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

	public OxygenCollectorTileEntity getCollector() {
		return collector;
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
