package micdoodle8.mods.galacticraft.core.datagen;

import micdoodle8.mods.galacticraft.core.blocks.CheeseBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
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
				.texture("particle", modLoc("block/arc_lamp_off"))
				.texture("texture", modLoc("block/arc_lamp_off"))
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
			.texture("particle", modLoc("block/arc_lamp_off"))
			.texture("texture", modLoc("block/arc_lamp_off"))
			.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
	
		models().getBuilder("pad_model")
			.element().from(0, 0, 0).to(16, 3, 16)
				.allFaces((d,f) -> {
					f.texture("#texture");
					if(d == Direction.DOWN) f.cullface(Direction.DOWN);
				}).end()
			.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
	
		models().getBuilder("pad_full_model")
			.element().from(0, 0, 0).to(16, 4, 16)
				.allFaces((d,f) -> {
					f.texture("#texture");
					if(d == Direction.DOWN) f.cullface(Direction.DOWN);
				}).end()
			.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
		
		models().getBuilder("rocket_pad")
			.texture("texture", modLoc("block/landing_pad"))
			.texture("particle", modLoc("block/landing_pad"))
			.parent(existing("block/pad_model"));
		
		models().getBuilder("rocket_pad_full")
			.texture("texture", modLoc("block/landing_pad"))
			.texture("particle", modLoc("block/landing_pad"))
			.parent(existing("block/pad_full_model"));
		
		models().getBuilder("buggy_pad")
			.texture("texture", modLoc("block/buggy_pad"))
			.texture("particle", modLoc("block/buggy_pad"))
			.parent(existing("block/pad_model"));
	
		models().getBuilder("buggy_pad_full")
			.texture("texture", modLoc("block/buggy_pad_full"))
			.texture("particle", modLoc("block/buggy_pad_full"))
			.parent(existing("block/pad_full_model"));
		
		//loader
		models().orientable("block/cargo_loader", modLoc("block/machine"), modLoc("block/machine_cargoloader"), modLoc("block/machine"));
		models().orientable("block/cargo_unloader", modLoc("block/machine"), modLoc("block/machine_cargounloader"), modLoc("block/machine"));
		models().orientable("block/fuel_loader", modLoc("block/machine"), modLoc("block/machine_fuelloader"), modLoc("block/machine"));
		models().orientable("block/circuit_fabricator", modLoc("block/machine"), modLoc("block/circuit_fabricator"), modLoc("block/machine"));
		models().orientable("block/deconstructor", modLoc("block/machine"), modLoc("block/deconstructor"), modLoc("block/machine"));
		models().orientable("block/ingot_compressor", modLoc("block/machine"), modLoc("block/electric_compressor"), modLoc("block/machine"));
		models().orientable("block/ingot_compressor_advanced", modLoc("block/advanced/machine"), modLoc("block/advanced/electric_compressor"), modLoc("block/advanced/machine"));
		
		backFrontModel("block/oxygen_compressor", modLoc("block/machine"), modLoc("block/oxygen_compressor"), modLoc("block/oxygen_compressor_back"));
		backFrontModel("block/oxygen_decompressor", modLoc("block/machine"), modLoc("block/oxygen_decompressor"), modLoc("block/oxygen_compressor_back"));
		backFrontModel("block/oxygen_collector", modLoc("block/machine"), modLoc("block/oxygen_collector"), modLoc("block/oxygen_collector")).texture("up", modLoc("block/oxygen_collector"));
		
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
				.texture("top", modLoc("block/cheese_1"))
				.texture("side", modLoc("block/cheese_2"))
				.texture("particle", modLoc("block/cheese_2"))
				.parent(new ExistingModelFile(new ResourceLocation("block/block"), models().existingFileHelper));
		});
		
		models().cubeBottomTop("block/moon_turf", modLoc("block/moon_turf_side"), modLoc("block/moon_dirt"), modLoc("block/moon_turf"));
	
	}
	
	protected BlockModelBuilder backFrontModel(String name, ResourceLocation side, ResourceLocation front, ResourceLocation back) {
		return models().withExistingParent(name, "block/cube")
    		.texture("down", side)
    		.texture("up", "#down")
    		.texture("north", front)
    		.texture("south", back)
    		.texture("west", side)
    		.texture("east", side)
    		.texture("particle", side);
	}

}
