package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.prefab.entity.IRocket;
import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import micdoodle8.mods.galacticraft.core.blocks.PadFullBlock;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class CargoBaseTileEntity extends EnergyInventoryTileEntity implements ITickableTileEntity, IDisableableMachine {
	protected final int ENERGY_USAGE = 10;

	protected LazyOptional<IItemHandlerModifiable> attachedInventory;
	
	protected int tier;
	protected boolean isDisabled;

	public CargoBaseTileEntity(TileEntityType<?> type, int tier, int slots, int maxEnergy) {
		super(type, slots, maxEnergy);
		this.tier = tier;
		this.isDisabled = false;
		
		this.attachedInventory = LazyOptional.empty();
	}

	public void checkForCargoEntity() {
		if(attachedInventory.isPresent()) {
			return;
		}

		for(final Direction dir : Direction.values()) {
			BlockPos pos = getPos().offset(dir);
			BlockState state = this.world.getBlockState(pos);
			if(state.getBlock() instanceof PadFullBlock) {
				PadFullBlock pad = (PadFullBlock) state.getBlock();
				TileEntity mainTile = pad.getMainTE(state, this.world, pos);
				if(mainTile instanceof TileEntityLandingPad) {
					TileEntityLandingPad padTE = (TileEntityLandingPad) mainTile;
					
					LazyOptional<IRocket> rocket = padTE.getDockedRocket();
					boolean found = rocket.map(r -> {
						this.attachedInventory = r.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
								.filter(i -> i instanceof IItemHandlerModifiable)
								.map(IItemHandlerModifiable.class::cast);
						return this.attachedInventory.isPresent();
					}).orElse(false);
					
					if(found)
						break;
				}
			}
		}
	}
	
	@Override
	public void read(CompoundNBT tag) {
		super.read(tag);
		this.isDisabled = tag.getBoolean("disabled");
	}
	
	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.putBoolean("disabled", this.isDisabled);
		return super.write(tag);
	}
	
	@Override
	public void setDisabled(boolean disabled) {
		if(this.isDisabled != disabled) {
			this.isDisabled = disabled;
			this.markDirty();
		}
	}
	
	@Override
	public boolean isDisabled() {
		return this.isDisabled;
	}
	
}
