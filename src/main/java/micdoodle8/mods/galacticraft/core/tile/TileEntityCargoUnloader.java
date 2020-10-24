package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.entity.ICargoEntity;
import micdoodle8.mods.galacticraft.api.entity.ICargoEntity.EnumCargoLoadingState;
import micdoodle8.mods.galacticraft.api.entity.ICargoEntity.RemovalResult;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.inventory.CargoLoaderContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntityCargoUnloader extends CargoBaseTileEntity {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.cargoUnloader)
	public static TileEntityType<TileEntityCargoUnloader> TYPE;

	public boolean targetEmpty;
	public boolean targetNoInventory;
	public boolean noTarget;

	private int ticks = 0;

	public TileEntityCargoUnloader(int tier) {
		super(TYPE, 14, 10000, tier);
	}

	@Override
	public void tick() {

		if(!this.world.isRemote) {
			if(this.ticks % 100 == 0) {
				this.checkForCargoEntity();
			}

			if(this.attachedFuelable != null) {
				this.noTarget = false;
				RemovalResult result = this.attachedFuelable.removeCargo(false);

				if(!result.resultStack.isEmpty()) {
					this.targetEmpty = false;

					EnumCargoLoadingState state = this.addCargo(result.resultStack, false);

					this.targetEmpty = state == EnumCargoLoadingState.EMPTY;

					if(this.energyStorage.getEnergyStored() < ENERGY_USAGE) {
						return;
					}

					this.energyStorage.extractEnergy(ENERGY_USAGE, false);

					if(this.ticks % (this.tier > 1 ? 9 : 15) == 0 && state == EnumCargoLoadingState.SUCCESS && !this.removed) {
						this.addCargo(this.attachedFuelable.removeCargo(true).resultStack, true);
					}
				}else {
					this.targetNoInventory = result.resultState == EnumCargoLoadingState.NOINVENTORY;
					this.noTarget = result.resultState == EnumCargoLoadingState.NOTARGET;
					this.targetEmpty = true;
				}
			}else {
				this.noTarget = true;
			}
		}
	}

	public void checkForCargoEntity() {
		boolean foundFuelable = false;

		BlockVec3 thisVec = new BlockVec3(this);
		for(final Direction dir : Direction.values()) {
			final TileEntity pad = thisVec.getTileEntityOnSide(this.world, dir);

			if(pad instanceof TileEntityFake) {
				final TileEntity mainTile = ((TileEntityFake) pad).getMainBlockTile();

				if(mainTile instanceof ICargoEntity) {
					this.attachedFuelable = (ICargoEntity) mainTile;
					foundFuelable = true;
					break;
				}
			}else if(pad instanceof ICargoEntity) {
				this.attachedFuelable = (ICargoEntity) pad;
				foundFuelable = true;
				break;
			}
		}

		if(!foundFuelable) {
			this.attachedFuelable = null;
		}
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.cargo_unloader");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new CargoLoaderContainer(p_createMenu_1_, p_createMenu_2_, this);
	}
}
