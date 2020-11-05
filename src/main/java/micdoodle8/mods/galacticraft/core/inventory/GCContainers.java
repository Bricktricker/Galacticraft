package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.entities.EntityBuggy;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GCContainers {
	
	public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, Constants.MOD_ID_CORE);
	
	public static final RegistryObject<ContainerType<ContainerBuggy>> BUGGY = CONTAINER.register(GCContainerNames.BUGGY, () -> IForgeContainerType.create((windowId, inv, data) -> new ContainerBuggy(windowId, inv, EntityBuggy.BuggyType.byId(data.readInt()))));
	
	public static final RegistryObject<ContainerType<CargoLoaderContainer>> CARGO_LOADER = CONTAINER.register(GCContainerNames.CARGO_LOADER, () -> IForgeContainerType.create(CargoLoaderContainer::new));
	
	public static final RegistryObject<ContainerType<CargoUnloaderContainer>> CARGO_UNLOADER = CONTAINER.register(GCContainerNames.CARGO_UNLOADER, () -> IForgeContainerType.create(CargoUnloaderContainer::new));
	
	public static final RegistryObject<ContainerType<CircuitFabricatorContainer>> CIRCUT_FABIRCATOR = CONTAINER.register(GCContainerNames.CIRCUIT_FABRICATOR, () -> IForgeContainerType.create(CircuitFabricatorContainer::new));
	
	public static final RegistryObject<ContainerType<DeconstructorContainer>> DECONSTRUCTOR = CONTAINER.register(GCContainerNames.DECONSTRUCTOR, () -> IForgeContainerType.create(DeconstructorContainer::new));
	
	public static final RegistryObject<ContainerType<ElectricIngotCompressorContainer>> INGOT_COMPRESSOR = CONTAINER.register(GCContainerNames.ELECTRIC_INGOT_COMPRESSOR, () -> IForgeContainerType.create(ElectricIngotCompressorContainer::new));
	
	public static final RegistryObject<ContainerType<ContainerExtendedInventory>> EXTENDED_INVENTORY = CONTAINER.register(GCContainerNames.EXTENDED_INVENTORY, () -> IForgeContainerType.create((windowId, inv, data) ->
	    {
	        GCPlayerStats stats = GCPlayerStats.get(inv.player);
	        return new ContainerExtendedInventory(windowId, inv, stats.getExtendedInventory());
	    }));
	
	public static final RegistryObject<ContainerType<FuelLoaderContainer>> FUEL_LAODER = CONTAINER.register(GCContainerNames.FUEL_LOADER, () -> IForgeContainerType.create(FuelLoaderContainer::new));
	
	public static final RegistryObject<ContainerType<OxygenCollectorContainer>> OXYGEN_COLLECTOR = CONTAINER.register(GCContainerNames.OXYGEN_COLLECTOR, () -> IForgeContainerType.create(OxygenCollectorContainer::new));
	
	public static final RegistryObject<ContainerType<OxygenCompressorContainer>> OXYGEN_COMPRESSOR = CONTAINER.register(GCContainerNames.OXYGEN_COMPRESSOR, () -> IForgeContainerType.create(OxygenCompressorContainer::new));
	
	public static final RegistryObject<ContainerType<OxygenDecompressorContainer>> OXYGEN_DECOMPRESSOR = CONTAINER.register(GCContainerNames.OXYGEN_DECOMPRESSOR, () -> IForgeContainerType.create(OxygenDecompressorContainer::new));
	
	public static final RegistryObject<ContainerType<ContainerPainter>> PAINTER = CONTAINER.register(GCContainerNames.PAINTER, () -> IForgeContainerType.create(ContainerPainter::new));
	
	public static final RegistryObject<ContainerType<ParaChestContainer>> PARACHEST = CONTAINER.register(GCContainerNames.PARACHEST, () -> IForgeContainerType.create(ParaChestContainer::new));
	
	public static final RegistryObject<ContainerType<ContainerRocketInventory>> ROCKET_INVENTORY = CONTAINER.register(GCContainerNames.ROCKET_INVENTORY, () -> IForgeContainerType.create((windowId, inv, data) -> new ContainerRocketInventory(windowId, inv, (EntityTieredRocket) inv.player.getRidingEntity())));

	public static final RegistryObject<ContainerType<NasaWorkbenchContainer>> NASA_WORKBENCH = CONTAINER.register(GCContainerNames.NASA_WORKBENCH, () -> IForgeContainerType.create(NasaWorkbenchContainer::new));

}
