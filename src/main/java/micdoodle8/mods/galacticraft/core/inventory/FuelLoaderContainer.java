package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.tile.FuelLoaderTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.registries.ObjectHolder;

public class FuelLoaderContainer extends Container {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + GCContainerNames.FUEL_LOADER)
	public static ContainerType<FuelLoaderContainer> TYPE;

	private final FuelLoaderTileEntity fuelLoader;

	public FuelLoaderContainer(int windowID, PlayerInventory inv, PacketBuffer buf) {
		super(TYPE, windowID);
		TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof FuelLoaderTileEntity) {
			this.fuelLoader = (FuelLoaderTileEntity) te;
			this.init(inv);
		}else {
			this.fuelLoader = null;
		}
	}

	public FuelLoaderContainer(int windowID, PlayerInventory playerInv, FuelLoaderTileEntity fuelLoader) {
		super(TYPE, windowID);
		this.fuelLoader = fuelLoader;
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

	public FuelLoaderTileEntity getFuelLoader() {
		return fuelLoader;
	}

	@Override
	public boolean canInteractWith(PlayerEntity var1) {
		return this.fuelLoader.isUsableByPlayer(var1);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity par1EntityPlayer, int par2) {
		return ItemStack.EMPTY;
	}
}
