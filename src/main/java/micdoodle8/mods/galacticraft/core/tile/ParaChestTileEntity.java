package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;

public class ParaChestTileEntity extends InventoryTileEntity {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.parachest)
	public static TileEntityType<ParaChestTileEntity> TYPE;

	private static final int TANK_CAPACITY = 5000;

	private final FluidTank fuelTank = this.getTank();
	private LazyOptional<IFluidHandler> tankCap = LazyOptional.of(() -> this.fuelTank);
	private DyeColor color = DyeColor.RED;

	public ParaChestTileEntity() {
		super(TYPE, 3);
		this.color = DyeColor.RED;
	}

	public int getScaledFuelLevel(int i) {
		final double fuelLevel = this.fuelTank.getFluid() == FluidStack.EMPTY ? 0 : this.fuelTank.getFluid().getAmount();

		return (int) (fuelLevel * i / TANK_CAPACITY);
	}

	public DyeColor getColor() {
		return color;
	}

	public void setColor(DyeColor color) {
		this.color = color;
		this.markDirty();
	}
	
	public void setInventory(NonNullList<ItemStack> items) {
		this.inventory.setSize(items.size());
		for(int i = 0; i < items.size(); i++) {
			this.inventory.insertItem(i, items.get(i), false);
		}
		this.markDirty();
	}

	@Override
	public void read(CompoundNBT nbt) {
		int size = nbt.getInt("chestContentLength");
		if((size - 3) % 18 != 0) {
			size += 18 - ((size - 3) % 18);
		}

		if(size != this.inventory.getSlots()) {
			this.inventoryCap.invalidate();
			this.inventory = this.createItemHandler(size);
			this.inventoryCap = LazyOptional.of(() -> this.inventory);
		}

		super.read(nbt);

		if(nbt.contains("fuelTank")) {
			this.fuelTank.readFromNBT(nbt.getCompound("fuelTank"));
		}

		if(nbt.contains("color")) {
			this.color = DyeColor.values()[nbt.getInt("color")];
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		super.write(nbt);

		nbt.putInt("chestContentLength", this.getInventory().getSlots());

		if(this.fuelTank.getFluid() != FluidStack.EMPTY) {
			nbt.put("fuelTank", this.fuelTank.writeToNBT(new CompoundNBT()));
		}

		nbt.putInt("color", this.color.ordinal());
		return nbt;
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	protected FluidTank getTank() {
		return new FluidTank(TANK_CAPACITY, Predicates.alwaysFalse()) {
			@Override
			protected void onContentsChanged() {
				ParaChestTileEntity.this.markDirty();
			}
		};
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack par2ItemStack) {
		return slot == 0;
	}

	@Override
	public void remove() {
		super.remove();
		this.tankCap.invalidate();
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.para_chest");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return null;
	}

	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return tankCap.cast();
		}
		return super.getCapability(capability, facing);
	}

	public void setTankContent(Fluid stack, int amount) {
		this.fuelTank.setValidator(Predicates.alwaysTrue());
		this.fuelTank.fill(new FluidStack(stack, amount), FluidAction.EXECUTE);
		this.fuelTank.setValidator(Predicates.alwaysFalse());
	}
}
