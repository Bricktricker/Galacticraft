package micdoodle8.mods.galacticraft.core;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.client.IGameScreen;
import micdoodle8.mods.galacticraft.api.galaxies.*;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.AtmosphereInfo;
import micdoodle8.mods.galacticraft.api.world.BiomeGC;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.core.advancement.GCTriggers;
import micdoodle8.mods.galacticraft.core.client.GCParticles;
import micdoodle8.mods.galacticraft.core.client.fx.LaunchSmoke;
import micdoodle8.mods.galacticraft.core.client.gui.container.CargoLoaderScreen;
import micdoodle8.mods.galacticraft.core.client.gui.container.CargoUnloaderScreen;
import micdoodle8.mods.galacticraft.core.client.gui.container.CircuitFabricatorScreen;
import micdoodle8.mods.galacticraft.core.client.gui.container.DeconstructorScreen;
import micdoodle8.mods.galacticraft.core.client.gui.container.ElectricIngotCompressorScreen;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiBuggy;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiExtendedInventory;
import micdoodle8.mods.galacticraft.core.client.gui.container.FuelLoaderScreen;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiPainter;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiRocketInventory;
import micdoodle8.mods.galacticraft.core.client.gui.container.OxygenCollectorScreen;
import micdoodle8.mods.galacticraft.core.client.gui.container.OxygenCompressorScreen;
import micdoodle8.mods.galacticraft.core.client.gui.container.OxygenDecompressorScreen;
import micdoodle8.mods.galacticraft.core.client.gui.container.ParaChestScreen;
import micdoodle8.mods.galacticraft.core.client.render.entities.RenderTier1Rocket;
import micdoodle8.mods.galacticraft.core.client.screen.GameScreenBasic;
import micdoodle8.mods.galacticraft.core.client.screen.GameScreenCelestial;
import micdoodle8.mods.galacticraft.core.client.screen.GameScreenText;
import micdoodle8.mods.galacticraft.core.dimension.DimensionMoon;
import micdoodle8.mods.galacticraft.core.dimension.TeleportTypeMoon;
import micdoodle8.mods.galacticraft.core.dimension.TeleportTypeOverworld;
import micdoodle8.mods.galacticraft.core.entities.GCEntities;
import micdoodle8.mods.galacticraft.core.entities.player.GCCapabilities;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler;
import micdoodle8.mods.galacticraft.core.event.EventHandlerGC;
import micdoodle8.mods.galacticraft.core.fluid.GCFluids;
import micdoodle8.mods.galacticraft.core.inventory.GCContainers;
import micdoodle8.mods.galacticraft.core.items.ItemSchematic;
import micdoodle8.mods.galacticraft.core.network.GalacticraftChannelHandler;
import micdoodle8.mods.galacticraft.core.networking.NetworkHandler;
import micdoodle8.mods.galacticraft.core.proxy.CommonProxyCore;
import micdoodle8.mods.galacticraft.core.schematic.SchematicAdd;
import micdoodle8.mods.galacticraft.core.schematic.SchematicMoonBuggy;
import micdoodle8.mods.galacticraft.core.schematic.SchematicRocketT1;
import micdoodle8.mods.galacticraft.core.tick.TickHandlerServer;
import micdoodle8.mods.galacticraft.core.util.*;
import micdoodle8.mods.galacticraft.core.world.gen.BiomeMoon;
import micdoodle8.mods.galacticraft.core.world.gen.BiomeOrbit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.EntityClassification;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

@Mod(Constants.MOD_ID_CORE)
public class GalacticraftCore
{
    public static final String NAME = "Galacticraft Core";
    
    public static final Logger LOGGER = LogManager.getLogger();

    public static CommonProxyCore proxy = DistExecutor.safeRunForDist(GalacticraftCore::getClientProxy, () -> CommonProxyCore::new);

    @OnlyIn(Dist.CLIENT)
    private static DistExecutor.SafeSupplier<CommonProxyCore> getClientProxy()
    {
        //NOTE: This extra method is needed to avoid classloading issues on servers
        return CommonProxyCore::new;
    }

    public static GalacticraftCore instance;

    @Deprecated
    public static boolean isPlanetsLoaded;
    @Deprecated
    public static boolean isHeightConflictingModInstalled;

    public static GalacticraftChannelHandler packetPipeline = new GalacticraftChannelHandler();
    public static GCPlayerHandler handler;

