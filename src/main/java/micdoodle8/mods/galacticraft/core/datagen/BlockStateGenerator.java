package micdoodle8.mods.galacticraft.core.datagen;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockBrightLamp;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
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
		
		simpleBlock(GCBlocks.treasureChestTier1,
				models().getBuilder(BlockNames.treasureChestTier1).texture("particle", new ResourceLocation(Constants.MOD_ID_CORE, "model/treasure")));
		
		
	}
	
	protected void air(Block block) {
		simpleBlock(block, new UncheckedModelFile(new ResourceLocation("block/air")));
	}
	
	protected ExistingModelFile existing(String loc) {
		return new ExistingModelFile(new ResourceLocation(Constants.MOD_ID_CORE, loc), models().existingFileHelper);
	}

}
