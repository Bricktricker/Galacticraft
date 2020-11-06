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

}
