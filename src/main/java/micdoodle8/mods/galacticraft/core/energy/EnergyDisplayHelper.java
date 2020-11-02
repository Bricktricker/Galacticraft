package micdoodle8.mods.galacticraft.core.energy;

import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class EnergyDisplayHelper
{
    public static void getEnergyDisplayTooltip(float energyVal, float maxEnergy, List<ITextComponent> strList)
    {
        strList.add(new TranslationTextComponent("gui.message.energy", getEnergyDisplayS(energyVal)).applyTextStyles(TextFormatting.GREEN));
        strList.add(new TranslationTextComponent("gui.message.max_energy", getEnergyDisplayS(maxEnergy)).applyTextStyles(TextFormatting.RED));
    }

    public static String getEnergyDisplayS(float energyVal)
    {
        if (ConfigManagerCore.INSTANCE.displayEnergyUnitsIC2.get())
        {
            return getEnergyDisplayIC2(energyVal * ConfigManagerCore.INSTANCE.getToIc2ConversionRateDisplay());
        }
        else if (ConfigManagerCore.INSTANCE.displayEnergyUnitsBC.get())
        {
            return getEnergyDisplayBC(energyVal * ConfigManagerCore.INSTANCE.getToBcConversionRateDisplay());
        }
        else if (ConfigManagerCore.INSTANCE.displayEnergyUnitsMek.get())
        {
            return getEnergyDisplayMek(energyVal * ConfigManagerCore.INSTANCE.getToMekConversionRateDisplay());
        }
        else if (ConfigManagerCore.INSTANCE.displayEnergyUnitsRF.get())
        {
            return getEnergyDisplayRF(energyVal * ConfigManagerCore.INSTANCE.getToRfConversionRateDisplay());
        }
        String val = String.valueOf(getEnergyDisplayI(energyVal));
        String newVal = "";

        for (int i = val.length() - 1; i >= 0; i--)
        {
            newVal += val.charAt(val.length() - 1 - i);
            if (i % 3 == 0 && i != 0)
            {
                newVal += ',';
            }
        }

        return newVal + " gJ";
    }

    public static String getEnergyDisplayIC2(float energyVal)
    {
        String val = String.valueOf(getEnergyDisplayI(energyVal));
        String newVal = "";

        for (int i = val.length() - 1; i >= 0; i--)
        {
            newVal += val.charAt(val.length() - 1 - i);
            if (i % 3 == 0 && i != 0)
            {
                newVal += ',';
            }
        }

        return newVal + " EU";
    }

    public static String getEnergyDisplayBC(float energyVal)
    {
        String val = String.valueOf(getEnergyDisplayI(energyVal));

        return val + " MJ";
    }

    public static String getEnergyDisplayMek(float energyVal)
    {
        if (energyVal < 1000)
        {
            String val = String.valueOf(getEnergyDisplayI(energyVal));
            return val + " J";
        }
        else if (energyVal < 1000000)
        {
            String val = getEnergyDisplay1DP(energyVal / 1000);
            return val + " kJ";
        }
        else
        {
            String val = getEnergyDisplay1DP(energyVal / 1000000);
            return val + " MJ";
        }
    }

    public static String getEnergyDisplayRF(float energyVal)
    {
        String val = String.valueOf(getEnergyDisplayI(energyVal));

        return val + " RF";
    }

    public static int getEnergyDisplayI(float energyVal)
    {
        return MathHelper.floor(energyVal);
    }

    public static String getEnergyDisplay1DP(float energyVal)
    {
        return "" + MathHelper.floor(energyVal) + "." + (MathHelper.floor(energyVal * 10) % 10) + (MathHelper.floor(energyVal * 100) % 10);
    }
}
