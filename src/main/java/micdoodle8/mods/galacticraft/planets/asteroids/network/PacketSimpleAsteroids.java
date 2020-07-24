package micdoodle8.mods.galacticraft.planets.asteroids.network;

import io.netty.buffer.ByteBuf;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.network.NetworkUtil;
import micdoodle8.mods.galacticraft.core.network.PacketBase;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.entities.EntityGrapple;
import micdoodle8.mods.galacticraft.planets.asteroids.tile.TileEntityShortRangeTelepad;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;

import java.util.Arrays;
import java.util.List;

public class PacketSimpleAsteroids extends PacketBase
{
    public enum EnumSimplePacketAsteroids
    {
        // SERVER
        S_UPDATE_ADVANCED_GUI(LogicalSide.SERVER, Integer.class, BlockPos.class, Integer.class),
        // CLIENT
        C_TELEPAD_SEND(LogicalSide.CLIENT, BlockVec3.class, Integer.class),
        C_UPDATE_GRAPPLE_POS(LogicalSide.CLIENT, Integer.class, Vector3.class);

        private final LogicalSide targetSide;
        private final Class<?>[] decodeAs;

        EnumSimplePacketAsteroids(LogicalSide targetSide, Class<?>... decodeAs)
        {
            this.targetSide = targetSide;
            this.decodeAs = decodeAs;
        }

        public LogicalSide getTargetSide()
        {
            return this.targetSide;
        }

        public Class<?>[] getDecodeClasses()
        {
            return this.decodeAs;
        }
    }

    private EnumSimplePacketAsteroids type;
    private List<Object> data;

    public PacketSimpleAsteroids()
    {
        super();
    }

    public PacketSimpleAsteroids(EnumSimplePacketAsteroids packetType, DimensionType dimID, Object[] data)
    {
        this(packetType, dimID, Arrays.asList(data));
    }

    public PacketSimpleAsteroids(EnumSimplePacketAsteroids packetType, DimensionType dimID, List<Object> data)
    {
        super(dimID);

        if (packetType.getDecodeClasses().length != data.size())
        {
            GCLog.info("Asteroids Simple Packet found data length different than packet type: " + packetType.name());
        }

        this.type = packetType;
        this.data = data;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        super.encodeInto(buffer);
        buffer.writeInt(this.type.ordinal());

        NetworkUtil.encodeData(buffer, this.data);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        super.decodeInto(buffer);
        this.type = EnumSimplePacketAsteroids.values()[buffer.readInt()];

        if (this.type.getDecodeClasses().length > 0)
        {
            this.data = NetworkUtil.decodeData(this.type.getDecodeClasses(), buffer);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClientSide(PlayerEntity player)
    {
        ClientPlayerEntity playerBaseClient = null;

        if (player instanceof ClientPlayerEntity)
        {
            playerBaseClient = (ClientPlayerEntity) player;
        }

        TileEntity tile;
        switch (this.type)
        {
            case C_TELEPAD_SEND:
                Entity entity = playerBaseClient.world.getEntityByID((Integer) this.data.get(1));

                if (entity instanceof LivingEntity)
                {
                    BlockVec3 pos = (BlockVec3) this.data.get(0);
                    entity.setPosition(pos.x + 0.5, pos.y + 2.2, pos.z + 0.5);
                }
                break;
            case C_UPDATE_GRAPPLE_POS:
                entity = playerBaseClient.world.getEntityByID((Integer) this.data.get(0));
                if (entity instanceof EntityGrapple)
                {
                    Vector3 vec = (Vector3) this.data.get(1);
                    entity.setPosition(vec.x, vec.y, vec.z);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void handleServerSide(PlayerEntity player)
    {
        ServerPlayerEntity playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);

        if (this.type == EnumSimplePacketAsteroids.S_UPDATE_ADVANCED_GUI)
        {
            TileEntity tile = player.world.getTileEntity((BlockPos) this.data.get(1));

            switch ((Integer) this.data.get(0))
            {
                case 0:
                    if (tile instanceof TileEntityShortRangeTelepad)
                    {
                        TileEntityShortRangeTelepad launchController = (TileEntityShortRangeTelepad) tile;
                        launchController.setAddress((Integer) this.data.get(2));
                    }
                    break;
                case 1:
                    if (tile instanceof TileEntityShortRangeTelepad)
                    {
                        TileEntityShortRangeTelepad launchController = (TileEntityShortRangeTelepad) tile;
                        launchController.setTargetAddress((Integer) this.data.get(2));
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
