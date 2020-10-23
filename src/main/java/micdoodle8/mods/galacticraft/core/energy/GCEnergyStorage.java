package micdoodle8.mods.galacticraft.core.energy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class GCEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {

	public GCEnergyStorage(int capacity) {
		super(capacity);
	}
	
	public void setEnergy(int energy) {
        this.energy = energy;
    }
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
        tag.putInt("energy", getEnergyStored());
        return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		setEnergy(nbt.getInt("energy"));
	}

}
