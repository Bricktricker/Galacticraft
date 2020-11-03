package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.core.GCTileEntities;
import micdoodle8.mods.galacticraft.core.inventory.OxygenCompressorContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class OxygenCompressorTileEntity extends OxygenTileEntity {

	protected ItemStackHandler inventory;
	protected LazyOptional<IItemHandlerModifiable> inventoryCap;

	public static final int TANK_TRANSFER_SPEED = 2;

	public OxygenCompressorTileEntity() {
		super(GCTileEntities.OXYGEN_COMPRESSOR.get(), 1200, 15, 1);
	}

	@Override
	public void tick() {
		if(!this.world.isRemote) {
			ItemStack oxygenItemStack = this.getStackInSlot(0);

			if(oxygenItemStack.isEmpty()) {
				return;
			}

			if(this.energyStorage.getEnergyStored() >= ENERGY_USAGE) {
				if(this.oxygenTank.getFluid() != FluidStack.EMPTY) {
					oxygenItemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(handler -> {
						// TODO: currenty only accepts water
						if(handler.isFluidValid(0, oxygenTank.getFluidInTank(0))) {
							int changeAmound = Math.min(this.oxygenTank.getFluidAmount(), TANK_TRANSFER_SPEED);
							FluidStack stack = this.oxygenTank.getFluid().copy();
							stack.setAmount(changeAmound);
							changeAmound = handler.fill(stack, FluidAction.EXECUTE);

							if(changeAmound == 0) {
								return;
							}

							this.oxygenTank.drain(changeAmound, FluidAction.EXECUTE);

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
		return new TranslationTextComponent("container.galacticraftcore.oxygen_compressor");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new OxygenCompressorContainer(p_createMenu_1_, p_createMenu_2_, this);
	}
}
