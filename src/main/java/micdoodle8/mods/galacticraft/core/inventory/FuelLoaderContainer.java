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
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.registries.ObjectHolder;

public class FuelLoaderContainer extends Container {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + GCContainerNames.FUEL_LOADER)
	public static ContainerType<FuelLoaderContainer> TYPE;

	private final FuelLoaderTileEntity fuelLoader;
	private IIntArray containerStats;

	public FuelLoaderContainer(int windowID, PlayerInventory inv, PacketBuffer buf) {
		super(TYPE, windowID);
		TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof FuelLoaderTileEntity) {
			this.fuelLoader = (FuelLoaderTileEntity) te;
			this.init(inv, new IntArray(2));
		}else {
			this.fuelLoader = null;
		}
	}

	public FuelLoaderContainer(int windowID, PlayerInventory playerInv, FuelLoaderTileEntity fuelLoader, IIntArray containerStats) {
		super(TYPE, windowID);
		this.fuelLoader = fuelLoader;
		this.init(playerInv, containerStats);
	}

	private void init(PlayerInventory playerInv, IIntArray containerStats) {
		this.containerStats = containerStats;
		this.trackIntArray(this.containerStats);
		
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
	
	public int getStoredEnergy() {
		return this.containerStats.get(0);
	}
	
	public int getMaxEnergy() {
		return this.fuelLoader.getMaxEnergy();
	}
	
	public int getStoredFuel() {
		return this.containerStats.get(1);
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
