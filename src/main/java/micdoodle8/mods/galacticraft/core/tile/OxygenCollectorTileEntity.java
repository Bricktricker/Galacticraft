package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.inventory.OxygenCollectorContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.registries.ObjectHolder;

public class OxygenCollectorTileEntity extends OxygenTileEntity {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.oxygenCollector)
	public static TileEntityType<OxygenCollectorTileEntity> TYPE;

	public static final int OUTPUT_PER_TICK = 100;
	public static final float OXYGEN_PER_PLANT = 0.75F;
	private float lastOxygenCollected;

	public OxygenCollectorTileEntity() {
		super(TYPE, 6000, 0, 0);
	}

	@Override
	public int getCappedScaledOxygenLevel(int scale) {
		return (int) Math.max(Math.min(Math.floor((double) this.getOxygenStored() / (double) this.getMaxOxygenStored() * scale), scale), 0);
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
		return new OxygenCollectorContainer(p_createMenu_1_, p_createMenu_2_, this);
	}
	
	public float getLastOxygenCollected() {
		return this.lastOxygenCollected;
	}
}
