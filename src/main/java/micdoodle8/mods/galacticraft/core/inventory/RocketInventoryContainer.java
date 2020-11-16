package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.api.prefab.entity.RocketEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.items.IItemHandler;

public class RocketInventoryContainer extends FuelInventoryEntityContainer {

	private final RocketEntity rocket;

	public RocketInventoryContainer(int containerId, PlayerInventory playerInv) {
		this(containerId, playerInv, IntReferenceHolder.single());
	}
	
	public RocketInventoryContainer(int containerId, PlayerInventory playerInv, IntReferenceHolder fuelReference) {
		super(GCContainers.ROCKET_INVENTORY.get(), containerId, playerInv, fuelReference);
		this.rocket = (RocketEntity) this.playerInv.player.getRidingEntity();
		this.init();
	}
	
	@Override
	public int getMaxFuel() {
		return this.rocket.getFuelTankCapacity();
	}

	@Override
	public boolean canInteractWith(PlayerEntity par1EntityPlayer) {
		return this.rocket.isAlive();
	}

	@Override
	public IItemHandler getInventoryHandler() {
		return this.rocket.getInventory();
	}
}
