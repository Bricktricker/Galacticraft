package micdoodle8.mods.galacticraft.core.tick;

import com.google.common.collect.Lists;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.api.world.IOrbitDimension;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockUnlitTorch;
import micdoodle8.mods.galacticraft.core.dimension.SpaceRace;
import micdoodle8.mods.galacticraft.core.dimension.SpaceRaceManager;
import micdoodle8.mods.galacticraft.core.dimension.WorldDataSpaceRaces;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.fluid.FluidNetwork;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.tile.PainterTileEntity;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import micdoodle8.mods.galacticraft.core.util.MapUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.core.wrappers.Footprint;
import micdoodle8.mods.galacticraft.core.wrappers.ScheduledBlockChange;
import micdoodle8.mods.galacticraft.core.wrappers.ScheduledDimensionChange;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TickHandlerServer
{
    private static final Map<DimensionType, CopyOnWriteArrayList<ScheduledBlockChange>> scheduledBlockChanges = new ConcurrentHashMap<>();
    private static final Map<DimensionType, CopyOnWriteArrayList<BlockVec3>> scheduledTorchUpdates = new ConcurrentHashMap<>();
    private static final Map<DimensionType, Set<BlockPos>> edgeChecks = new HashMap<>();
    public static final Map<DimensionType, Map<Long, List<Footprint>>> serverFootprintMap = new HashMap<>();
    public static final List<BlockVec3Dim> footprintBlockChanges = Lists.newArrayList();
    public static WorldDataSpaceRaces spaceRaceData = null;
    public static final ArrayList<ServerPlayerEntity> playersRequestingMapData = Lists.newArrayList();
    private static long tickCount;
    private static final CopyOnWriteArrayList<ScheduledDimensionChange> scheduledDimensionChanges = new CopyOnWriteArrayList<>();
    private final int MAX_BLOCKS_PER_TICK = 50000;
    //    private static List<GalacticraftPacketHandler> packetHandlers = Lists.newCopyOnWriteArrayList();
    private static final List<FluidNetwork> fluidNetworks = Lists.newArrayList();
    public static int timerHoustonCommand;

    public static void addFluidNetwork(FluidNetwork network)
    {
        fluidNetworks.add(network);
    }

    public static void removeFluidNetwork(FluidNetwork network)
    {
        fluidNetworks.remove(network);
    }

//    public static void addPacketHandler(GalacticraftPacketHandler handler)
//    {
//        TickHandlerServer.packetHandlers.add(handler);
//    }
//
//    @SubscribeEvent
//    public void worldUnloadEvent(WorldEvent.Unload event)
//    {
//        for (GalacticraftPacketHandler packetHandler : packetHandlers)
//        {
//            packetHandler.unload(event.getWorld());
//        }
//    }

    public static void restart()
    {
        TickHandlerServer.scheduledBlockChanges.clear();
        TickHandlerServer.scheduledTorchUpdates.clear();
        TickHandlerServer.edgeChecks.clear();
        TickHandlerServer.serverFootprintMap.clear();
//        TickHandlerServer.hydrogenTransmitterUpdates.clear();
        TickHandlerServer.playersRequestingMapData.clear();

        for (SpaceRace race : SpaceRaceManager.getSpaceRaces())
        {
            SpaceRaceManager.removeSpaceRace(race);
        }

        TickHandlerServer.spaceRaceData = null;
        TickHandlerServer.tickCount = 0L;
        TickHandlerServer.fluidNetworks.clear();
        MapUtil.reset();
        PainterTileEntity.loadedTilesForDim.clear();
    }

    public static void addFootprint(long chunkKey, Footprint print, DimensionType dimID)
    {
        Map<Long, List<Footprint>> footprintMap = TickHandlerServer.serverFootprintMap.get(dimID);
        List<Footprint> footprints;

        if (footprintMap == null)
        {
            footprintMap = new HashMap<>();
            TickHandlerServer.serverFootprintMap.put(dimID, footprintMap);
            footprints = new ArrayList<>();
            footprintMap.put(chunkKey, footprints);
        }
        else
        {
            footprints = footprintMap.computeIfAbsent(chunkKey, k -> new ArrayList<>());

        }

        footprints.add(print);
    }

    public static void scheduleNewBlockChange(DimensionType dimID, ScheduledBlockChange change)
    {
        CopyOnWriteArrayList<ScheduledBlockChange> changeList = TickHandlerServer.scheduledBlockChanges.get(dimID);

        if (changeList == null)
        {
            changeList = new CopyOnWriteArrayList<>();
        }

        changeList.add(change);
        TickHandlerServer.scheduledBlockChanges.put(dimID, changeList);
    }

    /**
     * Only use this for AIR blocks (any type of BlockAir)
     *
     * @param dimID
     * @param changeAdd List of <ScheduledBlockChange>
     */
    public static void scheduleNewBlockChange(DimensionType dimID, List<ScheduledBlockChange> changeAdd)
    {
        CopyOnWriteArrayList<ScheduledBlockChange> changeList = TickHandlerServer.scheduledBlockChanges.get(dimID);

        if (changeList == null)
        {
            changeList = new CopyOnWriteArrayList<>();
        }

        changeList.addAll(changeAdd);
        TickHandlerServer.scheduledBlockChanges.put(dimID, changeList);
    }

    public static void scheduleNewDimensionChange(ScheduledDimensionChange change)
    {
        scheduledDimensionChanges.add(change);
    }

    public static void scheduleNewTorchUpdate(DimensionType dimType, List<BlockVec3> torches)
    {
        CopyOnWriteArrayList<BlockVec3> updateList = TickHandlerServer.scheduledTorchUpdates.get(dimType);

        if (updateList == null)
        {
            updateList = new CopyOnWriteArrayList<>();
        }

        updateList.addAll(torches);
        TickHandlerServer.scheduledTorchUpdates.put(dimType, updateList);
    }

    public static void scheduleNewEdgeCheck(DimensionType dimType, BlockPos edgeBlock)
    {
        Set<BlockPos> updateList = TickHandlerServer.edgeChecks.get(dimType);

        if (updateList == null)
        {
            updateList = new HashSet<>();
        }

        updateList.add(edgeBlock);
        TickHandlerServer.edgeChecks.put(dimType, updateList);
    }

    public static boolean scheduledForChange(DimensionType dimID, BlockPos test)
    {
        CopyOnWriteArrayList<ScheduledBlockChange> changeList = TickHandlerServer.scheduledBlockChanges.get(dimID);

        if (changeList != null)
        {
            for (ScheduledBlockChange change : changeList)
            {
                if (test.equals(change.getChangePosition()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
//        Prevent issues when clients switch to LAN servers
        if (server == null)
        {
            return;
        }

        if (event.phase == TickEvent.Phase.START)
        {
            if (timerHoustonCommand > 0)
            {
                if (--timerHoustonCommand == 0)
                {
//                    CommandGCHouston.reset(); TODO Commands
                }
            }

            for (ScheduledDimensionChange change : TickHandlerServer.scheduledDimensionChanges)
            {
                try
                {
                    GCPlayerStats stats = GCPlayerStats.get(change.getPlayer());
                    final Dimension dimension = WorldUtil.getProviderForDimensionServer(change.getDimensionId());
                    if (dimension != null)
                    {
                        final DimensionType dim = GCCoreUtil.getDimensionType(dimension);
                        GCLog.info("Found matching world (" + dim + ") for name: " + change.getDimensionId());

                        if (change.getPlayer().world instanceof ServerWorld)
                        {
                            final ServerWorld world = (ServerWorld) change.getPlayer().world;

                            WorldUtil.transferEntityToDimension(change.getPlayer(), dim, world);
                        }
                    }
                    else
                    {
                        GCLog.severe("World not found when attempting to transfer entity to dimension: " + change.getDimensionId());
                    }

                    stats.setTeleportCooldown(10);
                    GalacticraftCore.packetPipeline.sendTo(new PacketSimple(EnumSimplePacket.C_CLOSE_GUI, GCCoreUtil.getDimensionType(change.getPlayer().world), new Object[]{}), change.getPlayer());
                }
                catch (Exception e)
                {
                    GCLog.severe("Error occurred when attempting to transfer entity to dimension: " + change.getDimensionId());
                    e.printStackTrace();
                }
            }

            TickHandlerServer.scheduledDimensionChanges.clear();

            if (MapUtil.calculatingMap.get())
            {
                MapUtil.BiomeMapNextTick_MultiThreaded();
            }
            else if (!MapUtil.doneOverworldTexture)
            {
                MapUtil.makeOverworldTexture();
            }

            if (TickHandlerServer.spaceRaceData == null)
            {
                ServerWorld world = server.getWorld(DimensionType.OVERWORLD);
                WorldDataSpaceRaces.initWorldData(world);
//                TickHandlerServer.spaceRaceData = (WorldDataSpaceRaces) world.getMapStorage().getOrLoadData(WorldDataSpaceRaces.class, WorldDataSpaceRaces.saveDataID);
//
//                if (TickHandlerServer.spaceRaceData == null)
//                {
//                    TickHandlerServer.spaceRaceData = new WorldDataSpaceRaces(WorldDataSpaceRaces.saveDataID);
//                    world.getMapStorage().setData(WorldDataSpaceRaces.saveDataID, TickHandlerServer.spaceRaceData);
//                }
            }

            SpaceRaceManager.tick();

            if (TickHandlerServer.tickCount % 33 == 0)
            {
                Iterable<ServerWorld> worlds = server.getWorlds();

                for (ServerWorld world : worlds)
                {
                    PainterTileEntity.onServerTick(world);
                }
            }
            if (TickHandlerServer.tickCount % 100 == 0)
            {
                Iterable<ServerWorld> worlds = server.getWorlds();

                for (ServerWorld world : worlds)
                {
                    ServerChunkProvider chunkProviderServer = world.getChunkProvider();

                    Map<Long, List<Footprint>> footprintMap = TickHandlerServer.serverFootprintMap.get(GCCoreUtil.getDimensionType(world));

                    if (footprintMap != null)
                    {
                        boolean mapChanged = false;

//                        if (chunkProviderServer != null)
//                        {
//                            Iterable<ChunkHolder> iterator = chunkProviderServer.chunkManager.func_223491_f();
//
//                            while (iterator.hasNext())
//                            {
//                                Chunk chunk = (Chunk) iterator.next();
//                                long chunkKey = ChunkPos.asLong(chunk.x, chunk.z);
//
//                                List<Footprint> footprints = footprintMap.get(chunkKey);
//
//                                if (footprints != null)
//                                {
//                                    List<Footprint> toRemove = new ArrayList<Footprint>();
//
//                                    for (int j = 0; j < footprints.size(); j++)
//                                    {
//                                        footprints.get(j).age += 100;
//
//                                        if (footprints.get(j).age >= Footprint.MAX_AGE)
//                                        {
//                                            toRemove.add(footprints.get(j));
//                                        }
//                                    }
//
//                                    if (!toRemove.isEmpty())
//                                    {
//                                        footprints.removeAll(toRemove);
//                                    }
//
//                                    footprintMap.put(chunkKey, footprints);
//                                    mapChanged = true;
//
//                                    GalacticraftCore.packetPipeline.sendToDimension(new PacketSimple(EnumSimplePacket.C_UPDATE_FOOTPRINT_LIST, worlds[i].dimension.getDimension(), new Object[] { chunkKey, footprints.toArray(new Footprint[footprints.size()]) }), worlds[i].dimension.getDimension());
//                                }
//                            }
//                        } TODO Footprints

//                        if (mapChanged)
//                        {
//                            TickHandlerServer.serverFootprintMap.put(GCCoreUtil.getDimensionID(world), footprintMap);
//                        }
                    }
                }
            }

            if (!footprintBlockChanges.isEmpty())
            {
                for (BlockVec3Dim targetPoint : footprintBlockChanges)
                {
                    Iterable<ServerWorld> worlds = server.getWorlds();

                    for (ServerWorld world : worlds)
                    {
                        if (GCCoreUtil.getDimensionType(world) == targetPoint.dim)
                        {
                            long chunkKey = ChunkPos.asLong(targetPoint.x >> 4, targetPoint.z >> 4);
                            GalacticraftCore.packetPipeline.sendToAllAround(new PacketSimple(EnumSimplePacket.C_FOOTPRINTS_REMOVED, GCCoreUtil.getDimensionType(world), new Object[]{chunkKey, new BlockVec3(targetPoint.x, targetPoint.y, targetPoint.z)}), new PacketDistributor.TargetPoint(targetPoint.x, targetPoint.y, targetPoint.z, 50, targetPoint.dim));


//                            Map<Long, List<Footprint>> footprintMap = TickHandlerServer.serverFootprintMap.get(world.getDimension().dimensionId);
//
//                            if (footprintMap != null && !footprintMap.isEmpty())
//                            {
//                                List<Footprint> footprints = footprintMap.get(chunkKey);
//                                if (footprints != null)
//                                	GalacticraftCore.packetPipeline.sendToAllAround(new PacketSimple(EnumSimplePacket.C_UPDATE_FOOTPRINT_LIST, new Object[] { chunkKey, footprints.toArray(new Footprint[footprints.size()]) }), new PacketDistributor.TargetPoint(targetPoint.dim, targetPoint.x, targetPoint.y, targetPoint.z, 50));
//                            }
                        }
                    }
                }

                footprintBlockChanges.clear();
            }

            if (tickCount % 20 == 0)
            {
                if (!playersRequestingMapData.isEmpty())
                {
                    File baseFolder = new File(server.getWorld(DimensionType.OVERWORLD).getSaveHandler().getPlayerFolder(), "galacticraft/overworldMap");
                    if (!baseFolder.exists() && !baseFolder.mkdirs())
                    {

                        GCLog.severe("Base folder(s) could not be created: " + baseFolder.getAbsolutePath());
                    }
                    else
                    {
                        ArrayList<ServerPlayerEntity> copy = new ArrayList<>(playersRequestingMapData);
                        BufferedImage reusable = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
                        for (ServerPlayerEntity playerMP : copy)
                        {
                            GCPlayerStats stats = GCPlayerStats.get(playerMP);
                            MapUtil.makeVanillaMap(playerMP.dimension, (int) Math.floor(stats.getCoordsTeleportedFromZ()) >> 4, (int) Math.floor(stats.getCoordsTeleportedFromZ()) >> 4, baseFolder, reusable);
                        }
                        playersRequestingMapData.removeAll(copy);
                    }
                }
            }

            TickHandlerServer.tickCount++;
        }
        else if (event.phase == TickEvent.Phase.END)
        {
            for (FluidNetwork network : new ArrayList<>(fluidNetworks))
            {
                if (!network.pipes.isEmpty())
                {
                    network.tickEnd();
                }
                else
                {
                    fluidNetworks.remove(network);
                }
            }

            int maxPasses = 10;
         

            maxPasses = 10;
            maxPasses = 10;
        }
    }

    private static final Set<DimensionType> worldsNeedingUpdate = new HashSet<>();

    public static void markWorldNeedsUpdate(DimensionType dimension)
    {
        worldsNeedingUpdate.add(dimension);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            final ServerWorld world = (ServerWorld) event.world;

            CopyOnWriteArrayList<ScheduledBlockChange> changeList = TickHandlerServer.scheduledBlockChanges.get(GCCoreUtil.getDimensionType(world));

            if (changeList != null && !changeList.isEmpty())
            {
                int blockCount = 0;
                int blockCountMax = Math.max(this.MAX_BLOCKS_PER_TICK, changeList.size() / 4);
                List<ScheduledBlockChange> newList = new ArrayList<>(Math.max(0, changeList.size() - blockCountMax));

                for (ScheduledBlockChange change : changeList)
                {
                    if (++blockCount > blockCountMax)
                    {
                        newList.add(change);
                    }
                    else
                    {
                        if (change != null)
                        {
                            BlockPos changePosition = change.getChangePosition();
                            Block block = world.getBlockState(changePosition).getBlock();
                            //Only replace blocks of type BlockAir or fire - this is to prevent accidents where other mods have moved blocks
                            if (block instanceof AirBlock || block == Blocks.FIRE)
                            {
                                world.setBlockState(changePosition, change.getChangeState(), change.getChangeUpdateFlag());
                            }
                        }
                    }
                }

                changeList.clear();
                TickHandlerServer.scheduledBlockChanges.remove(GCCoreUtil.getDimensionType(world));
                if (newList.size() > 0)
                {
                    TickHandlerServer.scheduledBlockChanges.put(GCCoreUtil.getDimensionType(world), new CopyOnWriteArrayList<>(newList));
                }
            }

            CopyOnWriteArrayList<BlockVec3> torchList = TickHandlerServer.scheduledTorchUpdates.get(GCCoreUtil.getDimensionType(world));

            if (torchList != null && !torchList.isEmpty())
            {
                for (BlockVec3 torch : torchList)
                {
                    if (torch != null)
                    {
                        BlockPos pos = new BlockPos(torch.x, torch.y, torch.z);
                        Block b = world.getBlockState(pos).getBlock();
                        if (b instanceof BlockUnlitTorch)
                        {
//                            world.scheduleUpdate(pos, b, 2 + world.rand.nextInt(30));
                            world.getPendingBlockTicks().scheduleTick(pos, b, 2 + world.rand.nextInt(30));
                        }
                    }
                }

                torchList.clear();
                TickHandlerServer.scheduledTorchUpdates.remove(GCCoreUtil.getDimensionType(world));
            }

            if (world.getDimension() instanceof IOrbitDimension)
            {
                DimensionType dim = ((IOrbitDimension) world.getDimension()).getPlanetIdToOrbit();
                int minY = ((IOrbitDimension) world.getDimension()).getYCoordToTeleportToPlanet();
                world.getEntities().filter(e -> e.getPosY() <= minY && e.world == world).forEach(e -> WorldUtil.transferEntityToDimension(e, dim, world, false, null));
            }

            DimensionType dimensionID = GCCoreUtil.getDimensionType(world);
            if (worldsNeedingUpdate.contains(dimensionID))
            {
                worldsNeedingUpdate.remove(dimensionID);
            }
        }
        else if (event.phase == TickEvent.Phase.END)
        {
            final ServerWorld world = (ServerWorld) event.world;

//            for (GalacticraftPacketHandler handler : packetHandlers)
//            {
//                handler.tick(world);
//            }

            DimensionType dimID = GCCoreUtil.getDimensionType(world);
            Set<BlockPos> edgesList = TickHandlerServer.edgeChecks.get(dimID);
            final HashSet<BlockPos> checkedThisTick = new HashSet<>();

            if (edgesList != null && !edgesList.isEmpty())
            {
                List<BlockPos> edgesListCopy = new ArrayList<>(edgesList);
                for (BlockPos edgeBlock : edgesListCopy)
                {
                    if (edgeBlock != null && !checkedThisTick.contains(edgeBlock))
                    {
                        if (TickHandlerServer.scheduledForChange(dimID, edgeBlock))
                        {
                            continue;
                        }

                        //ThreadFindSeal done = new ThreadFindSeal(world, edgeBlock, 0, new ArrayList<>());
                        //checkedThisTick.addAll(done.checkedAll());
                    }
                }

                TickHandlerServer.edgeChecks.remove(GCCoreUtil.getDimensionType(world));
            }
        }
    }
}
