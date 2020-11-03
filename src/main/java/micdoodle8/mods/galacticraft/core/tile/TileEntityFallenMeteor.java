package micdoodle8.mods.galacticraft.core.tile;

import io.netty.buffer.ByteBuf;
import micdoodle8.mods.galacticraft.core.GCTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFallenMeteor extends TileEntity implements ITickableTileEntity {

    public static final int MAX_HEAT_LEVEL = 5000;
    public int heatLevel = TileEntityFallenMeteor.MAX_HEAT_LEVEL;

    public TileEntityFallenMeteor() {
        super(GCTileEntities.FALLEN_METEOR.get());
    }

    @Override
    public void tick()
    {
        if (!this.world.isRemote && this.heatLevel > 0)
        {
            this.heatLevel--;
            this.markDirty();
        }
    }

    public int getHeatLevel()
    {
        return this.heatLevel;
    }

    public void setHeatLevel(int heatLevel)
    {
        this.heatLevel = heatLevel;
    }

    public float getScaledHeatLevel()
    {
        return (float) this.heatLevel / TileEntityFallenMeteor.MAX_HEAT_LEVEL;
    }

    //@Override TODO: find better update method
    public void readExtraNetworkedData(ByteBuf dataStream)
    {
        if (this.world.isRemote)
        {
//            this.world.notifyLightSet(this.getPos()); TODO Lighting
            world.getChunkProvider().getLightManager().checkBlock(this.getPos());
        }
    }

    @Override
    public void read(CompoundNBT nbt)
    {
        super.read(nbt);
        this.heatLevel = nbt.getInt("MeteorHeatLevel");
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt)
    {
        super.write(nbt);
        nbt.putInt("MeteorHeatLevel", this.heatLevel);
        return nbt;
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }
}
