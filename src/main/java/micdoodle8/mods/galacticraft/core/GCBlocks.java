package micdoodle8.mods.galacticraft.core;

import com.google.common.collect.Maps;

import micdoodle8.mods.galacticraft.core.blocks.*;
import micdoodle8.mods.galacticraft.core.tile.*;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.core.util.StackSorted;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID_CORE, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GCBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, Constants.MOD_ID_CORE);
	
	public static final RegistryObject<BlockBreathableAir> BREATHEABLE_AIR = BLOCKS.register(BlockNames.breatheableAir, () -> new BlockBreathableAir(Block.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().hardnessAndResistance(0.0F, 10000.0F)));

	public static final RegistryObject<BlockBrightAir> BRIGHT_AIR = BLOCKS.register(BlockNames.brightAir, () -> new BlockBrightAir(Block.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().hardnessAndResistance(0.0F, 10000.0F).lightValue(15)));

	public static final RegistryObject<BlockBrightBreathableAir> BRIGHT_BREATHEABLE_AIR = BLOCKS.register(BlockNames.brightBreatheableAir, () -> new BlockBrightBreathableAir(Block.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().hardnessAndResistance(0.0F, 10000.0F).lightValue(15)));

	public static final RegistryObject<BlockBrightLamp> ARC_LAMP = BLOCKS.register(BlockNames.arcLamp, () -> new BlockBrightLamp(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.1F).sound(SoundType.METAL).lightValue(13)));

	public static final RegistryObject<BlockTier1TreasureChest> TREASURE_CHEST_T1 = BLOCKS.register(BlockNames.treasureChestTier1, () -> new BlockTier1TreasureChest(Block.Properties.create(Material.ROCK).hardnessAndResistance(100000.0F).sound(SoundType.STONE).lightValue(13)));

	public static final RegistryObject<PadBlock> LANDING_PAD = BLOCKS.register(BlockNames.landingPad, () -> new PadBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F, 10.0F).sound(SoundType.METAL)));

	public static final RegistryObject<PadBlock> BUGGY_PAD = BLOCKS.register(BlockNames.buggyPad, () -> new PadBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F, 10.0F).sound(SoundType.METAL)));

	public static final RegistryObject<PadFullBlock> LANDING_PAD_FULL = BLOCKS.register(BlockNames.landingPadFull, () -> new PadFullBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F, 10.0F).sound(SoundType.METAL)));

	public static final RegistryObject<PadFullBlock> BUGGY_PAD_FULL = BLOCKS.register(BlockNames.buggyPadFull, () -> new PadFullBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F, 10.0F).sound(SoundType.METAL)));

	public static final RegistryObject<BlockUnlitTorch> UNLIT_TORCH = BLOCKS.register(BlockNames.unlitTorch, () -> new BlockUnlitTorch(false, Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(3).sound(SoundType.WOOD)));

	public static final RegistryObject<BlockUnlitTorch> UNLIT_TORCH_LIT = BLOCKS.register(BlockNames.unlitTorchLit, () -> new BlockUnlitTorch(true, Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(14).sound(SoundType.WOOD)));

	public static final RegistryObject<BlockGlowstoneTorch> GLOWSTONE_TORCH = BLOCKS.register(BlockNames.glowstoneTorch, () -> new BlockGlowstoneTorch(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(12).sound(SoundType.WOOD)));
	//TODO. rename to oxygen_collector
	public static final RegistryObject<BlockOxygenCollector> OXYGEN_COLLECTOR = BLOCKS.register(BlockNames.oxygenCollector, () -> new BlockOxygenCollector(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));

	public static final RegistryObject<BlockOxygenCompressor> OXYGEN_COMPRESSOR = BLOCKS.register(BlockNames.oxygenCompressor, () -> new BlockOxygenCompressor(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));

	public static final RegistryObject<BlockOxygenCompressor> OXYGEN_DECOMPRESSOR = BLOCKS.register(BlockNames.oxygenDecompressor, () -> new BlockOxygenCompressor(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));

	public static final RegistryObject<CargoLoaderBlock> CARGO_LOADER = BLOCKS.register(BlockNames.cargoLoader, () -> new CargoLoaderBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<CargoUnloaderBlock> CARGO_UNLOADER = BLOCKS.register(BlockNames.cargoUnloader, () -> new CargoUnloaderBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockNasaWorkbench> NASA_WORKBENCH = BLOCKS.register(BlockNames.nasaWorkbench, () -> new BlockNasaWorkbench(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockIngotCompressorElectric> INGOT_COMPRESSOR = BLOCKS.register(BlockNames.ingotCompressorElectric, () -> new BlockIngotCompressorElectric(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockIngotCompressorElectricAdvanced> INGOT_COMPRESSOR_ADVANCED = BLOCKS.register(BlockNames.ingotCompressorElectricAdvanced, () -> new BlockIngotCompressorElectricAdvanced(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockCircuitFabricator> CIRCUT_FABRICATOR = BLOCKS.register(BlockNames.circuitFabricator, () -> new BlockCircuitFabricator(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockDeconstructor> DECONSTRUCTOR = BLOCKS.register(BlockNames.deconstructor, () -> new BlockDeconstructor(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockPainter> PAINTER = BLOCKS.register(BlockNames.painter, () -> new BlockPainter(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockConcealedDetector> CONCEALED_DETECTOR = BLOCKS.register(BlockNames.concealedDetector, () -> new BlockConcealedDetector(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockFallenMeteor> FALLEN_METEOR = BLOCKS.register(BlockNames.fallenMeteor, () -> new BlockFallenMeteor(Block.Properties.create(Material.ROCK).hardnessAndResistance(40.0F).sound(SoundType.STONE)));

	public static final RegistryObject<BlockSpaceStationBase> SPACE_STATION = BLOCKS.register(BlockNames.spaceStationBase, () -> new BlockSpaceStationBase(Block.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).noDrops()));

	public static final RegistryObject<ParaChestBlock> PARA_CHEST = BLOCKS.register(BlockNames.parachest, () -> new ParaChestBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));

	public static final RegistryObject<BlockCheese> CHEESE = BLOCKS.register(BlockNames.cheeseBlock, () -> new BlockCheese(Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));

	public static final RegistryObject<BlockBossSpawner> BOSS_SPAWNER = BLOCKS.register(BlockNames.bossSpawner, () -> new BlockBossSpawner(Block.Properties.create(Material.ROCK).hardnessAndResistance(1000000.0F).noDrops()));

	public static final RegistryObject<BlockEmergencyBox> EMERGENCY_BOX = BLOCKS.register(BlockNames.emergencyBox, () -> new BlockEmergencyBox(Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 70.0F).lightValue(15).sound(SoundType.METAL)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_0 = BLOCKS.register(BlockNames.decoBlock0, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_1 = BLOCKS.register(BlockNames.decoBlock1, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_COPPER = BLOCKS.register(BlockNames.decoBlockCopper, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_TIN = BLOCKS.register(BlockNames.decoBlockTin, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_ALUMINIUM = BLOCKS.register(BlockNames.decoBlockAluminum, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_METEOR_IRON = BLOCKS.register(BlockNames.decoBlockMeteorIron, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_SILICON = BLOCKS.register(BlockNames.decoBlockSilicon, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	//TODO: rename to copper_ore
	public static final RegistryObject<OreBlock> COPPER_ORE = BLOCKS.register(BlockNames.oreCopper, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	//TODO: rename to tin_ore
	public static final RegistryObject<OreBlock> TIN_ORE = BLOCKS.register(BlockNames.oreTin, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	//TODO: rename to aluminium_ore
	public static final RegistryObject<OreBlock> ALUMINIUM_ORE = BLOCKS.register(BlockNames.oreAluminum, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	//TODO: rename to silicon_ore
	public static final RegistryObject<OreBlock> SILICON_ORE = BLOCKS.register(BlockNames.oreSilicon, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	//TODO: rename to meteoric_iron_ore
	public static final RegistryObject<OreBlock> METEORIC_IRON_ORE = BLOCKS.register(BlockNames.oreMeteoricIron, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<OreBlock> MOON_DIRT = BLOCKS.register(BlockNames.moonDirt, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<OreBlock> MOON_STONE = BLOCKS.register(BlockNames.moonStone, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<OreBlock> MOON_TURF = BLOCKS.register(BlockNames.moonTurf, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	//TODO: rename to 'copper_ore_moon'
	public static final RegistryObject<OreBlock> COPPER_ORE_MOON = BLOCKS.register(BlockNames.oreCopperMoon, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	//TODO: rename to 'tin_ore_moon'
	public static final RegistryObject<OreBlock> TIN_ORE_MOON = BLOCKS.register(BlockNames.oreTinMoon, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	//TODO: rename to 'saphire_ore'
	public static final RegistryObject<OreBlock> SAPHIRE_ORE = BLOCKS.register(BlockNames.oreSapphire, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	
	public static final RegistryObject<OreBlock> MOON_DUNGEON_BRICK = BLOCKS.register(BlockNames.moonDungeonBrick, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

    public static Material machine = new Material.Builder(MaterialColor.IRON).build();

    public static ArrayList<Block> hiddenBlocks = new ArrayList<>();
    public static ArrayList<Block> otherModTorchesLit = new ArrayList<>();
    public static ArrayList<Block> otherModTorchesUnlit = new ArrayList<>();

    public static Map<EnumSortCategoryBlock, List<StackSorted>> sortMapBlocks = Maps.newHashMap();
    public static HashMap<Block, Block> itemChanges = new HashMap<>(4, 1.0F);

    @Deprecated
    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, IForgeRegistryEntry<V> thing, ResourceLocation name)
    {
        reg.register(thing.setRegistryName(name));
    }

    @Deprecated
    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, IForgeRegistryEntry<V> thing, String name)
    {
        register(reg, thing, new ResourceLocation(Constants.MOD_ID_CORE, name));
    }

    @SubscribeEvent
    public static void initTileEntities(RegistryEvent.Register<TileEntityType<?>> evt)
    {
        IForgeRegistry<TileEntityType<?>> r = evt.getRegistry();

        register(r, TileEntityType.Builder.create(ChestTileEntity::new, TREASURE_CHEST_T1.get()).build(null), BlockNames.treasureChestTier1);
        //register(r, TileEntityType.Builder.create(TileEntityOxygenDistributor::new, oxygenDistributor).build(null), BlockNames.oxygenDistributor);
        register(r, TileEntityType.Builder.create(OxygenCollectorTileEntity::new, OXYGEN_COLLECTOR.get()).build(null), BlockNames.oxygenCollector);
        //register(r, TileEntityType.Builder.create(TileEntityAirLock::new, airLockFrame).build(null), BlockNames.airLockFrame);
        register(r, TileEntityType.Builder.create(TileEntityNasaWorkbench::new, NASA_WORKBENCH.get()).build(null), BlockNames.nasaWorkbench);
        register(r, TileEntityType.Builder.create(DeconstructorTileEntity::new, DECONSTRUCTOR.get()).build(null), BlockNames.deconstructor);
        register(r, TileEntityType.Builder.create(OxygenCompressorTileEntity::new, OXYGEN_COMPRESSOR.get()).build(null), BlockNames.oxygenCompressor);
        register(r, TileEntityType.Builder.create(OxygenDecompressorTileEntity::new, OXYGEN_DECOMPRESSOR.get()).build(null), BlockNames.oxygenDecompressor);
        //register(r, TileEntityType.Builder.create(FuelLoaderTileEntity::new, fuelLoader).build(null), BlockNames.fuelLoader);
        register(r, TileEntityType.Builder.create(TileEntityLandingPadSingle::new, LANDING_PAD.get()).build(null), BlockNames.landingPad);
        register(r, TileEntityType.Builder.create(TileEntityLandingPad::new, LANDING_PAD_FULL.get()).build(null), BlockNames.landingPadFull);
        register(r, TileEntityType.Builder.create(TileEntitySpaceStationBase::new, SPACE_STATION.get()).build(null), BlockNames.spaceStationBase);
        //register(r, TileEntityType.Builder.create(TileEntityFake::new, fakeBlock).build(null), BlockNames.fakeBlock);
        //register(r, TileEntityType.Builder.create(TileEntityOxygenSealer::new, oxygenSealer).build(null), BlockNames.oxygenSealer);
        register(r, TileEntityType.Builder.create(TileEntityDungeonSpawner::new, BOSS_SPAWNER.get()).build(null), BlockNames.bossSpawner);
        //register(r, TileEntityType.Builder.create(TileEntityOxygenDetector::new, oxygenDetector).build(null), BlockNames.oxygenDetector);
        register(r, TileEntityType.Builder.create(TileEntityBuggyFueler::new, BUGGY_PAD.get()).build(null), BlockNames.buggyPad);
        register(r, TileEntityType.Builder.create(TileEntityBuggyFuelerSingle::new, BUGGY_PAD_FULL.get()).build(null), BlockNames.buggyPadFull);
        register(r, TileEntityType.Builder.create(CargoLoaderTileEntity.T1::new, CARGO_LOADER.get()).build(null), BlockNames.cargoLoader);
        register(r, TileEntityType.Builder.create(CargoUnloaderTileEntity.T1::new, CARGO_UNLOADER.get()).build(null), BlockNames.cargoUnloader);
        register(r, TileEntityType.Builder.create(ParaChestTileEntity::new, PARA_CHEST.get()).build(null), BlockNames.parachest);
//        register(r, TileEntityType.Builder.create(TileEntityDish::new, radioTelescope).build(null), BlockNames.radioTelescope);
//        register(r, TileEntityType.Builder.create(TileEntityAluminumWireSwitch::new, "GC Switchable Aluminum Wire").build(null));
        register(r, TileEntityType.Builder.create(TileEntityFallenMeteor::new, FALLEN_METEOR.get()).build(null), BlockNames.fallenMeteor);
        register(r, TileEntityType.Builder.create(ElectricCompressorTileEntity.T1::new, INGOT_COMPRESSOR.get()).build(null), BlockNames.ingotCompressorElectric);
        register(r, TileEntityType.Builder.create(ElectricCompressorTileEntity.T2::new, INGOT_COMPRESSOR_ADVANCED.get()).build(null), BlockNames.ingotCompressorElectricAdvanced);
        register(r, TileEntityType.Builder.create(CircuitFabricatorTileEntity::new, CIRCUT_FABRICATOR.get()).build(null), BlockNames.circuitFabricator);
        //register(r, TileEntityType.Builder.create(TileEntityAirLockController::new, airLockController).build(null), BlockNames.airLockController);
        //register(r, TileEntityType.Builder.create(TileEntityThruster::new, spinThruster).build(null), BlockNames.spinThruster);
        register(r, TileEntityType.Builder.create(TileEntityArclamp::new, ARC_LAMP.get()).build(null), BlockNames.arcLamp);
        //register(r, TileEntityType.Builder.create(TileEntityTelemetry::new, telemetry).build(null), BlockNames.telemetry);
//        register(r, TileEntityType.Builder.create(TileEntityPainter::new, "GC Painter").build(null));
//        register(r, TileEntityType.Builder.create(TileEntityFluidTank::new, fluidTank).build(null), BlockNames.fluidTank); //todo
        //register(r, TileEntityType.Builder.create(TileEntityPlayerDetector::new, concealedDetector).build(null), BlockNames.concealedDetector);
//        register(r, TileEntityType.Builder.create(TileEntityPlatform::new, platform).build(null), BlockNames.platform); //todo
        register(r, TileEntityType.Builder.create(TileEntityEmergencyBox::new, EMERGENCY_BOX.get()).build(null), BlockNames.emergencyBox);
//        register(r, TileEntityType.Builder.create(TileEntityNull::new, "GC Null Tile").build(null));
    }
}
