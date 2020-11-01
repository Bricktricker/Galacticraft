package micdoodle8.mods.galacticraft.core.util;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;

public class GCCoreUtil
{
    private static boolean deobfuscated;
    private static MinecraftServer serverCached;

    static
    {
        try
        {
            Class.forName("net.minecraft.world.World");
            deobfuscated = true;
        }
        catch (final Exception e)
        {
            deobfuscated = false;
        }
    }

    @Deprecated
    public static boolean isDeobfuscated()
    {
        return deobfuscated;
    }

    @Deprecated
    public static String translate(String key)
    {
        String result = LanguageMap.getInstance().translateKey(key);
        int comment = result.indexOf('#');
        String ret = (comment > 0) ? result.substring(0, comment).trim() : result;
        for (int i = 0; i < key.length(); ++i)
        {
            char c = key.charAt(i);
            if (Character.isUpperCase(c))
            {
                System.err.println(ret);
            }
        }
        return ret;
    }

    //Needed?
    public static List<String> translateWithSplit(String key)
    {
        String translated = translate(key);
        int comment = translated.indexOf('#');
        translated = (comment > 0) ? translated.substring(0, comment).trim() : translated;
        return Arrays.asList(translated.split("\\$"));
    }

    //Needed?
    public static String translateWithFormat(String key, Object... values)
    {
        String translation = LanguageMap.getInstance().translateKey(key);
        String result;

        try
        {
            result = String.format(translation, values);
        }
        catch (IllegalFormatException var5)
        {
            result = "Format error: " + translation;
        }

        int comment = result.indexOf('#');
        String ret = (comment > 0) ? result.substring(0, comment).trim() : result;
        for (int i = 0; i < key.length(); ++i)
        {
            char c = key.charAt(i);
            if (Character.isUpperCase(c))
            {
                System.err.println(ret);
            }
        }
        return ret;
    }

    public static void drawStringRightAligned(String string, int x, int y, int color, FontRenderer fontRendererObj)
    {
        fontRendererObj.drawString(string, x - fontRendererObj.getStringWidth(string), y, color);
    }

    public static void drawStringCentered(String string, int x, int y, int color, FontRenderer fontRendererObj)
    {
        fontRendererObj.drawString(string, x - fontRendererObj.getStringWidth(string) / 2, y, color);
    }

    public static DimensionType getDimensionType(IWorldReader world)
    {
        return world.getDimension().getType();
    }

    public static DimensionType getDimensionType(Dimension dimension)
    {
        return dimension.getType();
    }

    public static DimensionType getDimensionType(TileEntity tileEntity)
    {
        return tileEntity.getWorld().dimension.getType();
    }

    public static Iterable<ServerWorld> getWorldServerList()
    {
        MinecraftServer server = getServer();
        if (server != null)
        {
            return server.getWorlds();
        }
        return null;
    }

    public static Iterable<ServerWorld> getWorldServerList(World world)
    {
        if (world instanceof ServerWorld)
        {
            return ((ServerWorld) world).getServer().getWorlds();
        }
        return GCCoreUtil.getWorldServerList();
    }

    public static void sendToAllDimensions(EnumSimplePacket packetType, Object[] data)
    {
        for (ServerWorld world : GCCoreUtil.getWorldServerList())
        {
            DimensionType id = getDimensionType(world);
            GalacticraftCore.packetPipeline.sendToDimension(new PacketSimple(packetType, id, data), id);
        }
    }

    public static void sendToAllAround(PacketSimple packet, World world, DimensionType dimID, BlockPos pos, double radius)
    {
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.5D;
        double z = pos.getZ() + 0.5D;
        double r2 = radius * radius;
        for (PlayerEntity playerMP : world.getPlayers())
        {
            if (playerMP.dimension == dimID)
            {
                final double dx = x - playerMP.getPosX();
                final double dy = y - playerMP.getPosY();
                final double dz = z - playerMP.getPosZ();

                if (dx * dx + dy * dy + dz * dz < r2)
                {
                    GalacticraftCore.packetPipeline.sendTo(packet, (ServerPlayerEntity) playerMP);
                }
            }
        }
    }

    /**
     * Call this to obtain a seeded random which will be the SAME on
     * client and server.  This means EntityItems won't jump position, for example.
     */
    public static Random getRandom(BlockPos pos)
    {
        long blockSeed = ((pos.getY() << 28) + pos.getX() + 30000000 << 28) + pos.getZ() + 30000000;
        return new Random(blockSeed);
    }

