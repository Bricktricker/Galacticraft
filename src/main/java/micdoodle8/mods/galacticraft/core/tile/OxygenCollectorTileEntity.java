package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.core.GCTileEntities;
import micdoodle8.mods.galacticraft.core.inventory.OxygenCollectorContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class OxygenCollectorTileEntity extends OxygenTileEntity {

	public static final int OUTPUT_PER_TICK = 100;
	public static final float OXYGEN_PER_PLANT = 0.75F;
	private int lastOxygenCollected;

	public OxygenCollectorTileEntity() {
		super(GCTileEntities.OXYGEN_COLLECTOR.get(), 6000, 0);
		
		this.containerStats = new IIntArray() {
			
			@Override
			public int size() {
				return 3;
			}
			
			@Override
			public void set(int index, int value) {
				switch(index) {
					case 0: OxygenCollectorTileEntity.this.energyStorage.setEnergy(value); break;
					case 1: OxygenCollectorTileEntity.this.oxygenTank.getFluid().setAmount(value); break;
					case 2: OxygenCollectorTileEntity.this.lastOxygenCollected = value; break;
				}
			}
			
			@Override
			public int get(int index) {
				switch(index) {
					case 0: return OxygenCollectorTileEntity.this.energyStorage.getEnergyStored();
					case 1: return OxygenCollectorTileEntity.this.oxygenTank.getFluidAmount();
					case 2: return OxygenCollectorTileEntity.this.lastOxygenCollected;
					default: return 0;
				}
			}
		};
	}

	@Override
	public void tick() {
		if(!this.world.isRemote) {

			// TODO: currently always prodices oxygen, check for non breathable air / leaves
			if(this.energyStorage.getEnergyStored() >= ENERGY_USAGE && this.oxygenTank.getSpace() > 0) {
				FluidStack oxygen = this.oxygenTank.getFluid().copy();
				oxygen.setAmount(OUTPUT_PER_TICK);
				this.lastOxygenCollected = this.oxygenTank.fill(oxygen, FluidAction.EXECUTE);
				this.energyStorage.extractEnergy(ENERGY_USAGE, false);
				this.markDirty();
			}

		}
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.collector");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new OxygenCollectorContainer(p_createMenu_1_, p_createMenu_2_, this, this.containerStats);
	}
	
	public float getLastOxygenCollected() {
		return this.lastOxygenCollected;
	}
}
