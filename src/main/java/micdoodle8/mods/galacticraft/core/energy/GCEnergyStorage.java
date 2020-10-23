package micdoodle8.mods.galacticraft.core.energy;

import net.minecraftforge.energy.EnergyStorage;

public class GCEnergyStorage extends EnergyStorage {

	public GCEnergyStorage(int capacity) {
		super(capacity);
	}
	
	public void setEnergy(int energy) {
        this.energy = energy;
    }

}
