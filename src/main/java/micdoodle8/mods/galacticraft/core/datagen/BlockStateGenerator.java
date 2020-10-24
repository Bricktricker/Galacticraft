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

public class BlockStateGenerator extends BlockStateProvider {

	public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Constants.MOD_ID_CORE, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		air(GCBlocks.breatheableAir);
		air(GCBlocks.brightAir);
		air(GCBlocks.brightBreatheableAir);
		
		//Arc lamp
		models().getBuilder("arclamp_base_model")
			.element().from(2, 0, 2).to(14, 1, 14)
				.allFaces((dir, face) -> {
					if(dir == Direction.UP) {
						face.uvs(0, 0, 8, 8);
					}else if(dir == Direction.DOWN) {
						face.uvs(8, 8, 0, 0);
					}else {
						face.uvs(0, 7, 8, 8);
					}
					face.texture("#texture");
				}).end()
			.texture("particle", new ResourceLocation(Constants.MOD_ID_CORE, "block/arc_lamp_off"))
			.texture("texture", new ResourceLocation(Constants.MOD_ID_CORE, "block/arc_lamp_off"))
			.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
		
		models().getBuilder("arclamp_base_side_model")
		.element().from(0, 2, 2).to(1, 14, 14)
			.allFaces((dir, face) -> {
				if(dir == Direction.WEST || dir == Direction.EAST) {
					face.uvs(0, 0, 8, 8);
				}else {
					face.uvs(0, 0, 1, 8);
				}
				face.texture("#texture");
			}).end()
		.texture("particle", new ResourceLocation(Constants.MOD_ID_CORE, "block/arc_lamp_off"))
		.texture("texture", new ResourceLocation(Constants.MOD_ID_CORE, "block/arc_lamp_off"))
		.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
		
		directionalBlock(GCBlocks.arcLamp, state -> {
			Direction dir = state.get(BlockBrightLamp.FACING);
			String model = dir.getAxis() == Direction.Axis.Y ? "block/arclamp_base_model" : "block/arclamp_base_side_model";
			return new ExistingModelFile(new ResourceLocation(Constants.MOD_ID_CORE, model), models().existingFileHelper);
		});
		
		//treasure chest
		simpleBlock(GCBlocks.treasureChestTier1,
				models().getBuilder(BlockNames.treasureChestTier1).texture("particle", new ResourceLocation(Constants.MOD_ID_CORE, "model/treasure")));
		
		//landing pads TODO: evaluate and remove old files
		models().getBuilder("landing_pad")
			.element().from(0, 0, 0).to(16, 3, 16)
			.cube("#texture")
			.end()
		.texture("texture", new ResourceLocation(Constants.MOD_ID_CORE, "block/landing_pad"))
		.texture("particle", new ResourceLocation(Constants.MOD_ID_CORE, "block/landing_pad"))
		.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
		
		models().getBuilder("landing_pad_full")
			.element().from(0, 0, 0).to(16, 4, 16)
			.cube("#texture")
			.end()
		.texture("texture", new ResourceLocation(Constants.MOD_ID_CORE, "block/landing_pad"))
		.texture("particle", new ResourceLocation(Constants.MOD_ID_CORE, "block/landing_pad"))
		.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
		
		simpleBlock(GCBlocks.landingPad, new ExistingModelFile(new ResourceLocation(Constants.MOD_ID_CORE ,"block/landing_pad"), models().existingFileHelper));
		getVariantBuilder(GCBlocks.landingPadFull)
			.forAllStates(state -> {
				boolean middle = state.get(PadFullBlock.POSITION).intValue() == 5;
				ModelFile model = middle ? new ExistingModelFile(new ResourceLocation(Constants.MOD_ID_CORE ,"block/landing_pad"), models().existingFileHelper) : new ExistingModelFile(new ResourceLocation(Constants.MOD_ID_CORE ,"block/landing_pad_full"), models().existingFileHelper);
				return ConfiguredModel.builder().modelFile(model).build();
			});
		
	}
	
	protected void air(Block block) {
		simpleBlock(block, new UncheckedModelFile(new ResourceLocation("block/air")));
	}
	
	protected ExistingModelFile existing(String loc) {
		return new ExistingModelFile(new ResourceLocation(Constants.MOD_ID_CORE, loc), models().existingFileHelper);
	}

}
