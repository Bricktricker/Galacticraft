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
		generatedModel(GCItems.OX_MASK.get(), loc("item/oxygen_mask"));
		generatedModel(GCItems.OX_GEAR.get(), loc("item/oxygen_gear"));
		generatedModel(GCItems.SENSOR_GLASSES.get(), loc("item/sensor_glasses"));
		generatedModel(GCItems.FREQUENCY_MODULE.get(), loc("item/frequency_module"));
		
		Arrays.stream(DyeColor.values())
			.forEach(color -> {
				Item parachute = ForgeRegistries.ITEMS.getValue(loc("parachute_" + color.getName()));
				generatedModel(parachute, loc("item/parachute_" + color.getName()));
			});
		
		generatedModel(GCItems.EMERGENCY_KIT.get(), loc("item/emergency_kit"));
		//TODO: rocket models
		generatedModel(GCItems.TIN_CANISTER.get(), loc("item/tin_canister"));
		generatedModel(GCItems.COPPER_CANISTER.get(), loc("item/copper_canister"));
		generatedModel(GCItems.SENSOR_LENS.get(), loc("item/sensor_lens"));
		generatedModel(GCItems.AIR_VENT.get(), loc("item/air_vent"));
		generatedModel(GCItems.OX_FAN.get(), loc("item/oxygen_fan"));
		generatedModel(GCItems.OX_CONCENTRATOR.get(), loc("item/oxygen_concentrator"));
		generatedModel(GCItems.ROCKET_ENGINE_T1.get(), loc("item/tier1engine"));
		generatedModel(GCItems.ROCKET_BOOSTER_T1.get(), loc("item/tier1booster"));
		generatedModel(GCItems.HEAVY_PLATING_T1.get(), loc("item/heavy_plating"));
		generatedModel(GCItems.PART_NOSE_CONE.get(), loc("item/nose_cone"));
		generatedModel(GCItems.PART_FINS.get(), loc("item/rocket_fins"));
		generatedModel(GCItems.FLAG_POLE.get(), loc("item/flag_pole"));
		generatedModel(GCItems.CANVAS.get(), loc("item/canvas"));
		generatedModel(GCItems.BUGGY_WHEEL.get(), loc("item/buggy_wheel"));
		generatedModel(GCItems.BUGGY_SEAT.get(), loc("item/buggy_seat"));
		generatedModel(GCItems.BUGGY_STORAGE.get(), loc("item/buggy_storage"));
		//TODO: buggy models
		generatedModel(GCItems.RAW_SILICON.get(), loc("item/raw_silicon"));
		generatedModel(GCItems.BASIC_WAFER.get(), loc("item/basic_wafer"));
		generatedModel(GCItems.ADVANCED_WAFER.get(), loc("item/advanced_wafer"));
		generatedModel(GCItems.AMBIENT_THERMAL_CONTROLLER.get(), loc("item/ambient_thermal_controller"));
	}
	
	protected ItemModelBuilder generatedModel(IItemProvider item) {
		return generatedModel(item, itemName(item));
	}
	
	protected ItemModelBuilder generatedModel(IItemProvider item, ResourceLocation texture) {
		return getBuilder(itemName(item).toString())
			.parent(new UncheckedModelFile("item/generated"))
			.texture("layer0", texture);
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
	
	protected ResourceLocation loc(String s) {
		return new ResourceLocation(Constants.MOD_ID_CORE, s);
	}

}
