package micdoodle8.mods.galacticraft.core.tile;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;

public abstract class ProcessingTileEntity<R extends IRecipe<?>> extends EnergyInventoryTileEntity implements ITickableTileEntity {

	protected int energyUsage; // Energy usage per tick
	protected int progress; // progress in ticks

	private R oldRecipe;
	private int recipeDuration;
	
	protected final IIntArray containerStats = new IIntArray() {
		
		@Override
		public int size() {
			return 3;
		}
		
		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0: ProcessingTileEntity.this.progress = value; break;
				case 1: ProcessingTileEntity.this.recipeDuration = value; break;
				case 2: ProcessingTileEntity.this.energyStorage.setEnergy(value); break;
			}
		}
		
		@Override
		public int get(int index) {
			switch(index) {
				case 0: return ProcessingTileEntity.this.progress;
				case 1: return ProcessingTileEntity.this.recipeDuration;
				case 2: return ProcessingTileEntity.this.getStoredEnergy();
				default: return 0;
			}
		}
	};

	public ProcessingTileEntity(TileEntityType<?> type, int slots, int maxEnergy, int energyUsage) {
		super(type, slots, maxEnergy);
		this.energyUsage = energyUsage;
	}

	@Override
	public void tick() {
		if(this.world.isRemote) {
			return;
		}

		if(this.getStoredEnergy() < energyUsage) {
			return;
		}

		R recipe = this.getRecipe();
		if(recipe == null) {
			return;
		}

		this.useEnergy(energyUsage);

		if(oldRecipe == null || oldRecipe != recipe) {
			progress = 0;
			oldRecipe = recipe;
			recipeDuration = this.getProcessingTime(recipe);
		}else {
			progress++;
			if(progress >= this.recipeDuration) {
				progress = 0;
				oldRecipe = null;
				this.recipeFinished(recipe);
			}
		}
		
		this.markDirty();
	}
	
	@Override
	public void read(CompoundNBT tag) {
		this.progress = tag.getInt("progress");

		super.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.putInt("progress", this.progress);

		return super.write(tag);
	}

	protected abstract R getRecipe();

	protected abstract int getProcessingTime(R recipe);

	protected abstract void recipeFinished(R recipe);

}
