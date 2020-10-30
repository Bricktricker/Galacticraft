package micdoodle8.mods.galacticraft.api.prefab.entity;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.advancement.GCTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public abstract class RocketEntity extends Entity implements IRocket {
	// TODO: maybe add an Enum DataSerializer
	protected static final DataParameter<Integer> PHASE = EntityDataManager.createKey(RocketEntity.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> LAUNCH_TIME = EntityDataManager.createKey(RocketEntity.class, DataSerializers.VARINT);

	protected float rollAmplitude;
	protected float shipDamage;
	protected int timeUntilLaunch;
	protected float rumble;

	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYaw;
	private double lerpPitch;

	private LazyOptional<IFluidHandler> fuelCap;
	protected FluidTank fuelTank;

	protected ItemStackHandler inventory;
	private LazyOptional<IItemHandlerModifiable> inventoryCap;

	private LazyOptional<IRocket> rocketCap;

	public RocketEntity(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.preventEntitySpawning = true;
		// this.ignoreFrustumCheck = true;
		this.timeUntilLaunch = this.getPreLaunchWait();

		this.fuelTank = new FluidTank(this.getFuelTankCapacity());
		this.fuelCap = LazyOptional.of(() -> this.fuelTank);

		this.inventory = new ItemStackHandler(this.getInventoryCapacity());
		this.inventoryCap = LazyOptional.of(() -> this.inventory);

		this.rocketCap = LazyOptional.of(() -> this);

		this.setMotion(Vec3d.ZERO);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity) {
		return null;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		// TODO: set a bounding box
		return null;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return this.isAlive();
	}

	@Override
	public boolean shouldRiderSit() {
		return false;
	}

	@Override
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getBoundingBox().getAverageEdgeLength();

		if(Double.isNaN(d0)) {
			d0 = 1.0D;
		}

		d0 = d0 * 64.0D * 5.0;
		return distance < d0 * d0;
	}

	@Override
	public void performHurtAnimation() {
		this.rollAmplitude = 5;
		this.shipDamage += this.shipDamage * 10;
	}

	@Override
	protected void registerData() {
		this.dataManager.register(PHASE, LaunchPhase.UNIGNITED.ordinal());
		this.dataManager.register(LAUNCH_TIME, 10);
	}

	@Override
	public void tick() {
		super.tick();

		for(Entity e : this.getPassengers()) {
			e.fallDistance = 0.0F;
		}

		// TODO: dynamically check exit hight
		if(this.getPosY() > 1200) {
			this.onReachAtmosphere();
		}

		if(this.rollAmplitude > 0) {
			this.rollAmplitude--;
		}

		if(this.shipDamage > 0) {
			this.shipDamage--;
		}

		if(!this.world.isRemote) {
			if(this.getPosY() < 0.0) {
				this.remove();
			}

//			if(this.timeSinceLaunch > 50 && this.onGround) {
//				this.failRocket();
//			}
		}

		if(this.timeUntilLaunch > 0 && this.getPhase() == LaunchPhase.IGNITED) {
			this.timeUntilLaunch--;
		}

		if(this.timeUntilLaunch == 0 && this.getPhase() == LaunchPhase.IGNITED) {
			this.setPhase(LaunchPhase.LAUNCHED);
			this.onLaunched();
		}

		if(this.rotationPitch > 90) {
			this.rotationPitch = 90;
		}

		if(this.rotationPitch < -90) {
			this.rotationPitch = -90;
		}

		this.tickLerp();
		this.updateMotion();

		this.move(MoverType.SELF, this.getMotion());

		if(this.rumble > 0) {
			this.rumble--;
		}else if(this.rumble < 0) {
			this.rumble++;
		}

		final double rumbleAmount = this.rumble / (double) (40 - 5 * Math.max(this.getRocketType().getRocketTier(), 5));
		for(Entity passenger : this.getPassengers()) {
			passenger.setPosition(passenger.getPosX() + rumbleAmount, passenger.getPosY(), passenger.getPosZ() + rumbleAmount);
		}

		if(this.getPhase().ordinal() >= LaunchPhase.IGNITED.ordinal()) {
			this.performHurtAnimation();
			this.rumble = (float) this.rand.nextInt(3) - 3;
		}

		// GalacticraftCore.LOGGER.debug("Rocket pos: {}, motion: {}, phase: {}",
		// this.getPositionVec(), this.getMotion(), this.getPhase());
	}

	@Override
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.lerpX = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.lerpYaw = (double) yaw;
		this.lerpPitch = (double) pitch;
		this.lerpSteps = 10;
	}

	private void tickLerp() {
		if(this.canPassengerSteer()) {
			this.lerpSteps = 0;
			this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
		}

		if(this.lerpSteps > 0) {
			double d0 = this.getPosX() + (this.lerpX - this.getPosX()) / (double) this.lerpSteps;
			double d1 = this.getPosY() + (this.lerpY - this.getPosY()) / (double) this.lerpSteps;
			double d2 = this.getPosZ() + (this.lerpZ - this.getPosZ()) / (double) this.lerpSteps;
			double d3 = MathHelper.wrapDegrees(this.lerpYaw - (double) this.rotationYaw);
			this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
			this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpPitch - (double) this.rotationPitch) / (double) this.lerpSteps);
			--this.lerpSteps;
			this.setPosition(d0, d1, d2);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}
	}

	private void updateMotion() {
		double d1 = this.hasNoGravity() ? 0.0D : (double) -0.04F;
		double momentum = 0.09F;

		Vec3d vec3d = this.getMotion();
		double motionUp = 0.0;
		if(this.getPhase() == LaunchPhase.LAUNCHED) {
			motionUp = this.getLiftPower();
		}else {
			motionUp = vec3d.y + d1;
		}
		
		double verticalMove = 0.0;
		if(this.isBeingRidden() && this.getPhase() == LaunchPhase.LAUNCHED) {
			LivingEntity livingentity = (LivingEntity)this.getPassengers().get(0);
			verticalMove = livingentity.moveForward * 0.05D;
			this.rotationYaw += livingentity.moveStrafing * 0.5D;
		}

		Vec3d newMotion = new Vec3d(vec3d.x * momentum, motionUp, vec3d.z * momentum);
		newMotion = newMotion.add(this.getForward().mul(verticalMove, 0, verticalMove));
		
		this.setMotion(newMotion);
		//this.setMotion(vec3d.x * (double) momentum, motionUp, vec3d.z * (double) momentum);

	}

	@Override
	public float getBrightness() {
		return 1;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if(!this.world.isRemote && this.isAlive()) {
			Entity e = par1DamageSource.getTrueSource();
			boolean isCreative = e instanceof PlayerEntity && ((PlayerEntity) e).abilities.isCreativeMode;
			if(this.isInvulnerableTo(par1DamageSource) || this.getPosY() > 300 || (e instanceof LivingEntity && !(e instanceof PlayerEntity))) {
				return false;
			}else {
				this.rollAmplitude = 10;
				this.markVelocityChanged();
				this.shipDamage += par2 * 10;

				if(e instanceof PlayerEntity && ((PlayerEntity) e).abilities.isCreativeMode) {
					this.shipDamage = 100;
				}

				if(isCreative || this.shipDamage > 90 && !this.world.isRemote) {
					this.removePassengers();

					if(isCreative) {
						this.remove();
					}else {
						this.remove();
						// this.dropShipAsItem();
					}
					return true;
				}

				return true;
			}
		}else {
			return true;
		}
	}

	@Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand) {
		if(hand != Hand.MAIN_HAND) {
			return false;
		}

		if(this.getPhase().ordinal() > LaunchPhase.IGNITED.ordinal()) {
			return false;
		}

		player.startRiding(this);
		return true;
	}
	
	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return entityIn instanceof PlayerEntity;
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		this.dataManager.set(PHASE, compound.getInt("phase"));
		this.dataManager.set(LAUNCH_TIME, compound.getInt("launchTime"));

		this.fuelTank.readFromNBT(compound.getCompound("fuel"));

		if(compound.contains("items", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND)) {
			this.inventory.deserializeNBT(compound.getCompound("items"));
		}
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putInt("phase", this.dataManager.get(PHASE));
		compound.putInt("launchTime", this.dataManager.get(LAUNCH_TIME));

		compound.put("fuel", this.fuelTank.writeToNBT(new CompoundNBT()));

		if(this.inventory.getSlots() > 0) {
			CompoundNBT invNBT = new CompoundNBT();
			this.inventory.deserializeNBT(invNBT);
			compound.put("items", invNBT);
		}
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public float getRollAmplitude() {
		return rollAmplitude;
	}

	public boolean isLaunched() {
		return this.getPhase().ordinal() >= LaunchPhase.LAUNCHED.ordinal();
	}

	public int getTimeUntilLaunch() {
		return this.timeUntilLaunch;
	}

	public void setPhase(LaunchPhase phase) {
		this.dataManager.set(PHASE, phase.ordinal());
	}

	public LaunchPhase getPhase() {
		return LaunchPhase.values()[this.dataManager.get(PHASE)];
	}

	public abstract int getFuelTankCapacity();

	public abstract int getInventoryCapacity();

	public abstract int getPreLaunchWait();

	public abstract float getLiftPower();

	protected void onReachAtmosphere() {
//		for(Entity e : this.getPassengers()) {
//			if(e instanceof ServerPlayerEntity) {
//				GCPlayerStats stats = GCPlayerStats.get(e);
//				if(stats.isUsingPlanetSelectionGui()) {
//					this.remove();
//				}
//			}else {
//				this.remove();
//			}
//		}
		this.remove();
	}

	public void onLaunched() {
		GalacticraftCore.LOGGER.info("RocketEntity: onLaunched");

		// would post a Event on FORGE_BUS
		if(getPassengers().size() >= 1) {
			if(getPassengers().get(0) instanceof ServerPlayerEntity) {
				GCTriggers.LAUNCH_ROCKET.trigger(((ServerPlayerEntity) getPassengers().get(0)));
			}
		}

		this.rocketCap.invalidate();
		this.rocketCap = null;
	}

	@Override
	public void ignite() {
		if(!this.world.isRemote && this.getPhase() == LaunchPhase.UNIGNITED) {
			this.setPhase(LaunchPhase.IGNITED);
			this.fuelCap.invalidate();
			this.fuelCap = null;
			GalacticraftCore.LOGGER.info("RocketEntity: ignite");
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && this.fuelCap != null) {
			return this.fuelCap.cast();
		}
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.inventory.getSlots() > 0) {
			return this.inventoryCap.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void onPadDestroyed() {
		// TODO: drop items
		this.remove();
		GalacticraftCore.LOGGER.info("RocketEntity: onPadDestroyed");
	}

	@Override
	public void remove(boolean keepData) {
		super.remove(keepData);
		if(!keepData) {
			if(this.fuelCap != null)
				this.fuelCap.invalidate();
			if(this.inventoryCap != null)
				this.inventoryCap.invalidate();
			if(this.rocketCap != null)
				this.rocketCap.invalidate();	
		}
		GalacticraftCore.LOGGER.info("RocketEntity: remove");
	}

	public enum LaunchPhase {
		UNIGNITED, IGNITED, LAUNCHED, LANDING
	}

}
