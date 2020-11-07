package micdoodle8.mods.galacticraft.api;

import com.google.common.collect.Lists;
import micdoodle8.mods.galacticraft.api.client.IGameScreen;
import micdoodle8.mods.galacticraft.api.item.EnumExtendedInventorySlot;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftDimension;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.api.world.SpaceStationType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.reflect.Method;
import java.util.*;

public class GalacticraftRegistry
{
    private static final Map<Class<? extends Dimension>, ITeleportType> teleportTypeMap = new HashMap<Class<? extends Dimension>, ITeleportType>();
    private static final List<SpaceStationType> spaceStations = new ArrayList<SpaceStationType>();
    private static final Map<Class<? extends Dimension>, ResourceLocation> rocketGuiMap = new HashMap<Class<? extends Dimension>, ResourceLocation>();
    private static final Map<Integer, List<ItemStack>> dungeonLootMap = new HashMap<Integer, List<ItemStack>>();
    private static final List<DimensionType> dimensionTypeIDs = new ArrayList<>();
    private static final List<IGameScreen> gameScreens = new ArrayList<IGameScreen>();
    private static int maxScreenTypes;
    private static final Map<Integer, List<Object>> gearMap = new HashMap<>();
    private static final Map<Integer, List<EnumExtendedInventorySlot>> gearSlotMap = new HashMap<>();
    private static final Method gratingRegister = null;

    /**
     * Register a new Teleport type for the world dimension passed
     *
     * @param clazz the world dimension class that you wish to customize
     *              teleportation for
     * @param type  an ITeleportType-implemented class that will be used for the
     *              provided world type
     */
    public static void registerTeleportType(Class<? extends Dimension> clazz, ITeleportType type)
    {
        if (!GalacticraftRegistry.teleportTypeMap.containsKey(clazz))
        {
            GalacticraftRegistry.teleportTypeMap.put(clazz, type);
        }
    }

    /**
     * Link a world dimension to a gui texture. This texture will be shown on the
     * left-LogicalSide of the screen while the player is in the rocket.
     *
     * @param clazz     The World dimension class
     * @param rocketGui Resource Location for the gui texture
     */
    public static void registerRocketGui(Class<? extends Dimension> clazz, ResourceLocation rocketGui)
    {
        if (!GalacticraftRegistry.rocketGuiMap.containsKey(clazz))
        {
            GalacticraftRegistry.rocketGuiMap.put(clazz, rocketGui);
        }
    }

    /**
     * Add loot to the list of items that can possibly spawn in dungeon chests,
     * but it is guaranteed that one will always spawn
     *
     * @param tier Tier of dungeon chest to add loot to. For example Moon is 1
     *             and Mars is 2
     * @param loot The itemstack to add to the possible list of items
     */
    public static void addDungeonLoot(int tier, ItemStack loot)
    {
        List<ItemStack> dungeonStacks = null;

        if (GalacticraftRegistry.dungeonLootMap.containsKey(tier))
        {
            dungeonStacks = GalacticraftRegistry.dungeonLootMap.get(tier);
            dungeonStacks.add(loot);
        }
        else
        {
            dungeonStacks = new ArrayList<ItemStack>();
            dungeonStacks.add(loot);
        }

        GalacticraftRegistry.dungeonLootMap.put(tier, dungeonStacks);
    }

    public static ITeleportType getTeleportTypeForDimension(Class<? extends Dimension> clazz)
    {
        if (!IGalacticraftDimension.class.isAssignableFrom(clazz))
        {
            clazz = OverworldDimension.class;
        }
        return GalacticraftRegistry.teleportTypeMap.get(clazz);
    }

    public static void registerSpaceStation(SpaceStationType type)
    {
        for (SpaceStationType type1 : GalacticraftRegistry.spaceStations)
        {
            if (type1.getWorldToOrbitID() == type.getWorldToOrbitID())
            {
                throw new RuntimeException("Two space station types registered with the same home planet ID: " + type.getWorldToOrbitID());
            }
        }

        GalacticraftRegistry.spaceStations.add(type);
    }

//    public static void replaceSpaceStationRecipe(int spaceStationID, HashMap<Object, Integer> obj)
//    {
//        for (SpaceStationType type1 : GalacticraftRegistry.spaceStations)
//        {
//            if (type1.getSpaceStationID() == spaceStationID)
//            {
//                type1.setRecipeForSpaceStation(new SpaceStationRecipe(obj));
//            }
//        }
//    }

    public SpaceStationType getTypeFromPlanetID(int planetID)
    {
        return GalacticraftRegistry.spaceStations.get(planetID);
    }

    public static List<SpaceStationType> getSpaceStationData()
    {
        return GalacticraftRegistry.spaceStations;
    }

    @OnlyIn(Dist.CLIENT)
    public static ResourceLocation getResouceLocationForDimension(Class<? extends Dimension> clazz)
    {
        if (!IGalacticraftDimension.class.isAssignableFrom(clazz))
        {
            clazz = OverworldDimension.class;
        }
        return GalacticraftRegistry.rocketGuiMap.get(clazz);
    }

    public static List<ItemStack> getDungeonLoot(int tier)
    {
        return GalacticraftRegistry.dungeonLootMap.get(tier);
    }

