package micdoodle8.mods.galacticraft.core.client.gui.container;

import micdoodle8.mods.galacticraft.core.entities.MoonBuggyEntity;
import micdoodle8.mods.galacticraft.core.inventory.FuelInventoryEntityContainer;
import micdoodle8.mods.galacticraft.core.inventory.GCContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.items.IItemHandler;

public class BuggyInventoryContainer extends FuelInventoryEntityContainer {
	
	private final MoonBuggyEntity buggy;

	public BuggyInventoryContainer(int containerId, PlayerInventory playerInv) {
		this(containerId, playerInv, IntReferenceHolder.single());
	}
	
	public BuggyInventoryContainer(int containerId, PlayerInventory playerInv, IntReferenceHolder fuelReference) {
		super(GCContainers.BUGGY_INVENTORY.get(), containerId, playerInv, fuelReference);
		this.buggy = (MoonBuggyEntity) this.playerInv.player.getRidingEntity();
		this.init();
	}

	@Override
	public IItemHandler getInventoryHandler() {
		return this.buggy.getInventory();
	}

	@Override
	public int getMaxFuel() {
		return MoonBuggyEntity.FUEL_CAPACITY;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.buggy.isAlive();
	}
	
	

}
