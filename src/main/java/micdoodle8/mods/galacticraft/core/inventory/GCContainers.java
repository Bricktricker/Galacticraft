package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.core.client.gui.container.*;
import micdoodle8.mods.galacticraft.core.entities.EntityBuggy;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.tile.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.IForgeRegistry;

import static micdoodle8.mods.galacticraft.core.GCBlocks.register;

public class GCContainers
{
    @SuppressWarnings("deprecation")
	@SubscribeEvent
    public static void initContainers(RegistryEvent.Register<ContainerType<?>> evt)
    {
        IForgeRegistry<ContainerType<?>> r = evt.getRegistry();

        ContainerType<ContainerBuggy> buggy = IForgeContainerType.create((windowId, inv, data) -> new ContainerBuggy(windowId, inv, EntityBuggy.BuggyType.byId(data.readInt())));
        ContainerType<CargoLoaderContainer> cargoLoader = IForgeContainerType.create(CargoLoaderContainer::new);
        ContainerType<CircuitFabricatorContainer> circuitFabricator = IForgeContainerType.create(CircuitFabricatorContainer::new);
        ContainerType<DeconstructorContainer> deconstructor = IForgeContainerType.create(DeconstructorContainer::new);
        ContainerType<ElectricIngotCompressorContainer> electricIngotCompressor = IForgeContainerType.create(ElectricIngotCompressorContainer::new);
        ContainerType<ContainerExtendedInventory> extendedInventory = IForgeContainerType.create((windowId, inv, data) ->
        {
            GCPlayerStats stats = GCPlayerStats.get(inv.player);
            return new ContainerExtendedInventory(windowId, inv, stats.getExtendedInventory());
        });
        ContainerType<ContainerFuelLoader> fuelLoader = IForgeContainerType.create((windowId, inv, data) -> new ContainerFuelLoader(windowId, inv, (FuelLoaderTileEntity) inv.player.world.getTileEntity(new BlockPos(data.readInt(), data.readInt(), data.readInt()))));
        ContainerType<OxygenCollectorContainer> oxygenCollector = IForgeContainerType.create((windowId, inv, data) -> new OxygenCollectorContainer(windowId, inv, (OxygenCollectorTileEntity) inv.player.world.getTileEntity(new BlockPos(data.readInt(), data.readInt(), data.readInt()))));
        ContainerType<OxygenCompressorContainer> oxygenCompressor = IForgeContainerType.create((windowId, inv, data) -> new OxygenCompressorContainer(windowId, inv, (OxygenCompressorTileEntity) inv.player.world.getTileEntity(new BlockPos(data.readInt(), data.readInt(), data.readInt()))));
        ContainerType<OxygenDecompressorContainer> oxygenDecompressor = IForgeContainerType.create((windowId, inv, data) -> new OxygenDecompressorContainer(windowId, inv, (OxygenDecompressorTileEntity) inv.player.world.getTileEntity(new BlockPos(data.readInt(), data.readInt(), data.readInt()))));
        //ContainerType<ContainerOxygenDistributor> oxygenDistributor = IForgeContainerType.create((windowId, inv, data) -> new ContainerOxygenDistributor(windowId, inv, (TileEntityOxygenDistributor) inv.player.world.getTileEntity(new BlockPos(data.readInt(), data.readInt(), data.readInt()))));
        //ContainerType<ContainerOxygenSealer> oxygenSealer = IForgeContainerType.create((windowId, inv, data) -> new ContainerOxygenSealer(windowId, inv, (TileEntityOxygenSealer) inv.player.world.getTileEntity(new BlockPos(data.readInt(), data.readInt(), data.readInt()))));
        ContainerType<ContainerPainter> painter = IForgeContainerType.create((windowId, inv, data) -> new ContainerPainter(windowId, inv, (PainterTileEntity) inv.player.world.getTileEntity(new BlockPos(data.readInt(), data.readInt(), data.readInt()))));
        ContainerType<ParaChestContainer> parachest = IForgeContainerType.create(ParaChestContainer::new);
        ContainerType<ContainerRocketInventory> rocketInventory = IForgeContainerType.create((windowId, inv, data) -> new ContainerRocketInventory(windowId, inv, (EntityTieredRocket) inv.player.getRidingEntity()));
        ContainerType<ContainerSchematic> schematic = IForgeContainerType.create((windowId, inv, data) -> new ContainerSchematic(windowId, inv));
        ContainerType<ContainerSchematicTier1Rocket> schematicT1Rocket = IForgeContainerType.create((windowId, inv, data) -> new ContainerSchematicTier1Rocket(windowId, inv));
        ContainerType<ContainerSchematicBuggy> schematicBuggy = IForgeContainerType.create((windowId, inv, data) -> new ContainerSchematicBuggy(windowId, inv));
        //ContainerType<TreasureChestContainer> treasureT1 = IForgeContainerType.create(TreasureChestContainer::new);

        register(r, buggy, GCContainerNames.BUGGY);
        register(r, cargoLoader, GCContainerNames.CARGO_LOADER);
        register(r, circuitFabricator, GCContainerNames.CIRCUIT_FABRICATOR);
        register(r, deconstructor, GCContainerNames.DECONSTRUCTOR);
        register(r, electricIngotCompressor, GCContainerNames.ELECTRIC_INGOT_COMPRESSOR);
        register(r, extendedInventory, GCContainerNames.EXTENDED_INVENTORY);
        register(r, fuelLoader, GCContainerNames.FUEL_LOADER);
        register(r, oxygenCollector, GCContainerNames.OXYGEN_COLLECTOR);
        register(r, oxygenCompressor, GCContainerNames.OXYGEN_COMPRESSOR);
        register(r, oxygenDecompressor, GCContainerNames.OXYGEN_DECOMPRESSOR);
        //register(r, oxygenDistributor, GCContainerNames.OXYGEN_DISTRIBUTOR);
        //register(r, oxygenSealer, GCContainerNames.OXYGEN_SEALER);
        register(r, painter, GCContainerNames.PAINTER);
        register(r, parachest, GCContainerNames.PARACHEST);
        register(r, rocketInventory, GCContainerNames.ROCKET_INVENTORY);
        register(r, schematic, GCContainerNames.SCHEMATIC);
        register(r, schematicT1Rocket, GCContainerNames.SCHEMATIC_T1_ROCKET);
        register(r, schematicBuggy, GCContainerNames.SCHEMATIC_BUGGY);
        //register(r, treasureT1, GCContainerNames.TREASURE_CHEST_T1);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            ScreenManager.registerFactory(buggy, GuiBuggy::new);
            ScreenManager.registerFactory(cargoLoader, CargoLoaderScreen::new);
            ScreenManager.registerFactory(circuitFabricator, CircuitFabricatorScreen::new);
            ScreenManager.registerFactory(deconstructor, DeconstructorScreen::new);
            ScreenManager.registerFactory(electricIngotCompressor, ElectricIngotCompressorScreen::new);
            ScreenManager.registerFactory(extendedInventory, GuiExtendedInventory::new);
            ScreenManager.registerFactory(fuelLoader, GuiFuelLoader::new);
            ScreenManager.registerFactory(oxygenCollector, OxygenCollectorScreen::new);
            ScreenManager.registerFactory(oxygenCompressor, OxygenCompressorScreen::new);
            ScreenManager.registerFactory(oxygenDecompressor, OxygenDecompressorScreen::new);
            //ScreenManager.registerFactory(oxygenDistributor, GuiOxygenDistributor::new);
            //ScreenManager.registerFactory(oxygenSealer, GuiOxygenSealer::new);
            ScreenManager.registerFactory(painter, GuiPainter::new);
            ScreenManager.registerFactory(parachest, ParaChestScreen::new);
            ScreenManager.registerFactory(rocketInventory, GuiRocketInventory::new);
            ScreenManager.registerFactory(schematic, GuiSchematicInput::new);
            ScreenManager.registerFactory(schematicT1Rocket, GuiSchematicTier1Rocket::new);
            ScreenManager.registerFactory(schematicBuggy, GuiSchematicBuggy::new);
            //ScreenManager.registerFactory(treasureT1, TreasureChestScreen::new);
        });
    }
}
