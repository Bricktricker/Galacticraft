package micdoodle8.mods.galacticraft.core.util;

import net.minecraft.world.WorldType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.ModList;


public class CompatibilityManager
{
    public final static String modidMekanism = "mekanism";

    public static boolean modJEILoaded = ModList.get().isLoaded("jei");
    private static final boolean modMekLoaded = ModList.get().isLoaded(modidMekanism);
    private static boolean modAppEngLoaded;
    private static boolean modPneumaticCraftLoaded;
    private static final boolean modBOPLoaded = ModList.get().isLoaded("biomesoplenty");
    private static boolean wailaLoaded;

    public static Class classBOPws = null;
    public static Class classBOPwcm = null;
    public static Class classBOPWorldType = null;

    public static void checkForCompatibleMods()
    {
        if (CompatibilityManager.modMekLoaded)
        {
            GCLog.info("Galacticraft: activating Mekanism compatibility.");
        }

        if (CompatibilityManager.modBOPLoaded)
        {
            try
            {
                classBOPWorldType = Class.forName("biomesoplenty.common.world.WorldTypeBOP");
                classBOPws = Class.forName("biomesoplenty.common.world.BOPWorldSettings");
                classBOPwcm = Class.forName("biomesoplenty.common.world.BiomeProviderBOP");
                GCLog.info("Galacticraft: activating Biomes O'Plenty compatibility feature.");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (ModList.get().isLoaded("appliedenergistics2"))
        {
            CompatibilityManager.modAppEngLoaded = true;
            GCLog.info("Galacticraft: activating AppliedEnergistics2 compatibility features.");
        }

        if (ModList.get().isLoaded("pneumaticcraft"))
        {
            CompatibilityManager.modPneumaticCraftLoaded = true;
            GCLog.info("Galacticraft: activating PneumaticCraft compatibility features.");
        }

        if (ModList.get().isLoaded("waila"))
        {
            CompatibilityManager.wailaLoaded = true;
            GCLog.info("Galacticraft: activating WAILA compatibility features.");
        }
    }

    public static boolean isMekanismLoaded()
    {
        return CompatibilityManager.modMekLoaded;
    }

    public static boolean isAppEngLoaded()
    {
        return CompatibilityManager.modAppEngLoaded;
    }

    public static boolean isBOPLoaded()
    {
        return CompatibilityManager.modBOPLoaded;
    }

    public static boolean isBOPWorld(WorldType worldType)
    {
        if (modBOPLoaded && classBOPWorldType != null && classBOPws != null && classBOPwcm != null)
        {
            return classBOPWorldType.isInstance(worldType);
        }
        return false;
    }

    public static boolean isPneumaticCraftLoaded()
    {
        return CompatibilityManager.modPneumaticCraftLoaded;
    }

    public static boolean isWailaLoaded()
    {
        return CompatibilityManager.wailaLoaded;
    }

    @Deprecated
    public static boolean forceLoadChunks(ServerWorld w)
    {
        return Boolean.TRUE.equals(null);
    }

    @Deprecated
    public static void forceLoadChunksEnd(ServerWorld w, boolean previous)
    {
       
    }

}
