package micdoodle8.mods.galacticraft.core.client;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.fx.EntityParticleData;
import micdoodle8.mods.galacticraft.core.client.fx.ParticleTypeNames;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GCParticles {
	
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Constants.MOD_ID_CORE);
	
	public static final RegistryObject<BasicParticleType> WHITE_SMOKE_IDLE = PARTICLES.register(ParticleTypeNames.whiteSmoke, () -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> WHITE_SMOKE_LAUNCHED = PARTICLES.register(ParticleTypeNames.whiteSmokeLaunched, () -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> WHITE_SMOKE_IDLE_LARGE = PARTICLES.register(ParticleTypeNames.whiteSmokeLarge, () -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> WHITE_SMOKE_LAUNCHED_LARGE = PARTICLES.register(ParticleTypeNames.whiteSmokeLaunchedLarge, () -> new BasicParticleType(false));
	
	public static final RegistryObject<ParticleType<EntityParticleData>> LAUNCH_FLAME_LAUNCHED = PARTICLES.register(ParticleTypeNames.launchFlameLaunched, () -> new ParticleType<EntityParticleData>(false, EntityParticleData.DESERIALIZER));
	
	public static final RegistryObject<ParticleType<EntityParticleData>> LANDER_FLAME = PARTICLES.register(ParticleTypeNames.landerFlame, () -> new ParticleType<EntityParticleData>(false, EntityParticleData.DESERIALIZER));
	
	public static final RegistryObject<BasicParticleType> LAUNCH_SMOKE = PARTICLES.register("launch_smoke", () -> new BasicParticleType(false));
	
	
	@Deprecated
    public static BasicParticleType OIL_DRIP;

	@Deprecated
    public static BasicParticleType OXYGEN;
	
	
}