    public static ItemGroupGC galacticraftBlocksTab;
    public static ItemGroupGC galacticraftItemsTab;
    
    @Deprecated //all below
    public static SolarSystem solarSystemSol;
    public static Planet planetMercury;
    public static Planet planetVenus;
    public static Planet planetMars;  //Used only if GCPlanets not loaded
    public static Planet planetOverworld;
    public static Planet planetJupiter;
    public static Planet planetSaturn;
    public static Planet planetUranus;
    public static Planet planetNeptune;
    public static Moon moonMoon;
    public static Satellite satelliteSpaceStation;

    public static LinkedList<BiomeGC> biomesList = new LinkedList<>();

    public static ImageWriter jpgWriter; //Allows you to save the maps to view them later
    public static ImageWriteParam writeParam;
    public static boolean enableJPEG = false;

    public final ArtifactVersion versionNumber;

    public GalacticraftCore()
    {
        versionNumber = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion();
        handler = new GCPlayerHandler();
        
        MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        WorldUtil.DIMENSIONS.register(modEventBus);
        GCEntities.ENTITIES.register(modEventBus);
        GCBlocks.BLOCKS.register(modEventBus);
        GCItems.ITEMS.register(modEventBus);
        GCParticles.PARTICLES.register(modEventBus);
        GCContainers.CONTAINER.register(modEventBus);
        GCTileEntities.TILE_ENTITIES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(handler);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigManagerCore.COMMON_SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().register(ConfigManagerCore.class);

        GalacticraftCore.galacticraftBlocksTab = new ItemGroupGC(-1, "gc_blocks", () -> new ItemStack(GCBlocks.OXYGEN_COMPRESSOR.get()));
        GalacticraftCore.galacticraftItemsTab = new ItemGroupGC(-1, "gc_items", () -> new ItemStack(GCItems.ROCKET_T1.get()));
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event)
    {
        GCCapabilities.register();
        NetworkHandler.registerPackets();

        isPlanetsLoaded = ModList.get().isLoaded(Constants.MOD_ID_PLANETS);

        MinecraftForge.EVENT_BUS.register(new EventHandlerGC());

        GalacticraftCore.solarSystemSol = new SolarSystem("sol", "milky_way").setMapPosition(new Vector3(0.0F, 0.0F, 0.0F));
        GalacticraftCore.planetOverworld = (Planet) new Planet("overworld").setParentSolarSystem(GalacticraftCore.solarSystemSol).setRingColorRGB(0.1F, 0.9F, 0.6F).setPhaseShift(0.0F);
        GalacticraftCore.moonMoon = (Moon) new Moon("moon").setParentPlanet(GalacticraftCore.planetOverworld).setRelativeSize(0.2667F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(13F, 13F)).setRelativeOrbitTime(1 / 0.01F);
        GalacticraftCore.satelliteSpaceStation = (Satellite) new Satellite("spacestation.overworld").setParentBody(GalacticraftCore.planetOverworld).setRelativeSize(0.2667F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(9F, 9F)).setRelativeOrbitTime(1 / 0.05F);

        GalacticraftCore.satelliteSpaceStation.setBiomeInfo(BiomeOrbit.space);
        GalacticraftCore.moonMoon.setBiomeInfo(BiomeMoon.moonBiome);

        GalacticraftCore.proxy.init();

        Star starSol = (Star) new Star("sol").setParentSolarSystem(GalacticraftCore.solarSystemSol).setTierRequired(-1);
        starSol.setBodyIcon(new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/celestialbodies/sun.png"));
        GalacticraftCore.solarSystemSol.setMainStar(starSol);

        GalacticraftCore.planetOverworld.setBodyIcon(new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/celestialbodies/earth.png"));
        GalacticraftCore.planetOverworld.setDimensionInfo(DimensionType.OVERWORLD, OverworldDimension.class, false).setTierRequired(1);
        GalacticraftCore.planetOverworld.atmosphereComponent(EnumAtmosphericGas.NITROGEN).atmosphereComponent(EnumAtmosphericGas.OXYGEN).atmosphereComponent(EnumAtmosphericGas.ARGON).atmosphereComponent(EnumAtmosphericGas.WATER);
        GalacticraftCore.planetOverworld.addChecklistKeys("equip_parachute");

        GalacticraftCore.moonMoon.setDimensionInfo(WorldUtil.MOON_DIMENSION, DimensionMoon.class).setTierRequired(1);
        GalacticraftCore.moonMoon.setBodyIcon(new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/celestialbodies/moon.png"));
        GalacticraftCore.moonMoon.setAtmosphere(new AtmosphereInfo(false, false, false, 0.0F, 0.0F, 0.0F));
        GalacticraftCore.moonMoon.addMobInfo(new SpawnListEntry(GCEntities.EVOLVED_ZOMBIE.get(), 8, 2, 3), EntityClassification.MONSTER);
        GalacticraftCore.moonMoon.addMobInfo(new SpawnListEntry(GCEntities.EVOLVED_SPIDER.get(), 8, 2, 3), EntityClassification.MONSTER);
        GalacticraftCore.moonMoon.addMobInfo(new SpawnListEntry(GCEntities.EVOLVED_SKELETON.get(), 8, 2, 3), EntityClassification.MONSTER);
        GalacticraftCore.moonMoon.addMobInfo(new SpawnListEntry(GCEntities.EVOLVED_CREEPER.get(), 8, 2, 3), EntityClassification.MONSTER);
        GalacticraftCore.moonMoon.addMobInfo(new SpawnListEntry(GCEntities.EVOLVED_ENDERMAN.get(), 10, 1, 4), EntityClassification.MONSTER);
        GalacticraftCore.moonMoon.addChecklistKeys("equip_oxygen_suit");

        SchematicRegistry.registerSchematicRecipe(new SchematicRocketT1());
        SchematicRegistry.registerSchematicRecipe(new SchematicMoonBuggy());
        SchematicRegistry.registerSchematicRecipe(new SchematicAdd());

        GalaxyRegistry.registerSolarSystem(GalacticraftCore.solarSystemSol);
        GalaxyRegistry.registerPlanet(GalacticraftCore.planetOverworld);
        GalaxyRegistry.registerMoon(GalacticraftCore.moonMoon);
        GalaxyRegistry.registerSatellite(GalacticraftCore.satelliteSpaceStation);
        GalacticraftRegistry.registerTeleportType(OverworldDimension.class, new TeleportTypeOverworld());
//        GalacticraftRegistry.registerTeleportType(DimensionOverworldOrbit.class, new TeleportTypeOrbit());
        GalacticraftRegistry.registerTeleportType(DimensionMoon.class, new TeleportTypeMoon());
//        GalacticraftRegistry.registerRocketGui(DimensionOverworldOrbit.class, new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/overworld_rocket_gui.png"));
//        GalacticraftRegistry.registerRocketGui(WorldProviderSurface.class, new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/overworld_rocket_gui.png"));
//        GalacticraftRegistry.registerRocketGui(DimensionMoon.class, new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/moon_rocket_gui.png"));

        registerCoreGameScreens();

        //TODO: register gear stuff?

        GalacticraftCore.proxy.registerFluidTexture(GCFluids.OIL.getFluid(), new ResourceLocation(Constants.MOD_ID_CORE, "textures/misc/underoil.png"));
        GalacticraftCore.proxy.registerFluidTexture(GCFluids.FUEL.getFluid(), new ResourceLocation(Constants.MOD_ID_CORE, "textures/misc/underfuel.png"));

        PermissionAPI.registerNode(Constants.PERMISSION_CREATE_STATION, DefaultPermissionLevel.ALL, "Allows players to create space stations");

        GCTriggers.registerTriggers();

        //TODO: move this to a forge registry
        GalacticraftCore.planetMercury = makeDummyPlanet("mercury", GalacticraftCore.solarSystemSol);
        if (GalacticraftCore.planetMercury != null)
        {
            GalacticraftCore.planetMercury.setRingColorRGB(0.1F, 0.9F, 0.6F).setPhaseShift(1.45F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(0.5F, 0.5F)).setRelativeOrbitTime(0.24096385542168674698795180722892F);
        }
        GalacticraftCore.planetVenus = makeDummyPlanet("venus", GalacticraftCore.solarSystemSol);
        if (GalacticraftCore.planetVenus != null)
        {
            GalacticraftCore.planetVenus.setRingColorRGB(0.1F, 0.9F, 0.6F).setPhaseShift(2.0F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(0.75F, 0.75F)).setRelativeOrbitTime(0.61527929901423877327491785323111F);
        }
        GalacticraftCore.planetMars = makeDummyPlanet("mars", GalacticraftCore.solarSystemSol);
        if (GalacticraftCore.planetMars != null)
        {
            GalacticraftCore.planetMars.setRingColorRGB(0.67F, 0.1F, 0.1F).setPhaseShift(0.1667F).setRelativeSize(0.5319F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.25F, 1.25F)).setRelativeOrbitTime(1.8811610076670317634173055859803F);
        }
        GalacticraftCore.planetJupiter = makeDummyPlanet("jupiter", GalacticraftCore.solarSystemSol);
        if (GalacticraftCore.planetJupiter != null)
        {
            GalacticraftCore.planetJupiter.setRingColorRGB(0.1F, 0.9F, 0.6F).setPhaseShift((float) Math.PI).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.5F, 1.5F)).setRelativeOrbitTime(11.861993428258488499452354874042F);
        }
        GalacticraftCore.planetSaturn = makeDummyPlanet("saturn", GalacticraftCore.solarSystemSol);
        if (GalacticraftCore.planetSaturn != null)
        {
            GalacticraftCore.planetSaturn.setRingColorRGB(0.1F, 0.9F, 0.6F).setPhaseShift(5.45F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.75F, 1.75F)).setRelativeOrbitTime(29.463307776560788608981380065717F);
        }
        GalacticraftCore.planetUranus = makeDummyPlanet("uranus", GalacticraftCore.solarSystemSol);
        if (GalacticraftCore.planetUranus != null)
        {
            GalacticraftCore.planetUranus.setRingColorRGB(0.1F, 0.9F, 0.6F).setPhaseShift(1.38F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(2.0F, 2.0F)).setRelativeOrbitTime(84.063526834611171960569550930997F);
        }
        GalacticraftCore.planetNeptune = makeDummyPlanet("neptune", GalacticraftCore.solarSystemSol);
        if (GalacticraftCore.planetNeptune != null)
        {
            GalacticraftCore.planetNeptune.setRingColorRGB(0.1F, 0.9F, 0.6F).setPhaseShift(1.0F).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(2.25F, 2.25F)).setRelativeOrbitTime(164.84118291347207009857612267251F);
        }

