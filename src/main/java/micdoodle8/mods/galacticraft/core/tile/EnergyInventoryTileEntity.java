package micdoodle8.mods.galacticraft.core.tile;

import javax.annotation.Nonnull;

import micdoodle8.mods.galacticraft.core.energy.GCEnergyStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * A copy of EnergyTileEntity, but with InventoryTileEntity as super class
 */
public abstract class EnergyInventoryTileEntity extends InventoryTileEntity {

	protected final GCEnergyStorage energyStorage;
	private final LazyOptional<IEnergyStorage> energyCap;

	public EnergyInventoryTileEntity(TileEntityType<?> type, int slots, int maxEnergy) {
		super(type, slots);

		this.energyStorage = this.createEnergyStorage(maxEnergy);
		this.energyCap = LazyOptional.of(() -> this.energyStorage);
	}

	public int getStoredEnergy() {
		return energyStorage.getEnergyStored();
	}
	
	public int getMaxEnergy() {
		return energyStorage.getMaxEnergyStored();
	}

	protected int useEnergy(int amount) {
		this.markDirty();
		return energyStorage.extractEnergy(amount, false);
	}

	@Override
	public void read(CompoundNBT tag) {
		energyStorage.setEnergy(tag.getInt("energy"));

		super.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.putInt("energy", this.getStoredEnergy());

		return super.write(tag);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
		if(!this.removed && cap == CapabilityEnergy.ENERGY) {
			this.energyCap.cast();
		}
		return super.getCapability(cap, side);
	}

	protected GCEnergyStorage createEnergyStorage(int maxEnergy) {
		return new GCEnergyStorage(maxEnergy);
	}
	
	@Override
	public void remove() {
		this.energyCap.invalidate();
		super.remove();
	}

}
