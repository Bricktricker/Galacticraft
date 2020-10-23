package micdoodle8.mods.galacticraft.core.tile;

import javax.annotation.Nonnull;

import micdoodle8.mods.galacticraft.core.energy.GCEnergyStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class EnergyTileEntity extends TileEntity {

	protected final GCEnergyStorage energyStorage;
	private final LazyOptional<IEnergyStorage> energyCap;

	public EnergyTileEntity(TileEntityType<?> tileEntityTypeIn, int maxEnergy) {
		super(tileEntityTypeIn);

		this.energyStorage = this.createEnergyStorage(maxEnergy);
		this.energyCap = LazyOptional.of(() -> this.energyStorage);
	}

	protected int getStoredEnergy() {
		return energyStorage.getEnergyStored();
	}

	protected int useEnergy(int amount) {
		this.markDirty();
		return energyStorage.extractEnergy(amount, false);
	}

	@Override
	public void read(CompoundNBT tag) {
		CompoundNBT energyTag = tag.getCompound("energy");
		energyStorage.deserializeNBT(energyTag);

		super.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		CompoundNBT compound = energyStorage.serializeNBT();
		tag.put("energy", compound);

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

}
