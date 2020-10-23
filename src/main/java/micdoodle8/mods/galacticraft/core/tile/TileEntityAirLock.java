package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

//TODO: why is this needed
public class TileEntityAirLock extends TileEntity
{
    @ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.airLockSeal)
    public static TileEntityType<TileEntityAirLock> TYPE;

    public TileEntityAirLock()
    {
        super(TYPE);
    }

}
