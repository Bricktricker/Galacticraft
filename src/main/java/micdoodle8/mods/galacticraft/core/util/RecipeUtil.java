package micdoodle8.mods.galacticraft.core.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class RecipeUtil
{

//    public static void addRecipe(ItemStack result, Object[] obj)
//    {
//        CraftingHelper.ShapedPrimer pattern = CraftingHelper.parseShaped(obj);
//        addCustomRecipe(new ShapedRecipe(result.getItem().getRegistryName().toString(), pattern.width, pattern.height, pattern.input, result));
//    }
//
//    public static void addCustomRecipe(IRecipe rec)
//    {
//        String modID = Loader.instance().activeModContainer().getModId();
//        ResourceLocation newLocation = new ResourceLocation(modID, rec.getRecipeOutput().getItem().getRegistryName().getResourcePath());
//        if (CraftingManager.REGISTRY.containsKey(newLocation))
//        {
//            int count = 1;
//            String newNameBase = newLocation.getResourcePath() + "_";
//            while (CraftingManager.REGISTRY.containsKey(newLocation))
//            {
//                newLocation = new ResourceLocation(modID, newNameBase + count++);
//            }
//        }
//        rec.setRegistryName(newLocation);
//
//        GameData.register_impl(rec);
//    }

//    public static ItemStack getIndustrialCraftItem(String indentifier, String variant)
//    {
//        return IC2Items.getItem(indentifier, variant);
//    }

    /**
     * An extended version of areItemStackTagsEqual which ignores LevelUp's "NoPlacing" tag on mined blocks
     */
    public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB)
    {
        if (ItemStack.areItemStackTagsEqual(stackA, stackB))
        {
            return true;
        }

        CompoundNBT query = null;
        if (stackA.getTag() == null && stackB.getTag() != null)
        {
            query = stackB.getTag();
        }
        else if (stackA.getTag() != null && stackB.getTag() == null)
        {
            query = stackA.getTag();
        }
        if (query != null)
        {
            return query.size() == 1 && query.contains("NoPlacing");
        }

        return false;
    }

    /**
     * @param itemstack  - it is assumed this one is not null in calling code
     * @param itemstack1
     * @return
     */
    public static boolean stacksMatch(ItemStack itemstack, ItemStack itemstack1)
    {
        return !itemstack1.isEmpty() && itemstack1.getItem() == itemstack.getItem() && (!itemstack.hasTag() || itemstack.getDamage() == itemstack1.getDamage()) && RecipeUtil.areItemStackTagsEqual(itemstack, itemstack1);
    }
}
