package micdoodle8.mods.galacticraft.core.datagen;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockBrightLamp;
import micdoodle8.mods.galacticraft.core.blocks.NasaWorkbenchBlock;
import micdoodle8.mods.galacticraft.core.blocks.CheeseBlock;
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
		
		simpleBlock(GCBlocks.ROCKET_PAD.get(), existing("block/rocket_pad"));
		getVariantBuilder(GCBlocks.LANDING_PAD_FULL.get())
			.forAllStates(state -> {
				boolean notMiddle = state.get(PadFullBlock.POSITION).intValue() != 4;
				ModelFile model = notMiddle ? existing("block/rocket_pad") : existing("block/rocket_pad_full");
				return ConfiguredModel.builder().modelFile(model).build();
			});
		
		simpleBlock(GCBlocks.BUGGY_PAD.get(), existing("block/buggy_pad"));
		getVariantBuilder(GCBlocks.BUGGY_PAD_FULL.get())
			.forAllStates(state -> {
				boolean notMiddle = state.get(PadFullBlock.POSITION).intValue() != 4;
				ModelFile model = notMiddle ? existing("block/buggy_pad") : existing("block/buggy_pad_full");
				return ConfiguredModel.builder().modelFile(model).build();
			});
		
		horizontalBlock(GCBlocks.CARGO_LOADER.get(), existing("block/cargo_loader"));
		horizontalBlock(GCBlocks.CARGO_UNLOADER.get(), existing("block/cargo_unloader"));
		horizontalBlock(GCBlocks.FUEL_LOADER.get(), existing("block/fuel_loader"));
		horizontalBlock(GCBlocks.CIRCUT_FABRICATOR.get(), existing("block/circuit_fabricator"));
		horizontalBlock(GCBlocks.DECONSTRUCTOR.get(), existing("block/deconstructor"));
		horizontalBlock(GCBlocks.INGOT_COMPRESSOR.get(), existing("block/ingot_compressor"));
		horizontalBlock(GCBlocks.INGOT_COMPRESSOR_ADVANCED.get(), existing("block/ingot_compressor_advanced"));
		horizontalBlock(GCBlocks.OXYGEN_COMPRESSOR.get(), existing("block/oxygen_compressor"));
		horizontalBlock(GCBlocks.OXYGEN_DECOMPRESSOR.get(), existing("block/oxygen_decompressor"));
		horizontalBlock(GCBlocks.OXYGEN_COLLECTOR.get(), existing("block/oxygen_collector"));
		
		getVariantBuilder(GCBlocks.CHEESE.get())
			.forAllStates(state -> {
				int bites = state.get(CheeseBlock.BITES);
				ModelFile model = existing("block/cheese_slice" + bites);
				return ConfiguredModel.builder().modelFile(model).build();
			});
		
		simpleBlock(GCBlocks.MOON_DIRT.get());
		simpleBlock(GCBlocks.MOON_BRICK.get());
		simpleBlock(GCBlocks.MOON_STONE.get());
		simpleBlock(GCBlocks.MOON_TURF.get(), existing("block/moon_turf"));
		
		simpleBlock(GCBlocks.SAPPHIRE_ORE.get());
		simpleBlock(GCBlocks.COPPER_ORE.get());
		simpleBlock(GCBlocks.COPPER_ORE_MOON.get());
		simpleBlock(GCBlocks.TIN_ORE.get());
		simpleBlock(GCBlocks.TIN_ORE_MOON.get());
		simpleBlock(GCBlocks.ALUMINIUM_ORE.get());
		simpleBlock(GCBlocks.SILICON_ORE.get());
		
		getVariantBuilder(GCBlocks.NASA_WORKBENCH.get())
			.forAllStates(state -> {
				int id = state.get(NasaWorkbenchBlock.POSITION);
				ModelFile model = id == 0 ? existing("block/nasa_workbench") : new UncheckedModelFile(new ResourceLocation("block/air"));
				return ConfiguredModel.builder().modelFile(model).build();
			});
		
		this.registerModels();
	}
	
	protected abstract void registerModels();
	
	protected void air(Block block) {
		simpleBlock(block, new UncheckedModelFile(new ResourceLocation("block/air")));
	}
	
	protected ExistingModelFile existing(String loc) {
		return new ExistingModelFile(modLoc(loc), models().existingFileHelper);
	}

}