//        GCDimensions.MOON = WorldUtil.getDimensionTypeById(ConfigManagerCore.INSTANCE.idDimensionMoon);

        CompatibilityManager.checkForCompatibleMods();
        ItemSchematic.registerSchematicItems();
        MinecraftForge.EVENT_BUS.register(new TickHandlerServer());
        GalaxyRegistry.refreshGalaxies();

        GalacticraftRegistry.registerScreen(new GameScreenText());  //Screen API demo
        //Note: add-ons can register their own screens in postInit by calling GalacticraftRegistry.registerScreen(IGameScreen) like this.
        //[Called on both client and server: do not include any client-specific code in the new game screen's constructor method.]

        try
        {
            jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
            writeParam = jpgWriter.getDefaultWriteParam();
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionQuality(1.0f);
            enableJPEG = true;
        }
        catch (UnsatisfiedLinkError e)
        {
            GCLog.severe("Error initialising JPEG compressor - this is likely caused by OpenJDK - see https://wiki.micdoodle8.com/wiki/Compatibility#For_clients_running_OpenJDK");
            e.printStackTrace();
        }
        
    }

	@SubscribeEvent
	public void clientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(GCEntities.ROCKET_T1.get(), RenderTier1Rocket::new);
		
		ScreenManager.registerFactory(GCContainers.BUGGY.get(), GuiBuggy::new);
		ScreenManager.registerFactory(GCContainers.CARGO_LOADER.get(), CargoLoaderScreen::new);
		ScreenManager.registerFactory(GCContainers.CARGO_UNLOADER.get(), CargoUnloaderScreen::new);
        ScreenManager.registerFactory(GCContainers.CIRCUT_FABIRCATOR.get(), CircuitFabricatorScreen::new);
        ScreenManager.registerFactory(GCContainers.DECONSTRUCTOR.get(), DeconstructorScreen::new);
        ScreenManager.registerFactory(GCContainers.INGOT_COMPRESSOR.get(), ElectricIngotCompressorScreen::new);
        ScreenManager.registerFactory(GCContainers.EXTENDED_INVENTORY.get(), GuiExtendedInventory::new);
        ScreenManager.registerFactory(GCContainers.FUEL_LAODER.get(), FuelLoaderScreen::new);
        ScreenManager.registerFactory(GCContainers.OXYGEN_COLLECTOR.get(), OxygenCollectorScreen::new);
        ScreenManager.registerFactory(GCContainers.OXYGEN_COMPRESSOR.get(), OxygenCompressorScreen::new);
        ScreenManager.registerFactory(GCContainers.OXYGEN_DECOMPRESSOR.get(), OxygenDecompressorScreen::new);
        ScreenManager.registerFactory(GCContainers.PAINTER.get(), GuiPainter::new);
        ScreenManager.registerFactory(GCContainers.PARACHEST.get(), ParaChestScreen::new);
        ScreenManager.registerFactory(GCContainers.ROCKET_INVENTORY.get(), GuiRocketInventory::new);
	}
    
    @SubscribeEvent
    public void registerParticleFactories(ParticleFactoryRegisterEvent event) {
    	Minecraft.getInstance().particles.registerFactory(GCParticles.LAUNCH_SMOKE.get(), LaunchSmoke.Factory::new);
    }

    @SubscribeEvent
    public void serverAboutToStart(FMLServerAboutToStartEvent event)
    {
        TickHandlerServer.restart();
    }

    @SubscribeEvent
    public void serverInit(FMLServerStartedEvent event)
    {

        ThreadVersionCheck.startCheck();
        BlockVec3.chunkCacheDim = null;
    }

    private void serverStarting(FMLServerStartingEvent event)
    {
        GCCoreUtil.notifyStarted(event.getServer());
        
        //TODO: register commands
        
        WorldUtil.registerSpaceStations(event.getServer(), new File(event.getServer().getWorld(DimensionType.OVERWORLD).getSaveHandler().getPlayerFolder(), "galacticraft"));

        ArrayList<CelestialBody> cBodyList = new ArrayList<CelestialBody>();
        cBodyList.addAll(GalaxyRegistry.getRegisteredPlanets().values());
        cBodyList.addAll(GalaxyRegistry.getRegisteredMoons().values());
    }

    private static void registerCoreGameScreens()
    {
        if (GCCoreUtil.getEffectiveSide() == LogicalSide.CLIENT)
        {
            IGameScreen rendererBasic = new GameScreenBasic();
            IGameScreen rendererCelest = new GameScreenCelestial();
            GalacticraftRegistry.registerScreen(rendererBasic);  //Type 0 - blank
            GalacticraftRegistry.registerScreen(rendererBasic);  //Type 1 - local satellite view
            GalacticraftRegistry.registerScreen(rendererCelest);  //Type 2 - solar system
            GalacticraftRegistry.registerScreen(rendererCelest);  //Type 3 - local planet
            GalacticraftRegistry.registerScreen(rendererCelest);  //Type 4 - render test
        }
        else
        {
            GalacticraftRegistry.registerScreensServer(5);
        }
    }

    @Deprecated //WHY, was this needed?
    private Planet makeDummyPlanet(String name, SolarSystem system)
    {
        // Loop through all planets to make sure it's not registered as a reachable dimension first
        for (CelestialBody body : new ArrayList<>(GalaxyRegistry.getRegisteredPlanets().values()))
        {
            if (body instanceof Planet && name.equals(body.getName()))
            {
                if (((Planet) body).getParentSolarSystem() == system)
                {
                    return null;
                }
            }
        }

        Planet planet = new Planet(name).setParentSolarSystem(system);
        planet.setBodyIcon(new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/celestialbodies/" + name + ".png"));
        GalaxyRegistry.registerPlanet(planet);
        return planet;
    }

    public static ResourceLocation rl(String path)
    {
        return new ResourceLocation(Constants.MOD_ID_CORE, path);
    }

    //TODO: remove in 1.16
    @SubscribeEvent
    public void registerBiomes(RegistryEvent.Register<Biome> event)
    {
        // First, final steps of item registration
        GalacticraftCore.handler.registerTorchTypes();
        GalacticraftCore.handler.registerItemChanges();

        for (BiomeGC biome : GalacticraftCore.biomesList)
        {
            event.getRegistry().register(biome);
            if (!ConfigManagerCore.INSTANCE.disableBiomeTypeRegistrations.get())
            {
                biome.registerTypes(biome);
            }
        }


    }
}
