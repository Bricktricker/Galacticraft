package micdoodle8.mods.galacticraft.core.tile;

import org.apache.commons.lang3.Validate;

import micdoodle8.mods.galacticraft.core.GCTileEntities;
import micdoodle8.mods.galacticraft.core.inventory.CargoUnloaderContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CargoUnloaderTileEntity extends CargoBaseTileEntity {

	private boolean hasTarget;
	private boolean targetEmpty;

	private int ticks = 0;
	
	private final IIntArray containerStats = new IIntArray() {

		@Override
		public int size() {
			return 3;
		}

		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0: CargoUnloaderTileEntity.this.energyStorage.setEnergy(value); break;
				case 1: CargoUnloaderTileEntity.this.hasTarget = value != 0; break;
				case 2: CargoUnloaderTileEntity.this.targetEmpty = value != 0; break;
			}
		}
		
		@Override
		public int get(int index) {
			switch(index) {
				case 0: return CargoUnloaderTileEntity.this.energyStorage.getEnergyStored();
				case 1: return CargoUnloaderTileEntity.this.hasTarget ? 1 : 0;
				case 2: return CargoUnloaderTileEntity.this.targetEmpty ? 1 : 0;
				default: return 0;
			}
		}
		
	};

	public CargoUnloaderTileEntity(int tier) {
		super(GCTileEntities.CARGO_UNLOADER.get(), tier, 14, 10000);
	}

	@Override
	public void tick() {

		if(!this.world.isRemote) {
			this.ticks++;
			if(this.ticks % 100 == 0) {
				this.checkForCargoEntity();
				this.hasTarget = this.attachedInventory.isPresent();
			}

			if(this.energyStorage.getEnergyStored() < ENERGY_USAGE) {
				return;
			}
			this.energyStorage.extractEnergy(ENERGY_USAGE, false);

			if(this.ticks % (this.tier > 1 ? 9 : 15) == 0) {
				this.attachedInventory.ifPresent(inv -> {
					this.targetEmpty = true;
					for(int i = 0; i < inv.getSlots(); i++) {
						ItemStack stack = inv.extractItem(i, 1, false);
						if(stack.isEmpty()) {
							continue;
						}
						this.targetEmpty = false;
						for(int j = 0; j < this.inventory.getSlots(); j++) {
							stack = this.inventory.insertItem(j, stack, false);
							if(stack.isEmpty()) {
								return;
							}
						}
						stack = inv.insertItem(i, stack, false);
						Validate.isTrue(stack.isEmpty(), "Could not insert item back into Rocket");
					}
				});
			}
			
			this.markDirty();
		}
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.cargo_unloader");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new CargoUnloaderContainer(p_createMenu_1_, p_createMenu_2_, this, this.containerStats);
	}

	public static class T1 extends CargoUnloaderTileEntity {
		public T1() {
			super(1);
		}
	}
}