    /**
     * Returns the angle of the compass (0 - 360 degrees) needed to reach the given position offset
     */
    public static float getAngleForRelativePosition(double nearestX, double nearestZ)
    {
        return ((float) MathHelper.atan2(nearestX, -nearestZ) * Constants.RADIANS_TO_DEGREES + 360F) % 360F;
    }

    /**
     * Custom getEffectiveSide method, covering more cases than FMLCommonHandler
     */
    @Deprecated //world.isRemote should be used
    public static LogicalSide getEffectiveSide()
    {
        if (EffectiveSide.get() == LogicalSide.SERVER || Thread.currentThread().getName().startsWith("Netty Epoll Server IO"))
        {
            return LogicalSide.SERVER;
        }

        return LogicalSide.CLIENT;
    }

    public static MinecraftServer getServer()
    {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null)
        {
            // This may be called from a different thread e.g. MapUtil
            // If on a different thread, FMLCommonHandler.instance().getMinecraftServerInstance() can return null on LAN servers
            // (I think because the FMLCommonHandler wrongly picks the client proxy if it's not in the Integrated Server thread)
            return serverCached;
        }
        return server;
    }

    public static ItemStack getMatchingItemEitherHand(PlayerEntity player, Item item)
    {
        ItemStack stack = player.inventory.getStackInSlot(player.inventory.currentItem);
        if (stack.getItem() == item)
        {
            return stack;
        }
        stack = player.inventory.offHandInventory.get(0);
        if (stack.getItem() == item)
        {
            return stack;
        }
        return null;
    }

    //For performance
    public static List<BlockPos> getPositionsAdjoining(BlockPos pos)
    {
        LinkedList<BlockPos> result = new LinkedList<>();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (y > 0)
        {
            result.add(new BlockPos(x, y - 1, z));
        }
        if (y < 255)
        {
            result.add(new BlockPos(x, y + 1, z));
        }
        result.add(new BlockPos(x, y, z - 1));
        result.add(new BlockPos(x, y, z + 1));
        result.add(new BlockPos(x - 1, y, z));
        result.add(new BlockPos(x + 1, y, z));
        return result;
    }

    //For performance
    public static void getPositionsAdjoining(int x, int y, int z, List<BlockPos> result)
    {
        result.clear();
        if (y > 0)
        {
            result.add(new BlockPos(x, y - 1, z));
        }
        if (y < 255)
        {
            result.add(new BlockPos(x, y + 1, z));
        }
        result.add(new BlockPos(x, y, z - 1));
        result.add(new BlockPos(x, y, z + 1));
        result.add(new BlockPos(x - 1, y, z));
        result.add(new BlockPos(x + 1, y, z));
    }

    public static void getPositionsAdjoiningLoaded(int x, int y, int z, List<BlockPos> result, World world)
    {
        result.clear();
        if (y > 0)
        {
            result.add(new BlockPos(x, y - 1, z));
        }
        if (y < 255)
        {
            result.add(new BlockPos(x, y + 1, z));
        }
        BlockPos pos = new BlockPos(x, y, z - 1);
        if ((z & 15) > 0 || world.isBlockLoaded(pos))
        {
            result.add(pos);
        }
        pos = new BlockPos(x, y, z + 1);
        if ((z & 15) < 15 || world.isBlockLoaded(pos))
        {
            result.add(pos);
        }
        pos = new BlockPos(x - 1, y, z);
        if ((x & 15) > 0 || world.isBlockLoaded(pos))
        {
            result.add(pos);
        }
        pos = new BlockPos(x + 1, y, z);
        if ((x & 15) < 15 || world.isBlockLoaded(pos))
        {
            result.add(pos);
        }
    }

    //For performance
    public static void getPositionsAdjoining(BlockPos pos, List<BlockPos> result)
    {
        result.clear();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (y > 0)
        {
            result.add(new BlockPos(x, y - 1, z));
        }
        if (y < 255)
        {
            result.add(new BlockPos(x, y + 1, z));
        }
        result.add(new BlockPos(x, y, z - 1));
        result.add(new BlockPos(x, y, z + 1));
        result.add(new BlockPos(x - 1, y, z));
        result.add(new BlockPos(x + 1, y, z));
    }

    public static void spawnItem(World world, BlockPos pos, ItemStack stack)
    {
        float var = 0.7F;
        while (!stack.isEmpty())
        {
            double dx = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
            double dy = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
            double dz = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
            ItemEntity entityitem = new ItemEntity(world, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, stack.split(world.rand.nextInt(21) + 10));

            entityitem.setPickupDelay(10);

            world.addEntity(entityitem);
        }
    }

    public static void notifyStarted(MinecraftServer server)
    {
        serverCached = server;
    }
}