    /***
     * Register a Galacticraft dimension
     */
//    public static DimensionType registerDimension(String name, String suffix, DimensionType id, Class<? extends Dimension> dimension, boolean keepLoaded) throws IllegalArgumentException
//    {
//        for (DimensionType other : DimensionType.values())
//        {
//            if (other.getId() == id)
//            {
//                return null;
//            }
//        }
//
//        DimensionType type = DimensionType.register(name, suffix, id, dimension, keepLoaded);
//        GalacticraftRegistry.dimensionTypeIDs.add(type == null ? 0 : id);
//        if (type == null)
//        {
//            FMLRelaunchLog.log("Galacticraft", Level.ERROR, "Problem registering dimension type " + id + ".  May be fixable by changing config.");
//        }
//
//        return type;
//    } TODO ?
    public static DimensionType getDimensionTypeID(int index)
    {
        return GalacticraftRegistry.dimensionTypeIDs.get(index);
    }

    public static boolean isDimensionTypeIDRegistered(DimensionType typeId)
    {
        return GalacticraftRegistry.dimensionTypeIDs.contains(typeId);
    }

    /**
     * Register an IGameScreen so the Display Screen can access it
     *
     * @param screen The IGameScreen to be registered
     * @return The type ID assigned to this screen type
     */
    public static int registerScreen(IGameScreen screen)
    {
        GalacticraftRegistry.gameScreens.add(screen);
        maxScreenTypes++;
        screen.setFrameSize(0.098F);
        return maxScreenTypes - 1;
    }

    public static void registerScreensServer(int maxTypes)
    {
        maxScreenTypes = maxTypes;
    }

    public static int getMaxScreenTypes()
    {
        return maxScreenTypes;
    }

    public static IGameScreen getGameScreen(int type)
    {
        return GalacticraftRegistry.gameScreens.get(type);
    }

    /**
     * Adds a custom item for 'extended inventory' slots
     * <p>
     * Gear IDs must be unique, and should be configurable for user convenience
     * <p>
     * Please do not use values less than 100, to avoid conflicts with future Galacticraft core additions
     *
     * @param gearID Unique ID for this gear item, please use values greater than 100
     * @param type   Slot this item can be placed in
     * @param item   Item to register, not metadata-sensitive
     */
    public static void registerGear(int gearID, EnumExtendedInventorySlot type, Item item)
    {
        addGearObject(gearID, type, item);
    }

    /**
     * Adds a custom item for 'extended inventory' slots
     * <p>
     * Gear IDs must be unique, and should be configurable for user convenience
     * <p>
     * Please do not use values less than 100, to avoid conflicts with future Galacticraft core additions
     *
     * @param gearID    Unique ID for this gear item, please use values greater than 100
     * @param type      Slot this item can be placed in
     * @param itemStack ItemStack to register, metadata-sensitive
     */
    public static void registerGear(int gearID, EnumExtendedInventorySlot type, ItemStack itemStack)
    {
        addGearObject(gearID, type, itemStack);
    }

    private static void addGearObject(int gearID, EnumExtendedInventorySlot type, Object obj)
    {
        if (GalacticraftRegistry.gearMap.containsKey(gearID))
        {
            if (!GalacticraftRegistry.gearMap.get(gearID).contains(obj))
            {
                GalacticraftRegistry.gearMap.get(gearID).add(obj);
            }
        }
        else
        {
            List<Object> gear = Lists.newArrayList();
            gear.add(obj);
            GalacticraftRegistry.gearMap.put(gearID, gear);
        }

        if (GalacticraftRegistry.gearSlotMap.containsKey(gearID))
        {
            if (!GalacticraftRegistry.gearSlotMap.get(gearID).contains(type))
            {
                GalacticraftRegistry.gearSlotMap.get(gearID).add(type);
            }
        }
        else
        {
            List<EnumExtendedInventorySlot> gearType = Lists.newArrayList();
            gearType.add(type);
            GalacticraftRegistry.gearSlotMap.put(gearID, gearType);
        }
    }

    public static List<ItemStack> listAllGearForSlot(EnumExtendedInventorySlot slotType)
    {
        List<ItemStack> result = new LinkedList<>();
        for (Map.Entry<Integer, List<Object>> entry : GalacticraftRegistry.gearMap.entrySet())
        {
            List<EnumExtendedInventorySlot> slotType1 = getSlotType(entry.getKey());
            if (slotType1.contains(slotType))
            {
                List<Object> objectList = entry.getValue();
                for (Object o : objectList)
                {
                    if (o instanceof ItemStack)
                    {
                        result.add((ItemStack) o);
                    }
                    else if (o instanceof Item)
                    {
                        result.add(new ItemStack((Item) o));
                    }
                }
            }
        }
        return result;
    }

    public static int findMatchingGearID(ItemStack stack, EnumExtendedInventorySlot slotType)
    {
        for (Map.Entry<Integer, List<Object>> entry : GalacticraftRegistry.gearMap.entrySet())
        {
            List<EnumExtendedInventorySlot> slotType1 = getSlotType(entry.getKey());
            List<Object> objectList = entry.getValue();

            if (!slotType1.contains(slotType))
            {
                continue;
            }

            for (Object o : objectList)
            {
                if (o instanceof Item)
                {
                    if (stack.getItem() == o)
                    {
                        return entry.getKey();
                    }
                }
                else if (o instanceof ItemStack)
                {
                    if (stack.getItem() == ((ItemStack) o).getItem() && stack.getDamage() == ((ItemStack) o).getDamage())
                    {
                        return entry.getKey();
                    }
                }
            }
        }

        return -1;
    }

    public static List<EnumExtendedInventorySlot> getSlotType(int gearID)
    {
        return GalacticraftRegistry.gearSlotMap.get(gearID);
    }

    @Deprecated
    /** Grating will now register fluids automatically if they extend BlockFluidBase
     *
     */
    public static void registerGratingFluid(Block fluidBlock)
    {
    }
}
