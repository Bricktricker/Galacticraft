package micdoodle8.mods.galacticraft.core;

import micdoodle8.mods.galacticraft.core.tile.CargoLoaderTileEntity;
import micdoodle8.mods.galacticraft.core.tile.CargoUnloaderTileEntity;
import micdoodle8.mods.galacticraft.core.tile.CircuitFabricatorTileEntity;
import micdoodle8.mods.galacticraft.core.tile.DeconstructorTileEntity;
import micdoodle8.mods.galacticraft.core.tile.IngotCompressorTileEntity;
import micdoodle8.mods.galacticraft.core.tile.FuelLoaderTileEntity;
import micdoodle8.mods.galacticraft.core.tile.OxygenCollectorTileEntity;
import micdoodle8.mods.galacticraft.core.tile.OxygenCompressorTileEntity;
import micdoodle8.mods.galacticraft.core.tile.OxygenDecompressorTileEntity;
import micdoodle8.mods.galacticraft.core.tile.ParaChestTileEntity;
import micdoodle8.mods.galacticraft.core.tile.TileEntityArclamp;
import micdoodle8.mods.galacticraft.core.tile.BuggyPadTileEntity;
import micdoodle8.mods.galacticraft.core.tile.TileEntityBuggyFuelerSingle;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;
import micdoodle8.mods.galacticraft.core.tile.TileEntityEmergencyBox;
import micdoodle8.mods.galacticraft.core.tile.TileEntityFallenMeteor;
import micdoodle8.mods.galacticraft.core.tile.RocketPadTileEntity;
import micdoodle8.mods.galacticraft.core.tile.TileEntityLandingPadSingle;
import micdoodle8.mods.galacticraft.core.tile.NasaWorkbenchTileEntity;
import micdoodle8.mods.galacticraft.core.tile.TileEntitySpaceStationBase;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GCTileEntities {
	
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Constants.MOD_ID_CORE);
	
	public static final RegistryObject<TileEntityType<ChestTileEntity>> TREASURE_CHEST_T1 = TILE_ENTITIES.register(BlockNames.treasureChestTier1, () -> TileEntityType.Builder.create(ChestTileEntity::new, GCBlocks.TREASURE_CHEST_T1.get()).build(null));

	public static final RegistryObject<TileEntityType<OxygenCollectorTileEntity>> OXYGEN_COLLECTOR = TILE_ENTITIES.register(BlockNames.oxygenCollector, () -> TileEntityType.Builder.create(OxygenCollectorTileEntity::new, GCBlocks.OXYGEN_COLLECTOR.get()).build(null));

	public static final RegistryObject<TileEntityType<NasaWorkbenchTileEntity>> NASA_WORKBENCH = TILE_ENTITIES.register(BlockNames.nasaWorkbench, () -> TileEntityType.Builder.create(NasaWorkbenchTileEntity::new, GCBlocks.NASA_WORKBENCH.get()).build(null));

	public static final RegistryObject<TileEntityType<DeconstructorTileEntity>> DECONSTRUCTOR = TILE_ENTITIES.register(BlockNames.deconstructor, () -> TileEntityType.Builder.create(DeconstructorTileEntity::new, GCBlocks.DECONSTRUCTOR.get()).build(null));

	public static final RegistryObject<TileEntityType<OxygenCompressorTileEntity>> OXYGEN_COMPRESSOR = TILE_ENTITIES.register(BlockNames.oxygenCompressor, () -> TileEntityType.Builder.create(OxygenCompressorTileEntity::new, GCBlocks.OXYGEN_COMPRESSOR.get()).build(null));

	public static final RegistryObject<TileEntityType<OxygenDecompressorTileEntity>> OXYGEN_DECOMPRESSOR = TILE_ENTITIES.register(BlockNames.oxygenDecompressor, () -> TileEntityType.Builder.create(OxygenDecompressorTileEntity::new, GCBlocks.OXYGEN_DECOMPRESSOR.get()).build(null));

	public static final RegistryObject<TileEntityType<FuelLoaderTileEntity>> FUEL_LOADER = TILE_ENTITIES.register(BlockNames.fuelLoader, () -> TileEntityType.Builder.create(FuelLoaderTileEntity::new, GCBlocks.FUEL_LOADER.get()).build(null));

	public static final RegistryObject<TileEntityType<TileEntityLandingPadSingle>> LANDING_PAD = TILE_ENTITIES.register(BlockNames.rocketPad, () -> TileEntityType.Builder.create(TileEntityLandingPadSingle::new, GCBlocks.ROCKET_PAD.get()).build(null));

	public static final RegistryObject<TileEntityType<RocketPadTileEntity>> ROCKET_PAD_FULL = TILE_ENTITIES.register(BlockNames.rocketPadFull, () -> TileEntityType.Builder.create(RocketPadTileEntity::new, GCBlocks.LANDING_PAD_FULL.get()).build(null));

	public static final RegistryObject<TileEntityType<TileEntitySpaceStationBase>> SPACE_STATION = TILE_ENTITIES.register(BlockNames.spaceStationBase, () -> TileEntityType.Builder.create(TileEntitySpaceStationBase::new, GCBlocks.SPACE_STATION.get()).build(null));

	public static final RegistryObject<TileEntityType<TileEntityDungeonSpawner<Entity>>> BOSS_SPAWNER = TILE_ENTITIES.register(BlockNames.bossSpawner, () -> TileEntityType.Builder.create(TileEntityDungeonSpawner::new, GCBlocks.BOSS_SPAWNER.get()).build(null));

	public static final RegistryObject<TileEntityType<TileEntityBuggyFuelerSingle>> BUGGY_PAD = TILE_ENTITIES.register(BlockNames.buggyPad, () -> TileEntityType.Builder.create(TileEntityBuggyFuelerSingle::new, GCBlocks.BUGGY_PAD.get()).build(null));

	public static final RegistryObject<TileEntityType<BuggyPadTileEntity>> BUGGY_PAD_FULL = TILE_ENTITIES.register(BlockNames.buggyPadFull, () -> TileEntityType.Builder.create(BuggyPadTileEntity::new, GCBlocks.BUGGY_PAD_FULL.get()).build(null));

	public static final RegistryObject<TileEntityType<CargoLoaderTileEntity.T1>> CARGO_LOADER = TILE_ENTITIES.register(BlockNames.cargoLoader, () -> TileEntityType.Builder.create(CargoLoaderTileEntity.T1::new, GCBlocks.CARGO_LOADER.get()).build(null));

	public static final RegistryObject<TileEntityType<CargoUnloaderTileEntity.T1>> CARGO_UNLOADER = TILE_ENTITIES.register(BlockNames.cargoUnloader, () -> TileEntityType.Builder.create(CargoUnloaderTileEntity.T1::new, GCBlocks.CARGO_UNLOADER.get()).build(null));

	public static final RegistryObject<TileEntityType<ParaChestTileEntity>> PARA_CHEST = TILE_ENTITIES.register(BlockNames.parachest, () -> TileEntityType.Builder.create(ParaChestTileEntity::new, GCBlocks.PARA_CHEST.get()).build(null));

	public static final RegistryObject<TileEntityType<TileEntityFallenMeteor>> FALLEN_METEOR = TILE_ENTITIES.register(BlockNames.fallenMeteor, () -> TileEntityType.Builder.create(TileEntityFallenMeteor::new, GCBlocks.FALLEN_METEOR.get()).build(null));

	public static final RegistryObject<TileEntityType<IngotCompressorTileEntity.T1>> INGOT_COMPRESSOR = TILE_ENTITIES.register(BlockNames.ingotCompressor, () -> TileEntityType.Builder.create(IngotCompressorTileEntity.T1::new, GCBlocks.INGOT_COMPRESSOR.get()).build(null));

	public static final RegistryObject<TileEntityType<IngotCompressorTileEntity.T2>> INGOT_COMPRESSOR_ADVANCED = TILE_ENTITIES.register(BlockNames.ingotCompressorAdvanced, () -> TileEntityType.Builder.create(IngotCompressorTileEntity.T2::new, GCBlocks.INGOT_COMPRESSOR_ADVANCED.get()).build(null));

	public static final RegistryObject<TileEntityType<CircuitFabricatorTileEntity>> CIRCUT_FABRICATOR = TILE_ENTITIES.register(BlockNames.circuitFabricator, () -> TileEntityType.Builder.create(CircuitFabricatorTileEntity::new, GCBlocks.CIRCUT_FABRICATOR.get()).build(null));

	public static final RegistryObject<TileEntityType<TileEntityArclamp>> ARC_LAMP = TILE_ENTITIES.register(BlockNames.arcLamp, () -> TileEntityType.Builder.create(TileEntityArclamp::new, GCBlocks.ARC_LAMP.get()).build(null));

	public static final RegistryObject<TileEntityType<TileEntityEmergencyBox>> EMERGENCY_BOX = TILE_ENTITIES.register(BlockNames.emergencyBox, () -> TileEntityType.Builder.create(TileEntityEmergencyBox::new, GCBlocks.EMERGENCY_BOX.get()).build(null));

}
