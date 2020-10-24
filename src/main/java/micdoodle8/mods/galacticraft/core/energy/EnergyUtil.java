package micdoodle8.mods.galacticraft.core.energy;

import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnergyUtil
{

    public static boolean isElectricItem(Item item)
    {
        if (item instanceof IItemElectric)
        {
            return true;
        }

        if (item == null)
        {
            return false;
        }

//        if (EnergyConfigHandler.isRFAPILoaded())
//        {
//            if (item instanceof IEnergyContainerItem)
//                return true;
//        }
//        if (EnergyConfigHandler.isIndustrialCraft2Loaded())
//        {
//            if (item instanceof IElectricItem)
//                return true;
//            if (item instanceof ISpecialElectricItem)
//                return true;
//        }
//        if (EnergyConfigHandler.isMekanismLoaded())
//        {
//            if (item instanceof IEnergizedItem)
//                return true;
//        }

        return false;
    }

    public static boolean isChargedElectricItem(ItemStack stack)
    {
        if (stack.isEmpty())
        {
            return false;
        }

        Item item = stack.getItem();
        if (item instanceof IItemElectric)
        {
            return ((IItemElectric) item).getElectricityStored(stack) > 0;
        }

        if (item == Items.AIR)
        {
            return false;
        }

//        if (EnergyConfigHandler.isRFAPILoaded())
//        {
//            if (item instanceof IEnergyContainerItem)
//                return ((IEnergyContainerItem)item).getEnergyStored(stack) > 0;
//        }
//
//        if (EnergyConfigHandler.isIndustrialCraft2Loaded())
//        {
//            if (item instanceof ISpecialElectricItem)
//            {
//                ISpecialElectricItem electricItem = (ISpecialElectricItem) item;
//                return electricItem.getManager(stack).discharge(stack, Double.POSITIVE_INFINITY, 3, true, true, true) > 0.0D;
//            }
//            else if (item instanceof IElectricItem)
//            {
//                IElectricItem electricItem = (IElectricItem) item;
//                return electricItem.canProvideEnergy(stack);
////TODO            return (Info.itemInfo.getEnergyValue(stack) > 0.0D);
//            }
//        }
//
//        if (EnergyConfigHandler.isMekanismLoaded())
//        {
//            if (item instanceof IEnergizedItem)
//                return ((IEnergizedItem)item).getEnergy(stack) > 0;
//        }

        return false;
    }

    public static boolean isFillableElectricItem(ItemStack stack)
    {
        if (stack.isEmpty())
        {
            return false;
        }

        Item item = stack.getItem();
        if (item instanceof IItemElectric)
        {
            return ((IItemElectric) item).getElectricityStored(stack) < ((IItemElectric) item).getMaxElectricityStored(stack);
        }

        if (item == Items.AIR)
        {
            return false;
        }

        return false;
    }

}
