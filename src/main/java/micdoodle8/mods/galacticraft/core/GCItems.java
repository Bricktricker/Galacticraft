package micdoodle8.mods.galacticraft.core;

import com.google.common.collect.Maps;
import micdoodle8.mods.galacticraft.core.items.*;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.StackSorted;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.*;

public class GCItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, Constants.MOD_ID_CORE);
	
	public static final RegistryObject<ItemOxygenTank> OX_TANK_LIGHT = ITEMS.register(ItemNames.oxTankLight, () -> new ItemOxygenTank(1, defaultBuilder().maxDamage(900)));
	
	public static final RegistryObject<ItemOxygenTank> OX_TANK_MEDIUM = ITEMS.register(ItemNames.oxTankMedium, () -> new ItemOxygenTank(1, defaultBuilder().maxDamage(1800)));
	
	public static final RegistryObject<ItemOxygenTank> OX_TANK_HEACY = ITEMS.register(ItemNames.oxTankHeavy, () -> new ItemOxygenTank(1, defaultBuilder().maxDamage(2700)));
	
	public static final RegistryObject<ItemOxygenMask> OX_MASK = ITEMS.register(ItemNames.oxMask, () -> new ItemOxygenMask(defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1 = ITEMS.register(ItemNames.rocketTierOne, () -> new ItemTier1Rocket(defaultBuilder().maxStackSize(1)));
	//TODO: add argument with inventory space to item constructor
	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1_CARGO_1 = ITEMS.register(ItemNames.rocketTierOneCargo1, () -> new ItemTier1Rocket(defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1_CARGO_2 = ITEMS.register(ItemNames.rocketTierOneCargo2, () -> new ItemTier1Rocket(defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1_CARGO_3 = ITEMS.register(ItemNames.rocketTierOneCargo3, () -> new ItemTier1Rocket(defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemTier1Rocket> ROCKET_T1_CREATIVE = ITEMS.register(ItemNames.rocketTierOneCreative, () -> new ItemTier1Rocket(defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemSensorGlasses> SENSOR_GLASSES = ITEMS.register(ItemNames.sensorGlasses, () -> new ItemSensorGlasses(defaultBuilder()));
	
	public static final RegistryObject<Item> SENSOR_LENS = ITEMS.register(ItemNames.sensorLens, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> OX_VENT = ITEMS.register(ItemNames.oxygenVent, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> OX_FAN = ITEMS.register(ItemNames.oxygenFan, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> OX_CONCENTRATOR = ITEMS.register(ItemNames.oxygenConcentrator, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> HEAVY_PLATING_T1 = ITEMS.register(ItemNames.heavyPlatingTier1, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> PART_NOSE_CONE = ITEMS.register(ItemNames.partNoseCone, () -> new Item(defaultBuilder()));
	
	public static final RegistryObject<Item> PART_FINS = ITEMS.register(ItemNames.partFins, () -> new Item(defaultBuilder()));
	
	public static final RegistryObject<ItemBuggy> BUGGY = ITEMS.register(ItemNames.buggy, () -> new ItemBuggy(defaultBuilder().maxStackSize(1)));
	//TODO: add argument with inventory space to item constructor
	public static final RegistryObject<ItemBuggy> BUGGY_CARGO_1 = ITEMS.register(ItemNames.buggyInventory1, () -> new ItemBuggy(defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemBuggy> BUGGY_CARGO_2 = ITEMS.register(ItemNames.buggyInventory2, () -> new ItemBuggy(defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemBuggy> BUGGY_CARGO_3 = ITEMS.register(ItemNames.buggyInventory3, () -> new ItemBuggy(defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemFlag> FLAG = ITEMS.register(ItemNames.flag, () -> new ItemFlag(defaultBuilder()));

	public static final RegistryObject<ItemOxygenGear> OX_GEAR = ITEMS.register(ItemNames.oxygenGear, () -> new ItemOxygenGear(defaultBuilder()));

	public static final RegistryObject<Item> CANVAS = ITEMS.register(ItemNames.canvas, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> FLAG_POLE = ITEMS.register(ItemNames.flagPole, () -> new Item(defaultBuilder()));

	public static final RegistryObject<ItemOilCanister> OIL_CANISTER = ITEMS.register(ItemNames.oilCanister, () -> new ItemOilCanister(defaultBuilder()));

	public static final RegistryObject<ItemFuelCanister> FUEL_CANISTER = ITEMS.register(ItemNames.fuelCanister, () -> new ItemFuelCanister(defaultBuilder()));

	public static final RegistryObject<ItemCanisterOxygenInfinite> OX_CANISTER_INFINITE = ITEMS.register(ItemNames.oxygenCanisterInfinite, () -> new ItemCanisterOxygenInfinite(defaultBuilder()));

	public static final RegistryObject<Item> SCHEMATIC_BUGGY = ITEMS.register(ItemNames.schematicBuggy, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> SCHEMATIC_ROCKET_T2 = ITEMS.register(ItemNames.schematicRocketT2, () -> new Item(defaultBuilder()));

	public static final RegistryObject<ItemKey> KEY = ITEMS.register(ItemNames.key, () -> new ItemKey(defaultBuilder()));

	public static final RegistryObject<ItemUniversalWrench> WRENCH = ITEMS.register(ItemNames.wrench, () -> new ItemUniversalWrench(defaultBuilder().maxDamage(256)));

	public static final RegistryObject<Item> CHEES_CURD = ITEMS.register(ItemNames.cheeseCurd, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(1).saturation(0.1F).fastToEat().build())));
	
	public static final RegistryObject<Item> METEORIC_IRON_RAW = ITEMS.register(ItemNames.meteoricIronRaw, () -> new Item(defaultBuilder()));

	public static final RegistryObject<ItemPreLaunchChecklist> PRELAUNCH_CHECKLIST = ITEMS.register(ItemNames.prelaunchChecklist, () -> new ItemPreLaunchChecklist(defaultBuilder()));

	public static final RegistryObject<Item> DUNGEON_FINDER = ITEMS.register(ItemNames.dungeonFinder, () -> new Item(defaultBuilder()));

	public static final RegistryObject<ItemEmergencyKit> EMERGENCY_KIT = ITEMS.register(ItemNames.emergencyKit, () -> new ItemEmergencyKit(defaultBuilder()));

	public static final RegistryObject<Item> RAW_SILICON = ITEMS.register(ItemNames.rawSilicon, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register(ItemNames.ingotCopper, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> TIN_INGOT = ITEMS.register(ItemNames.ingotTin, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register(ItemNames.ingotAluminum, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> COMPRESSED_COPPER = ITEMS.register(ItemNames.compressedCopper, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> COMPRESSED_TIN = ITEMS.register(ItemNames.compressedTin, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> COMPRESSED_ALUMINUM = ITEMS.register(ItemNames.compressedAluminum, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> COMPRESSED_STEEL = ITEMS.register(ItemNames.compressedSteel, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> COMPRESSED_BRONZE = ITEMS.register(ItemNames.compressedBronze, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> COMPRESSED_IRON = ITEMS.register(ItemNames.compressedIron, () -> new Item(defaultBuilder()));
	//TODO: rename to basic_wafer
	public static final RegistryObject<Item> BASIC_WAFER = ITEMS.register(ItemNames.compressedWaferBasic, () -> new Item(defaultBuilder()));
	//TODO: rename to advanced_wafer
	public static final RegistryObject<Item> ADVANCED_WAFER = ITEMS.register(ItemNames.compressedWaferAdvanced, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> FREQUENCY_MODULE = ITEMS.register(ItemNames.frequencyModule, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> AMBIENT_THERMAL_CONTROLLER = ITEMS.register(ItemNames.ambientThermalController, () -> new Item(defaultBuilder()));
	//TODO: rename to buggy_wheel
	public static final RegistryObject<Item> BUGGY_WHEEL = ITEMS.register(ItemNames.buggyMaterialWheel, () -> new Item(defaultBuilder()));
	//TODO: rename to buggy_seat
	public static final RegistryObject<Item> BUGGY_SEAT = ITEMS.register(ItemNames.buggyMaterialSeat, () -> new Item(defaultBuilder()));
	//TODO: rename to buggy_storage
	public static final RegistryObject<Item> BUGGY_STORAGE = ITEMS.register(ItemNames.buggyMaterialStorage, () -> new Item(defaultBuilder()));
	//TODO: rename to tin_canister
	public static final RegistryObject<Item> TIN_CANISTER = ITEMS.register(ItemNames.canisterTin, () -> new Item(defaultBuilder()));
	//TODO: rename to copper_canister
	public static final RegistryObject<Item> COPPER_CANISTER = ITEMS.register(ItemNames.canisterCopper, () -> new Item(defaultBuilder()));

	public static final RegistryObject<Item> DEHYDRATED_APPLE = ITEMS.register(ItemNames.dehydratedApple, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(8).saturation(0.3F).build())));

	public static final RegistryObject<Item> DEHYDRATED_CARROT = ITEMS.register(ItemNames.dehydratedCarrot, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(8).saturation(0.6F).build())));

	public static final RegistryObject<Item> DEHYDRATED_MELON = ITEMS.register(ItemNames.dehydratedMelon, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(4).saturation(0.3F).build())));

	public static final RegistryObject<Item> DEHYDRATED_POTATO = ITEMS.register(ItemNames.dehydratedPotato, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(2).saturation(0.3F).build())));

	public static final RegistryObject<Item> CHEESE_SLICE = ITEMS.register(ItemNames.cheeseSlice, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(2).saturation(0.1F).build())));

	public static final RegistryObject<Item> BURGER_BUN = ITEMS.register(ItemNames.burgerBun, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(4).saturation(0.8F).build())));

	public static final RegistryObject<Item> BEEF_PATTY_RAW = ITEMS.register(ItemNames.beefPattyRaw, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(2).saturation(0.3F).build())));

	public static final RegistryObject<Item> BEEF_PATTY_COOCKED = ITEMS.register(ItemNames.beefPattyCooked, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(4).saturation(0.6F).build())));

	public static final RegistryObject<Item> CHEESBURGER = ITEMS.register(ItemNames.cheeseburger, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(14).saturation(1.0F).build())));

	public static final RegistryObject<Item> CANNED_BEEF = ITEMS.register(ItemNames.cannedBeef, () -> new Item(defaultBuilder().food((new Food.Builder()).hunger(8).saturation(0.6F).build())));

	public static final RegistryObject<ItemMeteorChunk> METEOR_CHUNK = ITEMS.register(ItemNames.meteorChunk, () -> new ItemMeteorChunk(defaultBuilder().maxStackSize(16)));

	public static final RegistryObject<ItemMeteorChunk> METEOR_CHUNK_HOT = ITEMS.register(ItemNames.meteorChunkHot, () -> new ItemMeteorChunk(defaultBuilder().maxStackSize(16)));

	public static final RegistryObject<Item> METEORIC_IRON_INGOT = ITEMS.register(ItemNames.ingotMeteoricIron, () -> new Item(defaultBuilder()));
	
	public static final RegistryObject<Item> COMPRESSED_METEORIC_IRON = ITEMS.register(ItemNames.compressedMeteoricIron, () -> new Item(defaultBuilder()));
	
	public static final RegistryObject<Item> LUNAR_SAPPHIRE = ITEMS.register(ItemNames.lunarSapphire, () -> new Item(defaultBuilder()));

	public static final RegistryObject<ItemParaChute> PARACHUTE_WHITE = ITEMS.register("parachute_white", () -> new ItemParaChute(DyeColor.WHITE, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_ORANGE = ITEMS.register("parachute_orange", () -> new ItemParaChute(DyeColor.ORANGE, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_MAGENTA = ITEMS.register("parachute_magenta", () -> new ItemParaChute(DyeColor.MAGENTA, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_LIGHT_BLUE = ITEMS.register("parachute_light_blue", () -> new ItemParaChute(DyeColor.LIGHT_BLUE, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_YELLOW = ITEMS.register("parachute_yellow", () -> new ItemParaChute(DyeColor.YELLOW, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_LIME = ITEMS.register("parachute_lime", () -> new ItemParaChute(DyeColor.LIME, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_PINK = ITEMS.register("parachute_pink", () -> new ItemParaChute(DyeColor.PINK, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_GRAY = ITEMS.register("parachute_gray", () -> new ItemParaChute(DyeColor.GRAY, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_LIGHT_GRAY = ITEMS.register("parachute_light_gray", () -> new ItemParaChute(DyeColor.LIGHT_GRAY, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_CYAN = ITEMS.register("parachute_cyan", () -> new ItemParaChute(DyeColor.CYAN, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_PURPLE = ITEMS.register("parachute_purple", () -> new ItemParaChute(DyeColor.PURPLE, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_BLUE = ITEMS.register("parachute_blue", () -> new ItemParaChute(DyeColor.BLUE, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_BROWN = ITEMS.register("parachute_brown", () -> new ItemParaChute(DyeColor.BROWN, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_GREEN = ITEMS.register("parachute_green", () -> new ItemParaChute(DyeColor.GREEN, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_RED = ITEMS.register("parachute_red", () -> new ItemParaChute(DyeColor.RED, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<ItemParaChute> PARACHUTE_BLACK = ITEMS.register("parachute_black", () -> new ItemParaChute(DyeColor.BLACK, defaultBuilder().maxStackSize(1)));
	
	public static final RegistryObject<Item> ROCKET_ENGINE_T1 = ITEMS.register(ItemNames.rocketEngineT1, () -> new Item(defaultBuilder()));
	
	public static final RegistryObject<Item> ROCKET_BOOSTER_T1 = ITEMS.register(ItemNames.rocketBoosterT1, () -> new Item(defaultBuilder()));

//    public static ArmorMaterial ARMOR_SENSOR_GLASSES = EnumHelper.addArmorMaterial("SENSORGLASSES", "", 200, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
//    public static ArmorMaterial ARMOR_STEEL = EnumHelper.addArmorMaterial("steel", "", 30, new int[] { 3, 6, 8, 3 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
//    public static ToolMaterial TOOL_STEEL = EnumHelper.addToolMaterial("steel", 3, 768, 5.0F, 2, 8);

    public static ArrayList<Item> hiddenItems = new ArrayList<Item>();
    public static LinkedList<ItemCanisterGeneric> canisterTypes = new LinkedList<ItemCanisterGeneric>();
    public static Map<EnumSortCategoryItem, List<StackSorted>> sortMapItems = Maps.newHashMap();
    public static HashMap<ItemStack, ItemStack> itemChanges = new HashMap<>(4, 1.0F);

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().group(GalacticraftCore.galacticraftItemsTab);
    }
}
