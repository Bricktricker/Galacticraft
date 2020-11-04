package micdoodle8.mods.galacticraft.core.datagen;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.blocks.CheeseBlock;
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
		
		//loader
		models().orientable("block/cargo_loader", loc("block/machine"), loc("block/machine_cargoloader"), loc("block/machine"));
		models().orientable("block/cargo_unloader", loc("block/machine"), loc("block/machine_cargounloader"), loc("block/machine"));
		models().orientable("block/fuel_loader", loc("block/machine"), loc("block/machine_fuelloader"), loc("block/machine"));
		models().orientable("block/circuit_fabricator", loc("block/machine"), loc("block/circuit_fabricator"), loc("block/machine"));
		models().orientable("block/deconstructor", loc("block/machine"), loc("block/deconstructor"), loc("block/machine"));
		models().orientable("block/ingot_compressor", loc("block/machine"), loc("block/electric_compressor"), loc("block/machine"));
		models().orientable("block/ingot_compressor_advanced", loc("block/advanced/machine"), loc("block/advanced/electric_compressor"), loc("block/advanced/machine"));
		
		//Cheese block
		CheeseBlock.BITES.getAllowedValues().stream().forEach(slice -> {
			models().getBuilder("cheese_slice" + slice)
				.element().from(slice * 2 + 1, 0, 1).to(15, 8, 15)
					.allFaces((d,f) -> {
						if(d == Direction.DOWN) {
							f.texture("#top").cullface(Direction.DOWN);
						}else if(d == Direction.UP) {
							f.texture("#top");
						}else {
							f.texture("#side");
						}
					}).end()
				.texture("top", loc("block/cheese_1"))
				.texture("side", loc("block/cheese_2"))
				.texture("particle", loc("block/cheese_2"))
				.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
		});
		
		models().cubeBottomTop("block/moon_turf", loc("block/moon_turf_side"), loc("block/moon_dirt"), loc("block/moon_turf"));
	
	}

}
