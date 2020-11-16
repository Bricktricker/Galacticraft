package micdoodle8.mods.galacticraft.core;


import micdoodle8.mods.galacticraft.core.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;

public class GCBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, Constants.MOD_ID_CORE);
	
	public static final RegistryObject<BlockBreathableAir> BREATHEABLE_AIR = BLOCKS.register(BlockNames.breatheableAir, () -> new BlockBreathableAir(Block.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().hardnessAndResistance(0.0F, 10000.0F)));

	public static final RegistryObject<BlockBrightAir> BRIGHT_AIR = BLOCKS.register(BlockNames.brightAir, () -> new BlockBrightAir(Block.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().hardnessAndResistance(0.0F, 10000.0F).lightValue(15)));

	public static final RegistryObject<BlockBrightBreathableAir> BRIGHT_BREATHEABLE_AIR = BLOCKS.register(BlockNames.brightBreatheableAir, () -> new BlockBrightBreathableAir(Block.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().hardnessAndResistance(0.0F, 10000.0F).lightValue(15)));

	public static final RegistryObject<BlockBrightLamp> ARC_LAMP = BLOCKS.register(BlockNames.arcLamp, () -> new BlockBrightLamp(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.1F).sound(SoundType.METAL).lightValue(13)));

	public static final RegistryObject<BlockTier1TreasureChest> TREASURE_CHEST_T1 = BLOCKS.register(BlockNames.treasureChestTier1, () -> new BlockTier1TreasureChest(Block.Properties.create(Material.ROCK).hardnessAndResistance(100000.0F).sound(SoundType.STONE).lightValue(13)));

	public static final RegistryObject<PadBlock> ROCKET_PAD = BLOCKS.register(BlockNames.rocketPad, () -> new PadBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F, 10.0F).sound(SoundType.METAL)));

	public static final RegistryObject<PadBlock> BUGGY_PAD = BLOCKS.register(BlockNames.buggyPad, () -> new PadBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F, 10.0F).sound(SoundType.METAL)));

	public static final RegistryObject<PadFullBlock> LANDING_PAD_FULL = BLOCKS.register(BlockNames.rocketPadFull, () -> new PadFullBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F, 10.0F).sound(SoundType.METAL)));

	public static final RegistryObject<PadFullBlock> BUGGY_PAD_FULL = BLOCKS.register(BlockNames.buggyPadFull, () -> new PadFullBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F, 10.0F).sound(SoundType.METAL)));

	public static final RegistryObject<BlockUnlitTorch> UNLIT_TORCH = BLOCKS.register(BlockNames.unlitTorch, () -> new BlockUnlitTorch(false, Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(3).sound(SoundType.WOOD)));

	public static final RegistryObject<BlockUnlitTorch> UNLIT_TORCH_LIT = BLOCKS.register(BlockNames.unlitTorchLit, () -> new BlockUnlitTorch(true, Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(14).sound(SoundType.WOOD)));

	public static final RegistryObject<BlockGlowstoneTorch> GLOWSTONE_TORCH = BLOCKS.register(BlockNames.glowstoneTorch, () -> new BlockGlowstoneTorch(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(12).sound(SoundType.WOOD)));

	public static final RegistryObject<BlockOxygenCollector> OXYGEN_COLLECTOR = BLOCKS.register(BlockNames.oxygenCollector, () -> new BlockOxygenCollector(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));

	public static final RegistryObject<OxygenCompressorBlock> OXYGEN_COMPRESSOR = BLOCKS.register(BlockNames.oxygenCompressor, () -> new OxygenCompressorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));

	public static final RegistryObject<OxygenDecompressorBlock> OXYGEN_DECOMPRESSOR = BLOCKS.register(BlockNames.oxygenDecompressor, () -> new OxygenDecompressorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));

	public static final RegistryObject<CargoLoaderBlock> CARGO_LOADER = BLOCKS.register(BlockNames.cargoLoader, () -> new CargoLoaderBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<CargoUnloaderBlock> CARGO_UNLOADER = BLOCKS.register(BlockNames.cargoUnloader, () -> new CargoUnloaderBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<FuelLoaderBlock> FUEL_LOADER = BLOCKS.register(BlockNames.fuelLoader, () -> new FuelLoaderBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<NasaWorkbenchBlock> NASA_WORKBENCH = BLOCKS.register(BlockNames.nasaWorkbench, () -> new NasaWorkbenchBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<IngotCompressorBlock> INGOT_COMPRESSOR = BLOCKS.register(BlockNames.ingotCompressor, () -> new IngotCompressorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<IngotCompressorAdvancedBlock> INGOT_COMPRESSOR_ADVANCED = BLOCKS.register(BlockNames.ingotCompressorAdvanced, () -> new IngotCompressorAdvancedBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<CircuitFabricatorBlock> CIRCUT_FABRICATOR = BLOCKS.register(BlockNames.circuitFabricator, () -> new CircuitFabricatorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<DeconstructorBlock> DECONSTRUCTOR = BLOCKS.register(BlockNames.deconstructor, () -> new DeconstructorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockPainter> PAINTER = BLOCKS.register(BlockNames.painter, () -> new BlockPainter(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<BlockConcealedDetector> CONCEALED_DETECTOR = BLOCKS.register(BlockNames.concealedDetector, () -> new BlockConcealedDetector(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F).sound(SoundType.METAL)));
	
	public static final RegistryObject<FallenMeteorBlock> FALLEN_METEOR = BLOCKS.register(BlockNames.fallenMeteor, () -> new FallenMeteorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(40.0F).sound(SoundType.STONE)));

	public static final RegistryObject<BlockSpaceStationBase> SPACE_STATION = BLOCKS.register(BlockNames.spaceStationBase, () -> new BlockSpaceStationBase(Block.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).noDrops()));

	public static final RegistryObject<ParaChestBlock> PARA_CHEST = BLOCKS.register(BlockNames.parachest, () -> new ParaChestBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));

	public static final RegistryObject<CheeseBlock> CHEESE = BLOCKS.register(BlockNames.cheeseBlock, () -> new CheeseBlock(Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));

	public static final RegistryObject<BlockBossSpawner> BOSS_SPAWNER = BLOCKS.register(BlockNames.bossSpawner, () -> new BlockBossSpawner(Block.Properties.create(Material.ROCK).hardnessAndResistance(1000000.0F).noDrops()));

	public static final RegistryObject<BlockEmergencyBox> EMERGENCY_BOX = BLOCKS.register(BlockNames.emergencyBox, () -> new BlockEmergencyBox(Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 70.0F).lightValue(15).sound(SoundType.METAL)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_0 = BLOCKS.register(BlockNames.decoBlock0, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_1 = BLOCKS.register(BlockNames.decoBlock1, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_COPPER = BLOCKS.register(BlockNames.decoBlockCopper, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_TIN = BLOCKS.register(BlockNames.decoBlockTin, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_ALUMINIUM = BLOCKS.register(BlockNames.decoBlockAluminum, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_METEOR_IRON = BLOCKS.register(BlockNames.decoBlockMeteorIron, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<DecoBlock> DECO_BLOCK_SILICON = BLOCKS.register(BlockNames.decoBlockSilicon, () -> new DecoBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<OreBlock> COPPER_ORE = BLOCKS.register(BlockNames.copperOre, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<OreBlock> TIN_ORE = BLOCKS.register(BlockNames.tinOre, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<OreBlock> ALUMINIUM_ORE = BLOCKS.register(BlockNames.aluminumOre, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	
	public static final RegistryObject<OreBlock> SILICON_ORE = BLOCKS.register(BlockNames.siliconOre, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<OreBlock> METEORIC_IRON_ORE = BLOCKS.register(BlockNames.meteoricIronOre, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<Block> MOON_DIRT = BLOCKS.register(BlockNames.moonDirt, () -> new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<Block> MOON_STONE = BLOCKS.register(BlockNames.moonStone, () -> new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<Block> MOON_TURF = BLOCKS.register(BlockNames.moonTurf, () -> new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	
	public static final RegistryObject<OreBlock> COPPER_ORE_MOON = BLOCKS.register(BlockNames.copperOreMoon, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<OreBlock> TIN_ORE_MOON = BLOCKS.register(BlockNames.tinOreMoon, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

	public static final RegistryObject<OreBlock> SAPPHIRE_ORE = BLOCKS.register(BlockNames.sapphireOre, () -> new OreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
	
	public static final RegistryObject<Block> MOON_BRICK = BLOCKS.register(BlockNames.moonBrick, () -> new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));

    public static ArrayList<Block> otherModTorchesLit = new ArrayList<>();
    public static ArrayList<Block> otherModTorchesUnlit = new ArrayList<>();

    public static HashMap<Block, Block> itemChanges = new HashMap<>(4, 1.0F);

}
