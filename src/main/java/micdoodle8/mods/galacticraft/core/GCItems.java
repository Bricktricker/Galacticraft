package micdoodle8.mods.galacticraft.core;

import com.google.common.collect.Maps;

import micdoodle8.mods.galacticraft.core.entities.MoonBuggyEntity.BuggyType;
import micdoodle8.mods.galacticraft.core.items.*;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.StackSorted;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.*;

public class GCItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, Constants.MOD_ID_CORE);
	
	//----------- Items Tab ----------------
	
	public static final RegistryObject<ItemOxygenMask> OX_MASK = ITEMS.register(ItemNames.oxMask, () -> new ItemOxygenMask(defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemOxygenGear> OX_GEAR = ITEMS.register(ItemNames.oxygenGear, () -> new ItemOxygenGear(defaultItemBuilder()));
	
	public static final RegistryObject<ItemOxygenTank> OX_TANK_LIGHT = ITEMS.register(ItemNames.oxTankLight, () -> new ItemOxygenTank(1, defaultItemBuilder().maxDamage(900)));
	
	public static final RegistryObject<ItemOxygenTank> OX_TANK_MEDIUM = ITEMS.register(ItemNames.oxTankMedium, () -> new ItemOxygenTank(1, defaultItemBuilder().maxDamage(1800)));
	
	public static final RegistryObject<ItemOxygenTank> OX_TANK_HEAVY = ITEMS.register(ItemNames.oxTankHeavy, () -> new ItemOxygenTank(1, defaultItemBuilder().maxDamage(2700)));
	
	public static final RegistryObject<ItemCanisterOxygenInfinite> OX_CANISTER_INFINITE = ITEMS.register(ItemNames.oxygenCanisterInfinite, () -> new ItemCanisterOxygenInfinite(defaultItemBuilder()));
	
	public static final RegistryObject<ItemSensorGlasses> SENSOR_GLASSES = ITEMS.register(ItemNames.sensorGlasses, () -> new ItemSensorGlasses(defaultItemBuilder()));
	
	public static final RegistryObject<Item> FREQUENCY_MODULE = ITEMS.register(ItemNames.frequencyModule, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<ItemParaChute> PARACHUTE_WHITE = ITEMS.register("parachute_white", () -> new ItemParaChute(DyeColor.WHITE, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_ORANGE = ITEMS.register("parachute_orange", () -> new ItemParaChute(DyeColor.ORANGE, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_MAGENTA = ITEMS.register("parachute_magenta", () -> new ItemParaChute(DyeColor.MAGENTA, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_LIGHT_BLUE = ITEMS.register("parachute_light_blue", () -> new ItemParaChute(DyeColor.LIGHT_BLUE, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_YELLOW = ITEMS.register("parachute_yellow", () -> new ItemParaChute(DyeColor.YELLOW, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_LIME = ITEMS.register("parachute_lime", () -> new ItemParaChute(DyeColor.LIME, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_PINK = ITEMS.register("parachute_pink", () -> new ItemParaChute(DyeColor.PINK, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_GRAY = ITEMS.register("parachute_gray", () -> new ItemParaChute(DyeColor.GRAY, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_LIGHT_GRAY = ITEMS.register("parachute_light_gray", () -> new ItemParaChute(DyeColor.LIGHT_GRAY, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_CYAN = ITEMS.register("parachute_cyan", () -> new ItemParaChute(DyeColor.CYAN, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_PURPLE = ITEMS.register("parachute_purple", () -> new ItemParaChute(DyeColor.PURPLE, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_BLUE = ITEMS.register("parachute_blue", () -> new ItemParaChute(DyeColor.BLUE, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_BROWN = ITEMS.register("parachute_brown", () -> new ItemParaChute(DyeColor.BROWN, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_GREEN = ITEMS.register("parachute_green", () -> new ItemParaChute(DyeColor.GREEN, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_RED = ITEMS.register("parachute_red", () -> new ItemParaChute(DyeColor.RED, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_BLACK = ITEMS.register("parachute_black", () -> new ItemParaChute(DyeColor.BLACK, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemEmergencyKit> EMERGENCY_KIT = ITEMS.register(ItemNames.emergencyKit, () -> new ItemEmergencyKit(defaultItemBuilder()));

	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1 = ITEMS.register(ItemNames.rocketTierOne, () -> new ItemTier1Rocket(defaultItemBuilder().maxStackSize(1)));
	//TODO: add argument with inventory space to item constructor
	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1_CARGO_1 = ITEMS.register(ItemNames.rocketTierOneCargo1, () -> new ItemTier1Rocket(defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1_CARGO_2 = ITEMS.register(ItemNames.rocketTierOneCargo2, () -> new ItemTier1Rocket(defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1_CARGO_3 = ITEMS.register(ItemNames.rocketTierOneCargo3, () -> new ItemTier1Rocket(defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1_CREATIVE = ITEMS.register(ItemNames.rocketTierOneCreative, () -> new ItemTier1Rocket(defaultItemBuilder().maxStackSize(1)));

	public static final RegistryObject<Item> TIN_CANISTER = ITEMS.register(ItemNames.tinCanister, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> COPPER_CANISTER = ITEMS.register(ItemNames.copperCanister, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<ItemOilCanister> OIL_CANISTER = ITEMS.register(ItemNames.oilCanister, () -> new ItemOilCanister(defaultItemBuilder()));

	public static final RegistryObject<ItemFuelCanister> FUEL_CANISTER = ITEMS.register(ItemNames.fuelCanister, () -> new ItemFuelCanister(defaultItemBuilder()));

	public static final RegistryObject<Item> SENSOR_LENS = ITEMS.register(ItemNames.sensorLens, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> AIR_VENT = ITEMS.register(ItemNames.airVent, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> OX_FAN = ITEMS.register(ItemNames.oxygenFan, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> OX_CONCENTRATOR = ITEMS.register(ItemNames.oxygenConcentrator, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> ROCKET_ENGINE_T1 = ITEMS.register(ItemNames.rocketEngineT1, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> ROCKET_BOOSTER_T1 = ITEMS.register(ItemNames.rocketBoosterT1, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> HEAVY_PLATING_T1 = ITEMS.register(ItemNames.heavyPlatingTier1, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> PART_NOSE_CONE = ITEMS.register(ItemNames.partNoseCone, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> PART_FINS = ITEMS.register(ItemNames.partFins, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> FLAG_POLE = ITEMS.register(ItemNames.flagPole, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> CANVAS = ITEMS.register(ItemNames.canvas, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> BUGGY_WHEEL = ITEMS.register(ItemNames.buggyWheel, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> BUGGY_SEAT = ITEMS.register(ItemNames.buggySeat, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> BUGGY_STORAGE = ITEMS.register(ItemNames.buggyStorage, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<BuggyItem> BUGGY = ITEMS.register(ItemNames.buggy, () -> new BuggyItem(BuggyType.NO_INVENTORY, defaultItemBuilder().maxStackSize(1)));
	//TODO: add argument with inventory space to item constructor
	public static final RegistryObject<BuggyItem> BUGGY_CARGO_1 = ITEMS.register(ItemNames.buggyInventory1, () -> new BuggyItem(BuggyType.INVENTORY_1, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<BuggyItem> BUGGY_CARGO_2 = ITEMS.register(ItemNames.buggyInventory2, () -> new BuggyItem(BuggyType.INVENTORY_2, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<BuggyItem> BUGGY_CARGO_3 = ITEMS.register(ItemNames.buggyInventory3, () -> new BuggyItem(BuggyType.INVENTORY_3, defaultItemBuilder().maxStackSize(1)));
	
	public static final RegistryObject<Item> RAW_SILICON = ITEMS.register(ItemNames.rawSilicon, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> BASIC_WAFER = ITEMS.register(ItemNames.basicWafer, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> ADVANCED_WAFER = ITEMS.register(ItemNames.advancedWafer, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> AMBIENT_THERMAL_CONTROLLER = ITEMS.register(ItemNames.ambientThermalController, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> DEHYDRATED_APPLE = ITEMS.register(ItemNames.dehydratedApple, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(8).saturation(0.3F).build())));

	public static final RegistryObject<Item> DEHYDRATED_CARROT = ITEMS.register(ItemNames.dehydratedCarrot, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(8).saturation(0.6F).build())));

	public static final RegistryObject<Item> DEHYDRATED_MELON = ITEMS.register(ItemNames.dehydratedMelon, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(4).saturation(0.3F).build())));

	public static final RegistryObject<Item> DEHYDRATED_POTATO = ITEMS.register(ItemNames.dehydratedPotato, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(2).saturation(0.3F).build())));
	
	public static final RegistryObject<Item> CANNED_BEEF = ITEMS.register(ItemNames.cannedBeef, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(8).saturation(0.6F).build())));
	
	public static final RegistryObject<Item> CHEESE_SLICE = ITEMS.register(ItemNames.cheeseSlice, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(2).saturation(0.1F).build())));
	
	public static final RegistryObject<Item> BURGER_BUN = ITEMS.register(ItemNames.burgerBun, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(4).saturation(0.8F).build())));

	public static final RegistryObject<Item> BEEF_PATTY_RAW = ITEMS.register(ItemNames.beefPattyRaw, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(2).saturation(0.3F).build())));

	public static final RegistryObject<Item> BEEF_PATTY_COOCKED = ITEMS.register(ItemNames.beefPattyCooked, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(4).saturation(0.6F).build())));

	public static final RegistryObject<Item> CHEESBURGER = ITEMS.register(ItemNames.cheeseburger, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(14).saturation(1.0F).build())));
	
	public static final RegistryObject<ItemMeteorChunk> METEOR_CHUNK = ITEMS.register(ItemNames.meteorChunk, () -> new ItemMeteorChunk(defaultItemBuilder().maxStackSize(16)));

	public static final RegistryObject<ItemMeteorChunk> METEOR_CHUNK_HOT = ITEMS.register(ItemNames.meteorChunkHot, () -> new ItemMeteorChunk(defaultItemBuilder().maxStackSize(16)));
	
	public static final RegistryObject<Item> CHEESE_CURD = ITEMS.register(ItemNames.cheeseCurd, () -> new Item(defaultItemBuilder().food((new Food.Builder()).hunger(1).saturation(0.1F).fastToEat().build())));
	
	public static final RegistryObject<Item> RAW_METEORIC_IRON = ITEMS.register(ItemNames.rawMeteoricIron, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> LUNAR_SAPPHIRE = ITEMS.register(ItemNames.lunarSapphire, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<ItemFlag> FLAG = ITEMS.register(ItemNames.flag, () -> new ItemFlag(defaultItemBuilder()));

	public static final RegistryObject<ItemPreLaunchChecklist> PRELAUNCH_CHECKLIST = ITEMS.register(ItemNames.prelaunchChecklist, () -> new ItemPreLaunchChecklist(defaultItemBuilder()));
	
	public static final RegistryObject<Item> DUNGEON_FINDER = ITEMS.register(ItemNames.dungeonFinder, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<ItemKey> KEY = ITEMS.register(ItemNames.key, () -> new ItemKey(defaultItemBuilder()));

	public static final RegistryObject<BuggySchematicItem> SCHEMATIC_BUGGY = ITEMS.register(ItemNames.schematicBuggy, () -> new BuggySchematicItem(defaultItemBuilder()));

	public static final RegistryObject<RocketT1SchematicItem> SCHEMATIC_ROCKET_T1 = ITEMS.register(ItemNames.schematicRocketT1, () -> new RocketT1SchematicItem(new Item.Properties()));
	
	public static final RegistryObject<Item> SCHEMATIC_ROCKET_T2 = ITEMS.register(ItemNames.schematicRocketT2, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> COMPRESSED_COPPER = ITEMS.register(ItemNames.compressedCopper, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> COMPRESSED_TIN = ITEMS.register(ItemNames.compressedTin, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> COMPRESSED_ALUMINUM = ITEMS.register(ItemNames.compressedAluminum, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> COMPRESSED_STEEL = ITEMS.register(ItemNames.compressedSteel, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> COMPRESSED_BRONZE = ITEMS.register(ItemNames.compressedBronze, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> COMPRESSED_IRON = ITEMS.register(ItemNames.compressedIron, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> COMPRESSED_METEORIC_IRON = ITEMS.register(ItemNames.compressedMeteoricIron, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register(ItemNames.copperIngot, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> TIN_INGOT = ITEMS.register(ItemNames.tinIngot, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register(ItemNames.aluminumIngot, () -> new Item(defaultItemBuilder()));

	public static final RegistryObject<Item> METEORIC_IRON_INGOT = ITEMS.register(ItemNames.meteoricIronIngot, () -> new Item(defaultItemBuilder()));
	
	public static final RegistryObject<ItemUniversalWrench> WRENCH = ITEMS.register(ItemNames.wrench, () -> new ItemUniversalWrench(defaultItemBuilder().maxDamage(256)));
	
	//----------- Blocks Tab ---------------- 
	
	public static final RegistryObject<BlockItem> LANDING_PAD = ITEMS.register(BlockNames.landingPad, () -> new BlockItem(GCBlocks.LANDING_PAD.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> BUGGY_PAD = ITEMS.register(BlockNames.buggyPad, () -> new BlockItem(GCBlocks.BUGGY_PAD.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> OXYGEN_COLLECTOR = ITEMS.register(BlockNames.oxygenCollector, () -> new BlockItem(GCBlocks.OXYGEN_COLLECTOR.get(), defaultBlockBuilder()));

	public static final RegistryObject<BlockItem> OXYGEN_COMPRESSOR = ITEMS.register(BlockNames.oxygenCompressor, () -> new BlockItem(GCBlocks.OXYGEN_COMPRESSOR.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> OXYGEN_DECOMPRESSOR = ITEMS.register(BlockNames.oxygenDecompressor, () -> new BlockItem(GCBlocks.OXYGEN_DECOMPRESSOR.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> CARGO_LOADER = ITEMS.register(BlockNames.cargoLoader, () -> new BlockItem(GCBlocks.CARGO_LOADER.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> CARGO_UNLOADER = ITEMS.register(BlockNames.cargoUnloader, () -> new BlockItem(GCBlocks.CARGO_UNLOADER.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> FUEL_LOADER = ITEMS.register(BlockNames.fuelLoader, () -> new BlockItem(GCBlocks.FUEL_LOADER.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<NasaWorkbenchItem> NASA_WORKBENCH = ITEMS.register(BlockNames.nasaWorkbench, () -> new NasaWorkbenchItem(GCBlocks.NASA_WORKBENCH.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> INGOT_COMPRESSOR = ITEMS.register(BlockNames.ingotCompressor, () -> new BlockItem(GCBlocks.INGOT_COMPRESSOR.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> INGOT_COMPRESSOR_ADVANCED = ITEMS.register(BlockNames.ingotCompressorAdvanced, () -> new BlockItem(GCBlocks.INGOT_COMPRESSOR_ADVANCED.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> CIRCUT_FABRICATOR = ITEMS.register(BlockNames.circuitFabricator, () -> new BlockItem(GCBlocks.CIRCUT_FABRICATOR.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> DECONSTRUCTOR = ITEMS.register(BlockNames.deconstructor, () -> new BlockItem(GCBlocks.DECONSTRUCTOR.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> PAINTER = ITEMS.register(BlockNames.painter, () -> new BlockItem(GCBlocks.PAINTER.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> ARC_LAMP = ITEMS.register(BlockNames.arcLamp, () -> new BlockItem(GCBlocks.ARC_LAMP.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> PARA_CHEST = ITEMS.register(BlockNames.parachest, () -> new BlockItem(GCBlocks.PARA_CHEST.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> GLOWSTONE_TORCH = ITEMS.register(BlockNames.glowstoneTorch, () -> new BlockItem(GCBlocks.GLOWSTONE_TORCH.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> FALLEN_METEOR = ITEMS.register(BlockNames.fallenMeteor, () -> new BlockItem(GCBlocks.FALLEN_METEOR.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> MOON_DIRT = ITEMS.register(BlockNames.moonDirt, () -> new BlockItem(GCBlocks.MOON_DIRT.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> MOON_STONE = ITEMS.register(BlockNames.moonStone, () -> new BlockItem(GCBlocks.MOON_STONE.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> MOON_TURF = ITEMS.register(BlockNames.moonTurf, () -> new BlockItem(GCBlocks.MOON_TURF.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> CHEESE = ITEMS.register(BlockNames.cheeseBlock, () -> new BlockItem(GCBlocks.CHEESE.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> MOON_BRICK = ITEMS.register(BlockNames.moonBrick, () -> new BlockItem(GCBlocks.MOON_BRICK.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> COPPER_ORE = ITEMS.register(BlockNames.copperOre, () -> new BlockItem(GCBlocks.COPPER_ORE.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> TIN_ORE = ITEMS.register(BlockNames.tinOre, () -> new BlockItem(GCBlocks.TIN_ORE.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> ALUMINIUM_ORE = ITEMS.register(BlockNames.aluminumOre, () -> new BlockItem(GCBlocks.ALUMINIUM_ORE.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> SILICON_ORE = ITEMS.register(BlockNames.siliconOre, () -> new BlockItem(GCBlocks.SILICON_ORE.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> COPPER_ORE_MOON = ITEMS.register(BlockNames.copperOreMoon, () -> new BlockItem(GCBlocks.COPPER_ORE_MOON.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> TIN_ORE_MOON = ITEMS.register(BlockNames.tinOreMoon, () -> new BlockItem(GCBlocks.TIN_ORE_MOON.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> SAPPHIRE_ORE = ITEMS.register(BlockNames.sapphireOre, () -> new BlockItem(GCBlocks.SAPPHIRE_ORE.get(), defaultBlockBuilder()));
	
	public static final RegistryObject<BlockItem> TREASURE_CHEST_T1 = ITEMS.register(BlockNames.treasureChestTier1, () -> new BlockItem(GCBlocks.TREASURE_CHEST_T1.get(), defaultBlockBuilder()));
	
	
    public static ArrayList<Item> hiddenItems = new ArrayList<Item>();
    public static LinkedList<ItemCanisterGeneric> canisterTypes = new LinkedList<ItemCanisterGeneric>();
    public static Map<EnumSortCategoryItem, List<StackSorted>> sortMapItems = Maps.newHashMap();
    public static HashMap<ItemStack, ItemStack> itemChanges = new HashMap<>(4, 1.0F);

    private static Item.Properties defaultItemBuilder() {
        return new Item.Properties().group(GalacticraftCore.galacticraftItemsTab);
    }
    
    private static Item.Properties defaultBlockBuilder() {
        return new Item.Properties().group(GalacticraftCore.galacticraftBlocksTab);
    }
}
