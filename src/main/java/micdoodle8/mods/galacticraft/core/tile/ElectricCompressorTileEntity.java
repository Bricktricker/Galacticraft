package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.recipe.ShapedRecipesGC;
import micdoodle8.mods.galacticraft.core.GCTileEntities;
import micdoodle8.mods.galacticraft.core.client.sounds.GCSounds;
import micdoodle8.mods.galacticraft.core.inventory.ElectricIngotCompressorContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ElectricCompressorTileEntity extends ProcessingTileEntity<ShapedRecipesGC> {
	private static final int PROCESS_TIME_REQUIRED_BASE = 200;
	public static final int INV_SIZE = 11;

	private final int processingTime;

	public ElectricCompressorTileEntity(TileEntityType<?> type, int maxEnergy, int energyUsage, int processingTime) {
		super(type, INV_SIZE, maxEnergy, energyUsage);
		this.processingTime = processingTime;
	}

	@Override
	protected ShapedRecipesGC getRecipe() {
		return null; // TODO: implment
	}

	@Override
	protected int getProcessingTime(ShapedRecipesGC recipe) {
		return processingTime;
	}

	@Override
	protected void recipeFinished(ShapedRecipesGC recipe) {
		// TODO: implement
	}

	protected abstract void playSound();

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new ElectricIngotCompressorContainer(p_createMenu_1_, p_createMenu_2_, this, this.containerStats);
	}

	public static class T1 extends ElectricCompressorTileEntity {

		public T1() {
			super(GCTileEntities.INGOT_COMPRESSOR.get(), 1000, 75, PROCESS_TIME_REQUIRED_BASE);
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TranslationTextComponent("container.galacticraftcore.electric_compressor.1");
		}

		@Override
		protected void playSound() {
			this.world.playSound(null, this.getPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.12F, this.world.rand.nextFloat() * 0.1F - 9.5F);
		}
	}

	public static class T2 extends ElectricCompressorTileEntity {

		public T2() {
			super(GCTileEntities.INGOT_COMPRESSOR_ADVANCED.get(), 1500, 75, PROCESS_TIME_REQUIRED_BASE * 3 / 5);
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TranslationTextComponent("container.galacticraftcore.electric_compressor.2");
		}

		@Override
		protected void playSound() {
			this.world.playSound(null, this.getPos(), GCSounds.advanced_compressor, SoundCategory.BLOCKS, 0.23F, this.world.rand.nextFloat() * 0.1F + 9.5F);
		}
	}

}