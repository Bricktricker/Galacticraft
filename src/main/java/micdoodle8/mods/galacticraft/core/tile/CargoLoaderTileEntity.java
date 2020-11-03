package micdoodle8.mods.galacticraft.core.tile;

import org.apache.commons.lang3.Validate;

import micdoodle8.mods.galacticraft.core.GCTileEntities;
import micdoodle8.mods.galacticraft.core.inventory.CargoLoaderContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CargoLoaderTileEntity extends CargoBaseTileEntity {

	private boolean hasTarget;
	private boolean hasItems;
	private boolean targetFull;
	private boolean targetHasInventory;

	private int ticks = 0;
	
	private final IIntArray containerStats = new IIntArray() {
		
		@Override
		public int size() {
			return 6;
		}
		
		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0: CargoLoaderTileEntity.this.energyStorage.setEnergy(value); break;
				case 1: CargoLoaderTileEntity.this.hasTarget = value != 0; break;
				case 2: CargoLoaderTileEntity.this.hasItems = value != 0; break;
				case 3: CargoLoaderTileEntity.this.targetFull = value != 0; break;
				case 4: CargoLoaderTileEntity.this.targetHasInventory = value != 0; break;
				case 5: CargoLoaderTileEntity.this.isDisabled = value != 0; break;
			}
		}
		
		@Override
		public int get(int index) {
			switch(index) {
				case 0: return CargoLoaderTileEntity.this.energyStorage.getEnergyStored();
				case 1: return CargoLoaderTileEntity.this.hasTarget ? 1 : 0;
				case 2: return CargoLoaderTileEntity.this.hasItems ? 1 : 0;
				case 3: return CargoLoaderTileEntity.this.targetFull ? 1 : 0;
				case 4: return CargoLoaderTileEntity.this.targetHasInventory ? 1 : 0;
				case 5: return CargoLoaderTileEntity.this.isDisabled ? 1 : 0;
				default: return 0;
			}
		}
		
	};

	public CargoLoaderTileEntity(int tier) {
		super(GCTileEntities.CARGO_LOADER.get(), tier, 14, 10000);
	}

	@Override
	public void tick() {

		if(!this.world.isRemote) {
			this.ticks++;
			if(this.ticks % 100 == 0) {
				this.checkForCargoEntity();
				this.hasTarget = this.attachedInventory.isPresent();
				this.targetHasInventory = this.attachedInventory.map(inv -> inv.getSlots() != 0).orElse(false);
			}
			
			if(this.isDisabled) {
				return;
			}

			if(this.energyStorage.getEnergyStored() < ENERGY_USAGE) {
				return;
			}
			this.energyStorage.extractEnergy(ENERGY_USAGE, false);
			
			if(this.ticks % (this.tier > 1 ? 9 : 15) == 0) {
				this.attachedInventory.ifPresent(inv -> {
					this.hasItems = false;
					this.targetFull = false;
					
					for(int i = 0; i < this.inventory.getSlots(); i++) {
						ItemStack stack = this.inventory.extractItem(i, 1, false);
						if(stack.isEmpty()) {
							continue;
						}
						this.hasItems = true;
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
