package micdoodle8.mods.galacticraft.core.datagen;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockBrightLamp;
import micdoodle8.mods.galacticraft.core.blocks.PadFullBlock;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public abstract class BlockStateGenerator extends BlockStateProvider {

	public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Constants.MOD_ID_CORE, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		air(GCBlocks.BREATHEABLE_AIR.get());
		air(GCBlocks.BRIGHT_AIR.get());
		air(GCBlocks.BRIGHT_BREATHEABLE_AIR.get());
		
		directionalBlock(GCBlocks.ARC_LAMP.get(), state -> {
			Direction dir = state.get(BlockBrightLamp.FACING);
			String model = dir.getAxis() == Direction.Axis.Y ? "block/arclamp_base_model" : "block/arclamp_base_side_model";
			return existing(model);
		});
		
		//treasure chest
		simpleBlock(GCBlocks.TREASURE_CHEST_T1.get(),
				models().getBuilder(BlockNames.treasureChestTier1).texture("particle", new ResourceLocation(Constants.MOD_ID_CORE, "block/treasure_chest")));
		
		simpleBlock(GCBlocks.LANDING_PAD.get(), existing("block/landing_pad"));
		getVariantBuilder(GCBlocks.LANDING_PAD_FULL.get())
			.forAllStates(state -> {
				boolean notMiddle = state.get(PadFullBlock.POSITION).intValue() != 4;
				ModelFile model = notMiddle ? existing("block/landing_pad") : existing("block/landing_pad_full");
				return ConfiguredModel.builder().modelFile(model).build();
			});
		
		horizontalBlock(GCBlocks.CARGO_LOADER.get(), existing("block/cargo_loader"));
		horizontalBlock(GCBlocks.CARGO_UNLOADER.get(), existing("block/cargo_unloader"));
		
		
		this.registerModels();
	}
	
	protected abstract void registerModels();
	
	protected void air(Block block) {
		simpleBlock(block, new UncheckedModelFile(new ResourceLocation("block/air")));
	}
	
	protected ExistingModelFile existing(String loc) {
		return new ExistingModelFile(loc(loc), models().existingFileHelper);
	}
	
	protected ResourceLocation loc(String s) {
		return new ResourceLocation(Constants.MOD_ID_CORE, s);
	}

}
