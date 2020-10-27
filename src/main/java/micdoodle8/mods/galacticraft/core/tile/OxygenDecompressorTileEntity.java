package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.inventory.OxygenDecompressorContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.registries.ObjectHolder;

public class OxygenDecompressorTileEntity extends OxygenTileEntity
{
    @ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.oxygenDecompressor)
    public static TileEntityType<OxygenDecompressorTileEntity> TYPE;

    public static final int OUTPUT_PER_TICK = 100;

    public OxygenDecompressorTileEntity()
    {
        super(TYPE, 1200, OUTPUT_PER_TICK, 1);
    }

    @Override
    public void tick()
    {

        if (!this.world.isRemote)
        {
        	ItemStack oxygenItemStack = this.getStackInSlot(0);
        	
        	if(oxygenItemStack.isEmpty()) {
        		return;
        	}
        	
			if(this.energyStorage.getEnergyStored() >= ENERGY_USAGE) {
				if(this.oxygenTank.getSpace() > 0) {
					oxygenItemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(handler -> {
						// TODO: currenty only accepts water
						if(oxygenTank.isFluidValid(0, handler.getFluidInTank(0))) {
							int changeAmound = Math.min(handler.getFluidInTank(0).getAmount(), OUTPUT_PER_TICK);
							changeAmound = Math.min(changeAmound, this.oxygenTank.getSpace());
							
							if(changeAmound == 0) {
								return;
							}
							
							FluidStack stack = handler.drain(changeAmound, FluidAction.EXECUTE);
							this.oxygenTank.fill(stack, FluidAction.EXECUTE);

							this.energyStorage.extractEnergy(ENERGY_USAGE, false);
							this.markDirty();
						}
					});

				}
			} 
        }
    }

    @Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemstack) {
		return itemstack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).isPresent();
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.oxygen_decompressor");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new OxygenDecompressorContainer(p_createMenu_1_, p_createMenu_2_, this);
	}

}
