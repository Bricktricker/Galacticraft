package micdoodle8.mods.galacticraft.core.datagen;

import java.util.function.Supplier;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;

public class ItemModelGenerator extends ItemModelProvider  {

	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Constants.MOD_ID_CORE, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		forBlockItem(GCItems.LANDING_PAD);
		forBlockItem(GCItems.CARGO_LOADER);
		forBlockItem(GCItems.CARGO_UNLOADER);
		forBlockItem(GCItems.FUEL_LOADER);
		forBlockItem(GCItems.CIRCUT_FABRICATOR);
		forBlockItem(GCItems.DECONSTRUCTOR);
		forBlockItem(GCItems.INGOT_COMPRESSOR);
		forBlockItem(GCItems.INGOT_COMPRESSOR_ADVANCED);
		forBlockItem(GCItems.MOON_DIRT);
		forBlockItem(GCItems.MOON_BRICK);
		forBlockItem(GCItems.SAPPHIRE_ORE);
		forBlockItem(GCItems.COPPER_ORE);
		forBlockItem(GCItems.COPPER_ORE_MOON);
		forBlockItem(GCItems.TIN_ORE);
		forBlockItem(GCItems.TIN_ORE_MOON);
		forBlockItem(GCItems.ALUMINIUM_ORE);
		forBlockItem(GCItems.SILICON_ORE);
		forBlockItem(GCItems.CHEESE.get(), "cheese_slice0");
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
