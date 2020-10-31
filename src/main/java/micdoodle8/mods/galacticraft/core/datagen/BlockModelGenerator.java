package micdoodle8.mods.galacticraft.core.datagen;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;

public class BlockModelGenerator extends BlockStateGenerator {

	public BlockModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		//arc lamp
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
	
		models().getBuilder("landing_pad")
			.element().from(0, 0, 0).to(16, 3, 16)
				.allFaces((d,f) -> {
					f.texture("#texture");
					if(d == Direction.DOWN) f.cullface(Direction.DOWN);
				}).end()
			.texture("texture", new ResourceLocation(Constants.MOD_ID_CORE, "block/landing_pad"))
			.texture("particle", new ResourceLocation(Constants.MOD_ID_CORE, "block/landing_pad"))
			.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
	
		models().getBuilder("landing_pad_full")
			.element().from(0, 0, 0).to(16, 4, 16)
				.allFaces((d,f) -> {
					f.texture("#texture");
					if(d == Direction.DOWN) f.cullface(Direction.DOWN);
				}).end()
			.texture("texture", new ResourceLocation(Constants.MOD_ID_CORE, "block/landing_pad"))
			.texture("particle", new ResourceLocation(Constants.MOD_ID_CORE, "block/landing_pad"))
			.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
		
		//Cargo loader
		models().orientable("block/cargo_loader", loc("block/machine"), loc("block/machine_cargoloader"), loc("block/machine"));
	
	}

}
