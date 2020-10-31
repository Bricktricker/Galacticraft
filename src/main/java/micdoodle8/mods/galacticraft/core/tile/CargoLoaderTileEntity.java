package micdoodle8.mods.galacticraft.core.tile;

import org.apache.commons.lang3.Validate;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.inventory.CargoLoaderContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ObjectHolder;

public class CargoLoaderTileEntity extends CargoBaseTileEntity {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.cargoLoader)
	public static TileEntityType<CargoLoaderTileEntity> TYPE;

	private boolean noTarget;
	private boolean outOfItems;
	private boolean targetFull;
	private boolean targetNoInventory;

	private int ticks = 0;
	
	private final IIntArray containerStats = new IIntArray() {
		
		@Override
		public int size() {
			return 5;
		}
		
		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0: CargoLoaderTileEntity.this.energyStorage.setEnergy(value);
				case 1: CargoLoaderTileEntity.this.noTarget = value != 0;
				case 2: CargoLoaderTileEntity.this.outOfItems = value != 0;
				case 3: CargoLoaderTileEntity.this.targetFull = value != 0;
				case 4: CargoLoaderTileEntity.this.targetNoInventory = value != 0;
			}
		}
		
		@Override
		public int get(int index) {
			switch(index) {
				case 0: return CargoLoaderTileEntity.this.energyStorage.getEnergyStored();
				case 1: return CargoLoaderTileEntity.this.noTarget ? 1 : 0;
				case 2: return CargoLoaderTileEntity.this.outOfItems ? 1 : 0;
				case 3: return CargoLoaderTileEntity.this.targetFull ? 1 : 0;
				case 4: return CargoLoaderTileEntity.this.targetNoInventory ? 1 : 0;
				default: return 0;
			}
		}
		
	};

	public CargoLoaderTileEntity(int tier) {
		super(TYPE, tier, 14, 10000);
	}

	@Override
	public void tick() {

		if(!this.world.isRemote) {
			this.ticks++;
			if(this.ticks % 100 == 0) {
				this.checkForCargoEntity();
				this.noTarget = !this.attachedInventory.isPresent();
			}

			if(this.energyStorage.getEnergyStored() < ENERGY_USAGE) {
				return;
			}
			this.energyStorage.extractEnergy(ENERGY_USAGE, false);
			
			if(this.ticks % (this.tier > 1 ? 9 : 15) == 0) {
				this.attachedInventory.ifPresent(inv -> {
					this.outOfItems = true;
					this.targetFull = false;
					
					if(inv.getSlots() == 0) {
						this.targetNoInventory = true;
					}else {
						this.targetNoInventory = false;
					}
					
					for(int i = 0; i < this.inventory.getSlots(); i++) {
						ItemStack stack = this.inventory.extractItem(i, 1, false);
						if(stack.isEmpty()) {
							continue;
						}
						this.outOfItems = false;
						for(int j = 0; j < inv.getSlots(); j++) {
							stack = inv.insertItem(j, stack, false);
							if(stack.isEmpty()) {
								return;
							}
						}
						stack = this.inventory.insertItem(i, stack, false);
						Validate.isTrue(stack.isEmpty(), "Could not insert item back into CargoLoader");
					}
					this.targetFull = true;
				});	
			}
			
			this.markDirty();
		}

	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.cargo_loader");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new CargoLoaderContainer(p_createMenu_1_, p_createMenu_2_, this, this.containerStats);
	}

	public static class T1 extends CargoLoaderTileEntity {
		public T1() {
			super(1);
		}
	}

}
