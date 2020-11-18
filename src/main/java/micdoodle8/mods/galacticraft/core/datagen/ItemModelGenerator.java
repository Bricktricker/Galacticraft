package micdoodle8.mods.galacticraft.core.datagen;

import java.util.Arrays;
import java.util.function.Supplier;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemModelGenerator extends ItemModelProvider  {

	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Constants.MOD_ID_CORE, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		//Block Items
		forBlockItem(GCItems.LANDING_PAD);
		forBlockItem(GCItems.BUGGY_PAD);
		forBlockItem(GCItems.CARGO_LOADER);
		forBlockItem(GCItems.CARGO_UNLOADER);
		forBlockItem(GCItems.FUEL_LOADER);
		forBlockItem(GCItems.CIRCUT_FABRICATOR);
		forBlockItem(GCItems.DECONSTRUCTOR);
		forBlockItem(GCItems.INGOT_COMPRESSOR);
		forBlockItem(GCItems.INGOT_COMPRESSOR_ADVANCED);
		forBlockItem(GCItems.OXYGEN_COMPRESSOR);
		forBlockItem(GCItems.OXYGEN_DECOMPRESSOR);
		forBlockItem(GCItems.OXYGEN_COLLECTOR);
		
		forBlockItem(GCItems.MOON_DIRT);
		forBlockItem(GCItems.MOON_BRICK);
		forBlockItem(GCItems.MOON_STONE);
		forBlockItem(GCItems.MOON_TURF);
		
		forBlockItem(GCItems.SAPPHIRE_ORE);
		forBlockItem(GCItems.COPPER_ORE);
		forBlockItem(GCItems.COPPER_ORE_MOON);
		forBlockItem(GCItems.TIN_ORE);
		forBlockItem(GCItems.TIN_ORE_MOON);
		forBlockItem(GCItems.ALUMINIUM_ORE);
		forBlockItem(GCItems.SILICON_ORE);
		
		forBlockItem(GCItems.CHEESE.get(), "cheese_slice0");
		
		//Items
		generatedModel(GCItems.OX_MASK.get());
		generatedModel(GCItems.OX_GEAR.get());
		generatedModel(GCItems.SENSOR_GLASSES.get());
		generatedModel(GCItems.FREQUENCY_MODULE.get());
		
		Arrays.stream(DyeColor.values())
			.forEach(color -> {
				Item parachute = ForgeRegistries.ITEMS.getValue(modLoc("parachute_" + color.getName()));
				generatedModel(parachute);
			});
		
		generatedModel(GCItems.EMERGENCY_KIT.get());
		//TODO: rocket models
		generatedModel(GCItems.TIN_CANISTER.get());
		generatedModel(GCItems.COPPER_CANISTER.get());
		generatedModel(GCItems.SENSOR_LENS.get());
		generatedModel(GCItems.AIR_VENT.get());
		generatedModel(GCItems.OX_FAN.get());
		generatedModel(GCItems.OX_CONCENTRATOR.get());
		generatedModel(GCItems.ROCKET_ENGINE_T1.get());
		generatedModel(GCItems.ROCKET_BOOSTER_T1.get());
		generatedModel(GCItems.HEAVY_PLATING_T1.get());
		generatedModel(GCItems.PART_NOSE_CONE.get());
		generatedModel(GCItems.PART_FINS.get());
		generatedModel(GCItems.FLAG_POLE.get());
		generatedModel(GCItems.CANVAS.get());
		generatedModel(GCItems.BUGGY_WHEEL.get());
		generatedModel(GCItems.BUGGY_SEAT.get());
		generatedModel(GCItems.BUGGY_STORAGE.get());
		//TODO: buggy models
		generatedModel(GCItems.RAW_SILICON.get());
		generatedModel(GCItems.BASIC_WAFER.get());
		generatedModel(GCItems.ADVANCED_WAFER.get());
		generatedModel(GCItems.AMBIENT_THERMAL_CONTROLLER.get());
		generatedModel(GCItems.DEHYDRATED_APPLE.get());
		generatedModel(GCItems.DEHYDRATED_CARROT.get());
		generatedModel(GCItems.DEHYDRATED_MELON.get());
		generatedModel(GCItems.DEHYDRATED_POTATO.get());
		generatedModel(GCItems.CANNED_BEEF.get());
		generatedModel(GCItems.CHEESE_SLICE.get());
		generatedModel(GCItems.BURGER_BUN.get());
		generatedModel(GCItems.BEEF_PATTY_RAW.get());
		generatedModel(GCItems.BEEF_PATTY_COOCKED.get());
		generatedModel(GCItems.CHEESBURGER.get());
		generatedModel(GCItems.CHEESE_CURD.get());
		generatedModel(GCItems.RAW_METEORIC_IRON.get());
		generatedModel(GCItems.LUNAR_SAPPHIRE.get());
		//TODO: flag
		generatedModel(GCItems.PRELAUNCH_CHECKLIST.get());
		generatedModel(GCItems.DUNGEON_FINDER.get());
		generatedModel(GCItems.SCHEMATIC_BUGGY.get());
		generatedModel(GCItems.SCHEMATIC_ROCKET_T2.get());
		generatedModel(GCItems.COMPRESSED_COPPER.get());
		generatedModel(GCItems.COMPRESSED_TIN.get());
		generatedModel(GCItems.COMPRESSED_ALUMINUM.get());
		generatedModel(GCItems.COMPRESSED_STEEL.get());
		generatedModel(GCItems.COMPRESSED_BRONZE.get());
		generatedModel(GCItems.COMPRESSED_IRON.get());
		generatedModel(GCItems.COMPRESSED_METEORIC_IRON.get());
		generatedModel(GCItems.COPPER_INGOT.get());
		generatedModel(GCItems.TIN_INGOT.get());
		generatedModel(GCItems.ALUMINUM_INGOT.get());
		generatedModel(GCItems.METEORIC_IRON_INGOT.get());
		generatedModel(GCItems.WRENCH.get());
		
		parentModel(GCItems.BUGGY.get(), "buggy/buggy_inv");
		parentModel(GCItems.BUGGY_CARGO_1.get(), "buggy/buggy_inv");
		parentModel(GCItems.BUGGY_CARGO_2.get(), "buggy/buggy_inv");
		parentModel(GCItems.BUGGY_CARGO_3.get(), "buggy/buggy_inv");
		
		parentModel(GCItems.NASA_WORKBENCH.get(), "block/nasa_workbench")
			.transforms()
				.transform(Perspective.GUI)
					.rotation(30, 45, 0)
					.translation(0, -4, 0)
					.scale(0.25f)
				.end()
				.transform(Perspective.GROUND)
					.translation(0, 3, 0)
					.scale(0.25f)
				.end()
				.transform(Perspective.FIXED)
					.translation(0, -4, -4)
					.scale(0.25f)
				.end()
				.transform(Perspective.FIRSTPERSON_RIGHT)
					.rotation(0, 200, 0)
					.scale(0.25f)
				.end()
				.transform(Perspective.THIRDPERSON_RIGHT)
					.rotation(90, 0, 0)
					.translation(0, 4, -2)
					.scale(0.25f)
				.end()
			.end();
		
		//Meteor chunks
		getBuilder(itemName(GCItems.METEOR_CHUNK.get()).toString())
			.parent(new ExistingModelFile(modLoc("item/meteor_chunk_model"), this.existingFileHelper))
			.texture("texture", modLoc("block/meteor_chunk"))
			.texture("particle", modLoc("block/meteor_chunk"));
		getBuilder(itemName(GCItems.METEOR_CHUNK_HOT.get()).toString())
			.parent(new ExistingModelFile(modLoc("item/meteor_chunk_model"), this.existingFileHelper))
			.texture("texture", modLoc("block/meteor_chunk_hot"))
			.texture("particle", modLoc("block/meteor_chunk_hot"));
	}
	
	protected ItemModelBuilder generatedModel(IItemProvider item) {
		return generatedModel(item, itemName(item));
	}
	
	protected ItemModelBuilder generatedModel(IItemProvider item, ResourceLocation texture) {
		return getBuilder(itemName(item).toString())
			.parent(new UncheckedModelFile("item/generated"))
			.texture("layer0", texture);
	}
	
	protected ItemModelBuilder parentModel(IItemProvider item, String model) {
		return getBuilder(itemName(item).toString())
					.parent(new ExistingModelFile(modLoc(model), this.existingFileHelper));
	}
	
	protected ItemModelBuilder forBlockItem(Supplier<BlockItem> block) {
		return forBlockItem(block.get());
	}
	
	protected ItemModelBuilder forBlockItem(BlockItem block) {
		return forBlockItem(block, block.getRegistryName().getPath());
	}
	
	protected ItemModelBuilder forBlockItem(BlockItem block, String model) {
		return getBuilder(itemName(block).toString())
				.parent(new UncheckedModelFile(modLoc("block/" + model)));
	}
	
	protected ResourceLocation itemName(IItemProvider item) {
		return modLoc("item/" + item.asItem().getRegistryName().getPath());
	}

}
