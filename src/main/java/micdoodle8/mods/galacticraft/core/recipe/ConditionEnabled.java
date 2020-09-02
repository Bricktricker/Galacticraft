//package micdoodle8.mods.galacticraft.core.recipe;
//
//import com.google.gson.JsonObject;
//
//import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;
//import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
//import micdoodle8.mods.galacticraft.core.util.GCLog;
//import net.minecraft.util.JSONUtils;
//import net.minecraftforge.common.crafting.CraftingHelper;
//import net.minecraftforge.common.crafting.IConditionFactory;
//import net.minecraftforge.common.crafting.JsonContext;
//
//import java.util.function.BooleanSupplier;
//
//public class ConditionEnabled implements IConditionFactory
//{
//    @Override
//    public BooleanSupplier parse(JsonContext context, JsonObject json)
//    {
//        if(JSONUtils.hasField(json, "data"))
//        {
//            String data = JSONUtils.getString(json, "data");
//            if (data.equals("can_default"))
//            {
//                return () -> !ConfigManagerCore.INSTANCE.alternateCanisterRecipe;
//            }
//            if (data.equals("can_alt"))
//            {
//                return () -> ConfigManagerCore.INSTANCE.alternateCanisterRecipe;
//            }
//            if (data.equals("aa_loaded"))
//            {
//                return () -> !CompatibilityManager.modAALoaded;
//            }
//            GCLog.severe("Unrecognised condition data: " + data);
//        }
//
//        throw new IllegalStateException("Galacticraft recipe JSON condition error in recipe for " + CraftingHelper.getItemStack(json, context));
//    }
//}
